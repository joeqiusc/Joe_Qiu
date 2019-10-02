import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { Principal } from '../models/principal';
import { environment as env } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  currentUser$: Observable<Principal>;
  private currentUserSubject: BehaviorSubject<Principal>;
 
  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<Principal>(null);
    this.currentUser$ = this.currentUserSubject.asObservable();
  }

  get currentUserValue(): Principal{
    return this.currentUserSubject.value;
  }

  authenticate = (username: string, password: string) => {

    let creds = {username, password};
    //here call the tomcat server then connect to java part class, then talk to database
    return this.http.post(`${env.API_URL}/auth`, creds, {observe: 'response'}).pipe(
      map(resp => {
        let principal = resp.body as Principal;
        principal.jwt = resp.headers.get('Authorization');
        console.log(principal);
        localStorage.setItem('rbs-jwt', resp.headers.get('Authorization'));
        this.currentUserSubject.next(principal);

      })
    ); 
  }

  logout = () =>{
    localStorage.removeItem('rbs-jwt');
    this.currentUserSubject.next(null);
  }
}

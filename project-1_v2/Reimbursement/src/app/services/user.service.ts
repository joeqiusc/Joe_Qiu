import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { Principal } from '../models/principal';
import { environment as env } from '../../environments/environment';

import { User } from 'src/app/models/user';
@Injectable({
  providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) { 
      
    }

    getAll() {
        return this.http.get<User[]>(`${env.API_URL}/users`);
    }

    register(user: User) {
        return this.http.post(`${env.API_URL}/users/register`, user);
    }

    delete(id: number) {
        return this.http.delete(`${env.API_URL}}/users/${id}`);
    }
}
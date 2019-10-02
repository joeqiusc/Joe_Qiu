import { Component, OnDestroy } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service'
import { Router } from '@angular/router';
import { Pricipal } from 'src/app/models/pricipal';
import { Subscription } from 'rxjs';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnDestroy {

  currentUser: Pricipal;
  currentUserSub: Subscription;

  constructor(private authService: AuthService, private router: Router) { 
    this.currentUserSub = this.authService.currentUser$.subscribe(user =>{
      this.currentUser = user;
    })
  }

  ngOnDestroy(){
    this.currentUserSub.unsubscribe();
  }

  logout = () =>{
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}

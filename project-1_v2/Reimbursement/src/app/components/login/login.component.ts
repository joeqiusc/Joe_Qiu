import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;
  submitted = false;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
    console.log('LoginComponent instantiating...');
    console.log('LoginComponent instantiation complete.');
  }

  ngOnInit() {
    console.log('Initializing values for LoginComponent...');
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    console.log('LoginComponent value initialization complete.')
  }

  get fields() {
    return this.loginForm.controls;
  }

  onSubmit = () => {
    this.submitted = true;
    if(this.loginForm.invalid) return;
    this.authService.authenticate(this.fields.username.value, this.fields.password.value)
    .subscribe(
      () =>{  
        this.loading = false;
        console.log('Login successful!');
        console.log('Navigating to dashboard....');
        this.router.navigate(['/dashboard']);
      },
      err => {
        console.log(err);
        this.loading = false;
        this.submitted = false;
      }
    );
      
  }

}

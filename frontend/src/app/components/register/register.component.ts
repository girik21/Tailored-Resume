import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';

import { AuthService } from '../../shared/auth.service';
import { ResponseWrapper } from '../../utils/response-wrapper';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {  
  
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  form = {
    
    email:'',
    password:'',
    confirmPassword:'',
    
  }

  constructor(private auth: AuthService) {}

  ngOnInit(): void {}

  onSubmit(): void {    
    
    this.auth.register(this.form.email, this.form.password).subscribe({
      next: (response: ResponseWrapper<any>) => {
        this.isSuccessful = response.success;
        this.isSignUpFailed = !response.success;
        this.errorMessage = response.message;
      },
      error: (error) => {
        console.error(error);
        this.isSuccessful = false;
        this.isSignUpFailed = true;
        this.errorMessage = 'An error occurred during registration. Please try again.';
      }
    });
  }

  onReset(form: NgForm): void {
    form.onReset()
  }
}

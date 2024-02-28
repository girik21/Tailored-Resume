import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/auth.service';
import { user } from '@angular/fire/auth';
import { Subscription } from 'rxjs';
import { ResponseWrapper } from '../../utils/response-wrapper';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  private subscription: Subscription | undefined;
  email: string = '';
  password: string = '';
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  form: any = {
    
    email: null,
    password: null,
  };

  constructor(private auth: AuthService) {}

  ngOnInit(): void {}

  register() {
    if (this.email == '') {
      alert('Please enter email');
      return;
    }

    if (this.password == '') {
      alert('Please enter password');
      return;
    }
  

    this.auth.register(this.email, this.password).subscribe(
      (response: ResponseWrapper<any>) => {
        this.isSuccessful = response.success;
        this.isSignUpFailed = !response.success;
        this.errorMessage = response.message;
      },
      (error) => {
        console.error(error);
        this.isSuccessful = false;
        this.isSignUpFailed = true;
        this.errorMessage =
          'An error occurred during registration. Please try again.';
      }
    );
    this.email = '';
    this.password = '';
  }

  onSubmit(): void {
    const { email, password } = this.form;
    
    this.auth.register(this.email, this.password).subscribe(
      (response: ResponseWrapper<any>) => {
        this.isSuccessful = response.success;
        this.isSignUpFailed = !response.success;
        this.errorMessage = response.message;
      },
      (error) => {
        console.error(error);
        this.isSuccessful = false;
        this.isSignUpFailed = true;
        this.errorMessage =
          'An error occurred during registration. Please try again.';
      }
    );
  }
}

import { Component } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Router } from '@angular/router';
import { FirebaseError } from '@angular/fire/app';

@Component({
  selector: 'ngx-register',
  templateUrl: './register.component.html',
})
export class NgxRegisterComponent {
  user = {
    email: '',
    password: '',
    confirmPassword: ''
  };
  submitted = false;
  errors: string[] = [];
  messages: string[] = [];
  showMessages = {
    error: false,
    success: false
  };

  constructor(private fireAuth: AngularFireAuth, private router: Router) {}

  async register() {
    this.submitted = true;
    try {
      this.errors = []; // Reset errors
      this.messages = []; // Reset messages
      
      if (this.user.password !== this.user.confirmPassword) {
        throw new Error('Password does not match the confirm password.');
      }

      await this.fireAuth.createUserWithEmailAndPassword(this.user.email, this.user.password);
      // Registration successful, redirect to login page or dashboard
      this.messages.push('Registration successful. Redirecting to login...');
      this.showMessages.success = true;
      setTimeout(() => {
        this.router.navigate(['auth/login'], { queryParams: { registered: true } });
      }, 2000); // Redirect after 2 seconds
    } catch (error) {
      // Handle registration errors
      console.error('Error registering user:', error);
      if (error instanceof FirebaseError) {
        // Firebase authentication error handling
        switch (error.code) {
          case 'auth/email-already-in-use':
            this.errors.push('Email is already in use.');
            break;
          case 'auth/weak-password':
            this.errors.push('Password is too weak.');
            break;
          // Add more cases for other Firebase authentication error codes as needed
          default:
            this.errors.push('An error occurred during registration. Please try again later.');
            break;
        }
      } else {
        this.errors.push('An error occurred. Please try again later.');
      }
      this.showMessages.error = true;
    }
    this.submitted = false;
  }

  getConfigValue(key: string): any {
    // Implement this method based on your configuration retrieval logic
  }
}

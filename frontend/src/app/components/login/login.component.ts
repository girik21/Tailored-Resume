import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../shared/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email: string = '';
  password: string = '';
  loginError: string = '';
  logoutMessage: string = '';

  constructor(private authService: AuthService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    // Check if logout status query parameter is present
    this.route.queryParams.subscribe(params => {
      if (params['logout']) {
        this.logoutMessage = 'User logged out successfully!!';
      }
    });
  }

  login(loginForm: NgForm): void {
    if (loginForm.valid) {
      this.authService.login(this.email, this.password)
        .then(() => {
          // Navigate to the home page upon successful login
          this.router.navigate(['/home']);
        })
        .catch(error => {
          this.loginError = error.message; // Display Firebase Authentication error message
        });
    }
  }

  dismissMessage(): void {
    this.logoutMessage = '';
    this.loginError = '';
  }
}

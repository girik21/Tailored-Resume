import { Component, ChangeDetectorRef } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Router } from '@angular/router'; // Import Router from @angular/router
import { NbAuthService } from '@nebular/auth';
import { NbLoginComponent } from '@nebular/auth';
import { ActivatedRoute } from '@angular/router';
import { UserAPI } from '../../service/api/user-api.service';

@Component({
  selector: 'ngx-login',
  templateUrl: './login.component.html',
})
export class NgxLoginComponent extends NbLoginComponent {
  constructor(
    private fireAuth: AngularFireAuth,
    private authService: NbAuthService,
    public cd: ChangeDetectorRef, // Change to public or protected if NbLoginComponent's cd is public or protected
    public router: Router, // Import Router from @angular/router
    private route: ActivatedRoute,
    private userApi: UserAPI // Inject the UserAPI service
  ) {
    super(authService, {}, cd, router);
  }

  errors: string[] = [];
  messages: string[] = [];
  showMessages = {
    error: false,
    success: false
  };
  registrationSuccessMessage: string | null = null;

  ngOnInit(): void {
    // Check if registration success message exists in query parameters
    this.route.queryParams.subscribe(params => {
      if (params['registered']) {
        this.registrationSuccessMessage = 'Registration successful. Please login.';
      }
    });
  }

  async login() {
    try {
      await this.fireAuth.signInWithEmailAndPassword(this.user.email, this.user.password);
      const user = await this.fireAuth.currentUser; // Get current user
      if (user) {
        const accessToken = await user.getIdToken(); // Get Firebase Authentication token (access token)
        const expiresIn = new Date().getTime() + 3600 * 1000; // Token expiration time (1 hour)
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('accessTokenExpiresIn', expiresIn.toString()); // Store token expiration time
        
        // Check if the user exists in the backend API
        this.userApi.getAllUsersByEmail(user.email, accessToken).subscribe((users: any[]) => {
          if (users.length > 0) {
            // User found, redirect to dashboard
            this.router.navigate(['dashboard']);
            this.scheduleTokenRefresh(); // Schedule token refresh
          } else {
            // User not found, redirect to profile page
            this.router.navigate(['pages/layout/profile']);
          }
        });
      }
    } catch (error) {
      // Handle login errors here
      console.error('Error signing in:', error);
      // You can also display error messages to the user using this.errors array
      this.errors = ['Error signing in. Please try again.'];
  
      this.showMessages.error = true;
    }
  }
  
  private async scheduleTokenRefresh(): Promise<void> {
    const expiresInString = localStorage.getItem('accessTokenExpiresIn');
    if (expiresInString) {
      const expiresIn = +expiresInString;
      const timeUntilRefresh = expiresIn - new Date().getTime() - 300000; // Refresh token 5 minutes before expiration
      if (timeUntilRefresh > 0) {
        setTimeout(async () => {
          try {
            const user = await this.fireAuth.currentUser;
            if (user) {
              const newAccessToken = await user.getIdToken();
              localStorage.setItem('accessToken', newAccessToken);
              const newExpiresIn = new Date().getTime() + 3600 * 1000; // Extend expiration time by 1 hour
              localStorage.setItem('accessTokenExpiresIn', newExpiresIn.toString());
              this.scheduleTokenRefresh();
            }
          } catch (error) {
            console.error('Token refresh failed:', error);
          }
        }, timeUntilRefresh);
      }
    } else {
      console.error('Access token expiration time is not available.');
    }
  }

  getConfigValue(key: string): any {
    // Implement this method based on your configuration retrieval logic
  }
}

import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user$: Observable<firebase.default.User | null>;

  constructor(private fireAuth: AngularFireAuth, private router: Router) {
    this.user$ = this.fireAuth.authState;
  }

  async login(email: string, password: string): Promise<void> {
    try {
      await this.fireAuth.signInWithEmailAndPassword(email, password);
      const user = await this.fireAuth.currentUser; // Get current user
      if (user) {
        const accessToken = await user.getIdToken(); // Get Firebase Authentication token (access token)
        const expiresIn = new Date().getTime() + 3600 * 1000; // Token expiration time (1 hour)
        localStorage.setItem('accessToken', accessToken); 
        localStorage.setItem('accessTokenExpiresIn', expiresIn.toString()); // Store token expiration time
        this.router.navigate(['dashboard']);
        this.scheduleTokenRefresh(); // Schedule token refresh
      }
    } catch (error: any) {
      alert('Login failed: ' + error.message);
      this.router.navigate(['/login']);
    }
  }

  async register(email: string, password: string): Promise<void> {
    try {
      await this.fireAuth.createUserWithEmailAndPassword(email, password);
      alert('User has been successfully registered! Please log in to continue!');
      this.router.navigate(['/login']);
    } catch (error: any) {
      alert('Registration failed: ' + error.message);
      throw error;
    }
  }

  async logout(): Promise<void> {
    try {
      await this.fireAuth.signOut();
      localStorage.removeItem('accessToken');
      localStorage.removeItem('accessTokenExpiresIn');
      this.router.navigate(['/login']);
    } catch (error: any) {
      alert('Logout failed: ' + error.message);
      throw error;
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
  
}

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

  // login method
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
      let errorMessage: string;
      switch (error.code) {
        case 'auth/invalid-credential':
            errorMessage = 'Provided credentials are invalid. Please try again.';
            break;
        default:
          errorMessage = 'An error occurred. Please try again later.';
          break;
      }
      throw new Error(errorMessage);
    }
  }

  // register method
  register(email: string, password: string){
    this.fireAuth.createUserWithEmailAndPassword(email, password).then(()=> {
      alert('registraion successful')
      this.router.navigate(['/login'])
    }, err => {
      alert(err.message)
      this.router.navigate(['register'])
    }
    )
  }

  // logout method
  async logout(): Promise<void> {
    try {
      await this.fireAuth.signOut();
      localStorage.removeItem('accessToken');
      localStorage.removeItem('accessTokenExpiresIn');
      this.router.navigate(['/dashboard'], { queryParams: { logout: true } });
    } catch (error: any) {
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

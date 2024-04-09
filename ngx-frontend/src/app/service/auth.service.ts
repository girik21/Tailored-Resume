import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { SetUserId } from '../shared/user.actions';
import { UserAPI } from './api/user-api.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user$: Observable<firebase.default.User | null>;

  constructor(
    private fireAuth: AngularFireAuth,
    private router: Router,
    private userAPI: UserAPI,
    private store: Store
  ) {
    this.user$ = this.fireAuth.authState;
    this.user$.subscribe(user => {
      if (user) {
        this.dispatchUserId();
      }
    });
  }

  async getLoggedInUserId(): Promise<string | null> {
    try {
      const userEmail = localStorage.getItem('loggedInEmail');
      const token = localStorage.getItem('accessToken');
      if (userEmail && token) {
        const loggedInUser = await this.userAPI.getAllUsersByEmail(userEmail, token).toPromise();
        if (loggedInUser && loggedInUser.length > 0) {
          return loggedInUser[0].id;
        } else {
          return null;
        }
      } else {
        return null;
      }
    } catch (error: any) {
      console.error('Error getting logged-in user ID:', error);
      return null;
    }
  }

  async logout(): Promise<void> {
    try {
      await this.fireAuth.signOut();
      localStorage.removeItem('accessToken');
      localStorage.removeItem('accessTokenExpiresIn');
      localStorage.removeItem('loggedInEmail');
      this.router.navigate(['auth/login'], { queryParams: { logout: true } });
    } catch (error: any) {
      throw error;
    }
  }

  async dispatchUserId(): Promise<void> {
    try {
      const userId = await this.getLoggedInUserId();
      if (userId) {
        this.store.dispatch(new SetUserId(userId));
      }
    } catch (error: any) {
      console.error('Error dispatching user ID:', error);
    }
  }
}

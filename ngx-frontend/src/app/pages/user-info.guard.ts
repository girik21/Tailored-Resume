import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { UserAPI } from '../service/api/user-api.service';
import { User } from '../service/model/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserInfoGuard implements CanActivate {

  constructor(private router: Router, private userAPI: UserAPI) {}

  canActivate(): boolean {
    const userEmail = localStorage.getItem('loggedInEmail');
    const token = localStorage.getItem('accessToken');
    
    this.userAPI.getAllUsersByEmail(userEmail, token).subscribe(
        (loggedInUser: User[]) => {
          if (loggedInUser && loggedInUser.length > 0) {
          // User found, restrict access to the profile route and show alert
          alert('You have already created your profile. Redirecting to Home Page.');
          this.router.navigate(['/dashboard']); 
          return false;
        }
      }
    );

    // By default, return false to prevent route access before subscription completes
    return true;
  }
}

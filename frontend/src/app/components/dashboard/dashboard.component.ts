import { Component } from '@angular/core';
import { AuthService } from '../../shared/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  constructor(private authService: AuthService) { }

  logout(): void {
    this.authService.logout()
      .then(() => console.log('User logged out successfully'))
      .catch(error => console.error('Logout failed:', error));
  }
}

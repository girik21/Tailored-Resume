import { Component } from '@angular/core';
import { AuthService } from '../../shared/auth.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private authService: AuthService) { }

  logout(): void {
    this.authService.logout()
      .then(() => console.log('User logged out successfully'))
      .catch(error => console.error('Logout failed:', error));
  }

}

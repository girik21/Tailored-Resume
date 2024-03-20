import { Component } from '@angular/core';
import { AuthService } from '../../shared/auth.service';
import { locale } from '../common/constants';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  locale = locale

  constructor(private authService: AuthService) { }

  logout(): void {
    this.authService.logout()
      .then(() => console.log('User logged out successfully'))
      .catch(error => console.error('Logout failed:', error));
  }

}

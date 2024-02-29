import { Component } from '@angular/core';
import { locale } from '../common/constants';
import {MatIconModule} from '@angular/material/icon';
import { AuthService } from '../../shared/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  providers: [MatIconModule],

})
export class NavbarComponent {
  locale = locale;

  constructor(private authService: AuthService) { }

  logout(): void {
    this.authService.logout()
      .then(() => console.log('User logged out successfully'))
      .catch(error => console.error('Logout failed:', error));
  }

}

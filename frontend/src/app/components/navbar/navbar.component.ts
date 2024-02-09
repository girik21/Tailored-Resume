import { Component } from '@angular/core';
import { locale } from '../common/constants';
import {MatIconModule} from '@angular/material/icon';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  providers: [MatIconModule],

})
export class NavbarComponent {
  locale = locale;

}

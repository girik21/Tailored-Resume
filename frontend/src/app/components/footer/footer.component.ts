import { Component } from '@angular/core';
import { locale } from '../common/constants';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
  locale = locale;

}

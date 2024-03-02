import { Component } from '@angular/core';
import { locale } from '../common/constants';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrl: './carousel.component.css'
})
export class CarouselComponent {
  locale = locale

}

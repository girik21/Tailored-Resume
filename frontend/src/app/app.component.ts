import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  showNavbar: boolean = true;

  constructor(private router: Router) {
    router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Hide the navbar on the login and register routes
        const currentUrl = this.router.url;
        const isLoginOrRegisterRoute = currentUrl.includes('login') || currentUrl.includes('register');
        this.showNavbar = !isLoginOrRegisterRoute;
      }
    });
  }
}



import { AuthService } from '../../shared/auth.service'
import { Component, OnInit } from '@angular/core';
import { locale } from '../common/constants';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  locale = locale;



  constructor(private auth: AuthService) { }

  ngOnInit(): void {

  }
}
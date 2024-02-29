import { Injectable } from '@angular/core';
import {AngularFireAuth} from '@angular/fire/compat/auth'
import { Router } from '@angular/router';

import {Observable, catchError, from, map, of, BehaviorSubject  } from 'rxjs';
import {ResponseWrapper} from '../utils/response-wrapper'

@Injectable({
  providedIn: 'root'
})
export class AuthService {

 

  constructor(private fireAuth : AngularFireAuth, private router : Router) { }

  //login method
  login(email: string, password: string) {
    this.fireAuth.signInWithEmailAndPassword(email, password).then( () => {
      localStorage.setItem('token', 'true')
      this.router.navigate(['dashboard'])

    }, err => {
      alert('something went wrong!')
      this.router.navigate(['/login'])
    }

    )
  }
 
  register(email: string, password: string): Observable<ResponseWrapper<any>> {  
    return from(this.fireAuth.createUserWithEmailAndPassword(email, password))
      .pipe(
        map(() => {
          return { success: true, message: 'Registration successful', data: null };
        }),
        catchError(error => {
          return of({ success: false, message: error.message, data: null });
        })
      );
  }


  //sign out method 
  logout(){
    this.fireAuth.signOut().then(() => {
      localStorage.removeItem('token')
      this.router.navigate(['/login'])
    }, err => {
      alert(err.message)      
    })
  }
}

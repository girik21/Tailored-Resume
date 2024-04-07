import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user.model';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserAPI {

  private baseUrl = 'http://localhost:8080/api';
  private token: string;

  constructor(private http: HttpClient) {
    this.token = localStorage.getItem('accessToken');
  }

  // Saving user details
  saveUserDetails(data: any): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.post(`${this.baseUrl}/users`, data, { headers });
  }

  // Get single user
  getUserDetails(userId: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.get<any>(`${this.baseUrl}/users/${userId}`, { headers });
  }

  // Get all users
  getAllUsers(): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.get<any>(`${this.baseUrl}/users`);
  }

  getAllUsersByEmail(email: string, token: string): Observable<User[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    let params = new HttpParams();
    if (email) {
      params = params.set('email', email);
    }
    return this.http.get<any>(`${this.baseUrl}/users`, { headers, params }).pipe(
      map(response => {
        // API response contains a property named 'data' which holds the users array
        return response.data as User[];
      })
    );
  }

  // save user experience
  saveExperience(data: any, userId: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.post(`${this.baseUrl}/experiences?userId=${userId}`, data, { headers });
  }
  
  // save user education
  saveEducation(data: any, userId: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.post(`${this.baseUrl}/education?userId=${userId}`, data, { headers });
  }

  // save user projects
  saveProjects(data: any, userId: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.post(`${this.baseUrl}/projects?userId=${userId}`, data, { headers });
  }

  // save user skills
  saveSkills(data: any, userId: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.post(`${this.baseUrl}/skills?userId=${userId}`, data, { headers });
  }

  // save user certifications
  saveCertifications(data: any, userId: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.post(`${this.baseUrl}/certifications?userId=${userId}`, data, { headers });
  }
}

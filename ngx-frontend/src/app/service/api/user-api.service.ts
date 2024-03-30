import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    let test_token: String;
    //test_token  = 'eyJhbGciOiJSUzI1NiIsImtpZCI6ImJhNjI1OTZmNTJmNTJlZDQ0MDQ5Mzk2YmU3ZGYzNGQyYzY0ZjQ1M2UiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vdGVzdC1hcHAtMTMzZDAiLCJhdWQiOiJ0ZXN0LWFwcC0xMzNkMCIsImF1dGhfdGltZSI6MTcxMTc1Nzc0MywidXNlcl9pZCI6InBqQXpYenhoTmJVcU5JbnVkYzVydGdUZzBCMDMiLCJzdWIiOiJwakF6WHp4aE5iVXFOSW51ZGM1cnRnVGcwQjAzIiwiaWF0IjoxNzExNzU3NzQzLCJleHAiOjE3MTE3NjEzNDMsImVtYWlsIjoiZHd0Lmtyc0BnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsiZHd0Lmtyc0BnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.JgKSvlOCsSN1JmlQdaK-IMNJuKLnYcSR4DxF387EGYXi26tFx8KHu5QU1dXkfn1nSbip4cjlH8QR3FTOUfcivjvyaW7IcLrHzY_7noZAxCnOrwpBHe6X3wzIybSBn0oowK9M61c8_TSvC_KwTr51QRHoZuE8WhgLoX0rBNCrym41bua1_M5we-3mfSuPu3zvRnTz_Bq5tt9MW9WFKnAsQIwgN44Ts-v72TG7iZNt667f2sSn_GdreeG6trtEHL98fSubbskyHTRxY6_16uh6TBUICYqiPHia36a1nBDZyUkEm6EXXNAN0fHSXBnVx-TwSoK4iv_hklnBq9RUoK43nQ';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    console.log(`${this.baseUrl}/users/${userId}`)
    return this.http.get<any>(`${this.baseUrl}/users/${userId}`, { headers });
  }

  // Get all users
  getAllUsers(): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);
    return this.http.get<any>(`${this.baseUrl}/users`);
  }

  // Get all users
  getAllUsersByEmail(email: string, token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    let params = new HttpParams();
    if (email) {
      params = params.set('email', email);
    }
    return this.http.get<any>(`${this.baseUrl}/users`, { headers, params });
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

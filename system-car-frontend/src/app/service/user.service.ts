import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  login(loginInput: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/signin`, loginInput);
  }

  register(userInput: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/users`, userInput);
  }

  registerCar(carInput: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `${token}`);

    return this.http.post(`${this.apiUrl}/cars`, carInput, { headers });
  }

  getUserData(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `${token}`);

    return this.http.get<any>(`${this.apiUrl}/me`, { headers });
  }

  userUpdate(id:number, user:any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/users/${id}`, user);
  }

  verifyTokenIsValid(){
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `${token}`);

    return this.http.get<any>(`${this.apiUrl}/users/verifyToken`, { headers });
  }  
}
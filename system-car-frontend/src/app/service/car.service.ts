import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Car } from '../../model/car.model';

@Injectable({
  providedIn: 'root'
})
export class CarService{
    private apiUrl = 'http://localhost:8080/api';

    constructor(private http: HttpClient) {}

    registerCar(carInput: any): Observable<any> {
        const token = localStorage.getItem('token');
        const headers = new HttpHeaders().set('Authorization', `${token}`);
    
        return this.http.post(`${this.apiUrl}/cars`, carInput, { headers });
    }

    updateCar(id: number, carInput: Car): Observable<any>{
        const carInputWithoutId = {
            "year": carInput.year,
            "licensePlate": carInput.licensePlate,
            "model": carInput.model,
            "color": carInput.color
        }

        const token = localStorage.getItem('token');
        const headers = new HttpHeaders().set('Authorization', `${token}`);

        return this.http.put(`${this.apiUrl}/cars/${id}`, carInputWithoutId, { headers });
    }

    deleteCar(id: number): Observable<any> {
        const token = localStorage.getItem('token');
        const headers = new HttpHeaders().set('Authorization', `${token}`);

        return this.http.delete(`${this.apiUrl}/cars/${id}`, { headers });
    } 
}
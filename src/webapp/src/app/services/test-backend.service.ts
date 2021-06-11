import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TestBackend } from '../interfaces/TestBackend'

@Injectable({
  providedIn: 'root'
})
export class TestBackendService {
  private apiUrl = 'http://localhost:8081/testServices';

  constructor(private http: HttpClient) { }

  testBackend(): Observable<TestBackend> {
    return this.http.get<TestBackend>(this.apiUrl);
  }
}

import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class DashboardService {

  constructor(private http: HttpClient, private cookie: CookieService, private route: Router) { }

  test(): Observable<any> {
      const token = 'Bearer ' + this.cookie.get('token');
      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type':  'application/json',
          'Authorization': token,
        })
      };
      console.log(httpOptions);
      const payload = {
        email: 'sanjiv.nishanth@gmail.com',
        password: 'Nishanth97'
      };
      console.log(JSON.stringify(payload));
      return this.http.post('http://localhost:8082/auth/test', JSON.stringify(payload), httpOptions);
  }
}

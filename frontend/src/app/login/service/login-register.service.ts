import { EncryptionService } from '../../encryption.service';

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class LoginRegisterService {

  constructor(private http: HttpClient, private encryptionService: EncryptionService) { }
  login(payload): Observable<any> {
    console.log( this.encryptionService.encrypt(payload.password));
    payload.password = this.encryptionService.encrypt(payload.password);
    console.log(payload);
    return this.http.post('https://localhost:8443/auth/login', payload);
  }
  register(payload): Observable<any> {
    console.log( this.encryptionService.encrypt(payload.password));
    payload.password = this.encryptionService.encrypt(payload.password);
    console.log(payload);
    return this.http.post('https://localhost:8443/auth/register', payload);
  }
}

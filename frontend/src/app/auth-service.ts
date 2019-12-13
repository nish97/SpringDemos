import { EncryptionService } from './encryption.service';
import { CookieService } from 'ngx-cookie-service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class AuthService  {

  constructor(private cookie: CookieService, private encryptService: EncryptionService) { }

  public isAuthenticated(): boolean {
    if (this.cookie.check('token')) {
      const token = this.cookie.get('token');
      console.log('Inside auth guard', token);
      console.log('decrypted token', this.encryptService.decrypt(token).split(' ')[1]);
      if (this.encryptService.decrypt(token).split(' ')[1] === this.cookie.get('email')) {
      return true;
    } else {
      return false;
    }
    } else {
    return false;
    }
  }
}

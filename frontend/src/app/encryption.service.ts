import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable()
export class EncryptionService {
  key = CryptoJS.enc.Utf8.parse('aesEncryptionKey');
  iv = CryptoJS.enc.Utf8.parse( 'encryptionIntVec');
  constructor() { }
  encrypt(value): string {
    return CryptoJS.enc.Base64.parse(CryptoJS.AES.encrypt(value, this.key, { iv: this.iv }).toString()).toString(CryptoJS.enc.Base64);
  }
  decrypt(value): string {
    return CryptoJS.AES.decrypt(value, this.key, { iv: this.iv }).toString(CryptoJS.enc.Utf8).toString();
  }
}

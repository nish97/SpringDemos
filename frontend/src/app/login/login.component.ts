import { LoginRegisterService } from './service/login-register.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {Router} from '@angular/router';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import { RegisterComponent } from './register/register.component';
import { CookieService } from 'ngx-cookie-service';
import { EncryptionService } from '../encryption.service';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginRegisterService, CookieService]
})
export class LoginComponent implements OnInit {
  constructor(private route: Router, private formBuilder: FormBuilder,
              public dialog: MatDialog, private dialogRef: MatDialogRef<LoginComponent>,
              private loginService: LoginRegisterService,
              private cookie: CookieService,
              private encryptionService: EncryptionService) { }

  formGroup: FormGroup;


  ngOnInit() {
    this.createForm();
  }

  createForm() {
  this.formGroup = this.formBuilder.group({
    email: [''],
    password: [''],
   });
  }

  submit(post) {
    if (!this.cookie.check('token')) {
    this.loginService.login(post).subscribe
      (data => {console.log('Token received:', data);
                console.log(this.encryptionService.encrypt(this.encryptionService.decrypt(data) + ' ' + post.email));
                this.cookie.set('token', this.encryptionService.encrypt(this.encryptionService.decrypt(data) + ' ' + post.email), 1);
                this.cookie.set('email', post.email, 1);
                this.route.navigateByUrl('/dashboard');
                this.dialogRef.close(); });
    } else {
      this.route.navigateByUrl('/dashboard');
      this.dialogRef.close();
    }
  }
  openRegister() {
      this.dialog.open(RegisterComponent);
  }

}

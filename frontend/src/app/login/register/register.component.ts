import { LoginRegisterService } from '../service/login-register.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MustMatch } from './register.validator';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [LoginRegisterService]
})
export class RegisterComponent implements OnInit {
  hide = true;
  formGroup: FormGroup;
  titleAlert = 'This field is required';


  constructor(private formBuilder: FormBuilder, private registerService: LoginRegisterService,
              private dialogRef: MatDialogRef<RegisterComponent>) { }

  ngOnInit() {
    this.createForm();
  }

  createForm() {
    // tslint:disable-next-line: max-line-length
    const emailregex: RegExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    this.formGroup = this.formBuilder.group({
      email: ['', [Validators.required, Validators.pattern(emailregex)]],
      password: ['', [Validators.required, this.checkPassword]],
      confirmPassword: ['', Validators.required]
     },
     {
      validator: MustMatch('password', 'confirmPassword')
     });

  }

  get f() { return this.formGroup.controls; }

  checkPassword(control) {
    const enteredPassword = control.value;
    const passwordCheck = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})/;
    return (!passwordCheck.test(enteredPassword) && enteredPassword) ? { requirements: true } : null;
  }

  getErrorEmail() {
    return this.formGroup.get('email').hasError('required') ? 'Field is required' :
      this.formGroup.get('email').hasError('pattern') ? 'Not a valid emailaddress' :
        this.formGroup.get('email').hasError('alreadyInUse') ? 'This emailaddress is already in use' : '';
  }

  getErrorPassword() {
    // tslint:disable-next-line: max-line-length
    return this.formGroup.get('password').hasError('required') ? 'Field is required (at least eight characters, one uppercase letter and one number)' :
      // tslint:disable-next-line: max-line-length
      this.formGroup.get('password').hasError('requirements') ? 'Password needs to be at least eight characters, one uppercase letter and one number' : '';
  }



  onSubmit(post) {
    console.log(post);
    delete post.confirmPassword;
    console.log(post);
    this.registerService.register(post).subscribe
      (data => {console.log(data);
                this.dialogRef.close(); });
  }

}

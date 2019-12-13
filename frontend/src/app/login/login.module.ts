import { CookieService } from 'ngx-cookie-service';
import { MaterialModule } from './../material/material.module';
import { RegisterComponent } from './register/register.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { SignComponent } from './sign.component';
import { LoginRoutingModule } from './login-routing.module';
import { FormsModule } from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';



@NgModule({
  declarations: [RegisterComponent, SignComponent, LoginComponent],
  imports: [
    CommonModule,
    MaterialModule,
    LoginRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  exports: [LoginRoutingModule],
  providers: [CookieService],
  entryComponents: [LoginComponent, RegisterComponent]
})
export class LoginModule { }

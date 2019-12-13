import { Injectable } from '@angular/core';
import { Router, CanActivate, Route, CanLoad } from '@angular/router';
import { AuthService } from './auth-service';



@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate, CanLoad {
  constructor(public auth: AuthService, public router: Router) {}

  canActivate(): boolean {
    if (!this.auth.isAuthenticated()) {
      this.router.navigate(['home']);
      return false;
    }
    return true;
  }
  canLoad(route: Route): boolean {
    const url = `/${route.path}`;
    if (!this.auth.isAuthenticated()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}

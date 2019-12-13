import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthGuardService as AuthGuard} from './auth-guard-service';
import { HomeComponent } from './home/home.component';
import { CookieService } from 'ngx-cookie-service';
import { EncryptionService } from './encryption.service';

const routes: Routes = [
  {path: '', component: HomeComponent,
  children: [
    {
      path: 'login',
      loadChildren: () => import('./login/login.module').then(m => m.LoginModule)
    }
  ]
  },
  { path: 'dashboard',
    loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
    canLoad: [AuthGuard]
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true}), BrowserAnimationsModule],
  exports: [RouterModule],
  providers: [CookieService, EncryptionService]
})
export class AppRoutingModule {}

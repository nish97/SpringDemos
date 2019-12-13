import { LoginComponent } from './login.component';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  template: ''
  })
  export class SignComponent {
      constructor(public dialog: MatDialog, private router: Router,
                  private route: ActivatedRoute) {
      this.openDialog();
      }
      openDialog(): void {
        const dialogRef = this.dialog.open(LoginComponent);
        dialogRef.backdropClick().subscribe(result => {
        this.router.navigate([''], { relativeTo: this.route });
    });
      }
  }

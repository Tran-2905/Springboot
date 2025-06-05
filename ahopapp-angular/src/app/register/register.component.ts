import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Route, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  phone: String = '';
  password: string = '';
  retypePassword: string = '';
  fullname: string = '';
  address: string = '';
  isAccepted: boolean = false;
  dateOfBirth: Date;


  constructor(private http: HttpClient, private router: Router) {
    this.phone = "0849418878";
    this.password = "123456";
    this.retypePassword = "123456";
    this.fullname = "Tran Ngoc Han";
    this.address = "Thanh pho Ho Chi Minh";
    this.isAccepted = true;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18)
  }
  onPhoneChange() {
    console.log(`Phone typed:  ${this.phone}`)
  }
  register() {
    // const message =
    //   `phone: ${this.phone}` +
    //   `retypePassword: ${this.retypePassword}` +
    //   `password: ${this.password}` +
    //   `address: ${this.address}` +
    //   `fullname: ${this.fullname}` +
    //   `isAccepted: ${this.isAccepted}` +
    //   `dateOfBirth: ${this.dateOfBirth}`;
    // alert(message)
    const apiUrl = "http://localhost:8088/api/v1/register";
    const registerData = {
      "fullname": this.fullname,
      "phone_number": this.phone,
      "address": this.address,
      "password": this.password,
      "retype_password": this.retypePassword,
      "date_of_birth": this.dateOfBirth,
      "facebook_account_id": null,
      "google_account_id": null,
      "role_id": 1
    }
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    this.http.post(apiUrl, registerData, { headers },)
      .subscribe({
        next: (response: any) => {
          debugger
          if (response && (response.status === 200 && response.status === 201)) {
            this.router.navigate(['login']);

          } else {

          }
        },
        complete: () => {
          debugger
        },
        error: (error: any) => {
          debugger
          console.error('Đăng kí không thành công', error);
        }
      })
  }

  checkPasswordMatch() {
    if (this.password !== this.retypePassword) {
      this.registerForm.form.controls['retypePassword'].setErrors({ 'passwordMismatch': true });
    } else {
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }


  checkAge() {
    const today = new Date();
    const birthday = new Date(this.dateOfBirth);
    let age = today.getFullYear() - birthday.getFullYear();
    const monthDiff = today.getMonth() - birthday.getMonth();
    const dayDiff = today.getDate() - birthday.getDate();

    // If the birthday hasn't occurred yet this year, subtract one year from age
    if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
      age--;
    }

    if (age >= 18) {
      this.registerForm.form.controls['dateOfBirth'].setErrors(null);
    } else {
      this.registerForm.form.controls['dateOfBirth'].setErrors({ 'children': true });
    }
  }
}

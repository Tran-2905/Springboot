import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { DetailProductComponent } from './detail-product/detail-product.component';
import { OrderComponent } from './order/order.component';
import { OrderConfirmedComponent } from './order-confirmed/order-confirmed.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';

@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    DetailProductComponent,
    OrderComponent,
    OrderConfirmedComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [
    // HomeComponent
    // OrderComponent,
    // OrderConfirmedComponent,
    // LoginComponent
    RegisterComponent
  ]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { PdfMakerComponent } from './components/pdf-maker/pdf-maker.component'; // Import PdfMakerComponent
import { RegisterComponent } from './components/register/register.component';
import { AuthGuard } from './shared/auth.guard';

const routes: Routes = [
  {path: '', redirectTo:'dashboard', pathMatch:'full'},
    {path:'login', component: LoginComponent},
    {path: 'dashboard', component : DashboardComponent},
    {path: 'register', component : RegisterComponent},
    {path:'home', component: HomeComponent, canActivate: [AuthGuard]},
    { path: 'pdf-maker', component: PdfMakerComponent },
    //{path: 'varify-email', component : VarifyEmailComponent},
    //{path: 'forgot-password', component : ForgotPasswordComponent},
    //{path : 'file-upload', component:FileuploadComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

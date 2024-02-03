import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

const routes: Routes = [
  {path: '', redirectTo:'login', pathMatch:'full'},
    {path:'login', component: LoginComponent},
    {path: 'dashboard', component : DashboardComponent},
    {path: 'register', component : RegisterComponent},
    //{path: 'varify-email', component : VarifyEmailComponent},
    //{path: 'forgot-password', component : ForgotPasswordComponent},
    //{path : 'file-upload', component:FileuploadComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

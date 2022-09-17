import { InboxComponent } from './components/inbox/inbox.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from './components/signin/signin.component';

const routes: Routes = [
  { path: 'signIn', component: SigninComponent },
  { path: 'signUp', component: SignUpComponent },
  { 
    path: '',
    component: HomeComponent,
    children: [
      {
        path: 'inbox',
        component: InboxComponent
      }
    ]  
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

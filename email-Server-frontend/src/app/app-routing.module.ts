import { InboxComponent } from './components/inbox/inbox.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { HomeComponent } from './components/home/home.component';
import {Component, NgModule} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from './components/signin/signin.component';
import {SentComponent} from "./components/sent/sent.component";
import {TrashComponent} from "./components/trash/trash.component";

const routes: Routes = [
  { path: '', component: SigninComponent },
  { path: 'signUp', component: SignUpComponent },
  {
    path: 'home',
    component: HomeComponent,
    children: [
      {
        path: 'inbox',
        component: InboxComponent
      },{
        path: 'sent',
        component: SentComponent
    },{
        path: 'trash',
        component: TrashComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

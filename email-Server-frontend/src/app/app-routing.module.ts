import { EmailsListComponent } from './components/home/emails-list/emails-list.component';
import { DraftComponent } from './components/home/draft/draft.component';
import { InboxComponent } from './components/home/inbox/inbox.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { HomeComponent } from './components/home/home.component';
import { NgModule} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from './components/signin/signin.component';
import { SentComponent } from "./components/home/sent/sent.component";
import { TrashComponent } from "./components/home/trash/trash.component";

const routes: Routes = [
  { path: '', component: SigninComponent },
  { path: 'signUp', component: SignUpComponent },
  {
    path: 'home',
    component: HomeComponent,
    children: [
      {
        path: 'emails/:folderName',
        component: EmailsListComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

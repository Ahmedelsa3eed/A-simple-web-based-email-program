import { DraftComponent } from './components/home/draft/draft.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { HomeComponent } from './components/home/home.component';
import { NgModule} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SigninComponent } from './components/signin/signin.component';
import { InboxComponent } from './components/home/inbox/inbox.component';
import { SentComponent } from './components/home/sent/sent.component';
import { TrashComponent } from './components/home/trash/trash.component';

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
      },
      {
        path: 'sent',
        component: SentComponent
      },
      {
        path: 'trash',
        component: TrashComponent
      },
      {
        path: 'draft',
        component: DraftComponent
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

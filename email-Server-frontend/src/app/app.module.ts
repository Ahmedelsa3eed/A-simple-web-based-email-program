import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SigninComponent } from './components/signin/signin.component';
import { HomeComponent } from './components/home/home.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { NavComponent } from './components/home/nav/nav.component';
import { InboxComponent } from './components/home/inbox/inbox.component';
import { HeaderComponent } from './components/header/header.component';
import { EmailComponent } from './components/email/email.component';
import { SentComponent } from './components/home/sent/sent.component';
import { TrashComponent } from './components/home/trash/trash.component';
import { DraftComponent } from './components/home/draft/draft.component';
import { EmailsListComponent } from './components/home/emails-list/emails-list.component';

@NgModule({
  declarations: [
    AppComponent,
    SigninComponent,
    HomeComponent,
    SignUpComponent,
    NavComponent,
    InboxComponent,
    HeaderComponent,
    EmailComponent,
    SentComponent,
    TrashComponent,
    DraftComponent,
    EmailsListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

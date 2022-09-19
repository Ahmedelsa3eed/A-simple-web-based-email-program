export class Email {
  _id: string;
  sender: string;
  receiver: string;
  subject: string;
  body: string;
  seen: boolean;
  date: Date;
  attachments: FormData;

  constructor() {
    this._id = "";
    this.sender = "";
    this.receiver = "";
    this.subject = "";
    this.body = "";
    this.seen = false;
    this.date = new Date();
    this.attachments = new FormData;
  }

}

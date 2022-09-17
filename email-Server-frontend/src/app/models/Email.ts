export class Email {
  _id: string;
  sender: string;
  receiver: string;
  subject: string;
  content: string;

  constructor() {
    this._id = "";
    this.sender = "";
    this.receiver = "";
    this.subject = "";
    this.content = "";
  }

}

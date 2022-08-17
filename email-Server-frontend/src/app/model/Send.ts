export class Send {
  public id: number
  public priority: string;
  public date: string;
  public from: string;
  public to: string;
  public subject: string;
  public body: string
  public attachmentPath: string
  public attachmentsName: string[]

  constructor(from: string, to: string, subject: string, body: string) {
    this.from = from
    this.to = to
    this.subject = subject
    this.body = body
  }
  getFrom() {
    return this.from
  }
  getTo(): string {
    return this.to
  }
  getSubject(): string {
    return this.subject
  }
  getBody(): string {
    return this.body
  }

}
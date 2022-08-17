export class Contact{
  public id:number
  public name:string;
  public emails:string[];
  
  constructor(name: string,emails: string[]) {
    this.emails=emails
    this.name=name
  }
  getEmail(): string[]{
    return this.emails
  }
  getName(): string{
    return this.name
  }
}

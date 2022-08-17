export class User{
  private firstName:string;
  private secondName:string;
  private email:string;
  private password:string;

  constructor(first:string,last:string,email: string,password: string) {
    this.firstName=first
    this.secondName=last
    this.email=email
    this.password=password
  }
  getFirstName(): string{
    return this.firstName
  }
  getSecondName(): string{
    return this.secondName
  }
  getEmail(): string{
    return this.email
  }
  getPassword(): string{
    return this.password
  }
}

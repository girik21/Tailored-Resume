export class SetUserId {
  static readonly type = '[User] Set UserId';
  constructor(public userId: string) {}
}

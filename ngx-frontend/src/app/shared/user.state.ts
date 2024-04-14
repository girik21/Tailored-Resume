import { Action, Selector, State, StateContext } from '@ngxs/store';
import { SetUserId } from './user.actions';

export interface UserStateModel {
  userId: string | null;
}

@State<UserStateModel>({
  name: 'user',
  defaults: {
    userId: null
  }
})
export class UserState {

  @Selector()
  static getUserId(state: UserStateModel): string | null {
    return state.userId;
  }

  @Action(SetUserId)
  setUserId(ctx: StateContext<UserStateModel>, { userId }: SetUserId) {
    ctx.patchState({ userId });
  }
}

export { SetUserId }; // Exporting SetUserId here

import { NgModule } from '@angular/core';
import { NbMenuModule } from '@nebular/theme';

import { NbCardModule, NbChatModule, NbIconModule } from '@nebular/theme';
import { ThemeModule } from '../@theme/theme.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { ECommerceModule } from './e-commerce/e-commerce.module';
import { ChatService } from './extra-components/chat/chat.service';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';
import { PagesRoutingModule } from './pages-routing.module';
import { PagesComponent } from './pages.component';

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule,
    ECommerceModule,
    MiscellaneousModule,
    NbChatModule,
    NbCardModule,
    NbIconModule
  ],
  declarations: [
    PagesComponent,
  ],
  providers: [ChatService],
})
export class PagesModule {
}

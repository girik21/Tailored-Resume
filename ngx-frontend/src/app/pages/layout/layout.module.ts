import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  NbSelectModule,
  NbActionsModule,
  NbAccordionModule,
  NbButtonModule,
  NbCardModule,
  NbListModule,
  NbRouteTabsetModule,
  NbStepperModule,
  NbTabsetModule, NbUserModule,
  	NbIconModule,
  	NbProgressBarModule,
    NbSpinnerModule,
} from '@nebular/theme';

import { ThemeModule } from '../../@theme/theme.module';
import { LayoutRoutingModule } from './layout-routing.module';
import { LayoutComponent } from './layout.component';
import { Tab1Component, Tab2Component, TabsComponent } from './tabs/tabs.component';
import { StepperComponent } from './stepper/stepper.component';
import { ListComponent } from './list/list.component';
import { InfiniteListComponent } from './infinite-list/infinite-list.component';
import { NewsPostComponent } from './infinite-list/news-post/news-post.component';
import { NewsPostPlaceholderComponent } from './infinite-list/news-post-placeholder/news-post-placeholder.component';
import { AccordionComponent } from './accordion/accordion.component';
import { NewsService } from './news.service';
import { ProfileComponent } from './profile/profile.component';
import { ReportViewerComponent } from './report-viewer/report-viewer.component';
import { SpinnerColorComponent } from './spinner-color/spinner-color.component';

@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    ThemeModule,
    NbTabsetModule,
    NbRouteTabsetModule,
    NbStepperModule,
    NbCardModule,
    NbButtonModule,
    NbListModule,
    NbAccordionModule,
    NbUserModule,
    LayoutRoutingModule,
    NbActionsModule,
    NbSelectModule,
    NbIconModule,
  	NbProgressBarModule,
    NbSpinnerModule
  ],
  declarations: [
    LayoutComponent,
    TabsComponent,
    Tab1Component,
    Tab2Component,
    StepperComponent,
    ListComponent,
    NewsPostPlaceholderComponent,
    InfiniteListComponent,
    NewsPostComponent,
    AccordionComponent,
    ProfileComponent,
    ReportViewerComponent,
    SpinnerColorComponent
  ],
  exports: [
    ReportViewerComponent // Exporting the component
  ],
  providers: [
    NewsService,
  ],
})
export class LayoutModule { 

}


import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PlanificationRoutingModule } from './planification-routing.module';
import { OperatorShiftComponent } from './pages/shift/operator-shift/operator-shift/operator-shift.component';
import { ShiftMasterDetailComponent } from './pages/shift/shift-master-detail/shift-master-detail/shift-master-detail.component';
import { UserMasterDetailComponent } from './pages/user/user-master-detail/user-master-detail/user-master-detail.component';


@NgModule({
  declarations: [
    OperatorShiftComponent,
    ShiftMasterDetailComponent,
    UserMasterDetailComponent
  ],
  imports: [
    CommonModule,
    PlanificationRoutingModule
  ]
})
export class PlanificationModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PlanificationRoutingModule } from './planification-routing.module';
import { OperatorShiftComponent } from './pages/shift/operator-shift/operator-shift/operator-shift.component';
import { ShiftMasterDetailComponent } from './pages/shift/shift-master-detail/shift-master-detail/shift-master-detail.component';
import { UserMasterDetailComponent } from './pages/user/user-master-detail/user-master-detail/user-master-detail.component';
import { TaskComponent } from './pages/task/task/task.component';
import { AbilityComponent } from './pages/ability/ability/ability.component';
import { SharedModule } from '@shared/shared.module';
import { PlacementComponent } from './pages/placement/placement/placement.component';


@NgModule({
  declarations: [
    OperatorShiftComponent,
    ShiftMasterDetailComponent,
    UserMasterDetailComponent,
    TaskComponent,
    AbilityComponent,
    PlacementComponent
  ],
  imports: [
    SharedModule,
    PlanificationRoutingModule
  ]
})
export class PlanificationModule { }

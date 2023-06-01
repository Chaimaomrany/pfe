import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserMasterDetailComponent } from './pages/user/user-master-detail/user-master-detail/user-master-detail.component';
import { OperatorShiftComponent } from './pages/shift/operator-shift/operator-shift/operator-shift.component';
import { ShiftMasterDetailComponent } from './pages/shift/shift-master-detail/shift-master-detail/shift-master-detail.component';
import { AbilityComponent } from './pages/ability/ability/ability.component';
import { TaskComponent } from './pages/task/task/task.component';
import { PlacementComponent } from './pages/placement/placement/placement.component';

const routes: Routes = [
  { path: 'user', component: UserMasterDetailComponent },
  { path: 'shift', component: ShiftMasterDetailComponent},
  { path: 'affectation-operateur-shift', component:OperatorShiftComponent},
  { path: 'ability', component:AbilityComponent},
  
  { path: 'placement', component:PlacementComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlanificationRoutingModule { }

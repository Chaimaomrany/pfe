import { Component, Injector, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { compareDate } from '@app/accelengine-core/helpers/validators/date.validator';
import { MsgService } from '@app/accelengine-core/services/msg.service';
import { Task } from '@app/accelengine-modules/module-planification/models/Task.model';
import { User } from '@app/accelengine-modules/module-planification/models/user.model';
import { TaskService } from '@app/accelengine-modules/module-planification/services/task.service';
import { UserService } from '@app/accelengine-modules/module-planification/services/user.service';
import { CriteriaComponent } from '@app/accelengine-shared/components/criteria/criteria.component';
import { ColumnType, Column } from '@app/accelengine-shared/components/data-table/data-table.model';
import { RoleService } from '@app/accelengine-std/services/role.service';
import { APP_CONFIG } from '@app/app.config';
import { AECriteria, AECriteriaField, AECriteriaType, HybrideComponent, Logger } from 'accelengine-lib';

const log = new Logger('TaskComponent');
@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent extends HybrideComponent<Task> implements OnInit {
    constructor(injector: Injector,
        private roleService: RoleService) {
            super(injector, Task, TaskService, CriteriaComponent);
    
        this.formGroup = this.formBuilder.group({
            startDateTime: [this.data.startDateTime, [Validators.required]],
            endDateTime: [this.data.endDateTime, [Validators.required]]
    
        });
    
      }
    
      ngOnInit() {
       console.log('ngOnInit ChildFormComponent', this.data);
        if (this.data) {
          this.formGroup.patchValue(this.data);
        }
    
      }
    }
    
//   columnsOperatorShift: Column[];
//   appConfig = APP_CONFIG.app;

//   users: User[] = [];


//   constructor(
//       injector: Injector,
//       private taskService: TaskService,
      
//       private usersService: UserService,
//       private msgService: MsgService
//   ) {
//       super(injector, Task, taskService, CriteriaComponent);

//       this.pageSize = this.appConfig.pageSize;

//       // UI Customized DataTable
//       this.columns = Column.fromObjects([
//           { field: "user.name", header: "user.label_name", filter: true },
//           { field: "startDateTime", header: "user.label_start_period", filter: true, type: ColumnType.DATETIME, format: "DD/MM/YYYY" },
//           { field: "endDateTime", header: "user.label_end_period", filter: true, type: ColumnType.DATETIME, format: "DD/MM/YYYY" },
//           //  { field: 'users', header: 'users', filter: true},
//       ]);

//       this.pagination = true;
//       this.criteria = true;
//       this.criterias = AECriteriaField.fromObjects([
//           { field: "user", header: "user.label_name", value: "", type: AECriteriaType.LIST },
//           { field: "startDateTime", header: "user.label_start_period", value: "", type: AECriteriaType.DATE },
//           { field: "endDateTime", header: "user.label_end_period", value: "", type: AECriteriaType.DATE },
//           // { field: 'users', header: 'User', value: '', type: AECriteriaType.LIST }
//       ]);

//       this.columnsOperatorShift = Column.fromObjects([
//           { field: "account.profile.fullname", header: "user.label_fullname" },
//           { field: "abilities", header: "user.label_abilities", type: ColumnType.LIST, fieldArray: "label" },
//       ]);

//       // UI Customized Form Validation
//       this.formGroup = this.formBuilder.group({
//           shift: [this.selectedData.user, [Validators.required]],
//           startDateTime: [this.selectedData.startDateTime, [Validators.required]],
//           endDateTime: [this.selectedData.endDateTime, [Validators.required]]
//       }, {
//           validators: compareDate('endDateTime', 'startDateTime', '>=', "Date de fin ", "Date de début ")
//       });
//   }

//   ngOnInit(): void {
//       log.debug("ngOnInit");
//       this.initUI();
//       this.initData();
      
//       this.subscriptions.push(
//           this.usersService.getAllActivate().subscribe((result) => {
//               if (result) {
//                   this.users = result.datas;
//               }
//           })
//       );
//   }

//   // Init
//   initUI() {
//       // Do not remove
//       super.initUI();
//       log.debug("Init UI");
//   }

//   initData() {
//       // Do not remove
//       log.debug("Init Data");
//       if (this.taskService && this.taskService !== null) {
//           const subscribe = this.taskService
//               .getAll(this.pagination, this.currentPage, this.pageSize)
//               .subscribe((result) => {
//                   log.debug(result);
//                   if (result) {
//                       this.datas = result;
//                   }
//               });
//           this.subscriptions.push(subscribe);
//       }
//   }

//   onSaveClick() {
//       log.debug('Save Click HybrideComponent', this.selectedData);
//       const self = this;
//       const subscribe = this.validation().subscribe(isValid => {
//           if (isValid) {
             
//               if (this.selectedData.startDateTime > this.selectedData.endDateTime) {
//                   this.msgService.showErrorMessage('Erreur', "Veuillez choisir une period")
//                   return;
//               }
//               if (this.selectedData['id'] == null) {
//                   log.debug('Create');
//                   const subscribe = this.currentService.create(this.selectedData).subscribe(result => {
//                       log.debug('Create ok ', result);
//                       // Do not remove
//                       if (result) {
//                           this.selectedData = result;
//                           this.afterSaveOK();
//                       }
//                   });
//                   self.subscriptions.push(subscribe);
//               }
//               else {
//                   log.debug('Update');
//                   const subscribe2 = this.currentService.update(this.selectedData['id'], this.selectedData).subscribe(result => {
//                       // Do not remove
//                       if (result) {
//                           this.selectedData = result;
//                           this.afterSaveOK();
//                       }
//                   });
//                   self.subscriptions.push(subscribe2);
//               }
//           }
//       });
//       this.subscriptions.push(subscribe);
//   }

// }

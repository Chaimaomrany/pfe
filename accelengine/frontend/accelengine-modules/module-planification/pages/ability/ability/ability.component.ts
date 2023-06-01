import { Component, Injector, OnInit } from '@angular/core';
import { MsgService } from '@app/accelengine-core/services/msg.service';
import { Ability } from '@app/accelengine-modules/module-planification/models/Ability.model';
import { User } from '@app/accelengine-modules/module-planification/models/user.model';
import { AbilityService } from '@app/accelengine-modules/module-planification/services/ability.service';
import { UserService } from '@app/accelengine-modules/module-planification/services/user.service';
import { CriteriaComponent } from '@app/accelengine-shared/components/criteria/criteria.component';
import { APP_CONFIG } from '@app/app.config';
import { AECriteria, AECriteriaField, AECriteriaType, HybrideComponent, Logger} from 'accelengine-lib';
import { Column, ColumnType } from "@app/accelengine-shared/components/data-table/data-table.model";
import { Validators } from '@angular/forms';


const log = new Logger("AbilityComponent");
@Component({
  selector: 'app-ability',
  templateUrl: './ability.component.html',
  styleUrls: ['./ability.component.scss']
})
export class AbilityComponent extends HybrideComponent<Ability> implements OnInit {

  columnsOperatorShift: Column[];
  appConfig = APP_CONFIG.app;
 
  users: User[];

  constructor(
      injector: Injector,
      private abilityService: AbilityService,
     
      private usersService: UserService,
      private msgService: MsgService
  ) {
      super(injector, Ability, abilityService, CriteriaComponent);

      this.pageSize = this.appConfig.pageSize;

      // UI Customized DataTable
      this.columns = Column.fromObjects([
      
        { field: "name", header: "name", filter: true, type: ColumnType.STRING },
        { field: "description", header: "description", filter: true, type: ColumnType.STRING },
      
    ]);

      this.pagination = true;
      this.criteria = true;
      this.criterias = AECriteriaField.fromObjects([
      
           { field: "name", header: "name", value: "", type: AECriteriaType.STRING },
          { field: "description", header: "description", value: "", type: AECriteriaType.STRING},
            { field: 'users', header: 'User', value: '', type: AECriteriaType.LIST }
               
      ]);

    //    this.columnsOperatorShift = Column.fromObjects([
    //        { field: "account.profile.fullname", header: "user.label_fullname" },
    //      { field: "abilities", header: "user.label_abilities", type: ColumnType.LIST, fieldArray: "label" },
    //   ]);

  //      UI Customized Form Validation
     this.formGroup = this.formBuilder.group({
         
        name: [this.selectedData.name, [Validators.required]],
        description: [this.selectedData.description, [Validators.required]],
        // users: [this.selectedData.users, [Validators.required]]
       }
       //, {
  //         validators: compareDate('endDatePeriod', 'startDatePeriod', '>=', "Date de fin Période", "Date de début Période")
       );
    }

  ngOnInit(): void {
      log.debug("ngOnInit");
      this.initUI();
      this.initData();
      
      this.subscriptions.push(
          this.usersService.getAllActivate().subscribe((result) => {
              if (result) {
                  this.users = result.datas;
              }
          })
      );
  }

  // Init
  initUI() {
      // Do not remove
      super.initUI();
      log.debug("Init UI");
  }

  initData() {
    // Do not remove
    log.debug("Init Data");
    if (this.abilityService && this.abilityService !== null) {
        const subscribe = this.abilityService
            .getAll(this.pagination, this.currentPage, this.pageSize)
            .subscribe((result) => {
                log.debug(result);
                if (result) {
                    this.datas = result;
                }
            });
        this.subscriptions.push(subscribe);
    }
}

onSaveClick() {
    log.debug('Save Click HybrideComponent', this.selectedData);
    const self = this;
    const subscribe = this.validation().subscribe(isValid => {
        if (isValid) {
            // if (this.selectedData.users == null || this.selectedData.users.length == 0) {
            //     this.msgService.showErrorMessage('Erreur', "Veuillez choisir au moins un opérateur")
            //     return;
            // }
           
            if (this.selectedData['id'] == null) {
                log.debug('Create');
                const subscribe = this.currentService.create(this.selectedData).subscribe(result => {
                    log.debug('Create ok ', result);
                    // Do not remove
                    if (result) {
                        this.selectedData = result;
                        this.afterSaveOK();
                    }
                });
                self.subscriptions.push(subscribe);
            }
            else {
                log.debug('Update');
                const subscribe2 = this.currentService.update(this.selectedData['id'], this.selectedData).subscribe(result => {
                    // Do not remove
                    if (result) {
                        this.selectedData = result;
                        this.afterSaveOK();
                    }
                });
                self.subscriptions.push(subscribe2);
            }
        }
    });
    this.subscriptions.push(subscribe);
}

}

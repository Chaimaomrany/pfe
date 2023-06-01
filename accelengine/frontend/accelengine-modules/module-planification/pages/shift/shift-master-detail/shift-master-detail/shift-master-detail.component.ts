import { Component, Injector, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { compareDate } from '@app/accelengine-core/helpers/validators/date.validator';

// Components
import { CriteriaComponent } from '@app/accelengine-shared/components/criteria/criteria.component';
import { AECriteriaField, HybrideComponent } from 'accelengine-lib';

// Models

import { Column, ColumnType } from '@app/accelengine-shared/components/data-table/data-table.model';
import { AECriteria, AECriteriaType } from 'accelengine-lib';

// Services

// Helpers
import { APP_CONFIG } from '@app/app.config';
import { Logger } from 'accelengine-lib';
import { User } from '@app/accelengine-modules/module-planification/models/user.model';
import { Shift } from '@app/accelengine-modules/module-planification/models/shift.model';
import { ShiftService } from '@app/accelengine-modules/module-planification/services/shift.service';
const log = new Logger('ShiftMasterDetailComponent');
@Component({
  selector: 'app-shift-master-detail',
  templateUrl: './shift-master-detail.component.html',
  animations: APP_CONFIG.app.animations
})
export class ShiftMasterDetailComponent extends HybrideComponent<Shift> implements OnInit {

  appConfig = APP_CONFIG.app;
  users: User[];

  constructor(injector: Injector,
    private shiftService: ShiftService) {

    super(injector, Shift, shiftService, CriteriaComponent);
    this.pageSize = this.appConfig.pageSize;

    // UI Customized DataTable
    this.columns = Column.fromObjects([
      { field: 'name', header: 'user.label_name', filter: true },
      { field: 'startTimeWork', header: 'user.label_start_time', filter: true, type: ColumnType.DATETIME, format: 'HH:mm' },
      { field: 'endTimeWork', header: 'user.label_end_time', filter: true, type: ColumnType.DATETIME, format: 'HH:mm' }
    ]);

    this.formGroup = this.formBuilder.group({
      id: [this.selectedData.id],
      name: [this.selectedData.name, [Validators.required]],
      startTimeWork: [this.selectedData.startTimeWork, [Validators.required]],
      endTimeWork: [this.selectedData.endTimeWork, [Validators.required]]
    }, {
      validators: compareDate('endTimeWork', 'startTimeWork', '>=', "Heure de fin des traveaux", "Heure de debut des traveaux")
    });

    this.pagination = true;
    this.criteria = true;

    this.criterias = AECriteriaField.fromObjects([
      { field: 'name', header: 'user.label_name', value: '', type: AECriteriaType.STRING },
      { field: 'startTimeWork', header: 'user.label_start_time', value: '', type: AECriteriaType.DATE },
      { field: 'endTimeWork', header: 'user.label_end_time', value: '', type: AECriteriaType.DATE },
    ]);

  }

  ngOnInit(): void {
    log.debug('ngOnInit');
    this.initUI();
    super.initData();
  }

  // Init
  initUI() {
    // Do not remove
    super.initUI();
    log.debug('Init UI');
  }

  onSaveClick() {
    log.debug('Save Click HybrideComponent', this.selectedData);
    const self = this;
    const subscribe = this.validation().subscribe(isValid => {
      if (isValid) {
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

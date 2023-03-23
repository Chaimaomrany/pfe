import { Component, OnInit, Injector, Input, Output, EventEmitter } from '@angular/core';
import { AbstractControl, FormArray } from '@angular/forms';

// Component
import { AECriteriaField, FormPopupComponent } from 'accelengine-lib';

// Models
import { Setting, VALUE_TYPE } from '@app/accelengine-std/models/application.model';
import { Account } from '@app/accelengine-core/models/account.model';
import { CodeNameEntity, AECriteriaType } from 'accelengine-lib';

//Services
import { AccountService } from '@app/accelengine-core/services/account.service';
import { StorageService } from '@app/accelengine-core/services/storage.service';
import { CriteriaSubmitService } from '@app/accelengine-core/services/criteria-submit.service';

// Helpers
import { Logger } from 'accelengine-lib';


const log = new Logger('CriteriaComponent');

@Component({
  selector: 'app-criteria',
  templateUrl: 'criteria.component.html',
  styleUrls: ['criteria.component.scss'],
})
export class CriteriaComponent extends FormPopupComponent<AECriteriaField> implements OnInit {

  @Input() storable: boolean = true;
  @Input() data: any;
  @Output() onSubmitClick: EventEmitter<any> = new EventEmitter<any>();
  @Output() onResetClick: EventEmitter<any> = new EventEmitter<any>();
  @Output() onCancelClick: EventEmitter<any> = new EventEmitter<any>();

  stringOperations: CodeNameEntity[] = CodeNameEntity.fromObjects([
    { code: '=CONTAIN=', name: 'Contient' },
    { code: '=NOTCONTAIN=', name: 'Ne contient pas' },
    { code: '==', name: 'Égal à' },
    { code: '!=', name: 'N\'est pas égal à' },
    { code: '=BEGINWITH=', name: 'Commence par' },
    { code: '=NOTBEGINWITH=', name: 'Ne commence pas par' },
    { code: '=ENDWITH=', name: 'Se terminant par' },
    { code: '=NOTENDWITH=', name: 'Ne se terminant pas par' }
  ]);

  numberOperations: CodeNameEntity[] = CodeNameEntity.fromObjects([
    { code: '==', name: 'Égal à' },
    { code: '!=', name: 'N\'est pas égal à' },
    { code: '<', name: 'Inférieur à' },
    { code: '<=', name: 'Inférieur ou égal à' },
    { code: '>', name: 'Supérieur à' },
    { code: '>=', name: 'Supérieur ou égal à' }
  ]);

  listOperations: CodeNameEntity[] = CodeNameEntity.fromObjects([
    { code: '==', name: 'Égal à' },
    { code: '!=', name: 'N\'est pas égal à' }
  ]);

  dateOperations: CodeNameEntity[] = CodeNameEntity.fromObjects([
    { code: '=DATEEQUAL=', name: 'Égal à' },
    { code: '=DATENOTEQUAL=', name: 'N\'est pas égal à' },
    { code: '=DATELESSTHAN=', name: 'Inférieur à' },
    { code: '=DATELESSTHANOREQUAL=', name: 'Inférieur ou égal à' },
    { code: '=DATEGREATERTHAN=', name: 'Supérieur à' },
    { code: '=DATEGREATERTHANOREQUAL=', name: 'Supérieur ou égal à' }
  ]);

  allOperations: CodeNameEntity[] = CodeNameEntity.fromObjects([
    { code: '==', name: 'Égal à' },
    { code: '=BEGINWITH=', name: 'Commence par' },
    { code: '=ENDWITH=', name: 'Se terminant par' },
    { code: '=CONTAIN=', name: 'Contient' },
    { code: '!=', name: 'N\'est pas égal à' },
    { code: '=NOTBEGINWITH=', name: 'Ne commence pas par' },
    { code: '=NOTENDWITH=', name: 'Ne se terminant pas par' },
    { code: '=NOTCONTAIN=', name: 'Ne contient pas' },
    { code: '<', name: 'Inférieur à' },
    { code: '<=', name: 'Inférieur ou égal à' },
    { code: '>', name: 'Supérieur à' },
    { code: '>=', name: 'Supérieur ou égal à' }
  ]);

  multiselectOperations: CodeNameEntity[] = CodeNameEntity.fromObjects([
    { code: '=in=', name: 'Appartient' },
    { code: '=out=', name: 'N\'appartient pas' }
  ]);

  current_account: Account;
  current_menu_code: string;
  current_criterias: any[]

  constructor(
    private injector: Injector,
    private accountService: AccountService,
    private storageService: StorageService,
    private criteriaSubmitService: CriteriaSubmitService
  ) {
    super(injector, AECriteriaField);
  }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      criterias: this.formBuilder.array([])
    });
    if (this.data) {
      this.criterias = this.data['criterias'];
    }
    log.info('ngOnInit ', this.data, this.criterias);
    this.initFromStorage();
    if (this.criterias && this.criterias != null) {
      const controlArray = <FormArray>this.formGroup.get('criterias');
      this.criterias.forEach(criteria => {
        if ((criteria.type === AECriteriaType.NUMBER || criteria.type === AECriteriaType.DATE) && (!criteria.value || criteria.value === '')) {
          criteria.value = null; // TODO : change
        }
        if (criteria.display !== false) {
          controlArray.push(this.initCriteria(criteria));
        }
      });
    }
  }

  initFromStorage(): void {
    log.info("initFromStorage", this.storable);
    if (this.storable) {
      this.current_account = this.storageService.getCurrentAccount();
      this.current_menu_code = this.storageService.get(StorageService.CURRENT_MENU_CODE);
      if (this.current_menu_code) {
        let current_criterias_values = this.storageService.get(this.current_menu_code);
        if (current_criterias_values) {
          current_criterias_values = JSON.parse(current_criterias_values);
          if (current_criterias_values && current_criterias_values !== null && current_criterias_values.length > 0) {
            this.criterias = current_criterias_values.map((criteria) => AECriteriaField.fromObject(criteria));
          }
        }
      }
    }
  }

  initCriteria(criteriaField: AECriteriaField) {
    if ((criteriaField.type === AECriteriaType.NUMBER || criteriaField.type === AECriteriaType.DATE)
      && (!criteriaField.value || criteriaField.value === '')) {
      criteriaField.value = null;
    }

    return this.formBuilder.group({
      field: [criteriaField.field],
      header: [criteriaField.header],
      operation: [criteriaField.operation],
      type: [criteriaField.type],
      value: [criteriaField.value],
      typeCode: [criteriaField.typeCode],
      dateFormat: [criteriaField.dateFormat],
      values: [criteriaField.values],
      displayField: [criteriaField.displayField],
      returnValue: [criteriaField.returnValue],
      dataKey: [criteriaField.dataKey],
      customOperations: [criteriaField.customOperations]
    });
  }

  getCriterias() {
    return (<FormArray>this.formGroup.get('criterias')).controls;
  }

  getOperations(criteriaField: AECriteriaField) {
    if (criteriaField.customOperations) {
      return criteriaField.customOperations;
    }
    switch (criteriaField.type) {
      case AECriteriaType.NUMBER: {
        return this.numberOperations;
      }
      case AECriteriaType.DATE: {
        return this.dateOperations;
      }
      case AECriteriaType.STRING: {
        return this.stringOperations;
      }
      case AECriteriaType.LIST:
      case AECriteriaType.DICTIONARY: {
        return this.listOperations;
      }
      case AECriteriaType.MULTISELECT: {
        return this.multiselectOperations;
      }
      default: {
        return this.allOperations;
      }
    }
  }

  getCriteriaFormAt(i: number): AbstractControl {
    return (<FormArray>this.formGroup.get('criterias')).at(i);
  }

  onReset(): void {
    const controls = (<FormArray>this.formGroup.get('criterias')).controls;
    controls.forEach(control => {
      control.get('value').setValue(null);
    });
    this.onResetClick.emit(true);
  }

  onSubmit(): void {
    this.getCriterias().forEach((control, index) => {
      let value: any = control.get('value').value;
      if ((value === undefined || value === null) && this.criterias[index].type !== AECriteriaType.NUMBER) {
        control.get('value').setValue('');
      }
      if (this.criterias[index].type === AECriteriaType.NUMBER && value === 0) {
        control.get('value').setValue(0);
      }
      if (this.criterias[index].type === AECriteriaType.NUMBER && (value === '' || value === null || value === undefined)) {
        control.get('value').setValue('');
      }
    });
    let valueCriterias: AECriteriaField[] = this.getCriterias().map((res) => res.value);
    if (this.storable) {
      const sub = this.accountService.updateAccountSettings(this.current_account.id, this.createAccountSetting(this.current_menu_code)).subscribe(
        (res) => {
          if (res) {
            // update data Criterias
            this.storageService.set(this.current_menu_code, res.criterias);
            this.criteriaSubmitService.criteriaSubmit.next(true);
            super.onSubmit();
          }
        },
        err => {
          super.onSubmit();
        });
      this.subscriptions.push(sub);
    } else {
      this.onSubmitClick.emit(valueCriterias);
      super.onSubmit();
    }
  }

  onCancel(): void {
    super.onCancel();
    this.onCancelClick.emit(true);
  }

  createAccountSetting(current_menu_code) {
    let setting = new Setting();
    setting.code = current_menu_code;
    setting.name = `Criterias ${current_menu_code}`;
    setting.type = VALUE_TYPE.STRING;
    setting.criterias = JSON.stringify(this.formGroup.get('criterias').value);
    setting.displayOrder = 1000;
    return setting;
  }
}

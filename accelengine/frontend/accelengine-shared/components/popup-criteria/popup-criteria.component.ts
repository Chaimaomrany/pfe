import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Subscription } from 'rxjs';

// Components

// Models
import { InputForm } from '../forms/input-form';
import { AECriteriaField, AEEntity, AEList, CrudAPIService } from 'accelengine-lib';
import { Column } from '../data-table/data-table.model';

// Services
import { MsgService } from '@app/accelengine-core/services/msg.service';

// Helpers
import { APP_CONFIG } from '@app/app.config';
import * as moment from "moment";

@Component({
  selector: 'app-popup-criteria',
  templateUrl: './popup-criteria.component.html',
  styleUrls: ['./popup-criteria.component.scss'],
  //changeDetection: ChangeDetectionStrategy.OnPush
})
export class PopupCriteriaComponent extends InputForm implements OnChanges, OnInit, OnDestroy {

  @Input() service: CrudAPIService<AEEntity>;
  @Input() criteriasFields: AECriteriaField[] = [];
  @Input() displayedColumns: Column[] = [];
  @Input() displayValue: string;
  @Input() displayDate: boolean = false;
  @Input() returnValue: string;

  appConfig = APP_CONFIG.app;
  currentPage: number = 0;
  pageSize: number = this.appConfig.pageSize;
  valueFormControl: any;
  criterias: AECriteriaField[];
  datas: AEList<any>;
  subscriptions: Array<Subscription> = [];
  displayPopup: boolean = false;

  constructor(private msgService: MsgService) {
    super();
  }

  ngOnChanges(changes: SimpleChanges): void {
    // if (changes && changes["value"] && changes["value"].currentValue) {
    //   this.setDisplayValue(changes["value"].currentValue.value);
    // }
  }

  ngOnInit(): void {
    this.subscriptions.push(this.formGroup.valueChanges.subscribe((res) => {
      if (res) {
        this.setDisplayValue(this.formGroup.get(this.name).value);
      }
    }));
  }

  initData(): void {
    if (this.service) {
      this.subscriptions.push(this.service.getByCriteria({ criteriaFields: this.criterias }, true, this.currentPage, this.pageSize).subscribe((result: AEList<any>) => {
        this.datas = result;
      }));
    } else {
      this.msgService.showWarnMessage("Advertisement", "Aucun service est passé comme paramètre!");
    }
  }

  onSearchClick(): void {
    this.displayPopup = true;
  }

  onResetClick(): void {
    this.formGroup.get(this.name).setValue(null);
    this.valueFormControl = null;
  }

  onDblclickRow(row: any): void {
    this.displayPopup = false;
    this.setFormValue(row);
  }


  setFormValue(data: any): void {
    if (data) {
      let formValue: any = data;
      if (this.returnValue) {
        formValue = formValue[this.returnValue];
      }
      this.formGroup.get(this.name).setValue(formValue);
      this.setDisplayValue(data);
    }
  }

  setDisplayValue(data: any): void {
    if (data) {
      this.valueFormControl = data;
      if (this.displayValue) {
        if (data[this.displayValue]) {
          this.valueFormControl = data[this.displayValue];
        }
      }
      if (this.displayDate) {
        this.valueFormControl = moment(this.valueFormControl).format("DD/MM/YYYY HH:mm:ss");
      }
    }
  }

  onPageChanged(infoPage: string) {
    const splitted = infoPage.split(",");
    this.currentPage = Number(splitted[0]);
    this.pageSize = Number(splitted[1]);
    this.initData();
  }

  onSubmitClick(event: AECriteriaField[]): void {
    if (event) {
      this.criterias = event;
      this.initData();
    } else {
      this.msgService.showWarnMessage("Advertisement", "Aucun critère de recherche n'est renseigné!");
    }
  }

  onCancelClick(event): void {
    this.displayPopup = false;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}

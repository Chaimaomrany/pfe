import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';

// Service
import { ExportFileService } from '@app/accelengine-core/services/exportfile.service';
import { StorageService } from '@app/accelengine-core/services/storage.service';
import { CriteriaSubmitService } from '@app/accelengine-core/services/criteria-submit.service';

@Component({
  selector: 'app-menu-master',
  templateUrl: './menu-master.component.html',
  styleUrls: ['./menu-master.component.scss']
})
export class MenuMasterComponent implements OnInit {
  @Input() criteria: boolean = false;
  @Input() displayAdd: boolean = true;
  @Input() displayExport: boolean = false;

  @Output() addClick: EventEmitter<any> = new EventEmitter<any>();
  @Output() criteriaClick: EventEmitter<any> = new EventEmitter<any>();
  @Output() reloadClick: EventEmitter<any> = new EventEmitter<any>();

  nbrCriterias: number = 0;
  current_menu_code: string;

  constructor(
    private exportFileService: ExportFileService,
    private storageService: StorageService,
    private criteriaSubmitService: CriteriaSubmitService
  ) { }

  ngOnInit() {
    this.initNbrCriterias();
    this.criteriaSubmitService.criteriaSubmit.subscribe((response) => {
      this.initNbrCriterias();
    });
  }

  initNbrCriterias() {
    this.current_menu_code = this.storageService.get(StorageService.CURRENT_MENU_CODE);
    if (this.current_menu_code) {
      let criteriasValues = this.storageService.get(this.current_menu_code);
      this.nbrCriterias = criteriasValues ? this.getCriteriaNumber(JSON.parse(criteriasValues)) : 0;
    }
  }

  getCriteriaNumber(value: any) {
    return value.filter((val) => {
      return val.value.length > 0;
    }).length
  }

  onAddClick() {
    this.addClick.emit(true);
  }


  onCriteriaClick() {
    this.criteriaClick.emit(true);
  }

  onReloadClick() {
    this.reloadClick.emit(true);
  }

  exportPdf() {
    this.exportFileService.exportPdf.next(true);
  }

  exportExcel() {
    this.exportFileService.exportExcel.next(true);
  }

  exportCSV(): void {
    this.exportFileService.exportCSV.next(true);
  }

  manyCriterias() {
    return this.nbrCriterias > 0;
  }
}

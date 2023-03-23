import { Component, OnInit, OnDestroy, Injector } from '@angular/core';
import { Validators } from '@angular/forms';

// Component
import { HybrideComponent } from 'accelengine-lib';
import { Column, ColumnType } from '@shared/components/data-table/data-table.model';

// Models
import { BatchExecution, BATCH_STATUS_LIST } from '@std/models/batch.model';

// Services
import { BatchExecutionService } from '@std/services/batch.execution.service';
import { BatchsWebsocketService } from '@std/services/batchs.websocket.service';

// Helpers
import { APP_CONFIG } from '@app/app.config';
import { Logger } from 'accelengine-lib';


const log = new Logger('BatchExecutionComponent');

@Component({
  templateUrl: 'batch-execution.component.html',
  styleUrls: ['./batch-execution.component.scss'],
  animations: APP_CONFIG.app.animations
})
export class BatchExecutionComponent extends HybrideComponent<BatchExecution> implements OnInit, OnDestroy {

  fileLogContent: string;
  fileErrorContent: string;

  constructor(
    injector: Injector,
    private batchExecutionService: BatchExecutionService,
    private batchsWebsocketService: BatchsWebsocketService
  ) {
    super(injector, BatchExecution, batchExecutionService, null);
    // UI Customized DataTable
    this.columns = Column.fromObjects([
      { field: 'code', header: 'std.field_code', filter: true },
      { field: 'name', header: 'std.field_name', filter: true },
      { field: 'description', header: 'std.field_description', filter: true },
      { field: 'startTime', header: 'std.field_start', type: ColumnType.DATETIME, format: 'DD/MM/YYYY HH:mm' },
      { field: 'endTime', header: 'std.field_end', type: ColumnType.DATETIME, format: 'DD/MM/YYYY HH:mm' },
      { field: 'batchStatus', header: 'std.field_status', filter: true, list: BATCH_STATUS_LIST }
    ]);

    this.pagination = true;
    //this.criteria = true;

    // UI Customized Form Validation
    this.formGroup = this.formBuilder.group({
      id: [this.selectedData.id],
      name: [this.selectedData.name],
      description: [this.selectedData.description],
    });
  }

  ngOnInit(): void {
    log.debug('ngOnInit');
    this.initUI();
    this.initData();
  }

  // Init
  initUI() {
    // Do not remove
    super.initUI();
    log.debug('Init UI');
  }

  initData() {
    log.debug('Init Data');
    // Do not remove
    super.initData();
    const subscribe = this.batchsWebsocketService.getObservable().subscribe({
      next: this.onBatchUpdated,
      error: err => {
        log.error(err);
      }
    });
    this.subscriptions.push(subscribe);

  }

  private onBatchUpdated = data => {
    if (data.type === 'SUCCESS') {
      this.datas = data.message
    }
  }

  onDblclickRow(row: BatchExecution) {
    super.onDblclickRow(row);

    this.fileLogContent = null;
    this.fileErrorContent = null;

    if (row.fileLogPath != "") {
      const subscribe = this.batchExecutionService.getBatchLog(row.id).subscribe(result => {
        if (result) {
          this.fileLogContent = result;
        }
      });
      this.subscriptions.push(subscribe);
    }

    if (row.fileErrorPath != "") {
      const subscribe2 = this.batchExecutionService.getBatchError(row.id).subscribe(result => {
        if (result) {
          this.fileErrorContent = result;
        }
      });
      this.subscriptions.push(subscribe2);
    }
  }

  ngOnDestroy() {
    /**
     *  Unsubscribe from events
     */
    log.debug('On Destroy');
    this.batchsWebsocketService.unsubscribeFromWebSocketEvent(APP_CONFIG.app.prefixBatchSocket);
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}

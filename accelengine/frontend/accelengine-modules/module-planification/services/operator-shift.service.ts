import { Injectable, Injector } from '@angular/core';
import { APP_CONFIG } from '@app/app.config';
import { CrudAPIService } from 'accelengine-lib';
import { OperatorShift } from '../models/OperatorShift.model';

@Injectable({
  providedIn: 'root'
})
export class OperatorShiftService extends CrudAPIService<OperatorShift> {

    constructor(
        private injector: Injector) {
        super(injector);
        this.endpointService = APP_CONFIG.apiBaseUrl + '/user/affectation-operateur-shift';
      }
}

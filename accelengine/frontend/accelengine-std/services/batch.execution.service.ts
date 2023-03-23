import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

// Models
import { BatchExecution } from '../models/batch.model'

// Services
import { CrudAPIService } from 'accelengine-lib';

// Helpers
import { APP_CONFIG } from '@app/app.config';
import { Logger } from 'accelengine-lib';

const log = new Logger('BatchExecutionService');

@Injectable({ providedIn: 'root' })
export class BatchExecutionService extends CrudAPIService<BatchExecution> {

    constructor(
        private injector: Injector) {
        super(injector);
        this.endpointService = APP_CONFIG.apiBaseUrl + '/batch/execution';
    }

    getBatchLog(id: number): Observable<any> {
        log.debug('Get Batch Log');
        const url = `${this.endpointService}/getlogfile/${id}`;
        let opt = { responseType: 'text' as 'text' };
        return this.http.get(url, opt);
    }

    getBatchError(id: number): Observable<any> {
        log.debug('Get Batch Error');
        const url = `${this.endpointService}/geterrorfile/${id}`;
        let opt = { responseType: 'text' as 'text' };
        return this.http.get(url, opt);
    }

}
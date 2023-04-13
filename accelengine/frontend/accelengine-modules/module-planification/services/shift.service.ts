import { Injectable, Injector } from '@angular/core';
import { APP_CONFIG } from '@app/app.config';
import { CrudAPIService } from 'accelengine-lib';
import { Shift } from '../models/shift.model';

@Injectable({
  providedIn: 'root'
})
export class ShiftService extends CrudAPIService<Shift> {

    constructor(
        private injector: Injector) {
        super(injector);
        this.endpointService = APP_CONFIG.apiBaseUrl + '/user/shift';
      }

    //   findAllbyShifts(listUsers: number[]): Observable<User> {
    //     return this.http.get<User>(`${this.endpointService}/findallbyroles/${listUsers}`);
    //   }
}

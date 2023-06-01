import { Injectable, Injector } from '@angular/core';
import { Placement } from '../models/placement';
import { CrudAPIService } from 'accelengine-lib';
import { APP_CONFIG } from '@app/app.config';

@Injectable({
  providedIn: 'root'
})
export class PlacementService  extends CrudAPIService<Placement> {

  constructor(
    private injector: Injector) {
    super(injector);
    this.endpointService = APP_CONFIG.apiBaseUrl + '/user/placement';
  }
}

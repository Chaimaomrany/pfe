import { Injectable, Injector } from '@angular/core';
import { Task } from '../models/Task.model';
import { CrudAPIService } from 'accelengine-lib';
import { APP_CONFIG } from '@app/app.config';

@Injectable({
  providedIn: 'root'
})
export class TaskService extends CrudAPIService<Task> {

  constructor(
    private injector: Injector) {
    super(injector);
    this.endpointService = APP_CONFIG.apiBaseUrl + '/user/affectation-task';
  }
}

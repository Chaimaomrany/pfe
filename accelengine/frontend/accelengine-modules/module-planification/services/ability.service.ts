import { Injectable, Injector } from '@angular/core';
import { Ability } from '../models/Ability.model';
import { APP_CONFIG } from '@app/app.config';
import { CrudAPIService } from 'accelengine-lib';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AbilityService extends CrudAPIService<Ability> {
  endpointService: string;

  constructor(
      private injector: Injector) {
      super(injector);
      this.endpointService = APP_CONFIG.apiBaseUrl + '/user/ability';
    }
    findTypeByCode(code: string): Observable<Ability> {
      return this.http.get<Ability>(`${this.endpointService}/typebycode/${code}`);
    }
   
  
    findAllValuesByTypeCode(name: string): Observable<Ability[]> {
      return this.http.get<Ability[]>(`${this.endpointService}/valuesbytype/${name}`);
    }
  
  
    
}

import { Injectable, Injector } from '@angular/core';

// Models
import { DictionaryType } from '../models/dictionaryType.model';

// Services
import { CrudAPIService } from 'accelengine-lib';

import { APP_CONFIG } from '@app/app.config';
import { Observable } from 'rxjs';
import { DictionaryValue } from '../models/dictionaryValue.model';

@Injectable({
  providedIn: 'root'
})
export class DictionaryTypeService extends CrudAPIService<DictionaryType>{

  constructor(
    private injector: Injector) {
    super(injector);
    this.endpointService = APP_CONFIG.apiBaseUrl + '/dictionary';
  }

  findTypeByCode(code: string): Observable<DictionaryType> {
    return this.http.get<DictionaryType>(`${this.endpointService}/typebycode/${code}`);
  }

  findAllValuesByTypeCode(type: string): Observable<DictionaryValue[]> {
    return this.http.get<DictionaryValue[]>(`${this.endpointService}/valuesbytype/${type}`);
  }

}

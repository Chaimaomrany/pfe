import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

// Models
import { Import } from '../models/import.model';

// Services

import { APP_CONFIG } from '@app/app.config';



@Injectable({ providedIn: 'root' })
export class ImportExportService {

    endpointService: string;

    constructor(
        private http: HttpClient) {
        this.endpointService = APP_CONFIG.apiBaseUrl + '/importexport';
    }

    import(values: Import): Observable<any> {
        const formData: FormData = new FormData();
        formData.append("code", values.type.code + '');
        formData.append("file", values.file);
        const url = `${this.endpointService}/import`;
        return this.http.post<any>(url, formData);
    }
}
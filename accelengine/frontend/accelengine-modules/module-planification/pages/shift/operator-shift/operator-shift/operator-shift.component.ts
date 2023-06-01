
import { Component, Injector, OnInit } from "@angular/core";
import { Validators } from "@angular/forms";
import { MsgService } from "@app/accelengine-core/services/msg.service";

// Components
import { CriteriaComponent } from "@app/accelengine-shared/components/criteria/criteria.component";
import { AECriteriaField, HybrideComponent } from "accelengine-lib";

// Models

import { Column, ColumnType } from "@app/accelengine-shared/components/data-table/data-table.model";
import { AECriteria, AECriteriaType, Logger } from "accelengine-lib";

//Services

// Helpers
import { compareDate } from "@app/accelengine-core/helpers/validators/date.validator";
import { APP_CONFIG } from "@app/app.config";
import { OperatorShift } from "@app/accelengine-modules/module-planification/models/OperatorShift.model";
import { Shift } from "@app/accelengine-modules/module-planification/models/shift.model";
import { User } from "@app/accelengine-modules/module-planification/models/user.model";
import { OperatorShiftService } from "@app/accelengine-modules/module-planification/services/operator-shift.service";
import { ShiftService } from "@app/accelengine-modules/module-planification/services/shift.service";
import { UserService } from "@app/accelengine-modules/module-planification/services/user.service";

const log = new Logger("OperatorShiftComponent");
@Component({
    selector: "app-operator-shift",
    templateUrl: "./operator-shift.component.html",
    animations: APP_CONFIG.app.animations,
})
export class OperatorShiftComponent extends HybrideComponent<OperatorShift> implements OnInit {

    columnsOperatorShift: Column[];
    appConfig = APP_CONFIG.app;
    shifts: Shift[] = [];
    users: User[];

    constructor(
        injector: Injector,
        private operatorShiftService: OperatorShiftService,
        private shiftService: ShiftService,
        private usersService: UserService,
        private msgService: MsgService
    ) {
        super(injector, OperatorShift, operatorShiftService, CriteriaComponent);

        this.pageSize = this.appConfig.pageSize;

        // UI Customized DataTable
        this.columns = Column.fromObjects([
            { field: "shift.name", header: "equipe", filter: true },
            { field: "startDatePeriod", header: "debutdeperiode", filter: true, type: ColumnType.DATETIME, format: "DD/MM/YYYY" },
            { field: "endDatePeriod", header: "findeperiode", filter: true, type: ColumnType.DATETIME, format: "DD/MM/YYYY" },
              //{ field: 'users', header: 'users', filter: true},
        ]);

        this.pagination = true;
        this.criteria = true;
        this.criterias = AECriteriaField.fromObjects([
            { field: "shift", header: "equipe", value: "", type: AECriteriaType.LIST },
            { field: "startDatePeriod", header: "debutdeperiode", value: "", type: AECriteriaType.DATE },
            { field: "endDatePeriod", header: "findeperiode", value: "", type: AECriteriaType.DATE },
             { field: 'users', header: 'User', value: '', type: AECriteriaType.LIST }
        ]);

        this.columnsOperatorShift = Column.fromObjects([
            { field: "account.profile.fullname", header: "user.label_fullname" },
            { field: "abilities.name", header: "user.label_abilities", type: ColumnType.LIST, fieldArray: "label" },
        ]);

        // UI Customized Form Validation
        this.formGroup = this.formBuilder.group({
            shift: [this.selectedData.shift, [Validators.required]],
            startDatePeriod: [this.selectedData.startDatePeriod, [Validators.required]],
            endDatePeriod: [this.selectedData.endDatePeriod, [Validators.required]]
        }, {
            validators: compareDate('endDatePeriod', 'startDatePeriod', '>=', "Date de fin Période", "Date de début Période")
        });
    }

    ngOnInit(): void {
        log.debug("ngOnInit");
        this.initUI();
        this.initData();
        this.subscriptions.push(
            this.shiftService.getAllActivate().subscribe((result) => {
                if (result) {
                    this.shifts = result.datas;
                }
            })
        );
        this.subscriptions.push(
            this.usersService.getAllActivate().subscribe((result) => {
                if (result) {
                    this.users = result.datas;
                }
            })
        );
    }

    // Init
    initUI() {
        // Do not remove
        super.initUI();
        log.debug("Init UI");
    }

    initData() {
        // Do not remove
        log.debug("Init Data");
        if (this.operatorShiftService && this.operatorShiftService !== null) {
            const subscribe = this.operatorShiftService
                .getAll(this.pagination, this.currentPage, this.pageSize)
                .subscribe((result) => {
                    log.debug(result);
                    if (result) {
                        this.datas = result;
                    }
                });
            this.subscriptions.push(subscribe);
        }
    }

    onSaveClick() {
        log.debug('Save Click HybrideComponent', this.selectedData);
        const self = this;
        const subscribe = this.validation().subscribe(isValid => {
            if (isValid) {
                if (this.selectedData.users == null || this.selectedData.users.length == 0) {
                    this.msgService.showErrorMessage('Erreur', "Veuillez choisir au moins un opérateur")
                    return;
                }
                if (this.selectedData.startDatePeriod > this.selectedData.endDatePeriod) {
                    this.msgService.showErrorMessage('Erreur', "Veuillez choisir une period")
                    return;
                }
                if (this.selectedData['id'] == null) {
                    log.debug('Create');
                    const subscribe = this.currentService.create(this.selectedData).subscribe(result => {
                        log.debug('Create ok ', result);
                        // Do not remove
                        if (result) {
                            this.selectedData = result;
                            this.afterSaveOK();
                        }
                    });
                    self.subscriptions.push(subscribe);
                }
                else {
                    log.debug('Update');
                    const subscribe2 = this.currentService.update(this.selectedData['id'], this.selectedData).subscribe(result => {
                        // Do not remove
                        if (result) {
                            this.selectedData = result;
                            this.afterSaveOK();
                        }
                    });
                    self.subscriptions.push(subscribe2);
                }
            }
        });
        this.subscriptions.push(subscribe);
    }

}

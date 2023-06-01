import { AfterViewChecked, ChangeDetectorRef, Component, Injector, OnInit } from "@angular/core";
import { Validators } from "@angular/forms";
import { remove } from 'lodash';

//Components
import { CriteriaComponent } from "@shared/components/criteria/criteria.component";
import { AECriteriaField, AEList, HybrideComponent } from "accelengine-lib";

//Models
import { Role } from "@app/accelengine-core/models/account.model";

import { DictionaryValue } from "@app/accelengine-std/models/dictionaryValue.model";
import { Column, ColumnType } from "@shared/components/data-table/data-table.model";
import { AECriteria, AECriteriaType } from "accelengine-lib";
import { TreeNode } from "primeng/api";
import { Printer } from "@app/accelengine-std/models/printer.model";

//Services

import { DictionaryTypeService } from "@app/accelengine-std/services/dictionary-type.service";
import { RoleService } from "@app/accelengine-std/services/role.service";
import { PrinterService } from "@app/accelengine-std/services/printer.service";

// Helpers
import { APP_CONFIG } from "@app/app.config";
import { Logger } from "accelengine-lib";
import { cloneDeep } from "lodash";
import { User } from "@app/accelengine-modules/module-planification/models/user.model";
import { UserService } from "@app/accelengine-modules/module-planification/services/user.service";
import { Placement } from "@app/accelengine-modules/module-planification/models/placement";
import { Ability } from "@app/accelengine-modules/module-planification/models/Ability.model";
import { AbilityService } from "@app/accelengine-modules/module-planification/services/ability.service";
import { TaskComponent } from "../../../task/task/task.component";
const log = new Logger("UserMasterDetailComponent");

@Component({
    selector: "app-user-master-detail",
    templateUrl: "./user-master-detail.component.html",
    animations: APP_CONFIG.app.animations,
})
export class UserMasterDetailComponent extends HybrideComponent<User> implements OnInit, AfterViewChecked {
    appConfig = APP_CONFIG.app;
    selectedPlacment: Placement[];
    displayAccount: boolean = false;
    displayChief: boolean = false;
    listChiefs: User[];
    allChiefs: User[];
    allRoles: Role[];
    columnsAbilities: Column[];
    abilities: Ability[];
    //allAbilitys: Ability[];
  
    trees: TreeNode[] = [];
    selection: TreeNode[] = [];
    printers: Printer[] = [];

    constructor(
        injector: Injector,
        private userService: UserService,
        private abilityService: AbilityService,

        private roleService: RoleService,
        private changeDetectorRef: ChangeDetectorRef,
        private dictionaryTypeService: DictionaryTypeService,
        private printerService: PrinterService
    ) {
        super(injector, User, userService, CriteriaComponent);

 
        this.pageSize = this.appConfig.pageSize;
        // UI Customized DataTable
        this.columns = Column.fromObjects([
            { field: "code", header: "user.label_code", filter: true },
            { field: "account.profile.fullname", header: "user.label_fullname", filter: true },
            { field: "role.name", header: "user.label_role", filter: true },
            { field: "service.label", header: "user.label_service", filter: true },
            { field: "account.contact.mainPhone", header: "user.label_phone", filter: true },
           // { field: 'abilities', header: "user.label_abilities", filter: true, type: ColumnType.LIST},
        ]);
        this.columnsChild = Column.fromObjects([
            {  field: 'startDateTime', header: 'startDateTime'  },
            { field: 'endDateTime', header: 'endDateTime' },
            { field: 'details', header: 'Details', buttonLabel: 'view', type: ColumnType.BUTTON }
          ]);

        this.pagination = true;
        this.criteria = true;

        this.criterias = AECriteriaField.fromObjects([
            { field: 'code', header: "user.label_code", value: '', type: AECriteriaType.STRING },
            { field: 'account.profile.lastname', header: "user.label_lastname", value: '', type: AECriteriaType.STRING },
            { field: 'account.profile.firstname', header: "user.label_firstname", value: '', type: AECriteriaType.STRING },
            { field: 'role.id', header: "user.label_role", value: '', type: AECriteriaType.LIST, displayField: 'name', returnValue: 'id', values: [] },
            { field: 'service.label', header: "user.label_service", value: '', type: AECriteriaType.DICTIONARY, typeCode: "SERVICE_USER" },
            { field: 'abilities', header: 'abilities', value: '', type: AECriteriaType.LIST },
        ]);

        this.columnsAbilities = Column.fromObjects([
          //  { field: 'abilities.name', header: "user.label_code" },
            { field: "name", header: "user.label_abilities"},
            // , type: ColumnType.LIST, fieldArray: "label" 
        ]);
        this.formGroup = this.formBuilder.group({
            id: [this.selectedData.id],
            code: [this.selectedData.code, [Validators.required]],
            account: [this.selectedData.account, [Validators.required]],
            service: [this.selectedData.service, [Validators.required]],
            role: [this.selectedData.role, [Validators.required]],
         
            
           // abilities: [this.selectedData.abilities, [Validators.required]],
       
          });

    }


    ngOnInit(): void {
        log.debug('ngOnInit');
        this.initUI();
        this.initData();
        this.subscriptions.push(
            this.abilityService.getAllActivate().subscribe((res) => {
                if (res) {
                    this.abilities = res.datas;
                }
            })
           
        );
        // this.subscriptions.push(
        //     this.abilityService.getAllActivate().subscribe((result) => {
        //         if (result) {
        //             this.abilities = result.datas;
        //         }
        //     })
        // );
        //   this.subscriptions.push(this.abilityService.findAllValuesByTypeCode("ABILITY").subscribe((res: Ability[]) => {
        //     this.abilities = res;
        //  }))
        // this.subscriptions.push(this.printerService.getAllActivate().subscribe((res: AEList<Printer>) => {
        //     if (res) {
        //         this.printers = res.datas;
        //     }
        // }));
         //this.subscriptions.push(this.settingAppService.findOneByCode(SettingAppService.ACCOUNT).subscribe((result: SettingApp) => {
        //     //this.settingAppAccount = result;
        //     // UI Customized Form Validation
            this.formGroup = this.formBuilder.group({
               id: [this.selectedData.id],
              code: [this.selectedData.code, [Validators.required]],
               account: this.formBuilder.group({
                id: [this.selectedData.account.id],
                   username: [this.selectedData.account.username],
                 password: [this.selectedData.account.password, [Validators.minLength(4)]],
             email: [this.selectedData.account.email, [Validators.email]],
                    //activated: [this.selectedData.account.activated],
                 profile: this.formBuilder.group({
                         id: [this.selectedData.account.profile.id],
                        firstname: [this.selectedData.account.profile.firstname, [Validators.required]],
                       lastname: [this.selectedData.account.profile.lastname, [Validators.required]]
                    }),
                     settings: [],
                   roles: [this.selectedData.account.roles],
                     contact: this.formBuilder.group({
                         id: [this.selectedData.account.contact.id],
                        mainAddress: [this.selectedData.account.contact.mainAddress],
                        mainPhone: [this.selectedData.account.contact.mainPhone, [Validators.required, Validators.min(10000000), Validators.max(99999999)]]
                     })
                 }),
                 service: [this.selectedData.service],
                 abilities: [this.selectedData.abilities],
                chief: [this.selectedData.chief],
              role: [this.selectedData.role, [Validators.required]],
                printers: [this.selectedData?.printers],

             });
         

        //this.subscriptions.push(this.nodeService.getAllTreesOfNodes().subscribe((res: Node[]) => {
            //this.trees = [];
           // if (res) {
               // res.forEach((root) => {
                   // this.trees.push(this.treeNodeBuilderService.toTreeNode(root));
             //   });
           // }
        //}));

        this.subscriptions.push(this.roleService.getAllActivate().subscribe((result: AEList<Role>) => {
            this.allRoles = result.datas.filter((role: Role) => role.code !== "USER" && role.code !== "ADMIN_APP" && role.code !== "ADMIN_SYS");
            let criteriaRole = this.criterias.find((criteria: AECriteriaField) => criteria.field === "role.id");
            if (criteriaRole) {
                criteriaRole.values = this.allRoles;
            }
                 //  this.subscriptions.push(this.settingAppService.findOneByCode(SettingAppService.LINE_MANAGER).subscribe((result: SettingApp) => {
           //  this.settingAppLineManager = result;
          
        }));

      //  this.subscriptions.push(this.settingAppService.findOneByCode(SettingAppService.LINE_MANAGER).subscribe((result: SettingApp) => {
           //  this.settingAppLineManager = result;
        //     let listRoles: number[] = result.possibleValuesRoles.map((role: Role) => role.id);
        //     if (listRoles && listRoles.length !== 0) {
        //         this.subscriptions.push(this.userService.findAllByRoles(listRoles).subscribe((result: User[]) => {
        //             this.allChiefs = result;
        //             this.listChiefs = [...this.allChiefs];
        //         }));
        //     }
        // }));
    }


    ngAfterViewChecked(): void {
        this.changeDetectorRef.detectChanges();
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
        if (this.userService && this.userService !== null) {
            const subscribe = this.userService
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
    onAddChildClick() {
        log.debug('Add Child Click');
        const self = this;
        super.addChild(TaskComponent, 'Ajouter Child').subscribe(data => {
          self.selectedData.taskss.push(data);
        });
      }
    
      onEditChildClick() {
        log.debug('Edit Child Click');
        const self = this;
        super.editChild(TaskComponent, 'Modifier Child').subscribe(data => {
          Object.assign(self.selectedChildData, data);
        });
      }
    
      onDeleteChildClick() {
        const self = this;
        super.deleteChild().subscribe(data => {
          if (data) {
            remove(self.selectedData.taskss, data);
          }
        });
      }

    onDblclickRow(row: User) {
        log.info("onDblclickRow", row);
        // this.listChiefs = this.allChiefs.filter(
        //     (chief: User) => chief.id !== row.id
        // );
        // this.displayAccount = this.settingAppService.isVisible(
        //     this.settingAppAccount,
        //     row.role
        // );
        // this.displayChief = this.settingAppService.isVisible(
        //     this.settingAppLineManager,
        //     row.role
        // );
        this.editMode(false);
        this.canDeleteCopy = true;
        if (this.isMasterExpanded) {
            this.isMasterExpanded = !this.isMasterExpanded;
        }
        this.scrollToDetail();
        if (this.useDTO === false) {
            this.selectedData = cloneDeep(row);
            this.formGroup.patchValue(this.selectedData);
           // if (this.trees && this.trees.length !== 0) {
               // this.formGroup.get("nodes").setValue(
                  //  this.treeNodeBuilderService.getListTreeNodeById(
                     //   this.trees,
                       // row.nodes.map((node: Node) => node.id)
                   // )
                //);
           // }
            //this.afterDblclickRow();
        } else {
            if (row["id"] === undefined) {
                this.messageService.add({
                    severity: "error",
                    summary: "Erreur",
                    detail: "DTO ne contient pas la définition d'une propriété ID",
                });
            } else {
                let subscribe = this.userService
                    .getByID(row["id"])
                    .subscribe((result: User) => {
                        log.debug(result);
                        if (result) {
                            this.selectedData = result;
                            this.formGroup.patchValue(this.selectedData);
                            this.afterDblclickRow();
                        }
                    });
                this.subscriptions.push(subscribe);
            }
        }
    }

    onChangeRole(event): void {
        // this.displayAccount = this.settingAppService.isVisible(
        //     this.settingAppAccount,
        //     event
        // );
        // this.displayChief = this.settingAppService.isVisible(
        //     this.settingAppLineManager,
        //     event
        // );
    }

    onCopyClick(): void {
        log.info("onCopyClick");
        this.setIdsToNull(this.selectedData, this.formGroup, [
            "service",
            //"nodes",
            "chief",
            "role",
        ]);
        this.editMode(true);
    }

    onAddClick(): void {
        super.onAddClick();
        this.displayAccount = false;
        this.displayChief = false;
    }

    onSaveClick() {
        log.debug("Save Click HybrideComponent", this.selectedData);
        const self = this;
        const subscribe = this.validation().subscribe((isValid) => {
            if (isValid) {
                // let tab: string[] = this.selectedData.nodes.map(
                //    (treeNode: TreeNode) => treeNode.data.path
                // );
                let newTab: string[] = [];
                // tab.forEach((path1: string) => {
                //     if (
                //         !tab.find(
                //             (path2: string) =>
                //                 path1.startsWith(path2) && path1 !== path2
                //         )
                //     ) {
                //         newTab.push(path1);
                //     }
                // });
                // let resToSave: Node[] = this.selectedData.nodes
                //     .filter((treeNode: TreeNode) =>
                //         newTab.includes(treeNode.data.path)
                //     )
                //     .map((treeNode: TreeNode) => {
                //         return treeNode.data;
                //     });
                // this.selectedData.nodes = resToSave;
                if (this.selectedData["id"] == null) {
                    log.debug("Create");
                    const subscribe = this.userService
                        .create(this.selectedData)
                        .subscribe((result) => {
                            log.debug("Create ok ", result);
                            // Do not remove
                            if (result) {
                                this.selectedData = result;
                                this.afterSaveOK();
                            }
                        });
                    self.subscriptions.push(subscribe);
                } else {
                    log.debug("Update");
                    const subscribe2 = this.userService
                        .update(this.selectedData["id"], this.selectedData)
                        .subscribe((result) => {
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

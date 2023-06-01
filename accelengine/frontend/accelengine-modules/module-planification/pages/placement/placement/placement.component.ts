import { Component, Injector, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { Ability } from '@app/accelengine-modules/module-planification/models/Ability.model';
import { Placement } from '@app/accelengine-modules/module-planification/models/placement';
import { User } from '@app/accelengine-modules/module-planification/models/user.model';
import { AbilityService } from '@app/accelengine-modules/module-planification/services/ability.service';
import { PlacementService } from '@app/accelengine-modules/module-planification/services/placement.service';
import { CriteriaComponent } from '@app/accelengine-shared/components/criteria/criteria.component';
import { Column, ColumnType } from '@app/accelengine-shared/components/data-table/data-table.model';
import { Printer } from '@app/accelengine-std/models/printer.model';
import { PrinterService } from '@app/accelengine-std/services/printer.service';
import { APP_CONFIG } from '@app/app.config';
import { AECriteriaField, AECriteriaType, AEList, HybrideComponent } from 'accelengine-lib';
import { Logger } from 'accelengine-lib';
const log = new Logger('PlacementComponent');
@Component({
  selector: 'app-placement',
  templateUrl: './placement.component.html',
  styleUrls: ['./placement.component.scss']
})
export class PlacementComponent extends HybrideComponent <Placement> implements OnInit {
  appConfig = APP_CONFIG.app;
  selectedPlacment: Placement[];
  displayAccount: boolean = false;
  displayChief: boolean = false;
  listChiefs: User[];
  allChiefs: User[];
  
  columnsAbilities: Column[];
  abilities: Ability[];
  printers: Printer[] = [];
  //allAbilitys: Ability[];

 

  constructor(
      injector: Injector,
       
      private placementService: PlacementService,
      private abilityService: AbilityService,
      private printerService: PrinterService
   
  ) {
      super(injector, User, abilityService, CriteriaComponent);


      this.pageSize = this.appConfig.pageSize;
      // UI Customized DataTable
      this.columns = Column.fromObjects([
          { field: "name", header: "name", filter: true },
          { field: "abilities", header: "user.label_abilities", filter: true,type: ColumnType.LIST, fieldArray: "label" },
          
      ]);  

      this.pagination = true;
      this.criteria = true;
      this.criterias = AECriteriaField.fromObjects([
        
        { field: 'abilities.name', header: "user.label_role", value: '', type: AECriteriaType.LIST, displayField: 'name', returnValue: 'id', values: [] },
       
    ]);


      this.columnsAbilities = Column.fromObjects([
          { field: 'name', header: "user.label_code" },
        
      ]);
      this.formGroup = this.formBuilder.group({
          name: [this.selectedData.name],
      
       
          
          valuesAbilities: [this.selectedData.abilities, [Validators.required]],
     
        });

  }


  ngOnInit(): void {
      log.debug('ngOnInit');
      this.initUI();
      this.initData();
      this.subscriptions.push(this.abilityService.getAllActivate().subscribe((result: AEList<Ability>) => {
        this.abilities = result.datas;
        let criteriaability = this.criterias.find((criteria: AECriteriaField) => criteria.field === "abilities.name");
        if (criteriaability) {
            criteriaability.values = this.abilities;
        }   })
        );
      this.subscriptions.push(
        this.abilityService.getAllActivate().subscribe((res: AEList<Ability>) => {
            if (res) {
                this.abilities = res.datas;
            }
        })
    );
      
      this.subscriptions.push(this.printerService.getAllActivate().subscribe((res: AEList<Printer>) => {
          if (res) {
              this.printers = res.datas;
          }
      }));
       //this.subscriptions.push(this.settingAppService.findOneByCode(SettingAppService.ACCOUNT).subscribe((result: SettingApp) => {
      //     //this.settingAppAccount = result;
      //     // UI Customized Form Validation
          this.formGroup = this.formBuilder.group({
             name: [this.selectedData.name],
             abilities: [this.selectedData.abilities]
                   
                   })
     

           
       

      //this.subscriptions.push(this.nodeService.getAllTreesOfNodes().subscribe((res: Node[]) => {
          //this.trees = [];
         // if (res) {
             // res.forEach((root) => {
                 // this.trees.push(this.treeNodeBuilderService.toTreeNode(root));
           //   });
         // }
      //}));

   

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


 

  // Init
  initUI() {
      // Do not remove
      super.initUI();
      log.debug("Init UI");
  }

  initData() {
      // Do not remove
      log.debug("Init Data");
      if (this.placementService && this.placementService !== null) {
          const subscribe = this.placementService
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
                  const subscribe = this.placementService
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
                  const subscribe2 = this.placementService
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

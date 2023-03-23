package tn.accelengine.modules.installer.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import tn.accelengine.core.annotations.AEInstaller;
import tn.accelengine.core.domain.*;
import tn.accelengine.core.extend.AEWorkflow;
import tn.accelengine.modules.std.domain.Module;
import tn.accelengine.modules.std.domain.*;
import tn.accelengine.modules.std.domain.Module.CHECK_TYPE;
import tn.accelengine.modules.std.port.in.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AEInstaller
@RequiredArgsConstructor
@Slf4j
public class AEGlobalInstaller {

    public static final String MOD_CODE = "INSTALLER";

    public static final String MOD_NAME = "Installation";

    public static final String MOD_VERSION = "1.0.0";

    private final ModuleInput moduleInput;

    private final DocumentInput documentInput;

    private final ActionInput actionInput;

    private final RoleInput roleInput;

    private final MenuInput menuInput;

    private final InitInput initInput;

    @EventListener(ApplicationReadyEvent.class)
    @Order(11)
    public void configModuleBeforeAllModulesStartup() {
        log.info("AEGlobalInstaller : configModuleBeforeAllModulesStartup");

    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(999)
    public void configModuleAfterAllModulesStartup() {

        CHECK_TYPE action = this.moduleInput.checkModule(MOD_CODE, MOD_VERSION);

        if (action == CHECK_TYPE.CREATE) {

            log.info("AEGlobalInstaller : Init Menus");
            this.initInput.initMenus("installer_menus.csv");

            Set<Setting> settings = new HashSet<>();

            log.info("AEGlobalInstaller : Create Module");
            this.moduleInput.createNewData(new Module(MOD_CODE, MOD_NAME, MOD_VERSION, null, true, settings), false);

            log.info("configGlobal");
            var allMenus = this.menuInput.findAllActivate().getDatas();

            Set<Menu> topMenus = allMenus.stream().filter(menu -> menu.getParentCode().equalsIgnoreCase("-1") && menu.getCode().equalsIgnoreCase("7")).collect(Collectors.toSet());
            Set<Menu> menuDashboard = allMenus.stream().filter(menu -> menu.getCode().equalsIgnoreCase("1")).collect(Collectors.toSet());
            Set<Menu> menuAdministration = allMenus.stream().filter(menu -> menu.getCode().startsWith("90") || menu.getCode().equalsIgnoreCase("9")).collect(Collectors.toSet());


            /**
             * Std documents
             */
            log.info("AEGlobalInstaller : import Documents from STD");
            var dictionaryTypeDocument = this.documentInput.findOneByCode(DictionaryType.class.getSimpleName());
            var dictionaryValueDocument = this.documentInput.findOneByCode(DictionaryValue.class.getSimpleName());
            var notificationDocument = this.documentInput.findOneByCode(Notification.class.getSimpleName());
            var jobDocument = this.documentInput.findOneByCode(Job.class.getSimpleName());
            var accountDocument = this.documentInput.findOneByCode(Account.class.getSimpleName());
            var contactDocument = this.documentInput.findOneByCode(Contact.class.getSimpleName());
            var batchExecutionDocument = this.documentInput.findOneByCode(BatchExecution.class.getSimpleName());
            var menuDocument = this.documentInput.findOneByCode(Menu.class.getSimpleName());
            var profileDocument = this.documentInput.findOneByCode(Profile.class.getSimpleName());
            var settingDocument = this.documentInput.findOneByCode(Setting.class.getSimpleName());
            var applicationDocument = this.documentInput.findOneByCode(Application.class.getSimpleName());
            var aeFileDocument = this.documentInput.findOneByCode(AEFile.class.getSimpleName());
            var batchDocument = this.documentInput.findOneByCode(Batch.class.getSimpleName());
            var holidayDocument = this.documentInput.findOneByCode(Holiday.class.getSimpleName());
            var aeAccountHistoryDocument = this.documentInput.findOneByCode(AEAccountHistory.class.getSimpleName());
            var moduleDocument = this.documentInput.findOneByCode(Module.class.getSimpleName());
            var documentDocument = this.documentInput.findOneByCode(Document.class.getSimpleName());
            var workflowStatusDocument = this.documentInput.findOneByCode(AEWorkflowStatus.class.getSimpleName());
            var aeWorkflowDocument = this.documentInput.findOneByCode(AEWorkflow.class.getSimpleName());

            /**
             * get documents
             */
            log.info("AEGlobalInstaller : Get Documents");


            /**
             * create roles, accounts , profils
             */
            log.info("AEGlobalInstaller : Create Roles");
            var actionAll = this.actionInput.findActionByCode(Action.Type.ALL.name());
            var actionRead = this.actionInput.findActionByCode(Action.Type.READ.name());
            var actionUpdate = this.actionInput.findActionByCode(Action.Type.UPDATE.name());
            var actionCreate = this.actionInput.findActionByCode(Action.Type.CREATE.name());

            /**
             * create standard permissions
             */
            Set<Permission> permissionsStandard = new HashSet<>();
            permissionsStandard.add(new Permission(accountDocument, actionAll));
            permissionsStandard.add(new Permission(profileDocument, actionAll));
            permissionsStandard.add(new Permission(menuDocument, actionRead));
            permissionsStandard.add(new Permission(applicationDocument, actionAll));
            permissionsStandard.add(new Permission(settingDocument, actionAll));
            permissionsStandard.add(new Permission(batchDocument, actionAll));
            permissionsStandard.add(new Permission(batchExecutionDocument, actionAll));
            permissionsStandard.add(new Permission(jobDocument, actionAll));
            permissionsStandard.add(new Permission(notificationDocument, actionAll));
            permissionsStandard.add(new Permission(aeFileDocument, actionAll));
            permissionsStandard.add(new Permission(aeAccountHistoryDocument, actionAll));

            log.info("Create role {}", "ADMINISTRATOR");
            Set<Permission> permissionsAdministrator = new HashSet<>();
            permissionsAdministrator.addAll(permissionsStandard);
            permissionsAdministrator.add(new Permission(dictionaryTypeDocument, actionAll));
            permissionsAdministrator.add(new Permission(dictionaryValueDocument, actionAll));
            permissionsAdministrator.add(new Permission(contactDocument, actionAll));
            permissionsAdministrator.add(new Permission(aeFileDocument, actionAll));
            permissionsAdministrator.add(new Permission(holidayDocument, actionAll));
            permissionsAdministrator.add(new Permission(documentDocument, actionAll));
            permissionsAdministrator.add(new Permission(workflowStatusDocument, actionAll));
            permissionsAdministrator.add(new Permission(aeWorkflowDocument, actionAll));
            permissionsAdministrator.add(new Permission(moduleDocument, actionRead));
            permissionsAdministrator.add(new Permission(moduleDocument, actionUpdate));


            Set<Menu> menusAdministrator = new HashSet<>();
            menusAdministrator.addAll(menuDashboard);
            menuAdministration.addAll(topMenus);
            menusAdministrator.addAll(menuAdministration.stream().filter(menu ->
                !menu.getCode().equals("90.20.1")
                && !menu.getCode().equals("90.20.1.1")
                && !menu.getCode().equals("90.20.2")
                && !menu.getCode().equals("90.20.2.1")
                && !menu.getCode().equals("90.20.5")
                && !menu.getCode().equals("90.30.1")
                && !menu.getCode().equals("90.30.2")
                && !menu.getCode().equals("90.30.3")
                && !menu.getCode().equals("90.30.5")
                && !menu.getCode().equals("90.30.6")
                && !menu.getCode().equals("90.30.7")
                && !menu.getCode().equals("90.30.8")
                && !menu.getCode().equals("90.40")
                && !menu.getCode().equals("90.50")).collect(Collectors.toList()));

            var roleAdmin = this.roleInput.createNewDataAndGet(new Role("ROLE_ADMINISTRATOR", "Administrateur", permissionsAdministrator, menusAdministrator, false), false);

        }

        if (action == CHECK_TYPE.UPDATE) {
            log.info("AEGlobalInstaller : action UPDATE");
            if (MOD_VERSION.equals("1.0.0")) {
                log.info("UPDATE version 1.0.0");
            }
        }
    }
}

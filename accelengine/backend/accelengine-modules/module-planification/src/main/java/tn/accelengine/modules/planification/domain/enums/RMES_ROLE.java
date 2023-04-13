package tn.accelengine.modules.planification.domain.enums;

public enum RMES_ROLE {

    ADMINISTRATOR("ADMINISTRATOR"),
    OPERATOR("OPERATOR"),
    PREPARATOR("PREPARATOR"),
    WAREHOUSE_MANAGER("WAREHOUSE_MANAGER"),
    BTM("BTM"),
    SUPERVISOR("SUPERVISOR"),
    PRODUCTION_LINE_MANAGER("PRODUCTION_LINE_MANAGER"),
    MAINTENANCE_LINE_MANAGER("MAINTENANCE_LINE_MANAGER"),
    MAINTENANCE_TECH("MAINTENANCE_TECH"),
    MAINTENANCE_LEADER("MAINTENANCE_LEADER"),
    PRODUCTION_LEADER("PRODUCTION_LEADER");
    private final String name;

    RMES_ROLE( String name) {
        this.name = name;
    }


    public String getName() {
        return this.name;
    }
}

import { AEAuditingEntity } from "accelengine-lib";
export class Shift extends AEAuditingEntity {
    name: string;
    startTimeWork: Date;
    endTimeWork: Date;
}

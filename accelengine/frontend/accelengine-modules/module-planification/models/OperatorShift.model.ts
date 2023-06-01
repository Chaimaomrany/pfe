import { AEAuditingEntity } from "accelengine-lib";
import { Shift } from "./shift.model";
import { User } from "./user.model";
export class OperatorShift extends AEAuditingEntity  {

    shift: Shift;
    users: User[] = [];

    startDatePeriod: Date;
    endDatePeriod: Date;
}

import { Shift } from "./shift.model";
import { User } from "./user.model";
export class OperatorShift  {

    shift: Shift;
    users: User[] = [];

    startDatePeriod: Date;
    endDatePeriod: Date;
}

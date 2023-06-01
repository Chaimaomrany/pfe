import { AEAuditingEntity } from "accelengine-lib";
import { User } from "./user.model";
import { Placement } from "./placement";
export class Ability extends AEAuditingEntity {
    name: string;   
    description: string;
    users: User[] = [];
}

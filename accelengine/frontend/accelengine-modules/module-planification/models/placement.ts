import { AEAuditingEntity } from "accelengine-lib";
import { Ability } from "./Ability.model";

export class Placement extends AEAuditingEntity  {
    name: string;
    abilities: Ability[] = [];
}

import { Account, Role } from "@app/accelengine-core/models/account.model";
import { Printer } from "@app/accelengine-std/models/printer.model";
import { DictionaryValue } from "@std/models/dictionaryValue.model";
import { AEAuditingEntity } from "accelengine-lib";
import { Ability } from "./Ability.model";
import { Task } from "./Task.model";

export class User extends AEAuditingEntity {
    service: DictionaryValue = new DictionaryValue();
    account: Account = new Account();
    chief: User;
    role: Role;
    abilities: Ability[] = [];
    printers: Printer[];
    taskss: Task[] = [];
    

}

package tn.accelengine.modules.planification.dto;

import tn.accelengine.core.domain.Role;
import tn.accelengine.modules.std.domain.Account;

public interface IUserDTO {

	String getCode();

    DictionaryValueDTO getService();

    Account getAccount();

    Role getRole();

}

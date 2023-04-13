package tn.accelengine.modules.planification.port.in;

import java.util.List;

import tn.accelengine.core.extend.AECrudInputPortDTO;
import tn.accelengine.modules.planification.domain.User;
import tn.accelengine.modules.planification.dto.IUserDTO;
import tn.accelengine.modules.std.domain.Account;

public interface UserInput extends AECrudInputPortDTO<User, IUserDTO> {

	User findUserByAccountId(Long accountId, boolean doThrow);

	User findUserByAccount(Account account, boolean doThrow);

	List<User> findAllByRoles(List<Long> listRoles);

	void automaticPrinting(Long idAEfile, Long idUser, Long idNode);

	User checkAndReturnCurrentUser();
}

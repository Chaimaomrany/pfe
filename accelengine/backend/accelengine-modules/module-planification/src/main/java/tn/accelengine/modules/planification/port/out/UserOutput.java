package tn.accelengine.modules.planification.port.out;

import java.util.List;
import java.util.Optional;

import tn.accelengine.core.extend.AECrudOutputPortDTO;
import tn.accelengine.modules.planification.domain.User;
import tn.accelengine.modules.planification.dto.IUserDTO;

public interface UserOutput extends AECrudOutputPortDTO<User, IUserDTO> {

	Optional<User> findUserByAccountId(Long id);

	List<User> findAllByRoles(List<Long> listRoles);

	List<User> findAllByNodeAndRole(Long idNode, Long idRole);
}

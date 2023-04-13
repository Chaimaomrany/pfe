package tn.accelengine.modules.planification.adapter.persistence;

import java.util.List;
import java.util.Optional;

import tn.accelengine.core.extend.AEJpaRepositoryDTO;
import tn.accelengine.modules.planification.domain.User;
import tn.accelengine.modules.planification.dto.IUserDTO;

public interface UserJpaRepositoryDTO extends AEJpaRepositoryDTO<User, IUserDTO> {
	Optional<User> findUserByAccountIdAndDeletedIsFalse(Long id);

	List<User> findAllByRoleIdInAndDeletedIsFalse(List<Long> roleList);

}

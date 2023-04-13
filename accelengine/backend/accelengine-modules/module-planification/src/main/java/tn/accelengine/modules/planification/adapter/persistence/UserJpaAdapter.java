package tn.accelengine.modules.planification.adapter.persistence;

import java.util.List;
import java.util.Optional;

import tn.accelengine.core.annotations.AEPersistenceAdapter;
import tn.accelengine.core.extend.AECrudJpaAdapterDTO;
import tn.accelengine.modules.planification.domain.User;
import tn.accelengine.modules.planification.dto.IUserDTO;
import tn.accelengine.modules.planification.port.out.UserOutput;

@AEPersistenceAdapter
public class UserJpaAdapter extends AECrudJpaAdapterDTO<User, IUserDTO, UserJpaRepositoryDTO> implements UserOutput {

	public UserJpaAdapter(UserJpaRepositoryDTO userJpaRepositoryDTO) {
		super(userJpaRepositoryDTO);
	}

	@Override
	public Optional<User> findUserByAccountId(Long id) {
		return this.jpaRepositoryDTO.findUserByAccountIdAndDeletedIsFalse(id);
	}

	@Override
	public List<User> findAllByRoles(List<Long> roleList) {
		return this.jpaRepositoryDTO.findAllByRoleIdInAndDeletedIsFalse(roleList);
	}

	@Override
	public List<User> findAllByNodeAndRole(Long idNode, Long idRole) {
		// TODO Auto-generated method stub
		return null;
	}

}
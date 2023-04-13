package tn.accelengine.modules.planification.adapter.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import tn.accelengine.core.extend.AEJpaRepositoryDTO;
import tn.accelengine.modules.planification.dto.IUserDTO;

public interface UserJpaRepositoryDTO extends AEJpaRepositoryDTO<User, IUserDTO> {
	Optional<User> findUserByAccountIdAndDeletedIsFalse(Long id);

	List<User> findAllByRoleIdInAndDeletedIsFalse(List<Long> roleList);

	@Query(value = "select u from User u where u.role.id =:idRole and :idNode in (select nd.id from u.nodes nd where nd.deleted = false and nd.status = true)  and u.deleted = false and u.status = true")
	List<User> findAllByNodeIdAndRoleId(@Param("idNode") Long idNode, @Param("idRole") Long idRole);

}

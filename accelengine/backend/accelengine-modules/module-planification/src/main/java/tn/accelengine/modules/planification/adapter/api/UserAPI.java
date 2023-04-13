package tn.accelengine.modules.planification.adapter.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.accelengine.core.extend.AECrudApiDTO;
import tn.accelengine.modules.planification.domain.User;
import tn.accelengine.modules.planification.dto.IUserDTO;
import tn.accelengine.modules.planification.port.in.UserInput;

@RestController
@RequestMapping(value = UserAPI.ENDPOINT)
class UserAPI extends AECrudApiDTO<User, IUserDTO> {

	public static final String ENDPOINT = ROOT_ENDPOINT + "/user";
	private final UserInput userInput;

	public UserAPI(UserInput userInput) {
		super(userInput);
		this.userInput = userInput;

	}

	@GetMapping("/findbycode/{code}")
	public ResponseEntity<User> findByCode(@PathVariable("code") String code) {
		return ResponseEntity.ok(this.userInput.findOneByCode(code));
	}

	@GetMapping("/userbyaccount/{accountId}")
	public ResponseEntity<User> findUserByAccountId(@PathVariable("accountId") Long accountId) {
		return ResponseEntity.ok(this.userInput.findUserByAccountId(accountId, false));
	}

	@GetMapping("/findallbyroles/{listRoles}")
	public ResponseEntity<List<User>> findAllByRoles(@PathVariable("listRoles") List<Long> listRoles) {
		return ResponseEntity.ok(this.userInput.findAllByRoles(listRoles));
	}

	@GetMapping(value = "/automaticprinting/{idAEfile}/{idUser}/{idNode}")
	ResponseEntity<Boolean> automaticPrinting(@PathVariable("idAEfile") Long idAEfile,
			@PathVariable("idUser") Long idUser, @PathVariable("idNode") Long idNode) {

		if (idUser < 0)
			idUser = null;
		if (idNode < 0)
			idNode = null;
		this.userInput.automaticPrinting(idAEfile, idUser, idNode);
		return new ResponseEntity(true, HttpStatus.OK);
	}

}

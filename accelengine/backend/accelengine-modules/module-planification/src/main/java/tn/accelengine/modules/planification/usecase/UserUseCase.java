package tn.accelengine.modules.planification.usecase;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.SerializationUtils;

import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.annotations.AEUseCase;
import tn.accelengine.core.events.AEEventRunner;
import tn.accelengine.core.exceptions.AEBusinessException;
import tn.accelengine.core.exceptions.AENotFoundException;
import tn.accelengine.core.extend.AECrudMessage;
import tn.accelengine.core.extend.AECrudUseCaseDTO;
import tn.accelengine.modules.ged.port.in.FileInput;
import tn.accelengine.modules.planification.config.UserCst;
import tn.accelengine.modules.planification.domain.User;
import tn.accelengine.modules.planification.dto.IUserDTO;
import tn.accelengine.modules.planification.port.in.UserInput;
import tn.accelengine.modules.planification.port.out.UserOutput;
import tn.accelengine.modules.std.domain.Account;
import tn.accelengine.modules.std.port.in.AccountInput;
import tn.accelengine.modules.std.port.in.PrinterInput;
import tn.accelengine.modules.std.port.in.RoleInput;

@Slf4j
@AEUseCase
public class UserUseCase extends AECrudUseCaseDTO<User, IUserDTO> implements UserInput {

	private static final String MSG_CODE_ALREADY_EXISTS = "Le matricule (%s) existe déjà";

	private static final String MSG_EMAIL_ACCOUNT_ALREADY_EXISTS = "L'email (%s) existe déjà";

	private static final String MSG_USERNAME_ACCOUNT_ALREADY_EXISTS = "Le nom d'utilisateur (%s) existe déjà";

	private static final String MSG_USERNAME_AND_EMAIL_ACCOUNT_REQUIRED = "L'email et le nom d'utilisateur sont obligatoire";

	private static final String MSG_ACCOUNT_USER_NOTFOUND = "Le compte (%s) n'est pas affecté à un utilisateur";

	private static final String MSG_CHIEF_NOTFOUND = "Le chef n'existe pas";

	private static final String MSG_CHIEF_IS_WRONG = "Un utilisateur ne peut pas être le chef de lui même";

	private final UserOutput userOutput;

	private final AccountInput accountInput;

	private final RoleInput roleInput;

	private final PrinterInput printerInput;

	private final FileInput fileInput;

	public UserUseCase(UserOutput userOutput, AccountInput accountInput, RoleInput roleInput, FileInput fileInput,
			PrinterInput printerInput) {
		super(userOutput);
		this.userOutput = userOutput;
		this.accountInput = accountInput;

		this.roleInput = roleInput;
		this.fileInput = fileInput;
		this.printerInput = printerInput;

	}

	@Override
	public User createNewDataAndGet(User data, boolean sendEvent) {
		log.info("Creating User");
		this.authorization.assertCanCreate(User.class);
		if (this.findOneByCodeOptional(data.getCode()).isPresent()) {
			throw new AEBusinessException("message", String.format(MSG_CODE_ALREADY_EXISTS, data.getCode()));
		}
		this.validateRGChief(data);
		var account = this.createAccount(data);
		data.setAccount(account);
		User newdata = this.userOutput.saveAndFlush(data);
		if (sendEvent) {
			AECrudMessage.DataCreated<User> event = new AECrudMessage.DataCreated<>(User.class);
			event.setNewData(newdata);
			AEEventRunner.getInstance().send(event);
		}
		return newdata;
	}

	@Override
	public User updateDataAndGet(Long id, User data, boolean sendEvent) {
		log.info("Updating User");
		this.authorization.assertCanUpdate(User.class);
		Optional<User> oldData = this.userOutput.findOne(id);
		if (!oldData.isPresent()) {
			throw new AENotFoundException(
					String.format("Unable to update %s with id %s not found", User.class, id.toString()));
		}
		if (!data.getCode().equalsIgnoreCase(oldData.get().getCode())
				&& this.findOneByCodeOptional(data.getCode()).isPresent()) {
			throw new AEBusinessException("message", String.format(MSG_CODE_ALREADY_EXISTS, data.getCode()));
		}
		this.validateRGChief(data);
		var oldUser = SerializationUtils.clone(oldData.get());
		this.updateAccount(oldUser, data);
		User updatedData = this.userOutput.saveAndFlush(data);
		if (sendEvent) {
			AECrudMessage.DataUpdated<User> event = new AECrudMessage.DataUpdated<>(User.class);
			event.setOldData(oldUser);
			event.setNewData(updatedData);
			AEEventRunner.getInstance().send(event);
		}
		return updatedData;
	}

	private Account createAccount(User user) {
		/*
		 * var settingAppAccount =
		 * this.settingAppInput.findOneByCodeOptional(SettingAppCst.ACCOUNT); if
		 * (settingAppAccount.isPresent() &&
		 * settingAppAccount.get().getVisibleForRoles().contains(user.getRole())) { if
		 * ((user.getAccount() != null && (user.getAccount().getEmail() == null ||
		 * user.getAccount().getUsername() == null)) || user.getAccount() == null) {
		 * throw new AEBusinessException("message",
		 * MSG_USERNAME_AND_EMAIL_ACCOUNT_REQUIRED); } } else { return
		 * this.createAccountForNotVisibleRoles(user); }
		 */
		if (this.accountInput.findAccountByEmail(user.getAccount().getEmail()) != null)
			throw new AEBusinessException("message",
					String.format(MSG_EMAIL_ACCOUNT_ALREADY_EXISTS, user.getAccount().getEmail()));
		if (this.accountInput.findAccountByUsername(user.getAccount().getUsername()) != null)
			throw new AEBusinessException("message",
					String.format(MSG_USERNAME_ACCOUNT_ALREADY_EXISTS, user.getAccount().getUsername()));
		var account = new Account();
		account.setProfile(user.getAccount().getProfile());
		account.setEmail(user.getAccount().getEmail());
		account.setUsername(user.getAccount().getUsername());
		account.setPasswordNoEncode(user.getAccount().getPassword());
		account.setContact(user.getAccount().getContact());
		account.setRoles(new HashSet<>());
		account.getRoles().add(user.getRole());
		var accountData = this.accountInput.createNewDataAndGet(account, false);
		return accountData;
	}

	private void updateAccount(User oldUser, User newUser) {
		/*
		 * var settingAppAccount =
		 * this.settingAppInput.findOneByCodeOptional(SettingAppCst.ACCOUNT); if
		 * (settingAppAccount.isPresent() &&
		 * settingAppAccount.get().getVisibleForRoles().contains(newUser.getRole())) {
		 * if ((newUser.getAccount() != null && (newUser.getAccount().getEmail() == null
		 * || newUser.getAccount().getUsername() == null)) || newUser.getAccount() ==
		 * null) { throw new AEBusinessException("message",
		 * MSG_USERNAME_AND_EMAIL_ACCOUNT_REQUIRED); } } else { //
		 * newUser.setAccount(this.accountInput.findAccountByUsername(MasterdataCst.
		 * OPERATOR_USERNAME)); // return; }
		 */
		if (((oldUser.getAccount() != null && newUser.getAccount() != null)
				&& !oldUser.getAccount().getEmail().equalsIgnoreCase(newUser.getAccount().getEmail()))
				|| newUser.getAccount() != null) {
			var accountFromDB = this.accountInput.findAccountByEmail(newUser.getAccount().getEmail());
			if (accountFromDB != null && !accountFromDB.getId().equals(newUser.getAccount().getId())) {
				throw new AEBusinessException("message",
						String.format(MSG_EMAIL_ACCOUNT_ALREADY_EXISTS, newUser.getAccount().getEmail()));
			}
		}
		if (((oldUser.getAccount() != null && newUser.getAccount() != null)
				&& !oldUser.getAccount().getUsername().equalsIgnoreCase(newUser.getAccount().getUsername()))
				|| newUser.getAccount() != null) {
			var accountFromDB = this.accountInput.findAccountByUsername(newUser.getAccount().getUsername());
			if (accountFromDB != null && !accountFromDB.getId().equals(newUser.getAccount().getId())) {
				throw new AEBusinessException("message",
						String.format(MSG_USERNAME_ACCOUNT_ALREADY_EXISTS, newUser.getAccount().getUsername()));
			}
		}
		if (!oldUser.getRole().getId().equals(newUser.getRole().getId()) && newUser.getAccount() != null) {
			newUser.getAccount().setRoles(new HashSet<>());
			newUser.getAccount().getRoles().add(newUser.getRole());
		}
		if (oldUser.getAccount() != null) {
			this.accountInput.updateData(newUser.getId(), newUser.getAccount(), false);
		}
	}

	private Account createAccountForNotVisibleRoles(User user) {
		var account = new Account();
		account.setProfile(user.getAccount().getProfile());
		Long currentTimestamp = System.currentTimeMillis();
		account.setEmail(user.getRole().getCode() + currentTimestamp + "@" + user.getRole().getCode() + ".com");
		account.setUsername(user.getRole().getCode() + currentTimestamp);
		account.setPassword(UserCst.OPERATOR_PASSWORD);
		account.setContact(user.getAccount().getContact());
		account.setRoles(new HashSet<>());
		account.getRoles().add(user.getRole());
		var accountData = this.accountInput.createNewDataAndGet(account, false);
		return accountData;
	}

	private void validateRGChief(User user) {
		if (user.getChief() != null) {
			this.userOutput.findOne(user.getChief().getId())
					.orElseThrow(() -> new AEBusinessException("message", String.format(MSG_CHIEF_NOTFOUND)));
			if (user.getId() != null && user.getChief() != null && user.getId().equals(user.getChief().getId())) {
				throw new AEBusinessException("message", String.format(MSG_CHIEF_IS_WRONG));
			}
		}
	}

	@Override
	public User findUserByAccountId(Long accountId, boolean doThrow) {
		return this.findUserByAccount(this.accountInput.findByID(accountId), doThrow);
	}

	@Override
	public User findUserByAccount(Account account, boolean doThrow) {
		this.authorization.assertCanRead(User.class);
		log.info("findUserByAccountId");
		var userOption = this.userOutput.findUserByAccountId(account.getId());
		User user = null;
		if (userOption.isPresent()) {
			user = userOption.get();
		}
		if (doThrow && user == null)
			throw new AENotFoundException(String.format(MSG_ACCOUNT_USER_NOTFOUND, account.getUsername()));
		return user;
	}

	@Override
	public List<User> findAllByRoles(List<Long> listRoles) {
		this.authorization.assertCanRead(User.class);
		log.info("findAllByRoles, {}", listRoles);
		List<User> result = this.userOutput.findAllByRoles(listRoles);
		return result;
	}

	@Override
	public User checkAndReturnCurrentUser() {
		this.authorization.assertCanRead(User.class);
		log.info("checkAndReturnCurrentUser");
		var account = this.accountInput.getCurrentAccount();
		return this.findUserByAccount(account, true);
	}

	@Override
	public void automaticPrinting(Long idAEfile, Long idUser, Long idNode) {
		// TODO Auto-generated method stub

	}

}

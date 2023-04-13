package tn.accelengine.modules.planification.usecase;

import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.annotations.AEUseCase;
import tn.accelengine.core.events.AEEventRunner;
import tn.accelengine.core.exceptions.AEBusinessException;
import tn.accelengine.core.extend.AECrudMessage;
import tn.accelengine.core.extend.AECrudUseCase;
import tn.accelengine.modules.planification.domain.Shift;
import tn.accelengine.modules.planification.port.in.ShiftInput;
import tn.accelengine.modules.planification.port.out.ShiftOutput;

@Slf4j
@AEUseCase
public class ShiftUseCase extends AECrudUseCase<Shift> implements ShiftInput {

	private static final String MSG_CODE_ALREADY_EXISTS = "Le nom (%s) existe déjà";
	private ShiftOutput shiftOutput;

	public ShiftUseCase(ShiftOutput shiftOutput) {
		super(shiftOutput);
		this.shiftOutput = shiftOutput;
	}

	public Shift createNewDataAndGet(Shift data, boolean sendEvent) {
		log.info("Creating Shift");
		this.authorization.assertCanCreate(Shift.class);
		if (this.findOneByCodeOptional(data.getName()).isPresent()) {
			System.out.println("message A");
			throw new AEBusinessException("message", String.format(MSG_CODE_ALREADY_EXISTS, data.getName()));
		}
		System.out.println("message B");
		data.setCode(data.getName());
		Shift newdata = this.shiftOutput.saveAndFlush(data);
		if (sendEvent) {
			System.out.println("message C");
			AECrudMessage.DataCreated<Shift> event = new AECrudMessage.DataCreated<>(Shift.class);
			event.setNewData(newdata);
			AEEventRunner.getInstance().send(event);
		}

		return newdata;
	}
	/*
	 * public Shift findAllbyShifts(List<Long> listUsers) {
	 * this.authorization.assertCanRead(User.class); log.info("findAllByUsers, {}",
	 * listUsers); Shift result = this.shiftOutput.findAllByShifts(listUsers);
	 * return result; }
	 * 
	 */
}

package tn.accelengine.modules.planification.usecase;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.annotations.AEUseCase;
import tn.accelengine.core.extend.AECrudUseCase;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.User;
import tn.accelengine.modules.planification.port.in.OperatorShiftInput;
import tn.accelengine.modules.planification.port.out.OperatorShiftOutput;

@Slf4j
@AEUseCase
public class OperatorShiftUseCase extends AECrudUseCase<OperatorShift> implements OperatorShiftInput {

	private OperatorShiftOutput operatorShiftOutput;

	public OperatorShiftUseCase(OperatorShiftOutput operatorShiftOutput) {
		super(operatorShiftOutput);
		this.operatorShiftOutput = operatorShiftOutput;
	}

	@Override
	public List<OperatorShift> findAllByShift(Long Shift) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OperatorShift> findAllByUser(List<User> users) {
		// TODO Auto-generated method stub
		return null;
	}

}

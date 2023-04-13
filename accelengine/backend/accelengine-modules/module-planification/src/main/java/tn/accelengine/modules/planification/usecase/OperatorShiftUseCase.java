package tn.accelengine.modules.planification.usecase;

import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.annotations.AEUseCase;
import tn.accelengine.core.extend.AECrudUseCase;
import tn.accelengine.modules.planification.domain.OperatorShift;
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

}

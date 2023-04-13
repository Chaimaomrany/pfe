package tn.accelengine.modules.planification.adapter.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.accelengine.core.extend.AECrudApi;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.port.in.OperatorShiftInput;

@RestController
@RequestMapping(value = OperatorShiftAPI.ENDPOINT)
class OperatorShiftAPI extends AECrudApi<OperatorShift> {

	public static final String ENDPOINT = ROOT_ENDPOINT + "/user/affectation-operateur-shift";

	private final OperatorShiftInput operatorShiftInput;

	public OperatorShiftAPI(OperatorShiftInput operatorShiftInput) {
		super(operatorShiftInput);
		this.operatorShiftInput = operatorShiftInput;
	}
}
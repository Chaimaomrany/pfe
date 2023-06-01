package tn.accelengine.modules.planification.adapter.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/getoperatorbyshift/{shift}")
	public ResponseEntity<List<OperatorShift>> getPlannedVisitByListId(@PathVariable("shift") Long shift) {
		var res = this.operatorShiftInput.findAllByShift(shift);
		return ResponseEntity.ok(res);

	}

}
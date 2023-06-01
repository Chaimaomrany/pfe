package tn.accelengine.modules.planification.adapter.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.accelengine.core.extend.AECrudApi;
import tn.accelengine.modules.planification.domain.Shift;
import tn.accelengine.modules.planification.port.in.ShiftInput;

@RestController
@RequestMapping(value = ShiftAPI.ENDPOINT)
class ShiftAPI extends AECrudApi<Shift> {

	public static final String ENDPOINT = ROOT_ENDPOINT + "/user/shift";

	private final ShiftInput shiftInput;

	public ShiftAPI(ShiftInput shiftInput) {
		super(shiftInput);
		this.shiftInput = shiftInput;
	}

	@GetMapping("/findallbyshifts/{listShifts}")
	public ResponseEntity<Shift> findAllbyShifts(@PathVariable("listShifts") List<Long> listShifts) {
		return ResponseEntity.ok(this.shiftInput.findAllbyShifts(listShifts));
	}

}

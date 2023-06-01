package tn.accelengine.modules.planification.adapter.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.accelengine.core.extend.AECrudApi;
import tn.accelengine.modules.planification.domain.Placement;
import tn.accelengine.modules.planification.port.in.PlacementInput;

@RestController
@RequestMapping(value = PlacementAPI.ENDPOINT)
public class PlacementAPI extends AECrudApi<Placement> {
	public static final String ENDPOINT = ROOT_ENDPOINT + "/user/placement";

	private final PlacementInput placementInput;

	public PlacementAPI(PlacementInput placementInput) {
		super(placementInput);
		this.placementInput = placementInput;
	}
}
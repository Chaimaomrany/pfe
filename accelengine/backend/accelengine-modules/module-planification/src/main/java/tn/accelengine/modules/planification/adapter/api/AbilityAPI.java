package tn.accelengine.modules.planification.adapter.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.accelengine.core.extend.AECrudApi;
import tn.accelengine.modules.planification.domain.Ability;
import tn.accelengine.modules.planification.port.in.AbilityInput;

@RestController
@RequestMapping(value = AbilityAPI.ENDPOINT)
public class AbilityAPI extends AECrudApi<Ability> {

	public static final String ENDPOINT = ROOT_ENDPOINT + "/user/ability";

	private final AbilityInput abilityInput;

	public AbilityAPI(AbilityInput abilityInput) {
		super(abilityInput);
		this.abilityInput = abilityInput;
	}
}
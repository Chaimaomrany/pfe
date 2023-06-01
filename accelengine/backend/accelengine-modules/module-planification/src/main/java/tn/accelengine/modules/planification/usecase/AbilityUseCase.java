package tn.accelengine.modules.planification.usecase;

import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.annotations.AEUseCase;
import tn.accelengine.core.extend.AECrudUseCase;
import tn.accelengine.modules.planification.domain.Ability;
import tn.accelengine.modules.planification.port.in.AbilityInput;
import tn.accelengine.modules.planification.port.out.AbilityOutput;

@Slf4j
@AEUseCase
public class AbilityUseCase extends AECrudUseCase<Ability> implements AbilityInput {
	private AbilityOutput abilityOutput;

	public AbilityUseCase(AbilityOutput abilityOutput) {
		super(abilityOutput);
		this.abilityOutput = abilityOutput;
	}

}

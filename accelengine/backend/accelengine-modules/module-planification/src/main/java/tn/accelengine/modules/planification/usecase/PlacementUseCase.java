package tn.accelengine.modules.planification.usecase;

import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.annotations.AEUseCase;
import tn.accelengine.core.extend.AECrudUseCase;
import tn.accelengine.modules.planification.domain.Placement;
import tn.accelengine.modules.planification.port.in.PlacementInput;
import tn.accelengine.modules.planification.port.out.PlacementOutput;

@Slf4j
@AEUseCase
public class PlacementUseCase extends AECrudUseCase<Placement> implements PlacementInput {
	private PlacementOutput placementOutput;

	public PlacementUseCase(PlacementOutput placementOutput) {
		super(placementOutput);
		this.placementOutput = placementOutput;
	}

}

package tn.accelengine.modules.start.adapter.persistence;

import tn.accelengine.core.annotations.AEPersistenceAdapter;
import tn.accelengine.core.extend.AECrudJpaAdapter;
import tn.accelengine.modules.start.domain.Father;
import tn.accelengine.modules.start.port.out.FatherOutput;

@AEPersistenceAdapter
class FatherJpaAdapter extends AECrudJpaAdapter<Father, FatherJpaRepository> implements FatherOutput {

    public FatherJpaAdapter(FatherJpaRepository fatherJpaRepository) {
        super(fatherJpaRepository);
    }
}
package tn.accelengine.modules.planification.adapter.sync.adapter.api;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static tn.accelengine.core.extend.AECrudUseCase.ROOT_ENDPOINT;

@RestController
@RequestMapping(value = SyncUserApi.ENDPOINT)
public class SyncUserApi {

    public static final String ENDPOINT = ROOT_ENDPOINT + "/sync/user";
}

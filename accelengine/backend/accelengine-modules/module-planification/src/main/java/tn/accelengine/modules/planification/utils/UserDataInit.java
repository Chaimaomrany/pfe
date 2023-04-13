package tn.accelengine.modules.planification.utils;

import lombok.RequiredArgsConstructor;
import tn.accelengine.core.annotations.AEUtil;
import tn.accelengine.modules.std.domain.DictionaryType;
import tn.accelengine.modules.std.domain.DictionaryValue;
import tn.accelengine.modules.std.port.in.DictionaryTypeInput;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@AEUtil
@RequiredArgsConstructor
public class UserDataInit {
    private final DictionaryTypeInput dictionaryTypeInput;
    public void init() {

        // SERVICES
        this.dictionaryTypeInput.createNewData(new DictionaryType("SERVICE_USER", "Service",
            "Ce type va être attribuer à un utilisateur pour définir leur service",
            Stream.of(new DictionaryValue("SERV1", "Service 1", "Cette valeur à attribuer à un utilisateur"),
                    new DictionaryValue("SERV2", "Service 2", "Cette valeur à attribuer à un utilisateur"))
                .collect(Collectors.toSet())), false);
    }
}

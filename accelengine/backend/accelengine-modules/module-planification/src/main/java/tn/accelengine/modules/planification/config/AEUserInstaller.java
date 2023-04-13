package tn.accelengine.modules.planification.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.accelengine.core.annotations.AEInstaller;
import tn.accelengine.core.domain.Document;
import tn.accelengine.modules.importexport.domain.FieldParam;
import tn.accelengine.modules.importexport.domain.FileParam;
import tn.accelengine.modules.importexport.domain.LineParam;
import tn.accelengine.modules.importexport.port.in.FileParamInput;
import tn.accelengine.modules.planification.domain.OperatorShift;
import tn.accelengine.modules.planification.domain.Shift;
import tn.accelengine.modules.planification.domain.User;
import tn.accelengine.modules.planification.importexport.module.UserImportModule;
import tn.accelengine.modules.planification.utils.UserDataInit;
import tn.accelengine.modules.std.domain.DictionaryValue;
import tn.accelengine.modules.std.domain.Module;
import tn.accelengine.modules.std.domain.Module.CHECK_TYPE;
import tn.accelengine.modules.std.domain.Setting;
import tn.accelengine.modules.std.port.in.DictionaryTypeInput;
import tn.accelengine.modules.std.port.in.DocumentInput;
import tn.accelengine.modules.std.port.in.InitInput;
import tn.accelengine.modules.std.port.in.ModuleInput;

@AEInstaller
@RequiredArgsConstructor
@Slf4j
public class AEUserInstaller {

	public static final String MOD_CODE = "USERS";

	public static final String MOD_NAME = "Utilisateurs";

	public static final String MOD_VERSION = "0.0.1";

	private final ModuleInput moduleInput;

	private final InitInput initInput;

	private final DictionaryTypeInput dictionaryTypeInput;

	private final FileParamInput fileParamInput;

	private final DocumentInput documentInput;

	private final UserDataInit userDataInit;

	@EventListener(ApplicationReadyEvent.class)
	@Order(20)
	public void configModuleAfterAEStartup() {

		CHECK_TYPE action = this.moduleInput.checkModule(MOD_CODE, MOD_VERSION);

		if (action == CHECK_TYPE.CREATE) {

			Set<Setting> settings = new HashSet<>();
			log.info("AEUserInstaller : Create Module");
			this.moduleInput.createNewData(new Module(MOD_CODE, MOD_NAME, MOD_VERSION, null, true, settings), false);

			log.info("AEUserInstaller : Init Data");
			this.userDataInit.init();

			log.info("AEUserInstaller : Init Menus");
			this.initInput.initMenus("user_menus.csv");

			log.info("AEUserInstaller : Init Translate Menus");
			this.initInput.initTranslate("user_translate_ui_en.csv");

			log.info("AEUserInstaller : Init Translate Menus");
			this.initInput.initTranslate("user_translate_ui_fr.csv");
			this.initInput.initTranslate("user_translate_menu.csv");

			log.info("AEUserInstaller : Create documents");
			var userDocument = this.documentInput.createNewDataAndGet(new Document(User.class.getSimpleName()), false);
			var operatorShiftDocument = this.documentInput
					.createNewDataAndGet(new Document(OperatorShift.class.getSimpleName()), false);
			var shiftDocument = this.documentInput.createNewDataAndGet(new Document(Shift.class.getSimpleName()),
					false);
			/***
			 * RDD
			 */
			var type = this.dictionaryTypeInput.findOneByCode("RDD");

			/***
			 * RDD_USER
			 */
			var rddUsers = new DictionaryValue(UserCst.RDD_USER, "Fichier utilisateurs",
					"Importer les valeurs des utilisateurs");
			rddUsers.setValString1(UserImportModule.class.getName());
			type.getDictionaryValues().add(rddUsers);
			var fieldParamU1 = new FieldParam(FieldParam.FieldType.STRING, "Matricule", 0, true, 1000, "", "", true);
			fieldParamU1.setCode("fullname");
			var fieldParamU2 = new FieldParam(FieldParam.FieldType.STRING, "Nom", 1, true, 1000, "", "", true);
			fieldParamU2.setCode("username");
			var fieldParamU3 = new FieldParam(FieldParam.FieldType.STRING, "Prenom", 2, false, 1000, "", "", true);
			fieldParamU3.setCode("superior");
			var fieldParamU4 = new FieldParam(FieldParam.FieldType.STRING, "N°Tel", 3, false, 1000, "", "", true);
			fieldParamU4.setCode("purchaseDemand");
			var fieldParamU5 = new FieldParam(FieldParam.FieldType.STRING, "Role", 4, false, 1000, "", "", true);
			fieldParamU5.setCode("purchaser");
			var fieldParamU6 = new FieldParam(FieldParam.FieldType.STRING, "Email", 5, false, 1000, "", "", true);
			fieldParamU6.setCode("msGroup");
			var fieldParamU7 = new FieldParam(FieldParam.FieldType.STRING, "Indice nœud", 6, false, 1000, "", "", true);
			fieldParamU7.setCode("additionalInfo");
			var fieldParamU8 = new FieldParam(FieldParam.FieldType.STRING, "Indice nœud", 7, false, 1000, "", "", true);
			fieldParamU8.setCode("internalExternal");
			var lineParamU = new LineParam("", "Ligne fichier utilisateur", true, Set.of(fieldParamU1, fieldParamU2,
					fieldParamU3, fieldParamU4, fieldParamU5, fieldParamU6, fieldParamU7, fieldParamU8));
			var fileParamU = new FileParam(FileParam.FileType.CSV, "V0.0.1", "Utilisateur", "csv", ";",
					Set.of(lineParamU));
			fileParamU.setCode(UserCst.RDD_USER);
			this.fileParamInput.createNewData(fileParamU, false);

			// save all RDD dictionary values
			this.dictionaryTypeInput.updateData(type.getId(), type, false);
		}

		if (action == CHECK_TYPE.UPDATE) {
			log.info("AEUserInstaller : action UPDATE");
			if (MOD_VERSION.equals("1.0.0")) {
				log.info("UPDATE version 1.0.0");
			}

		}
	}

}

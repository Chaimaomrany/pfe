package tn.accelengine.modules.planification.importexport.module;

import org.springframework.stereotype.Component;

@Component
public class UserImportModule { // extends ImportModule implements IImportModule

//    @PostConstruct
//    public void init() {
//        this.BATCH_DESCRIPTION = "Import utilisateurs";
//    }
//
//    @Override
//    public void getCriteria(LoggerUtil logger, Map<String, Object> criteria) {
//        logger.addInfoMessage("Récupération des critères d'import");
//    }
//
//    @Override
//    public void filterRG(LoggerUtil logger, List<ImportLineDTO> datas) {
//        logger.addInfoMessage("Début de filtrage des lignes à importer par règles de gestion");
//    }
//
//    @Override
//    public void dumpData(LoggerUtil logger, FileParam fileParam, List<ImportLineDTO> datas) {
//        logger.addInfoMessage("Début de la création des enregistrements dans la base");
//        if (datas.isEmpty()) {
//            return;
//        }
//        logger.addInfoMessage("L'import des utilisateurs est commencé");
//        logger.timeStart();
//        var i = 0;
//        for (ImportLineDTO data : datas) {
//            i++;
//            var fullname = data.getFields().get("fullname").toString();
//            logger.addInfoMessage("Ligne : " + i + ", nom : " + fullname);
//        }
//        logger.addInfoMessage("L'import des utilisateurs est terminé : " + logger.timeEnd());
//        AEEventRunner.getInstance().enableSend();
//    }
}

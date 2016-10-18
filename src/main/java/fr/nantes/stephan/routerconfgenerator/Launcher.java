package fr.nantes.stephan.routerconfgenerator;

import fr.nantes.stephan.routerconfgenerator.app.CSVReader;
import fr.nantes.stephan.routerconfgenerator.app.ConfigurationCreator;
import fr.nantes.stephan.routerconfgenerator.app.RouterModel;
import fr.nantes.stephan.routerconfgenerator.util.Errors;
import fr.nantes.stephan.routerconfgenerator.util.Files;
import fr.nantes.stephan.routerconfgenerator.util.Popup;
import fr.nantes.stephan.routerconfgenerator.util.Props;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by ughostephan on 13/10/2016.
 */
public class Launcher {

    public static void main(String[] args) {

        /************************/
        /**** Property File *****/
        /************************/
        // Déclaration du fichier de proprietées
        final Props prop = Props.getInstance();

        /************************/
        /******* CSV File *******/
        /************************/
        // Récupération du nom du fichier csv
        final String csvFilePath = prop.getPropertyWithError("csv_file");

        // Vérification de l'existance de ce fichier
        final File csvFile = new File(csvFilePath);
        if (Files.notExist(csvFile)) {
            Errors.setFatalMessage("Le fichier ("+ csvFilePath +") n'existe pas");
        }

        /************************/
        /**** Template File *****/
        /************************/
        // Récupération du nom du template conf
        final String templateFilePath = prop.getPropertyWithError("template_file");

        // Vérification de l'existance de ce fichier
        final File templateFile = new File(templateFilePath);
        if (Files.notExist(templateFile)) {
            Errors.setFatalMessage("Le fichier ("+ templateFilePath +") n'existe pas");
        }

        // Read csv file and get all routers objects
        final CSVReader csvReader = new CSVReader(csvFile);
        // Create all routers conf by template
        final ConfigurationCreator configurationCreator = new ConfigurationCreator(templateFile);
        configurationCreator.createConfigurations(csvReader.getRoutersFromCSV());
        // Resume stats :
        final StringBuilder str = new StringBuilder();
        str.append("Le script à fini la création des fichiers de configurations :");
        str.append(System.getProperty("line.separator"));
        str.append(System.getProperty("line.separator"));
        str.append("Fichiers créés : " + prop.getFile_count_success());
        str.append(System.getProperty("line.separator"));
        str.append("Fichiers non créés : " + prop.getFile_count_error());

        final String folder_name = prop.getProperty("folder_name");
        if (folder_name != null && !"".equals(folder_name)) {
            str.append(System.getProperty("line.separator"));
            str.append("Dossier créés : " + prop.getFolder_success());
            str.append(System.getProperty("line.separator"));
            str.append("Dossier non créés : " + prop.getFolder_error());
        }

        // Affichage de la popup
        Popup.infoBox(str.toString());
        System.exit(0);
    }
}

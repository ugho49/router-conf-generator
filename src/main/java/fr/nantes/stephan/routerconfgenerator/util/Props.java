package fr.nantes.stephan.routerconfgenerator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * Created by ughostephan on 14/10/2016.
 */
public class Props {

    /** L'instance statique */
    private static Props instance;

    private Properties properties;
    private int file_count_success;
    private int file_count_error;
    private int folder_success;
    private int folder_error;

    /** Récupère l'instance unique de la class Props.<p>
     * Remarque : le constructeur est rendu inaccessible
     */
    public static synchronized Props getInstance() {
        if (instance == null) {
            instance = new Props();
        }
        return instance;
    }

    /** Constructeur redéfini comme étant privé pour interdire
     * son appel et forcer à passer par la méthode <link
     */
    private Props() {
        properties = new Properties();

        try {
            // Chargement du fichier de propriétées
            final File f = new File("config.properties");
            properties.load(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            final StringBuilder str = new StringBuilder("Fichier de configuration introuvable (config.properties)");
            str.append(System.getProperty("line.separator"));
            str.append(System.getProperty("line.separator"));
            str.append("Message de l'erreur :");
            str.append(System.getProperty("line.separator"));
            str.append(e.getMessage());
            Errors.setFatalMessage(str.toString());
        } catch (Exception e) {
            Errors.setFatalMessage(e);
        }

        file_count_success = 0;
        file_count_error = 0;
        folder_success = 0;
        folder_error = 0;
    }

    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public String getPropertyWithError(final String key) {
        final String property = getProperty(key);
        if (property == null || "".equals(property)) {
            Errors.setFatalMessage("Proprieté (" + key + ") introuvable dans le fichier de configuration");
        }
        return property;
    }

    public int getFile_count_success() {
        return file_count_success;
    }

    public void incress_file_count_success() {
        this.file_count_success++;
    }

    public int getFile_count_error() {
        return file_count_error;
    }

    public void incress_file_count_error() {
        this.file_count_error++;
    }

    public int getFolder_success() {
        return folder_success;
    }

    public void incress_folder_success() {
        this.folder_success++;
    }

    public int getFolder_error() {
        return folder_error;
    }

    public void incress_folder_error() {
        this.folder_error++;
    }
}

package fr.nantes.stephan.routerconfgenerator.app;

import fr.nantes.stephan.routerconfgenerator.util.Props;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ughostephan on 13/10/2016.
 */
public class ConfigurationCreator {

    //Récupération du chemin courant
    private final File CURRENT_PATH = new File(new File("").getAbsolutePath());
    //Le repertoire source des fichiers à créer :
    private final String DIRECTORY = CURRENT_PATH + "/configurations/";
    //Prefix file out
    private String prefix_out;
    //Suffix file out
    private String suffix_out;
    //Folder name for sorts
    private String folder_name_sorts;
    // template file
    private File templateFile;

    public ConfigurationCreator(final File templateFile) {
        this.templateFile = templateFile;
        // Déclaration du fichier de proprietées
        final Props prop = Props.getInstance();
        // Récupération des proprietés utiles
        prefix_out = prop.getProperty("prefix_out_file");
        suffix_out = prop.getProperty("suffix_out_file");
        folder_name_sorts = prop.getProperty("folder_name");
    }

    public void createConfigurations(final ArrayList<RouterModel> routers) {
        final String template_content = readTemplateFile();

        deleteAllExisitingConfigurations();

        int cpt = 0;
        for (final RouterModel model : routers) {
            final String content = generateRouterConfigurationContent(template_content, model);
            final String prefixe = getPrefixe(model);
            final String folder_name = model.getAttribute(folder_name_sorts);

            createFile(folder_name, cpt + "_" + prefixe + suffix_out, content);
            cpt++;
        }
    }

    private void deleteAllExisitingConfigurations () {
        final File dir = new File(DIRECTORY);
        if (dir.exists()) {
            deleteRecursive(dir);
        }
    }

    private void deleteRecursive(File path){
        File[] c = path.listFiles();
        for (File file : c){
            if (file.isDirectory()){
                deleteRecursive(file);
                file.delete();
            } else {
                file.delete();
            }
        }
        path.delete();
    }

    private String getPrefixe(final RouterModel model) {
        String str = model.getAttribute(prefix_out);

        if (str == null || "".equals(str)) {
            str = "no_name";
        }

        return str;
    }

    private String generateRouterConfigurationContent(final String template, final RouterModel model) {
        String str = template;

        for (final Map.Entry<String, String> entry : model.getAttributes().entrySet()) {
            str = str.replace(entry.getKey(), entry.getValue());
        }

        return str;
    }

    private String readTemplateFile() {
        final StringBuilder str = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.templateFile));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                str.append(sCurrentLine);
                str.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    private boolean createFile(final String folder, final String filename, final String content) {
        //Déclaration des variables utiles
        PrintWriter writer = null;

        // full path
        final String filePath = DIRECTORY + folder + "/" + filename;

        //Création du répertoire source si inexistant
        final File dir = new File(DIRECTORY + folder + "/");
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                Props.getInstance().incress_folder_success();
            } else {
                Props.getInstance().incress_folder_error();
            }
        }

        try {
            // Create file
            final File conf_file = new File(filePath);
            if (conf_file.createNewFile()) {
                Props.getInstance().incress_file_count_success();
            } else {
                Props.getInstance().incress_file_count_error();
            }
            //Instantiation du PrintWriter avec le chemin du fichier en paramètre
            writer = new PrintWriter(conf_file, "UTF-8");
            //Ecriture de la chaine dans le fichier
            writer.write(content);
        } catch(FileNotFoundException e) {
            //Affichage de la trace en cas d'erreur d'encodage
            e.printStackTrace();
            //Retour false car erreur
            return false;
        } catch(UnsupportedEncodingException e) {
            //Affichage de la trace en cas d'erreur d'encodage
            e.printStackTrace();
            //Retour false car erreur
            return false;
        } catch (IOException e) {
            //Affichage de la trace en cas d'erreur d'encodage
            e.printStackTrace();
            //Retour false car erreur
            return false;
        } finally {
            if (writer != null) {
                //Fermeture du fichier
                writer.close();
            }
        }

        return true;
    }
}

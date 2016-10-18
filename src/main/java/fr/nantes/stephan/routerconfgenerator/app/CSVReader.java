package fr.nantes.stephan.routerconfgenerator.app;

import fr.nantes.stephan.routerconfgenerator.util.Errors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ughostephan on 13/10/2016.
 */
public class CSVReader {

    private final Character cvsDivider = ';';
    private ArrayList<String> headers = new ArrayList<String>();
    private File csvFile;

    public CSVReader(final File csvFile) {
        this.csvFile = csvFile;
    }

    public ArrayList<RouterModel> getRoutersFromCSV() {
        readHeaders();
        return createRouterModelArray();
    }

    private ArrayList<RouterModel> createRouterModelArray() {
        final ArrayList<RouterModel> routers = new ArrayList<RouterModel>();

        try {
            Reader in = new FileReader(csvFile);
            Iterable<CSVRecord> records = CSVFormat.newFormat(cvsDivider).withFirstRecordAsHeader().parse(in);
            for (final CSVRecord record : records) {
                // create new model
                final RouterModel model = new RouterModel();
                // iterate each header
                for (final String key : headers) {
                    model.addAttribute(key, record.get(key));
                    //System.out.println(record.get(key));
                }
                // add router to the list
                routers.add(model);
            }
        } catch (FileNotFoundException e) {
            Errors.setFatalMessage(e);
        } catch (IOException e) {
            Errors.setFatalMessage(e);
        }

        return routers;
    }

    private void readHeaders() {

        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(this.csvFile));

            if ((line = br.readLine()) != null) {
                headers = new ArrayList<String>(Arrays.asList(line.split(cvsDivider.toString())));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

package fr.nantes.stephan.routerconfgenerator.app;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ughostephan on 13/10/2016.
 */
public class CSVReader {

    private final String cvsSplitBy = ";";
    private ArrayList<String> headers = new ArrayList<String>();
    private ArrayList<ArrayList<String>> routers = new ArrayList<ArrayList<String>>();

    public ArrayList<RouterModel> getRoutersFromCSV(final File f) {
        read(f);
        return createRouterModelArray();
    }

    private ArrayList<RouterModel> createRouterModelArray() {
        final ArrayList<RouterModel> routers_list = new ArrayList<RouterModel>();

        // for each router
        for (final ArrayList<String> router : routers) {
            // create new model
            final RouterModel model = new RouterModel();
            // iterate each header
            for (int i = 0; i < headers.size(); i++) {
                model.addAttribute(headers.get(i), router.get(i));
            }
            routers_list.add(model);
        }

        return routers_list;
    }

    private void read(final File f) {

        BufferedReader br = null;
        String line = "";
        int count_lines = 0;

        try {
            br = new BufferedReader(new FileReader(f));

            while ((line = br.readLine()) != null) {

                if (count_lines == 0) {
                    headers = new ArrayList<String>(Arrays.asList(line.split(cvsSplitBy)));
                } else {
                    routers.add(new ArrayList<String>(Arrays.asList(line.split(cvsSplitBy))));
                }

                count_lines++;
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

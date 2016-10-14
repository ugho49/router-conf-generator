package fr.nantes.stephan.routerconfgenerator.util;

import java.io.File;

/**
 * Created by ughostephan on 13/10/2016.
 */
public class Files {
    public static boolean exist(final File f) {
        return f.exists() && !f.isDirectory();
    }

    public static boolean notExist(final File f) {
        return !exist(f);
    }
}

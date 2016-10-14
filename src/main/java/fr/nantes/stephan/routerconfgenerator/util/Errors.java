package fr.nantes.stephan.routerconfgenerator.util;

/**
 * Created by ughostephan on 13/10/2016.
 */
public class Errors {

    public static void setFatalMessage(final String message) {
        System.err.println(message);
        Popup.errorBox(message);
        System.exit(0);
    }

    public static void setFatalMessage(final Exception e) {
        setFatalMessage(e.getMessage());
    }

}

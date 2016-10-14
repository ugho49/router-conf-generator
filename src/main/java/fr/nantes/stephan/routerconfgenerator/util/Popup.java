package fr.nantes.stephan.routerconfgenerator.util;

import javax.swing.*;

/**
 * Created by ughostephan on 14/10/2016.
 */
public class Popup {

    public static void infoBox(final String message) {
        box(message, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void errorBox(final String message) {
        box(message, JOptionPane.ERROR_MESSAGE);
    }

    public static void warningBox(final String message) {
        box(message, JOptionPane.WARNING_MESSAGE);
    }

    private static void box(final String message, final int type) {
        JOptionPane.showMessageDialog(null, message, "Router configuration generator", type);
    }
}

package edu.idziak.planner;

import edu.idziak.planner.gui.application.ApplicationController;

import javax.swing.SwingUtilities;

public class Loader {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(ApplicationController::new);

    }
}

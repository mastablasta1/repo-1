package com.idziak.planner;

import com.idziak.planner.gui.application.ApplicationController;

import javax.swing.*;

public class Loader {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(ApplicationController::new);

    }
}

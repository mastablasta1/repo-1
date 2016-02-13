package edu.idziak.planner.gui;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIUtils {

    private static final Logger LOG = LoggerFactory.getLogger(GUIUtils.class);

    public static void checkOnGUIThread() {
        Preconditions.checkArgument(SwingUtilities.isEventDispatchThread(), "Called outside of event dispatch thread");
    }

    public static void callOnGUIThread(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeLater(runnable::run);
        }
    }

    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            LOG.info("System look and feel loading error", e);
        }
    }
}

package com.idziak.planner.gui.grid;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ContextMenu {

    private JComponent parentView;
    private JPopupMenu menu;

    public ContextMenu(JComponent parentView) {
        this.parentView = parentView;
        menu = new JPopupMenu();
    }

    protected JMenuItem addItem(String title, ActionListener listener) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(listener);
        menu.add(item);
        return item;
    }

    protected void show(int x, int y) {
        menu.show(parentView, x, y);
    }
}

/*
 * This file is part of MONGKIE. Visit <http://www.mongkie.org/> for details.
 * Copyright (C) 2011 Korean Bioinformation Center(KOBIC)
 * 
 * MONGKIE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MONGKIE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * CollapsiblePanel.java
 *
 * Created on Jul 10, 2011, 3:13:01 AM
 */
package org.mongkie.lib.widgets;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JComponent;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
public class CollapsiblePanel extends javax.swing.JPanel {

    private boolean extended;
    private final List<CollapseListener> listeners = Collections.synchronizedList(new ArrayList<CollapseListener>());

    /**
     * Creates new form CollapsiblePanel
     */
    private CollapsiblePanel(JComponent topBar, final JComponent extendedPanel,
            final Icon extendIcon, final Icon extendHoverIcon, final String extendTooltip,
            final Icon collapseIcon, final Icon collapseHoverIcon, final String collapseTooltip,
            boolean extended) {
        initComponents();

        if (topBar != null) {
            add(topBar, BorderLayout.CENTER);
        }
        add(extendedPanel, BorderLayout.SOUTH);

        this.extended = extended;
        extendButton.setIcon(extended ? collapseIcon : extendIcon);
        extendButton.setRolloverIcon(extended ? collapseHoverIcon : extendHoverIcon);
        extendButton.setToolTipText(extended ? collapseTooltip : extendTooltip);
        extendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ext = CollapsiblePanel.this.extended;
                ext = !ext;
                CollapsiblePanel.this.extended = ext;
                extendButton.setIcon(ext ? collapseIcon : extendIcon);
                extendButton.setRolloverIcon(ext ? collapseHoverIcon : extendHoverIcon);
                extendButton.setToolTipText(ext ? collapseTooltip : extendTooltip);
                extendedPanel.setVisible(ext);
                getParent().validate();
                getParent().repaint();
                synchronized (listeners) {
                    for (Iterator<CollapseListener> listenersIter = listeners.iterator(); listenersIter.hasNext();) {
                        CollapseListener l = listenersIter.next();
                        if (ext) {
                            l.expanded();
                        } else {
                            l.collapsed();
                        }
                    }
                }
            }
        });
        if (!extended) {
            extendedPanel.setVisible(extended);
        }
    }

    public static CollapsiblePanel createPanel(JComponent topBar, JComponent extendedPanel,
            Icon extendIcon, Icon extendHoverIcon, String extendTooltip,
            Icon collapseIcon, Icon collapseHoverIcon, String collapseTooltip,
            boolean extended) {
        return new CollapsiblePanel(topBar, extendedPanel, extendIcon, extendHoverIcon, extendTooltip, collapseIcon, collapseHoverIcon, collapseTooltip, extended);
    }

    public boolean addCollapseListener(CollapseListener l) {
        return listeners.add(l);
    }

    public boolean removeCollapseListener(CollapseListener l) {
        return listeners.remove(l);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        extendButton = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 3));

        extendButton.setText(org.openide.util.NbBundle.getMessage(CollapsiblePanel.class, "CollapsiblePanel.extendButton.text")); // NOI18N
        extendButton.setAlignmentY(0.0F);
        extendButton.setBorderPainted(false);
        extendButton.setContentAreaFilled(false);
        extendButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        extendButton.setFocusable(false);
        extendButton.setMargin(new java.awt.Insets(2, 4, 2, 2));
        buttonPanel.add(extendButton);

        add(buttonPanel, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton extendButton;
    // End of variables declaration//GEN-END:variables

    public static interface CollapseListener {

        public void collapsed();

        public void expanded();
    }
}

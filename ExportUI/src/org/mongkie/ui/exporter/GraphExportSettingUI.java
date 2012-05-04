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
 * GraphExportSettingUI.java
 *
 * Created on Jul 2, 2011, 4:41:32 PM
 */
package org.mongkie.ui.exporter;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
public class GraphExportSettingUI extends javax.swing.JPanel {

    /** Creates new form GraphExportSettingUI */
    public GraphExportSettingUI() {
        initComponents();
    }

    public void setExportSelectedOnly(boolean selectedOnly) {
        exportSettingButtonGroup.setSelected((selectedOnly ? selectedOnlyRadioButton.getModel() : fullRadioButton.getModel()), true);
    }

    public boolean isExportSelectedOnly() {
        return exportSettingButtonGroup.isSelected(selectedOnlyRadioButton.getModel());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exportSettingButtonGroup = new javax.swing.ButtonGroup();
        fullRadioButton = new javax.swing.JRadioButton();
        selectedOnlyRadioButton = new javax.swing.JRadioButton();
        labelFullGraph = new javax.swing.JLabel();
        labelSelectedOnly = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        exportSettingButtonGroup.add(fullRadioButton);
        fullRadioButton.setSelected(true);
        fullRadioButton.setText(org.openide.util.NbBundle.getMessage(GraphExportSettingUI.class, "GraphExportSettingUI.fullRadioButton.text")); // NOI18N

        exportSettingButtonGroup.add(selectedOnlyRadioButton);
        selectedOnlyRadioButton.setText(org.openide.util.NbBundle.getMessage(GraphExportSettingUI.class, "GraphExportSettingUI.selectedOnlyRadioButton.text")); // NOI18N
        selectedOnlyRadioButton.setEnabled(false);

        labelFullGraph.setFont(new java.awt.Font("Tahoma", 0, 10));
        labelFullGraph.setForeground(new java.awt.Color(102, 102, 102));
        labelFullGraph.setText(org.openide.util.NbBundle.getMessage(GraphExportSettingUI.class, "GraphExportSettingUI.labelFullGraph.text")); // NOI18N

        labelSelectedOnly.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        labelSelectedOnly.setForeground(new java.awt.Color(102, 102, 102));
        labelSelectedOnly.setText(org.openide.util.NbBundle.getMessage(GraphExportSettingUI.class, "GraphExportSettingUI.labelSelectedOnly.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fullRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelFullGraph))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectedOnlyRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSelectedOnly)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fullRadioButton)
                    .addComponent(labelFullGraph))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectedOnlyRadioButton)
                    .addComponent(labelSelectedOnly))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup exportSettingButtonGroup;
    private javax.swing.JRadioButton fullRadioButton;
    private javax.swing.JLabel labelFullGraph;
    private javax.swing.JLabel labelSelectedOnly;
    private javax.swing.JRadioButton selectedOnlyRadioButton;
    // End of variables declaration//GEN-END:variables
}

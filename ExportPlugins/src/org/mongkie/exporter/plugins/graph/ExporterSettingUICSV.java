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
 * ExporterOptionUICSV.java
 *
 * Created on Jul 3, 2011, 1:32:52 PM
 */
package org.mongkie.exporter.plugins.graph;

import javax.swing.JPanel;
import org.mongkie.exporter.spi.Exporter;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
@ServiceProvider(service = Exporter.SettingUI.class)
public class ExporterSettingUICSV extends javax.swing.JPanel implements Exporter.SettingUI<ExporterCSV> {

    private ExporterCSV exporter;

    /**
     * Creates new form ExporterOptionUICSV
     */
    public ExporterSettingUICSV() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableButtonGroup = new javax.swing.ButtonGroup();
        printHeaderCheckBox = new javax.swing.JCheckBox();
        selectTableLabel = new javax.swing.JLabel();
        nodeRadioButton = new javax.swing.JRadioButton();
        edgeRadioButton = new javax.swing.JRadioButton();
        exportInternalIdsCheckBox = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        printHeaderCheckBox.setSelected(true);
        printHeaderCheckBox.setText(org.openide.util.NbBundle.getMessage(ExporterSettingUICSV.class, "ExporterSettingUICSV.printHeaderCheckBox.text")); // NOI18N
        printHeaderCheckBox.setFocusPainted(false);

        selectTableLabel.setText(org.openide.util.NbBundle.getMessage(ExporterSettingUICSV.class, "ExporterSettingUICSV.selectTableLabel.text")); // NOI18N

        tableButtonGroup.add(nodeRadioButton);
        nodeRadioButton.setSelected(true);
        nodeRadioButton.setText(org.openide.util.NbBundle.getMessage(ExporterSettingUICSV.class, "ExporterSettingUICSV.nodeRadioButton.text")); // NOI18N
        nodeRadioButton.setFocusPainted(false);

        tableButtonGroup.add(edgeRadioButton);
        edgeRadioButton.setText(org.openide.util.NbBundle.getMessage(ExporterSettingUICSV.class, "ExporterSettingUICSV.edgeRadioButton.text")); // NOI18N
        edgeRadioButton.setFocusPainted(false);

        exportInternalIdsCheckBox.setText(org.openide.util.NbBundle.getMessage(ExporterSettingUICSV.class, "ExporterSettingUICSV.exportInternalIdsCheckBox.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(selectTableLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(nodeRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edgeRadioButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(printHeaderCheckBox))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(exportInternalIdsCheckBox)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectTableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nodeRadioButton)
                    .addComponent(edgeRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(printHeaderCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportInternalIdsCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton edgeRadioButton;
    private javax.swing.JCheckBox exportInternalIdsCheckBox;
    private javax.swing.JRadioButton nodeRadioButton;
    private javax.swing.JCheckBox printHeaderCheckBox;
    private javax.swing.JLabel selectTableLabel;
    private javax.swing.ButtonGroup tableButtonGroup;
    // End of variables declaration//GEN-END:variables

    @Override
    public void load(ExporterCSV exporter) {
        this.exporter = exporter;
        ExporterCSV.Table tableToExport = exporter.getTableToExport();
        switch (tableToExport) {
            case EDGE:
                tableButtonGroup.setSelected(edgeRadioButton.getModel(), true);
                edgeRadioButton.setEnabled(true);
                nodeRadioButton.setEnabled(exporter.isTableSelectable());
                break;
            case NODE:
                tableButtonGroup.setSelected(nodeRadioButton.getModel(), true);
                nodeRadioButton.setEnabled(true);
                edgeRadioButton.setEnabled(exporter.isTableSelectable());
                break;
            default:
                break;
        }
        printHeaderCheckBox.setSelected(exporter.isPrintHeader());
        exportInternalIdsCheckBox.setSelected(exporter.isExportInternalIdColumns());
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void apply() {
        exporter.setTableToExport(edgeRadioButton.isSelected() ? ExporterCSV.Table.EDGE : ExporterCSV.Table.NODE);
        exporter.setPrintHeader(printHeaderCheckBox.isSelected());
        exporter.setExportInternalIdColumns(exportInternalIdsCheckBox.isSelected());
    }

    @Override
    public boolean isUIForExporter(Exporter exporter) {
        return exporter instanceof ExporterCSV;
    }
}

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
 * ReportPanel.java
 *
 * Created on May 16, 2011, 5:54:09 PM
 */
package org.mongkie.ui.importer;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.mongkie.importer.GraphContainer;
import org.mongkie.importer.Issue;
import org.mongkie.importer.Report;
import org.mongkie.importer.spi.Processor;
import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.RenderDataProvider;
import org.netbeans.swing.outline.RowModel;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import prefuse.data.Graph;
import prefuse.data.Table;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
public class ReportPanel extends javax.swing.JPanel {

    private GraphContainer container;
    private static final Object PROC_KEY = new Object();
    private static ImageIcon ICON_INFO, ICON_WARNING, ICON_SEVERE, ICON_CRITICAL;

    static {
        ICON_INFO = new javax.swing.ImageIcon(ReportPanel.class.getResource("/org/mongkie/ui/importer/resources/info.png"));
        ICON_WARNING = new javax.swing.ImageIcon(ReportPanel.class.getResource("/org/mongkie/ui/importer/resources/warning.png"));
        ICON_SEVERE = new javax.swing.ImageIcon(ReportPanel.class.getResource("/org/mongkie/ui/importer/resources/severe.png"));
        ICON_CRITICAL = new javax.swing.ImageIcon(ReportPanel.class.getResource("/org/mongkie/ui/importer/resources/critical.png"));
    }

    /**
     * Creates new form ReportPanel
     */
    public ReportPanel() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    initComponents();
                    initProcessors();
                }
            });
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        } catch (InvocationTargetException ex) {
            Exceptions.printStackTrace(ex);
        }

//        autoScaleCheck.addItemListener(new ItemListener() {
//
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (autoScaleCheck.isSelected() != container.isAutoScale()) {
//                    container.setAutoScale(autoScaleCheck.isSelected());
//                }
//            }
//        });
    }

    private void initProcessors() {
        int i = 0;
        for (Processor processor : Lookup.getDefault().lookupAll(Processor.class)) {
            JRadioButton radio = new JRadioButton(processor.getDisplayName(), i == 0);
            radio.putClientProperty(PROC_KEY, processor);
            processorGroup.add(radio);
            processorPanel.add(radio);
            radio.setEnabled(processor.isEnabled());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        issuesOutline = new org.netbeans.swing.outline.Outline();
        processorGroup = new javax.swing.ButtonGroup();
        issuesScrollPane = new javax.swing.JScrollPane();
        noIssueLabel = new javax.swing.JLabel();
        fillingLabel = new javax.swing.JLabel();
        reportHeader = new org.jdesktop.swingx.JXHeader();
        issuesPanel = new org.jdesktop.swingx.JXTitledPanel();
        processorPanel = new javax.swing.JPanel();
        statePanel = new javax.swing.JPanel();
        nodeCountLabel = new javax.swing.JLabel();
        edgeCountLabel = new javax.swing.JLabel();
        nodeCountValue = new javax.swing.JLabel();
        edgeCountValue = new javax.swing.JLabel();
        processingSeparator = new org.jdesktop.swingx.JXTitledSeparator();
        reportSeparator = new org.jdesktop.swingx.JXTitledSeparator();
        nodeJLabel = new javax.swing.JLabel();
        nodeLabelComboBox = new javax.swing.JComboBox();
        edgeJLabel = new javax.swing.JLabel();
        edgeLabelComboBox = new javax.swing.JComboBox();
        optionsPanel = new javax.swing.JPanel();
        autoScaleCheck = new javax.swing.JCheckBox();
        directionCheck = new javax.swing.JCheckBox();

        issuesOutline.setBorder(null);
        issuesOutline.setFont(issuesOutline.getFont().deriveFont(issuesOutline.getFont().getSize()-1f));
        issuesOutline.setRenderDataProvider(new IssueRenderer());
        issuesOutline.setRootVisible(false);
        issuesOutline.setRowSelectionAllowed(false);
        issuesOutline.setShowHorizontalLines(false);
        issuesOutline.setShowVerticalLines(false);
        issuesOutline.setTableHeader(null);

        issuesScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        noIssueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noIssueLabel.setText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.noIssueLabel.text")); // NOI18N
        noIssueLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.noIssueLabel.toolTipText")); // NOI18N

        fillingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fillingLabel.setText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.fillingLabel.text")); // NOI18N

        reportHeader.setDescription(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.reportHeader.description")); // NOI18N
        reportHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/mongkie/ui/importer/resources/import32.png"))); // NOI18N
        reportHeader.setTitle(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.reportHeader.title")); // NOI18N

        issuesPanel.setTitle(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.issuesPanel.title")); // NOI18N
        issuesPanel.setBorder(null);
        issuesPanel.setContentContainer(issuesScrollPane);

        processorPanel.setBorder(null);
        processorPanel.setLayout(new javax.swing.BoxLayout(processorPanel, javax.swing.BoxLayout.Y_AXIS));

        statePanel.setBorder(null);

        nodeCountLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        nodeCountLabel.setText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.nodeCountLabel.text")); // NOI18N

        edgeCountLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        edgeCountLabel.setText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.edgeCountLabel.text")); // NOI18N

        nodeCountValue.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        nodeCountValue.setText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.nodeCountValue.text")); // NOI18N

        edgeCountValue.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        edgeCountValue.setText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.edgeCountValue.text")); // NOI18N

        javax.swing.GroupLayout statePanelLayout = new javax.swing.GroupLayout(statePanel);
        statePanel.setLayout(statePanelLayout);
        statePanelLayout.setHorizontalGroup(
            statePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nodeCountLabel)
                    .addComponent(edgeCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nodeCountValue)
                    .addComponent(edgeCountValue))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        statePanelLayout.setVerticalGroup(
            statePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nodeCountValue, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nodeCountLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(statePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edgeCountValue, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(edgeCountLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        processingSeparator.setTitle(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.processingSeparator.title")); // NOI18N

        reportSeparator.setTitle(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.reportSeparator.title")); // NOI18N

        nodeJLabel.setText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.nodeJLabel.text")); // NOI18N

        nodeLabelComboBox.setFont(nodeLabelComboBox.getFont().deriveFont(nodeLabelComboBox.getFont().getSize()-1f));

        edgeJLabel.setText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.edgeJLabel.text")); // NOI18N

        edgeLabelComboBox.setFont(edgeLabelComboBox.getFont().deriveFont(edgeLabelComboBox.getFont().getSize()-1f));
        edgeLabelComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---NO NABEL" }));

        autoScaleCheck.setText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.autoScaleCheck.text")); // NOI18N
        autoScaleCheck.setToolTipText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.autoScaleCheck.toolTipText")); // NOI18N
        autoScaleCheck.setEnabled(false);

        directionCheck.setText(org.openide.util.NbBundle.getMessage(ReportPanel.class, "ReportPanel.directionCheck.text")); // NOI18N

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(directionCheck)
                    .addComponent(autoScaleCheck))
                .addContainerGap())
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                .addComponent(directionCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autoScaleCheck)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nodeJLabel)
                    .addComponent(edgeJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nodeLabelComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(edgeLabelComboBox, 0, 140, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(reportSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(processingSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(processorPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(issuesPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(reportHeader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(reportHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(issuesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(nodeLabelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nodeJLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(edgeLabelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edgeJLabel)))
                    .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(reportSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(processingSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(processorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox autoScaleCheck;
    private javax.swing.JCheckBox directionCheck;
    private javax.swing.JLabel edgeCountLabel;
    private javax.swing.JLabel edgeCountValue;
    private javax.swing.JLabel edgeJLabel;
    private javax.swing.JComboBox edgeLabelComboBox;
    private javax.swing.JLabel fillingLabel;
    private org.netbeans.swing.outline.Outline issuesOutline;
    private org.jdesktop.swingx.JXTitledPanel issuesPanel;
    private javax.swing.JScrollPane issuesScrollPane;
    private javax.swing.JLabel noIssueLabel;
    private javax.swing.JLabel nodeCountLabel;
    private javax.swing.JLabel nodeCountValue;
    private javax.swing.JLabel nodeJLabel;
    private javax.swing.JComboBox nodeLabelComboBox;
    private javax.swing.JPanel optionsPanel;
    private org.jdesktop.swingx.JXTitledSeparator processingSeparator;
    private javax.swing.ButtonGroup processorGroup;
    private javax.swing.JPanel processorPanel;
    private org.jdesktop.swingx.JXHeader reportHeader;
    private org.jdesktop.swingx.JXTitledSeparator reportSeparator;
    private javax.swing.JPanel statePanel;
    // End of variables declaration//GEN-END:variables

    void setData(GraphContainer container) {
        this.container = container;
        Report report = container.getReport();
        report.pruneReport(100);
        fillIssues(report);
        fillStates(container);
        fillLabelColumns(container.getGraph());
    }

    void destroy() {
        issuesScrollPane.setViewportView(null);
    }

    Processor getProcessor() {
        for (Enumeration<AbstractButton> enumeration = processorGroup.getElements(); enumeration.hasMoreElements();) {
            AbstractButton radioButton = enumeration.nextElement();
            if (radioButton.isSelected()) {
                return (Processor) radioButton.getClientProperty(PROC_KEY);
            }
        }
        return null;
    }

    private void fillIssues(Report report) {
        final List<Issue> issues = report.getIssues();
        if (issues.isEmpty()) {
            issuesScrollPane.setViewportView(noIssueLabel);
        } else {
            issuesScrollPane.setViewportView(fillingLabel);
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    issuesOutline.setModel(DefaultOutlineModel.createOutlineModel(new IssueTreeModel(issues), new IssueRowModel(), true));
                    issuesScrollPane.setViewportView(issuesOutline);
                }
            });
        }
    }

    private void fillStates(final GraphContainer container) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                //Source
                String source = container.getSource();
                String[] label = source.split("\\.");
                if (label.length > 2 && label[label.length - 2].matches("\\d+")) { //case of temp file
                    source = source.replaceFirst("." + label[label.length - 2], "");
                }
                reportHeader.setDescription(source);
                //Autoscale
                autoScaleCheck.setSelected(container.isAutoScale());
                //Graph is directed?
                directionCheck.setSelected(container.isDirected());
                //Node & Edge count
                nodeCountValue.setText("" + container.getGraph().getNodeCount());
                edgeCountValue.setText("" + container.getGraph().getEdgeCount());
            }
        });
    }

    void apply() {
        Graph g = container.getGraph();
        g.setNodeLabelField((String) nodeLabelComboBox.getSelectedItem());
        Object edgeLabel = edgeLabelComboBox.getSelectedItem();
        g.setEdgeLabelField(edgeLabel.equals(NO_LABEL) ? null : (String) edgeLabel);
        container.setAutoScale(autoScaleCheck.isSelected());
        boolean directed = directionCheck.isSelected();
        if (g.isDirected() != directed) {
            g.setDirected(directed);
            container.setDirected(directed);
        }
    }

    private void fillLabelColumns(Graph graph) {
        Table nodeTable = graph.getNodeTable();
        nodeLabelComboBox.removeAllItems();
        for (int i = 0; i < nodeTable.getColumnCount(); i++) {
            nodeLabelComboBox.addItem(nodeTable.getColumnName(i));
        }
        String nodeLabel = graph.getNodeLabelField();
        if (nodeLabel != null) {
            nodeLabelComboBox.setSelectedItem(nodeLabel);
        } else {
            nodeLabelComboBox.setSelectedIndex(0);
        }
        Table edgeTable = graph.getEdgeTable();
        edgeLabelComboBox.removeAllItems();
        edgeLabelComboBox.addItem(NO_LABEL);
        for (int i = 0; i < edgeTable.getColumnCount(); i++) {
            edgeLabelComboBox.addItem(edgeTable.getColumnName(i));
        }
        String edgeLabel = graph.getEdgeLabelField();
        edgeLabelComboBox.setSelectedItem((edgeLabel == null) ? NO_LABEL : edgeLabel);
    }
    private static final String NO_LABEL = "---NO LABEL";

    private static class IssueTreeModel implements TreeModel {

        private List<Issue> issues;

        public IssueTreeModel(List<Issue> issues) {
            this.issues = issues;
        }

        @Override
        public Object getRoot() {
            return "root";
        }

        @Override
        public Object getChild(Object parent, int index) {
            return issues.get(index);
        }

        @Override
        public int getChildCount(Object parent) {
            return issues.size();
        }

        @Override
        public boolean isLeaf(Object node) {
            if (node instanceof Issue) {
                return true;
            }
            return false;
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {
        }

        @Override
        public int getIndexOfChild(Object parent, Object child) {
            return issues.indexOf(child);
        }

        @Override
        public void addTreeModelListener(TreeModelListener l) {
        }

        @Override
        public void removeTreeModelListener(TreeModelListener l) {
        }
    }

    private static class IssueRowModel implements RowModel {

        @Override
        public int getColumnCount() {
            return 0;
        }

        @Override
        public Object getValueFor(Object node, int column) {
            if (node instanceof Issue) {
                Issue issue = (Issue) node;
                return issue.getLevel().toString();
            }
            return "";
        }

        @Override
        public Class getColumnClass(int column) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(Object node, int column) {
            return false;
        }

        @Override
        public void setValueFor(Object node, int column, Object value) {
        }

        @Override
        public String getColumnName(int column) {
            return "Issues";
        }
    }

    private static class IssueRenderer implements RenderDataProvider {

        @Override
        public String getDisplayName(Object o) {
            Issue issue = (Issue) o;
            return issue.getMessage();
        }

        @Override
        public boolean isHtmlDisplayName(Object o) {
            return false;
        }

        @Override
        public Color getBackground(Object o) {
            return null;
        }

        @Override
        public Color getForeground(Object o) {
            return null;
        }

        @Override
        public String getTooltipText(Object o) {
            return "";
        }

        @Override
        public Icon getIcon(Object o) {
            Issue issue = (Issue) o;
            switch (issue.getLevel()) {
                case INFO:
                    return ICON_INFO;
                case WARNING:
                    return ICON_WARNING;
                case SEVERE:
                    return ICON_SEVERE;
                case CRITICAL:
                    return ICON_CRITICAL;
            }
            return null;
        }
    }
}

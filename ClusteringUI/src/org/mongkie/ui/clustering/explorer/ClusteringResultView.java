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
package org.mongkie.ui.clustering.explorer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import net.java.dev.colorchooser.ColorChooser;
import org.jdesktop.swingx.icon.EmptyIcon;
import org.mongkie.clustering.ClusteringController;
import org.mongkie.clustering.spi.Cluster;
import org.mongkie.clustering.spi.Clustering;
import org.mongkie.lib.widgets.Palette;
import org.mongkie.visualization.color.ColorController;
import org.netbeans.swing.outline.Outline;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.NodePopupFactory;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
public class ClusteringResultView extends OutlineView {

    private final ExplorerManager em;
    private final OutlinePanel outlinePanel;
    private Clustering clustering;

    public ClusteringResultView(ExplorerManager em) {
        super("Cluster");
        setBackground(Color.WHITE);
        setPropertyColumns(
                NbBundle.getMessage(ClusterNode.class, "ClusterNode.property.color.name"), NbBundle.getMessage(ClusterNode.class, "ClusterNode.property.color.displayName"),
                NbBundle.getMessage(ClusterNode.class, "ClusterNode.property.name.name"), NbBundle.getMessage(ClusterNode.class, "ClusterNode.property.name.displayName"),
                NbBundle.getMessage(ClusterNode.class, "ClusterNode.property.size.name"), NbBundle.getMessage(ClusterNode.class, "ClusterNode.property.size.displayName"));
//        setAllowedDragActions(DnDConstants.ACTION_NONE);
//        setAllowedDropActions(DnDConstants.ACTION_NONE);
        setDragSource(false);
        setDropTarget(false);

        Outline outline = getOutline();
        outline.setAutoCreateColumnsFromModel(false);
        outline.setRootVisible(false);
        outline.getColumnModel().removeColumn(outline.getColumnModel().getColumn(0));

        outline.setBackground(Color.WHITE);
        outline.setForeground(Color.BLACK);
        outline.setFont(outline.getFont().deriveFont(outline.getFont().getSize() - 1f));
        outline.setOpaque(true);
        outline.setRowMargin(4);
        outline.setShowHorizontalLines(false);
        outline.setShowVerticalLines(false);
        outline.setTableHeader(null);

        TableColumn nameCol = outline.getColumnModel().getColumn(1);
        nameCol.setCellRenderer(new TextPropertyRenderer(outline.getFont()));
        nameCol.setCellEditor(new DoubleClickTextPropertyEditor(outline.getFont()));
        TableColumn sizeCol = outline.getColumnModel().getColumn(2);
        sizeCol.setCellRenderer(new TextPropertyRenderer(outline.getFont()));
        sizeCol.setPreferredWidth(100);
        sizeCol.setMaxWidth(100);
        TableColumn colorCol = outline.getColumnModel().getColumn(0);
        colorCol.setCellRenderer(new ColorPropertyRenderer());
        colorCol.setCellEditor(new ColorChooserEditor());
        colorCol.setPreferredWidth(outline.getRowHeight());
        colorCol.setMaxWidth(outline.getRowHeight());

        NodePopupFactory npf = new NodePopupFactory() {
            @Override
            public JPopupMenu createPopupMenu(int row, int column, Node[] selectedNodes, Component component) {
                JPopupMenu popup = super.createPopupMenu(row, column, selectedNodes, component);
                if (selectedNodes.length > 0) {
                    Component groupAction = popup.getComponent(0);
                    groupAction.setEnabled(false);
                    Component ungroupAction = popup.getComponent(1);
                    ungroupAction.setEnabled(false);
                    for (Node n : selectedNodes) {
                        Cluster c = ((ClusterNode) n).getCluster();
                        boolean grouped = Lookup.getDefault().lookup(ClusteringController.class).isGrouped(c);
                        if (grouped) {
                            ungroupAction.setEnabled(true);
                        } else {
                            groupAction.setEnabled(true);
                        }
                    }
                }
                JMenuItem randomizeColors = new JMenuItem(
                        NbBundle.getMessage(ClusteringResultView.class, "ClusteringResultView.menuItem.randomizeColors.text"));
                randomizeColors.setIcon(ImageUtilities.loadImageIcon("org/mongkie/ui/clustering/resources/randomize.png", false));
                randomizeColors.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        randomizeColors();
                    }
                });
                popup.insert(randomizeColors, popup.getComponentCount() > 0 ? popup.getComponentCount() - 1 : 0);
                if (selectedNodes.length > 1) {
                    popup.getComponent(popup.getComponentCount() - 1).setEnabled(false);
                }
                popup.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                return popup;
            }
        };
        npf.setShowQuickFilter(false);
        setNodePopupFactory(npf);

        outlinePanel = new OutlinePanel(outline);

        this.em = em;
    }

    public void randomizeColors() {
        Collection<Cluster> clusters =
                Lookup.getDefault().lookup(ClusteringController.class).getClusters(clustering);
        if (clusters.isEmpty()) {
            return;
        }
        List<Color> colors = Palette.getSequenceColors(clusters.size());
        int i = 0;
        for (Cluster c : clusters) {
            Color color = colors.get(i);
            c.setColor(color);
            if (Lookup.getDefault().lookup(ClusteringController.class).isGrouped(c)) {
                Lookup.getDefault().lookup(ColorController.class).setFillColor(
                        Lookup.getDefault().lookup(ClusteringController.class).getGroup(c), color);
            }
            i++;
        }
        repaint();
    }

//    @Override
//    public void setViewportView(Component view) {
//        super.setViewportView(view);
//        if (view == outlinePanel) {
//            setBorder(null);
//        }
//    }
    public JPanel setClustering(Clustering clustering) {
        this.clustering = clustering;
        getOutline().clearSelection();
        em.setRootContext(new AbstractNode(Children.create(new ClusterChildFactory(clustering), false)) {
            @Override
            public Action[] getActions(boolean context) {
                return new Action[]{};
            }
        });
        return outlinePanel;
    }

    private static class OutlinePanel extends JPanel {

        public OutlinePanel(Outline outline) {
            setBackground(Color.WHITE);
            setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new Insets(4, 10, 0, 0);
            add(outline, gridBagConstraints);
        }
    }

    private static class ColorPropertyRenderer extends JLabel implements TableCellRenderer {

        public ColorPropertyRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                try {
                    setBackground(((Property<Color>) value).getValue());
                } catch (IllegalAccessException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (InvocationTargetException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            return this;
        }
    }

    private static class TextPropertyRenderer extends JLabel implements TableCellRenderer {

        public TextPropertyRenderer(Font f) {
            setFont(f);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }
            if (value != null) {
                try {
                    setIcon(EMPTY_ICON);
                    setText(((Property<String>) value).getValue());
                } catch (IllegalAccessException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (InvocationTargetException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            return this;
        }
    }
    private static final Icon EMPTY_ICON = new EmptyIcon(6, 6);

    private static class DoubleClickTextPropertyEditor extends AbstractCellEditor implements TableCellEditor {

        private final JTextField textField;

        public DoubleClickTextPropertyEditor(Font f) {
            textField = new JTextField();
            textField.setFont(f);
            textField.setBorder(BorderFactory.createEmptyBorder());

        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
            Property<String> property = (Property<String>) value;
            try {
                textField.setText(property.getValue());
            } catch (IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
            } catch (InvocationTargetException ex) {
                Exceptions.printStackTrace(ex);
            }
            return textField;
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            boolean editable = false;
            if (e instanceof MouseEvent) {
                MouseEvent me = (MouseEvent) e;
                editable = (me.getButton() == MouseEvent.BUTTON1 && me.getClickCount() >= 2);
            }
            return editable;
        }

        @Override
        public Object getCellEditorValue() {
            return textField.getText();
        }
    }

    private static class ColorChooserEditor extends AbstractCellEditor implements TableCellEditor {

        private final ColorChooser chooser;
        private Object property;

        public ColorChooserEditor() {
            chooser = new ColorChooser();
            chooser.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals(ColorChooser.PROP_COLOR)) {
                        try {
                            ((Property<Color>) property).setValue((Color) evt.getNewValue());
                        } catch (IllegalAccessException ex) {
                            Exceptions.printStackTrace(ex);
                        } catch (IllegalArgumentException ex) {
                            Exceptions.printStackTrace(ex);
                        } catch (InvocationTargetException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        stopCellEditing();
                    } else if (evt.getPropertyName().equals(ColorChooser.PROP_PICKER_VISIBLE)
                            && !(Boolean) evt.getNewValue() && chooser.getTransientColor() == null) {
                        cancelCellEditing();
                    }
                }
            });
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return false;
        }

        @Override
        public Object getCellEditorValue() {
            try {
                return ((Property<Color>) property).getValue();
            } catch (IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
            } catch (InvocationTargetException ex) {
                Exceptions.printStackTrace(ex);
            }
            return Color.black;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                int row, int column) {
            property = value;
            try {
                chooser.setColor(((Property<Color>) value).getValue());
            } catch (IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
            } catch (InvocationTargetException ex) {
                Exceptions.printStackTrace(ex);
            }
            return chooser;
        }
    }
}

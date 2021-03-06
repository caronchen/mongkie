/*
 *  This file is part of MONGKIE. Visit <http://www.mongkie.org/> for details.
 *  Copyright (C) 2012 Korean Bioinformation Center(KOBIC)
 * 
 *  MONGKIE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  MONGKE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mongkie.ui.datatable.graph.actions.column;

import java.awt.Image;
import static kobic.prefuse.Constants.EDGES;
import org.mongkie.datatable.DataTableControllerUI;
import org.mongkie.datatable.spi.DataAction;
import org.mongkie.datatable.spi.DataTable;
import org.mongkie.datatable.spi.PopupAction;
import org.mongkie.ui.datatable.graph.AbstractDataTable;
import org.mongkie.ui.datatable.graph.AbstractDataTable.AbstractModel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import prefuse.data.Table;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
@ServiceProvider(service = DataAction.class, position = 30)
public class DeleteColumn extends AbstractColumnAction implements PopupAction<AbstractDataTable> {

    @Override
    public String getName() {
        return "Delete column";
    }

    @Override
    public String getDescription() {
        return "Delete a selected column";
    }

    @Override
    public Image getIcon() {
        return ImageUtilities.loadImage("org/mongkie/ui/datatable/resources/table-delete-column.png", false);
    }

    @Override
    public void execute(AbstractDataTable table) {
        throw new UnsupportedOperationException("This action can not be executed.");
    }

    @Override
    public boolean isEnabled(AbstractModel model) {
        String dataGroup = model.getDataTable().getDataGroup();
        return model.getDisplay().getDataEditSupport(dataGroup).isRemoveColumnSupported()
                && model.getDisplay().getDataViewSupport(dataGroup).getOutlineSchema().getColumnCount() > 0;
    }

    @Override
    public boolean isPopupOnly() {
        return true;
    }

    @Override
    public String getPopupDescription() {
        return "Delete a selected column";
    }

    @Override
    public UI getUI() {
        return null;
    }

    @Override
    public DataAction[] getDataActions(AbstractDataTable table) {
        AbstractModel model = table.getModel();
        final Table t = model.getTable();
        boolean isEdgeTable = table.getDataGroup().equals(EDGES);
        DataAction[] actions = new DataAction[isEdgeTable ? t.getColumnCount() - 2 : t.getColumnCount()];
        for (int col = 0, i = 0; col < t.getColumnCount(); col++) {
            final String name = t.getColumnName(col);
            //TODO node key and ID columns can not be deleted also
            if (isEdgeTable
                    && (name.equals(model.getGraph().getEdgeSourceField()) || name.equals(model.getGraph().getEdgeTargetField()))) {
                continue;
            }
            actions[i++] = new DataAction<AbstractDataTable>() {
                @Override
                public String getName() {
                    return name;
                }

                @Override
                public String getDescription() {
                    return null;
                }

                @Override
                public Image getIcon() {
                    return null;
                }

                @Override
                public void execute(AbstractDataTable table) {
                    if (DialogDisplayer.getDefault().notify(
                            new NotifyDescriptor.Confirmation("Are you sure you want to delete '" + name + "'?",
                            DeleteColumn.this.getName(), NotifyDescriptor.OK_CANCEL_OPTION))
                            == NotifyDescriptor.OK_OPTION) {
                        table.getModel().getTable().removeColumn(name);
                        Lookup.getDefault().lookup(DataTableControllerUI.class).refreshModel(table, false);
                    }
                }

                @Override
                public boolean isEnabled(AbstractDataTable table) {
                    return true;
                }

                @Override
                public UI getUI() {
                    return null;
                }

                @Override
                public boolean isActionFor(DataTable table) {
                    return false;
                }

                @Override
                public boolean hideActionText() {
                    return false;
                }
            };
        }
        return actions;
    }
}

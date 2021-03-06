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
package org.mongkie.datatable.spi;

import java.awt.Image;
import javax.swing.JPanel;

/**
 * Data actions to be used for DataTable UI or graph context menus of Display in Visualization UI.
 * These actions are added as buttons in the toolbar of the data tables.
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
public interface DataAction<T extends DataTable> {

    public static final String PROP_KEY = "actionToManipulateDataTable";

    public String getName();

    public String getDescription();

    public Image getIcon();

    public boolean hideActionText();

    public void execute(T table);

    public boolean isEnabled(T table);

    public UI getUI();

    public boolean isActionFor(DataTable table);

    public static interface UI<T extends DataTable, A extends DataAction<T>> {

        public void load(T table, A action);

        public boolean close(Object option);

        public JPanel getPanel();

        public Object[] getDialogOptions();
    }
}

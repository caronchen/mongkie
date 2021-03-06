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
package org.mongkie.datatable;

import java.util.List;
import org.mongkie.datatable.spi.DataAction;
import org.mongkie.datatable.spi.DataNodeFactory;
import org.mongkie.datatable.spi.DataTable;
import org.mongkie.datatable.spi.GraphDataTable;
import prefuse.data.Table;
import prefuse.data.Tuple;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
public interface DataTableController {

    public DataTable getDataTable(String name);

    public GraphDataTable getNodeDataTable();

    public GraphDataTable getEdgeDataTable();

    public DataNode createDataNode(Tuple data, String labelColumn);

    public DataNodeFactory getDataNodeFactory(Table table);

    public List<? extends DataAction> getDataActionsFor(DataTable table);
}

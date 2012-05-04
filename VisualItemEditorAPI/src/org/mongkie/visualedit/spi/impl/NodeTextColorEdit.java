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
package org.mongkie.visualedit.spi.impl;

import java.awt.Color;
import org.mongkie.visualedit.spi.NodeItemEdit;
import org.mongkie.visualedit.spi.VisualEdit;
import org.mongkie.visualization.color.ColorController;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import prefuse.visual.VisualItem;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
@ServiceProvider(service = VisualEdit.class, position = 700)
public class NodeTextColorEdit extends NodeItemEdit<Color> {

    @Override
    public Color getValue(VisualItem n) {
        return Lookup.getDefault().lookup(ColorController.class).getTextColor(n);
    }

    @Override
    public void setValue(VisualItem n, Color c) {
        Lookup.getDefault().lookup(ColorController.class).setTextColor(n, c);
    }

    @Override
    public String getName() {
        return "Text Color";
    }

    @Override
    public Class<Color> getValueType() {
        return Color.class;
    }
}

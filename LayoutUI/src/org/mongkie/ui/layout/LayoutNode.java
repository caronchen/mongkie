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
package org.mongkie.ui.layout;

import java.util.LinkedHashMap;
import java.util.Map;
import org.mongkie.layout.spi.ILayout;
import org.mongkie.layout.spi.LayoutProperty;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node.PropertySet;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;

/**
 * 
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
public class LayoutNode extends AbstractNode {

    private ILayout layout;
    private PropertySet[] propertySets;

    public LayoutNode(ILayout layout) {
        super(Children.LEAF);
        this.layout = layout;
        setName(layout.getBuilder().getName());
    }

    @Override
    public PropertySet[] getPropertySets() {
        if (propertySets == null) {
            try {
                Map<String, Sheet.Set> sheetMap = new LinkedHashMap<String, Sheet.Set>();
                for (LayoutProperty layoutProperty : layout.getProperties()) {
                    Sheet.Set set = sheetMap.get(layoutProperty.getCategory());
                    if (set == null) {
                        set = Sheet.createPropertiesSet();
                        set.setDisplayName(layoutProperty.getCategory());
                        sheetMap.put(layoutProperty.getCategory(), set);
                    }
                    set.put(layoutProperty.getProperty());
                }
                propertySets = sheetMap.values().toArray(new PropertySet[0]);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
                return null;
            }
        }
        return propertySets;
    }

    public ILayout getLayout() {
        return layout;
    }
}

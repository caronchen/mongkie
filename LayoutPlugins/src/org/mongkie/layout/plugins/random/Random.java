/*
 * This file is part of MONGKIE. Visit <http://www.mongkie.org/> for details.
 * Copyright (C) 2012 Korean Bioinformation Center(KOBIC)
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
package org.mongkie.layout.plugins.random;

import java.util.ArrayList;
import java.util.List;
import static kobic.prefuse.Constants.NODES;
import org.mongkie.layout.LayoutProperty;
import org.mongkie.layout.spi.LayoutBuilder;
import org.mongkie.layout.spi.PrefuseLayout;
import prefuse.action.layout.RandomLayout;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
public class Random extends PrefuseLayout.Delegation<RandomLayout> {

    Random(LayoutBuilder<Random> builder) {
        super(builder);
    }

    @Override
    protected RandomLayout createDeligateLayout() {
        RandomLayout l = new RandomLayout(NODES);
        l.setMargin(50, 50, 50, 50);
        return l;
    }

    @Override
    public LayoutProperty[] createProperties() {
        List<LayoutProperty> properties = new ArrayList<LayoutProperty>();
        return properties.toArray(new LayoutProperty[0]);
    }

    @Override
    public void resetProperties() {
    }

    @Override
    protected void setLayoutParameters(RandomLayout layout) {
    }

    @Override
    protected boolean isRunOnce() {
        return true;
    }

    @Override
    public boolean supportsSelectionOnly() {
        return false;
    }
}

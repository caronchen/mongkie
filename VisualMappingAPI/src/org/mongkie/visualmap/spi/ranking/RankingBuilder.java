/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mongkie.visualmap.spi.ranking;

import org.mongkie.visualmap.ranking.RankingModel;

/**
 * Ranking builder, creating <code>Ranking</code> instances suitable to the given
 * {@link RankingModel}.
 * <p>
 * Implementors should add the <code>@ServiceProvider</code> annotation to be
 * registered by the system.
 * <p>
 * @author Mathieu Bastian
 */
public interface RankingBuilder {

    /**
     * Return an array of newly created rankings. The <code>model</code> is useful
     * to know which <code>workspace</code> to create rankings for,
     * @param model the model to be used in the building
     * @return an array of rankings
     */
    public Ranking[] buildRankings(RankingModel model);
    
    /**
     * Return a new instance of the same ranking, but with refresh minimum and
     * maximum value.
     * @param ranking the ranking to refresh
     * @return a new instance of the same ranking, but refreshed
     */
    public Ranking refreshRanking(Ranking ranking);
}

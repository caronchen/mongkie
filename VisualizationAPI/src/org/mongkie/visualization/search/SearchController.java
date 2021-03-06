/*
 * This file is part of MONGKIE. Visit <http://www.mongkie.org/> for details.
 * Visit <http://www.mongkie.org> for details about MONGKIE.
 * Copyright (C) 2013 Korean Bioinformation Center (KOBIC)
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
package org.mongkie.visualization.search;

import java.util.Iterator;
import java.util.regex.Pattern;
import kobic.prefuse.data.TupleProvider;
import prefuse.data.Schema;
import prefuse.data.Tuple;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
public interface SearchController {

    public boolean isStringColumnAvailable(Schema s);

    public Pattern makeRegexPattern(String text, SearchOption options);

    public Pattern makeRegexPattern(String text, boolean wholeWords, boolean caseSensitive);

    public <T extends TupleProvider> SearchResult<T> search(Iterator<T> sources, String text, SearchOption options, SearchResult<T> results, String... columns);

    public <T extends TupleProvider> SearchResult<T> search(T[] sources, String text, SearchOption options, SearchResult<T> results, String... columns);
    
    public <T extends TupleProvider> SearchResult<T> search(Object[] sources, String text, SearchOption options, SearchResult<T> results, String... columns);

    public boolean match(Tuple data, Pattern pattern, String... columns);

    public <T extends TupleProvider> T replace(SearchResult<T> results, String replacement, boolean forward, String... columns);

    public <T extends TupleProvider> int replaceAll(SearchResult<T> results, String replacement, String... columns);

    public <T extends TupleProvider> void replace(T result, Pattern pattern, String replacement, String... columns);
}

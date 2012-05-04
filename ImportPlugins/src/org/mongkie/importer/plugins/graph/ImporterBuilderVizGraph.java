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
package org.mongkie.importer.plugins.graph;

import org.mongkie.importer.VizGraphContainer;
import org.mongkie.importer.spi.GraphFileImporterBuilder;
import org.mongkie.util.io.FileType;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Yeongjun Jang <yjjang@kribb.re.kr>
 */
@ServiceProvider(service = GraphFileImporterBuilder.class, position = 50)
public class ImporterBuilderVizGraph implements GraphFileImporterBuilder<VizGraphContainer> {

    @Override
    public ImporterVizGraph buildImporter() {
        return new ImporterVizGraph();
    }

    @Override
    public String getName() {
        return "Visual Graph Importer";
    }

    @Override
    public FileType[] getFileTypes() {
        return new FileType[]{new FileType("Serializable Visual Graph", ".vlg")};
    }

    @Override
    public boolean isMatchingImporter(FileObject fileObject) {
        return fileObject.getExt().equalsIgnoreCase("vlg");
    }
}
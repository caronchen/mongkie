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
package org.mongkie.util.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.filechooser.FileFilter;

/**
 * File filters for the open and save system dialog.
 *
 * @author Mathieu Bastian
 */
public class DialogFileFilter extends FileFilter {

    private String description;
    private List<String> extensions;

    public DialogFileFilter(String description) {
        if (description == null) {
            Logger.getLogger(DialogFileFilter.class.getName()).throwing(getClass().getName(), "constructor", new NullPointerException("Description cannot be null."));
        }
        this.description = description;
        this.extensions = new ArrayList<String>();
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory() || extensions.isEmpty()) {
            return true;
        }
        String fileName = file.getName().toLowerCase();
        for (String extension : extensions) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        StringBuilder buffer = new StringBuilder(description);
        buffer.append(" (");
        for (String extension : extensions) {
            buffer.append("*").append(extension).append(" ");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.append(")").toString();
    }

    public void setDescription(String description) {
        if (description == null) {
            Logger.getLogger(DialogFileFilter.class.getName()).throwing(getClass().getName(), "setDescription", new NullPointerException("Description cannot be null."));
        }
        this.description = description;
    }

    public void addExtension(String extension) {
        if (extension == null) {
            Logger.getLogger(DialogFileFilter.class.getName()).throwing(getClass().getName(), "addExtension", new NullPointerException("Extension cannot be null."));
        }
        extensions.add(extension);
    }

    public void addExtensions(String... extension) {
        if (extension == null) {
            Logger.getLogger(DialogFileFilter.class.getName()).throwing(getClass().getName(), "addExtensions", new NullPointerException("Extensions cannot be null."));
        }
        extensions.addAll(Arrays.asList(extension));
    }

    public void removeExtension(String extension) {
        extensions.remove(extension);
    }

    public void clearExtensions() {
        extensions.clear();
    }

    public List<String> getExtensions() {
        return extensions;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DialogFileFilter)) {
            return false;
        }
        DialogFileFilter s = (DialogFileFilter) obj;
        if (s.extensions.size() != this.extensions.size()) {
            return false;
        }
        for (int i = 0; i < extensions.size(); i++) {
            if (this.extensions.get(i) == null ? s.extensions.get(i) != null : !this.extensions.get(i).equals(s.extensions.get(i))) {
                if (!this.extensions.get(i).equals(s.extensions.get(i))) {
                    return false;
                }
            }
        }
        if (!description.equals(s.description)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 47 * hash + (this.extensions != null ? this.extensions.hashCode() : 0);
        return hash;
    }
}

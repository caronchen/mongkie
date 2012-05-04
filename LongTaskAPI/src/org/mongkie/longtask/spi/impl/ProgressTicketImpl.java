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
package org.mongkie.longtask.spi.impl;

import org.mongkie.longtask.progress.ProgressTicket;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Cancellable;

/**
 *
 * @author Mathieu Bastian
 */
public final class ProgressTicketImpl implements ProgressTicket {

    private final ProgressHandle handle;
    private String displayName;
    private int progress100 = 0;
    private int progressTotal;
    private int currentUnit = 0;
    private boolean started = false, finished = false;

    public ProgressTicketImpl(String displayName, Cancellable cancellable) {
        handle = ProgressHandleFactory.createHandle(displayName, cancellable);
        this.displayName = displayName;
    }

    /**
     * Finish the task.
     */
    @Override
    public void finish() {
        if (handle != null && started && !finished) {
            try {
                handle.finish();
                finished = true;
            } catch (Exception e) {
                System.err.println("Progress Handle failed to finish");
            }
        }
    }

    /**
     * Notify the user about a new completed unit. Equivalent to incrementing
     * workunits by one.
     */
    @Override
    public void progress() {
        progress(currentUnit + 1);
    }

    /**
     * Notify the user about completed workunits.
     *
     * @param a cumulative number of workunits completed so far
     */
    @Override
    public void progress(int workunit) {
        this.currentUnit = workunit;
        if (handle != null) {
            int ratioProgress = (int) (100.0 * workunit / progressTotal);
            if (ratioProgress != progress100) {
                progress100 = ratioProgress;
                handle.progress(progress100 <= 100 ? progress100 : 100);
            }
        }
    }

    /**
     * Notify the user about progress by showing message with details.
     *
     * @param details about the status of the task
     */
    @Override
    public void progress(String message) {
        if (handle != null) {
            handle.progress(message);
        }
    }

    /**
     * Notify the user about completed workunits and show additional detailed
     * message.
     *
     * @param message details about the status of the task
     * @param workunit a cumulative number of workunits completed so far
     */
    @Override
    public void progress(String message, int workunit) {
        currentUnit = workunit;
        if (handle != null) {
            int ratioProgress = (int) (100.0 * workunit / progressTotal);
            if (ratioProgress != progress100) {
                progress100 = ratioProgress;
                handle.progress(message, progress100 <= 100 ? progress100 : 100);
            }
        }
    }

    /**
     * Change the display name of the progress task. Use with care, please make
     * sure the changed name is not completely different, or otherwise it might
     * appear to the user as a different task.
     *
     * @param newDisplayName the new display name
     */
    @Override
    public void setDisplayName(String newDisplayName) {
        if (handle != null) {
            handle.setDisplayName(newDisplayName);
            this.displayName = newDisplayName;
        }
    }

    /**
     * Returns the current display name.
     *
     * @return the current task's display name
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Start the progress indication for indeterminate task.
     */
    @Override
    public void start() {
        if (handle != null) {
            started = true;
            handle.start();
        }
    }

    /**
     * Start the progress indication for a task with known number of steps.
     *
     * @param workunits total number of workunits that will be processed
     */
    @Override
    public void start(int workunits) {
        if (handle != null) {
            started = true;
            this.progressTotal = workunits;
            handle.start(100);
        }
    }

    /**
     * Currently indeterminate task can be switched to show percentage
     * completed.
     *
     * @param workunits workunits total number of workunits that will be
     * processed
     */
    @Override
    public void switchToDeterminate(int workunits) {
        if (handle != null) {
            if (started) {
                this.progressTotal = workunits;
                handle.switchToDeterminate(100);
            } else {
                start(workunits);
            }
        }
    }

    /**
     * Currently determinate task can be switched to indeterminate mode.
     */
    @Override
    public void switchToIndeterminate() {
        if (handle != null) {
            handle.switchToIndeterminate();
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mongkie.ui.overview;

import java.awt.BorderLayout;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import kobic.prefuse.display.DisplayListener;
import static org.mongkie.kopath.viz.Config.ROLE_PATHWAY;
import static org.mongkie.visualization.Config.MODE_OVERVIEW;
import static org.mongkie.visualization.Config.ROLE_NETWORK;
import org.mongkie.visualization.MongkieDisplay;
import org.mongkie.visualization.VisualizationController;
import org.mongkie.visualization.workspace.WorkspaceListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import prefuse.action.ActionList;
import prefuse.activity.Activity;
import prefuse.activity.ActivityListener;
import prefuse.data.Graph;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.mongkie.ui.overview//Overview//EN",
autostore = false)
@TopComponent.Description(preferredID = OverviewTopComponent.PREFERRED_ID,
iconBase = "org/mongkie/ui/overview/resources/overview.png",
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = MODE_OVERVIEW, openAtStartup = true, roles = {ROLE_NETWORK, ROLE_PATHWAY}, position = 100)
public final class OverviewTopComponent extends TopComponent implements DisplayListener<MongkieDisplay>, ActivityListener {

    private static OverviewTopComponent instance;
    /**
     * path to the icon used by the component and its open action
     */
    static final String PREFERRED_ID = "OverviewTopComponent";

    public OverviewTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(OverviewTopComponent.class, "CTL_OverviewTopComponent"));
        setToolTipText(NbBundle.getMessage(OverviewTopComponent.class, "HINT_OverviewTopComponent"));
        putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);

        Lookup.getDefault().lookup(VisualizationController.class).addWorkspaceListener(new WorkspaceListener() {
            @Override
            public void displaySelected(final MongkieDisplay display) {
                display.getLayoutAction().addActivityListener(OverviewTopComponent.this);
                display.addDisplayListener(OverviewTopComponent.this);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        overviewPanel.add(display.getOverview(), BorderLayout.CENTER);
                        overviewPanel.revalidate();
                        overviewPanel.repaint();
                    }
                });
            }

            @Override
            public void displayDeselected(final MongkieDisplay display) {
                display.getLayoutAction().removeActivityListener(OverviewTopComponent.this);
                display.removeDisplayListener(OverviewTopComponent.this);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        overviewPanel.remove(display.getOverview());
                    }
                });
            }

            @Override
            public void displayClosed(MongkieDisplay display) {
            }

            @Override
            public void displayClosedAll() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        overviewPanel.revalidate();
                        overviewPanel.repaint();
                    }
                });
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        overviewPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        overviewPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        overviewPanel.setLayout(new java.awt.BorderLayout());
        add(overviewPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel overviewPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files
     * only, i.e. deserialization routines; otherwise you could get a
     * non-deserialized instance. To obtain the singleton instance, use
     * {@link #findInstance}.
     */
    public static synchronized OverviewTopComponent getDefault() {
        if (instance == null) {
            instance = new OverviewTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the OverviewTopComponent instance. Never call {@link #getDefault}
     * directly!
     */
    public static synchronized OverviewTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(OverviewTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof OverviewTopComponent) {
            return (OverviewTopComponent) win;
        }
        Logger.getLogger(OverviewTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    @Override
    public void graphDisposing(MongkieDisplay d, Graph g) {
        d.getLayoutAction().removeActivityListener(this);
    }

    @Override
    public void graphChanged(final MongkieDisplay d, Graph g) {
        d.getLayoutAction().addActivityListener(this);
        repaintOverview(d);
    }

    private void repaintOverview(final MongkieDisplay d) {
        WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
            @Override
            public void run() {
                d.getOverview().repaint();
            }
        });
    }

    @Override
    public void activityScheduled(Activity a) {
    }

    @Override
    public void activityStarted(Activity a) {
    }

    @Override
    public void activityStepped(Activity a) {
    }

    @Override
    public void activityFinished(Activity a) {
        repaintOverview((MongkieDisplay) ((ActionList) a).getVisualization().getDisplay(0));
    }

    @Override
    public void activityCancelled(Activity a) {
        activityFinished(a);
    }
}

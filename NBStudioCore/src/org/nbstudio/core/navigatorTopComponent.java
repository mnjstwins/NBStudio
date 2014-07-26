/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nbstudio.core;

import org.nbstudio.core.mac.macEventListener;
import java.awt.BorderLayout;
import javax.swing.ActionMap;
import org.nbstudio.explorer.ConnectionsNode;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.nbstudio.core//navigator//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "navigatorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(
        mode = "explorer",
        openAtStartup = true)
@ActionID(
        category = "Window",
        id = "org.nbstudio.core.navigatorTopComponent")
@ActionReference(
        path = "Menu/Window",
        position = 0)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_navigatorAction",
        preferredID = "navigatorTopComponent")
@Messages({
    "CTL_navigatorAction=Servers",
    "CTL_navigatorTopComponent=Servers",
    "HINT_navigatorTopComponent=Servers"
})
public final class navigatorTopComponent extends TopComponent implements ExplorerManager.Provider, macEventListener {

    private final ExplorerManager manager = new ExplorerManager();

    public navigatorTopComponent() {
        initComponents();
        setName(Bundle.CTL_navigatorTopComponent());
        setToolTipText(Bundle.HINT_navigatorTopComponent());
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);

        setLayout(new BorderLayout());
        add(new BeanTreeView(), BorderLayout.CENTER);
        try {
            FileObject connectionsFolder = FileUtil.getConfigFile("Connections");
            if (connectionsFolder == null) {
                connectionsFolder = FileUtil.getConfigRoot().createFolder("Connections");
            }
            manager.setRootContext(new ConnectionsNode(connectionsFolder, "Connections"));
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        ActionMap map = getActionMap();
//        DeleteAction delete = SystemAction.get(DeleteAction.class);
//        map.put(delete.getActionMapKey(), ExplorerUtils.actionDelete(manager, true));
        associateLookup(ExplorerUtils.createLookup(manager, map));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
//    @Override
//    public void componentOpened() {
//        // TODO add custom code on component opening
//    }
//
//    @Override
//    public void componentClosed() {
//        // TODO add custom code on component closing
//    }
//
    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }
}

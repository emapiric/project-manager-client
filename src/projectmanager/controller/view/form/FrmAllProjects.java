/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.form;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTable;

/**
 *
 * @author EMA
 */
public class FrmAllProjects extends javax.swing.JFrame {

    /**
     * Creates new form FrmAllProjects
     */
    public FrmAllProjects() {
        setLocationRelativeTo(null);
        initComponents();
   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblProjects = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnDetails = new javax.swing.JButton();
        btnTasks = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmiNewProject = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 600));

        tblProjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Owner"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProjects);

        btnAdd.setText("Add");

        btnRemove.setText("Remove");

        btnDetails.setText("Details");

        btnTasks.setText("Tasks");

        jMenu1.setText("File");

        jmiNewProject.setText("New project");
        jMenu1.add(jmiNewProject);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTasks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDetails)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTasks))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDetails;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnTasks;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem jmiNewProject;
    private javax.swing.JTable tblProjects;
    // End of variables declaration//GEN-END:variables


    public JTable getTblProjects() {
        return tblProjects;
    }
        
    public JButton getBtnDetails() {
        return btnDetails;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnRemove() {
        return btnRemove;
    }

    public JMenuItem getJmiNewProject() {
        return jmiNewProject;
    }

    public JButton getBtnTasks() {
        return btnTasks;
    }
    
    public void btnDetailsActionListener(ActionListener actionListener) {
        btnDetails.addActionListener(actionListener);
    }
    
    public void jmiNewProjectActionListener(ActionListener actionListener) {
        jmiNewProject.addActionListener(actionListener);
    }

    public void btnAddActionListener(ActionListener actionListener) {
        btnAdd.addActionListener(actionListener);
    }

    public void btnRemoveActionListener(ActionListener actionListener) {
        btnRemove.addActionListener(actionListener);
    }
    
    public void btnTasksActionListener(ActionListener actionListener) {
        btnTasks.addActionListener(actionListener);
    }

}

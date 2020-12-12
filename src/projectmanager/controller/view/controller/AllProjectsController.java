/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import projectmanager.communication.Communication;
import projectmanager.controller.view.constant.Constants;
import projectmanager.controller.view.coordinator.MainCoordinator;
import projectmanager.controller.view.form.FrmAllProjects;
import projectmanager.controller.view.form.component.table.ProjectTableModel;
import projectmanager.domain.Project;

/**
 *
 * @author EMA
 */
public class AllProjectsController {
    private final FrmAllProjects frmAllProjects;

    public AllProjectsController(FrmAllProjects frmAllProjects) {
        this.frmAllProjects = frmAllProjects;
        addActionListener();
    }
    
    private void addActionListener() {
        frmAllProjects.jmiNewProjectActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAddNewProjectForm();
            }
        });
       frmAllProjects.btnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAddNewProjectForm();
            }
        });
        frmAllProjects.btnDetailsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmAllProjects.getTblProjects().getSelectedRow();
                if (row >= 0) {
                    Project project = ((ProjectTableModel) frmAllProjects.getTblProjects().getModel()).getProjectAt(row);
                    MainCoordinator.getInstance().addParam(Constants.PARAM_PROJECT, project);
                    MainCoordinator.getInstance().openProjectDetailsForm();
                } else {
                    JOptionPane.showMessageDialog(frmAllProjects, "You must select a project", "PROJECT DETAILS", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmAllProjects.btnRemoveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmAllProjects.getTblProjects().getSelectedRow();
                if (row >= 0) {
                    Project project = ((ProjectTableModel) frmAllProjects.getTblProjects().getModel()).getProjectAt(row);
                    try {
                        Communication.getInstance().deleteProject(project);
                        JOptionPane.showMessageDialog(frmAllProjects, "Project deleted successfully!\n", "Delete project", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                         JOptionPane.showMessageDialog(frmAllProjects, "Error deleting project!\n" + ex.getMessage(), "Delete project", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(AllProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmAllProjects, "You must select a project", "PROJECT DETAILS", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmAllProjects.btnTasksActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmAllProjects.getTblProjects().getSelectedRow();
                if (row >= 0) {
                    Project project = ((ProjectTableModel) frmAllProjects.getTblProjects().getModel()).getProjectAt(row);
                    MainCoordinator.getInstance().addParam(Constants.PARAM_PROJECT, project);
                    MainCoordinator.getInstance().openAllTasksForm();
                } else {
                    JOptionPane.showMessageDialog(frmAllProjects, "You must select a project", "PROJECT DETAILS", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmAllProjects.addWindowListener(new WindowAdapter(){
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblProjects();
            }
        });

    }
    
     public void openForm() {
        frmAllProjects.setLocationRelativeTo(null);
        prepareView();
        frmAllProjects.setVisible(true);
    }

    private void prepareView() {
        frmAllProjects.setTitle("View products");
    }

    private void fillTblProjects() {
        List<Project> projects;
        try {
            projects = Communication.getInstance().getAllProjects();
            ProjectTableModel ptm = new ProjectTableModel(projects);
            frmAllProjects.getTblProjects().setModel(ptm);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmAllProjects, "Error: " + ex.getMessage(), "ERROR DETAILS", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(AllProjectsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FrmAllProjects getFrmAllProjects() {
        return frmAllProjects;
    }
    
}

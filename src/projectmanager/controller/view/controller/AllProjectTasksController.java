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
import projectmanager.controller.view.form.FrmAllProjectTasks;
import projectmanager.controller.view.form.component.table.ProjectTaskTableModel;
import projectmanager.domain.Project;
import projectmanager.domain.ProjectTask;

/**
 *
 * @author Ema
 */
public class AllProjectTasksController {
    private final FrmAllProjectTasks frmAllProjectTasks;

    public AllProjectTasksController(FrmAllProjectTasks frmAllTasks) {
        this.frmAllProjectTasks = frmAllTasks;
        addActionListener();
    }

    private void addActionListener() {
        frmAllProjectTasks.btnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAddNewProjectTaskForm();
            }
        });
        frmAllProjectTasks.btnDetailsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row =  frmAllProjectTasks.getTblTasks().getSelectedRow();
                if (row>=0){
                    ProjectTask projectTask = ((ProjectTaskTableModel) frmAllProjectTasks.getTblTasks().getModel()).getProjectTaskAt(row);
                    MainCoordinator.getInstance().addParam(Constants.PARAM_PROJECT_TASK, projectTask);
                    MainCoordinator.getInstance().openProjectTaskDetailsForm();
                }
                else {
                    JOptionPane.showMessageDialog(frmAllProjectTasks, "You must select a task.", "PROJECT TASK DETAILS", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmAllProjectTasks.btnRemoveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmAllProjectTasks.getTblTasks().getSelectedRow();
                if (row>=0) {
                    ProjectTask projectTask = ((ProjectTaskTableModel) frmAllProjectTasks.getTblTasks().getModel()).getProjectTaskAt(row);
                    try {
                        Communication.getInstance().deleteProjectTask(projectTask);
                        JOptionPane.showMessageDialog(frmAllProjectTasks, "Project task deleted successfully!\n", "Delete project task", JOptionPane.INFORMATION_MESSAGE);
                    }catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmAllProjectTasks, "Error deleting task.", "PROJECT TASK DETAILS", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(frmAllProjectTasks, "You must select a task.", "PROJECT TASK DETAILS", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmAllProjectTasks.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblTasks();
            }
        });
    }
    
    public void openForm() {
        frmAllProjectTasks.setLocationRelativeTo(null);
        prepareView();
        frmAllProjectTasks.setVisible(true);
    }
    
    private void fillTblTasks() {
        try {
            List<ProjectTask> projectTasks;
            Project project = (Project) MainCoordinator.getInstance().getParam(Constants.PARAM_PROJECT);
            projectTasks = Communication.getInstance().getAllProjectTasks(project);
            ProjectTaskTableModel pttm = new ProjectTaskTableModel(projectTasks);
            frmAllProjectTasks.getTblTasks().setModel(pttm);
        } catch (Exception ex) {
            Logger.getLogger(AllProjectTasksController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareView() {
        frmAllProjectTasks.setTitle("View project tasks");
        Project project = (Project) MainCoordinator.getInstance().getParam(Constants.PARAM_PROJECT);
        frmAllProjectTasks.getLblProject().setText(project.getId()+" "+project.getName());
    }

    public FrmAllProjectTasks getFrmAllTasks() {
        return frmAllProjectTasks;
    }
    
    
}

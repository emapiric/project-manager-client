/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import projectmanager.communication.Communication;
import projectmanager.controller.view.component.validator.impl.RequiredStringValidator;
import projectmanager.controller.view.constant.Constants;
import projectmanager.controller.view.coordinator.MainCoordinator;
import projectmanager.controller.view.form.FrmProject;
import projectmanager.controller.view.form.component.table.AssigneeTableModel;
import projectmanager.domain.Project;
import projectmanager.domain.User;
import projectmanager.view.form.util.FormMode;

/**
 *
 * @author Ema
 */
public class ProjectController {
    private final FrmProject frmProject;
    private FormMode formMode;

    public ProjectController(FrmProject frmProject) {
        this.frmProject = frmProject;
        addActionListeners();
    }

    private void addActionListeners() {
        frmProject.addSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();         
            }

            private void save() {
                 try {
                    Project project = new Project();
                    project.setName(frmProject.getInputName().getValue().toString());
                    project.setDescription(frmProject.getInputDescription().getValue().toString());
                    AssigneeTableModel model = (AssigneeTableModel) frmProject.getTblAssignees().getModel();
                    if (model.getAssignees().isEmpty()) {
                        JOptionPane.showMessageDialog(frmProject,"Add at least 1 assignee");
                        return;
                    }
                    project.setAssignees(model.getAssignees());
                    switch(formMode) {
                        case FORM_ADD:
                            User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                            project.setOwner(user);
                            Communication.getInstance().addProject(project);
                            break;
                        case FORM_VIEW:
                            project.setId(Integer.parseInt(frmProject.getInputId().getValue().toString()));
                            Communication.getInstance().editProject(project);
                            break;
                    }
                    JOptionPane.showMessageDialog(frmProject, "Project successfully saved");
                    frmProject.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(FrmProject.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmProject, ex.getMessage());
                    if (ex.getMessage().equals(Constants.SERVER_CLOSED))
                        System.exit(0);
                }
            }
        });
        
        frmProject.addAddAssigneeBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AssigneeTableModel model = (AssigneeTableModel) frmProject.getTblAssignees().getModel();
                    User assignee = (User) frmProject.getInputAssignee().getValue();
                    model.addAssignee(assignee);
                    model.fireTableRowsInserted(model.getRowCount(), model.getRowCount());
                } catch (Exception ex) {
                    Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
     public void openForm(FormMode formMode) {
        frmProject.setLocationRelativeTo(MainCoordinator.getInstance().getAllProjectsController().getFrmAllProjects());
        prepareView(formMode);
        this.formMode = formMode;
        setupComponents(formMode);
        frmProject.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        fillCbAssignees();
        fillTblAssignees(formMode);
        frmProject.getInputId().getLblText().setText("ID:");
        frmProject.getInputId().getLblErrorValue().setText("");
        frmProject.getInputId().getTxtValue().setEnabled(false);
        
        frmProject.getInputName().setValidator(new RequiredStringValidator());
        frmProject.getInputName().getLblText().setText("Name:");
        frmProject.getInputName().getLblErrorValue().setText("");
        
        frmProject.getInputDescription().setValidator(new RequiredStringValidator());
        frmProject.getInputDescription().getLblText().setText("Description:");
        frmProject.getInputDescription().getLblErrorValue().setText("");    
        
        frmProject.getInputOwner().getLblText().setText("Author:");
        frmProject.getInputOwner().getTxtValue().setEnabled(false);
        frmProject.getInputOwner().getLblErrorValue().setText("");
        
        frmProject.getInputAssignee().getLblText().setText("Add assignee:");
        frmProject.getInputAssignee().getLblErrorValue().setText("");
    }
         
    private void setupComponents(FormMode formMode) {
        User currentUser = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        switch(formMode) {
            case FORM_ADD:
                frmProject.getInputOwner().getTxtValue().setText(currentUser.getUsername());
                break;
            case FORM_VIEW:
                Project project = (Project) MainCoordinator.getInstance().getParam(Constants.PARAM_PROJECT);
                frmProject.getInputId().getTxtValue().setText(String.valueOf(project.getId()));
                frmProject.getInputName().getTxtValue().setText(project.getName());
                frmProject.getInputDescription().getTxtAreaValue().setText(project.getDescription());
                frmProject.getInputOwner().getTxtValue().setText(project.getOwner().getUsername());
                authorize(currentUser, project);
                break;
        }
    }

    private void authorize(User currentUser, Project project) {
        if (currentUser.getId() != project.getOwner().getId()) {
            frmProject.getInputName().getTxtValue().setEnabled(false);
            frmProject.getInputDescription().getTxtAreaValue().setEnabled(false);
            frmProject.getBtnSave().setEnabled(false);
            frmProject.getInputAssignee().getCb().setEnabled(false);
            frmProject.getBtnAddAssignee().setEnabled(false);
        }
    }

    private void fillCbAssignees() {
        try {
            frmProject.getInputAssignee().getCb().setModel(new DefaultComboBoxModel<>(Communication.getInstance().getAllUsers().toArray()));
            frmProject.getInputAssignee().getCb().setSelectedIndex(0);
            frmProject.getInputAssignee().getCb().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        User user = (User) e.getItem();
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ProjectTaskController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals(Constants.SERVER_CLOSED))
                System.exit(0);
        }
    }

    private void fillTblAssignees(FormMode formMode) {
        List<User> assignees = new ArrayList<User>();
        try {
            if (formMode == FormMode.FORM_VIEW) {
                Project project = (Project) MainCoordinator.getInstance().getParam(Constants.PARAM_PROJECT);
                assignees = project.getAssignees();
            }
            AssigneeTableModel atm = new AssigneeTableModel(assignees);
            frmProject.getTblAssignees().setModel(atm);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmProject, "Error: " + ex.getMessage(), "ERROR DETAILS", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(AllProjectsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
}

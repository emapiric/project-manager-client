/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import projectmanager.communication.Communication;
import projectmanager.controller.view.component.validator.impl.RequiredStringValidator;
import projectmanager.controller.view.constant.Constants;
import projectmanager.controller.view.coordinator.MainCoordinator;
import projectmanager.controller.view.form.FrmProject;
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
                }
            }
        });
    }
    
     public void openForm(FormMode formMode) {
        frmProject.setLocationRelativeTo(MainCoordinator.getInstance().getAllProjectsController().getFrmAllProjects());
        prepareView();
        this.formMode = formMode;
        setupComponents(formMode);
        frmProject.setVisible(true);
    }

    private void prepareView() {
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
        }
    }


    
}

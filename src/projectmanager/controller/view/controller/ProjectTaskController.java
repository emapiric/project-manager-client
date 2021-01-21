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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import projectmanager.communication.Communication;
import projectmanager.controller.view.component.validator.impl.RequiredStringValidator;
import projectmanager.controller.view.constant.Constants;
import projectmanager.controller.view.coordinator.MainCoordinator;
import projectmanager.controller.view.form.FrmProjectTask;
import projectmanager.domain.Project;
import projectmanager.domain.ProjectTask;
import projectmanager.domain.Status;
import projectmanager.domain.Task;
import projectmanager.domain.User;
import projectmanager.view.form.util.FormMode;

/**
 *
 * @author Ema
 */
public class ProjectTaskController {
    private final FrmProjectTask frmProjectTask;
    private FormMode formMode;

    public ProjectTaskController(FrmProjectTask frmProjectTask) {
        this.frmProjectTask = frmProjectTask;
        addActionListener();
    }

    private void addActionListener() {
        frmProjectTask.addSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
            private void save() {
                try {
                    ProjectTask projectTask = new ProjectTask();
                    projectTask.setDescription(frmProjectTask.getInputProjectTaskDescription().getValue().toString());
                    projectTask.setTask((Task) frmProjectTask.getInputTask().getValue());
                    projectTask.setAssignee((User) frmProjectTask.getInputAssignee().getValue());
                    projectTask.setStatus((Status) frmProjectTask.getInputStatus().getValue());
                    switch(formMode) {
                        case FORM_ADD:
                            Project project = (Project) MainCoordinator.getInstance().getParam(Constants.PARAM_PROJECT);
                            User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                            projectTask.setProject(project);
                            projectTask.setAuthor(user);
                            Communication.getInstance().addProjectTask(projectTask);
                            break;
                        case FORM_VIEW:
                            projectTask.setId(Integer.parseInt(frmProjectTask.getInputId().getValue().toString()));
                            Communication.getInstance().editProjectTask(projectTask);
                            break;
                    }
                    JOptionPane.showMessageDialog(frmProjectTask, "Project task successfully saved");
                    frmProjectTask.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmProjectTask, e.getMessage());
                    if (e.getMessage().equals(Constants.SERVER_CLOSED))
                        System.exit(0);
                }
            }
        });
    }
    
    public void openForm(FormMode formMode) {
        this.formMode = formMode;
        frmProjectTask.setLocationRelativeTo(MainCoordinator.getInstance().getAllProjectsController().getFrmAllProjects());
        prepareView();
        setupComponents();
        frmProjectTask.setVisible(true);
    }

    private void prepareView() {
        fillCbTask();
        fillCbAssignee();
        fillCbStatus();
        frmProjectTask.getInputId().getLblText().setText("ID:");
        frmProjectTask.getInputId().getLblErrorValue().setText("");
        frmProjectTask.getInputId().getTxtValue().setEnabled(false);
        
        frmProjectTask.getInputProject().getLblText().setText("Project:");
        frmProjectTask.getInputProject().getLblErrorValue().setText("");
        frmProjectTask.getInputProject().getTxtValue().setEnabled(false);
        
        frmProjectTask.getInputAuthor().getLblText().setText("Author:");
        frmProjectTask.getInputAuthor().getLblErrorValue().setText("");
        frmProjectTask.getInputAuthor().getTxtValue().setEnabled(false);
        
        frmProjectTask.getInputTask().getLblText().setText("Task:");
        frmProjectTask.getInputTask().getLblErrorValue().setText("");
        
        frmProjectTask.getInputTaskDescription().getLblText().setText("Task description:");
        frmProjectTask.getInputTaskDescription().getLblErrorValue().setText("");
        frmProjectTask.getInputTaskDescription().getTxtAreaValue().setEnabled(false);
        
        frmProjectTask.getInputProjectTaskDescription().setValidator(new RequiredStringValidator());
        frmProjectTask.getInputProjectTaskDescription().getLblText().setText("Description:");
        frmProjectTask.getInputProjectTaskDescription().getLblErrorValue().setText("");
        
        frmProjectTask.getInputAssignee().getLblText().setText("Assignee:");
        frmProjectTask.getInputAssignee().getLblErrorValue().setText("");
        
        frmProjectTask.getInputStatus().getLblText().setText("Status:");
        frmProjectTask.getInputStatus().getLblErrorValue().setText("");
    }
    
    private void fillCbTask() {
        try {
            frmProjectTask.getInputTask().getCb().setModel(new DefaultComboBoxModel<>(Communication.getInstance().getAllTasks().toArray()));
            frmProjectTask.getInputTask().getCb().setSelectedIndex(0);
            frmProjectTask.getInputTaskDescription().getTxtAreaValue().setText("Create registration form");
            frmProjectTask.getInputTask().getCb().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Task task = (Task) e.getItem();
                        frmProjectTask.getInputTaskDescription().getTxtAreaValue().setText(task.getDescription());
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ProjectTaskController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals(Constants.SERVER_CLOSED))
                System.exit(0);
        }
    }
    
    private void fillCbAssignee() {
        try {
            Project currentProject = (Project) MainCoordinator.getInstance().getParam(Constants.PARAM_PROJECT);
            frmProjectTask.getInputAssignee().getCb().setModel(new DefaultComboBoxModel<>(currentProject.getAssignees().toArray()));
            frmProjectTask.getInputAssignee().getCb().setSelectedIndex(0);
            frmProjectTask.getInputAssignee().getCb().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        User user = (User) e.getItem();
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ProjectTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillCbStatus() {
        try {
            frmProjectTask.getInputStatus().getCb().setModel(new DefaultComboBoxModel<>(Status.class.getEnumConstants()));
            frmProjectTask.getInputStatus().getCb().setSelectedIndex(0);
            frmProjectTask.getInputStatus().getCb().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Status status = (Status) e.getItem();
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ProjectTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupComponents() {
        User currentUser = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        Project currentProject = (Project) MainCoordinator.getInstance().getParam(Constants.PARAM_PROJECT);
        switch(formMode) {
            case FORM_ADD:
                frmProjectTask.getInputProject().getTxtValue().setText(currentProject.getId()+" "+currentProject.getName());
                frmProjectTask.getInputAuthor().getTxtValue().setText(currentUser.getUsername());
                break;
            case FORM_VIEW:
                ProjectTask projectTask = (ProjectTask) MainCoordinator.getInstance().getParam(Constants.PARAM_PROJECT_TASK);
                frmProjectTask.getInputId().getTxtValue().setText(String.valueOf(projectTask.getId()));
                frmProjectTask.getInputProject().getTxtValue().setText(projectTask.getProject().getId()+" "+projectTask.getProject().getName());
                frmProjectTask.getInputAuthor().getTxtValue().setText(projectTask.getAuthor().getUsername());
                frmProjectTask.getInputAssignee().getCb().setSelectedItem(projectTask.getAssignee());
                frmProjectTask.getInputTask().getCb().setSelectedItem(projectTask.getTask());
                frmProjectTask.getInputStatus().getCb().setSelectedItem(projectTask.getStatus());
                frmProjectTask.getInputTaskDescription().getTxtAreaValue().setText(projectTask.getTask().getDescription());
                frmProjectTask.getInputProjectTaskDescription().getTxtAreaValue().setText(projectTask.getDescription());
                authorize(currentUser, projectTask);
                break;
        }
    }

    private void authorize(User currentUser, ProjectTask projectTask) {
        if (currentUser.getId() != projectTask.getAuthor().getId()) {
            frmProjectTask.getInputAssignee().setEnabled(false);
            frmProjectTask.getInputTask().setEnabled(false);
            frmProjectTask.getInputStatus().setEnabled(false);
            frmProjectTask.getInputTaskDescription().setEnabled(false);
            frmProjectTask.getInputProjectTaskDescription().setEnabled(false);
            frmProjectTask.getBtnSave().setEnabled(false);
        }
    }

}

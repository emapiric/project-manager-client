/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.coordinator;

import java.util.HashMap;
import java.util.Map;
import projectmanager.controller.view.constant.Constants;
import projectmanager.controller.view.controller.AllProjectTasksController;
import projectmanager.controller.view.controller.AllProjectsController;
import projectmanager.controller.view.controller.LoginController;
import projectmanager.controller.view.controller.ProjectController;
import projectmanager.controller.view.controller.ProjectTaskController;
import projectmanager.controller.view.form.FrmAllProjectTasks;
import projectmanager.controller.view.form.FrmAllProjects;
import projectmanager.controller.view.form.FrmLogin;
import projectmanager.controller.view.form.FrmProject;
import projectmanager.controller.view.form.FrmProjectTask;
import projectmanager.view.form.util.FormMode;

/**
 *
 * @author EMA
 */
public class MainCoordinator {
     private static MainCoordinator instance;

    private final AllProjectsController allProjectsController;
    private final Map<String, Object> params;
    

    private MainCoordinator() {
        allProjectsController = new AllProjectsController(new FrmAllProjects());
        params = new HashMap<>();
    }

    public static MainCoordinator getInstance() {
        if (instance == null) {
            instance = new MainCoordinator();
        }
        return instance;
    }
     public void openLoginForm() {
        LoginController loginContoller = new LoginController(new FrmLogin());
        loginContoller.openForm();
    }

    public void openAllProjectsForm() {
        allProjectsController.openForm();
    }

    public void openAddNewProjectForm() {
        ProjectController projectController = new ProjectController(new FrmProject(allProjectsController.getFrmAllProjects(), true));
        projectController.openForm(FormMode.FORM_ADD);
    }
    public void openProjectDetailsForm() {
        FrmProject projectDetails = new FrmProject(allProjectsController.getFrmAllProjects(), true);
        ProjectController projectController = new ProjectController(projectDetails);
        projectController.openForm(FormMode.FORM_VIEW);
        params.put(Constants.PARAM_PROJECT,projectDetails);
    }
    
    public void openAllTasksForm() {
        FrmAllProjectTasks allProjectTasks = new FrmAllProjectTasks();
        AllProjectTasksController allProjectTasksController = new AllProjectTasksController(allProjectTasks);
        allProjectTasksController.openForm();
        params.put(Constants.FORM_ALL_PROJECT_TASKS,allProjectTasks);
    }
    
    public void openAddNewProjectTaskForm() {
        ProjectTaskController projectTaskController = new ProjectTaskController(new FrmProjectTask((FrmAllProjectTasks)params.get(Constants.FORM_ALL_PROJECT_TASKS), true));
        projectTaskController.openForm(FormMode.FORM_ADD);
    }

    public void openProjectTaskDetailsForm() {
        FrmProjectTask taskDetails = new FrmProjectTask((FrmAllProjectTasks)params.get(Constants.FORM_ALL_PROJECT_TASKS), true);
        ProjectTaskController projectTaskController = new ProjectTaskController(taskDetails);
        projectTaskController.openForm(FormMode.FORM_VIEW);
        params.put(Constants.PARAM_PROJECT_TASK,taskDetails);
    }
    
    public AllProjectsController getAllProjectsController() {
        return allProjectsController;
    }


    public void addParam(String name, Object key) {
        params.put(name, key);
    }

    public Object getParam(String name) {
        return params.get(name);
    }

}

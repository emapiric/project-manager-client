/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.communication;

import java.net.Socket;
import java.util.List;
import projectmanager.domain.Project;
import projectmanager.domain.ProjectTask;
import projectmanager.domain.Task;
import projectmanager.domain.User;

/**
 *
 * @author EMA
 */
public class Communication {
    Socket socket;
    Sender sender;
    Receiver receiver;
    private static Communication instance;
    private Communication() throws Exception{
        socket=new Socket("127.0.0.1", 9000);
        sender=new Sender(socket);
        receiver=new Receiver(socket);
    }
    public static Communication getInstance() throws Exception{
        if(instance==null){
            instance=new Communication();
        }
        return instance;
    }
    
    public User login(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Request request = new Request(Operation.LOGIN,user);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException()==null) {
            return (User) response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public List<User> getAllUsers() throws Exception{
        Request request = new Request(Operation.GET_ALL_USERS,null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<User>) response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public User getUserById(int id) throws Exception {
        Request request = new Request(Operation.GET_USER_BY_ID,id);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (User) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Project> getAllProjects() throws Exception{
        Request request = new Request(Operation.GET_ALL_PROJECTS,null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Project>) response.getResult();
        } else {
            throw response.getException();
        }
    }
        
    public void addProject(Project project) throws Exception {
        Request request = new Request(Operation.ADD_PROJECT,project);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }
        
    public void editProject(Project project) throws Exception {
        Request request = new Request(Operation.EDIT_PROJECT,project);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }
    
    public void deleteProject(Project project) throws Exception {
       Request request = new Request(Operation.DELETE_PROJECT,project);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }


    public List<ProjectTask> getAllProjectTasks(Project project) throws Exception{
        Request request = new Request(Operation.GET_ALL_PROJECT_TASKS,project);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<ProjectTask>) response.getResult();
        } else {
            throw response.getException();
        }
    }


    public void addProjectTask(ProjectTask projectTask) throws Exception{
        Request request = new Request(Operation.ADD_PROJECT_TASK,projectTask);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public void editProjectTask(ProjectTask projectTask) throws Exception {
        Request request = new Request(Operation.EDIT_PROJECT_TASK,projectTask);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }
    
    public void deleteProjectTask(ProjectTask projectTask) throws Exception{
        Request request = new Request(Operation.DELETE_PROJECT_TASK,projectTask);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public List<Task> getAllTasks() throws Exception{
        Request request = new Request(Operation.GET_ALL_TASKS,null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Task>) response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    public Task getTaskById(int id) throws Exception{
        Request request = new Request(Operation.GET_TASK_BY_ID,id);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (Task) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public ProjectTask getProjectTaskById(ProjectTask projectTask) throws Exception{
        Request request = new Request(Operation.GET_PROJECT_TASK_BY_ID, projectTask);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (ProjectTask) response.getResult();
        } else {
            throw response.getException();
        }
    }

    
    
}

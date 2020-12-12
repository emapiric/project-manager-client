/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.form.component.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import projectmanager.domain.ProjectTask;

/**
 *
 * @author Ema
 */
public class ProjectTaskTableModel extends AbstractTableModel{
    
    private final String[] columnNames = {"ID","Created On","Task","Assignee","Status","Author"};
    private final List<ProjectTask> projectTasks;

    public ProjectTaskTableModel(List<ProjectTask> projectTasks) {
        this.projectTasks = projectTasks;
    }

    @Override
    public String getColumnName(int column) {
        if (column>columnNames.length) return "n/a";
        return columnNames[column];
    }
    
    @Override
    public int getRowCount() {
        if (projectTasks == null) return 0;
        return projectTasks.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProjectTask projectTask = projectTasks.get(rowIndex);
        switch(columnIndex){
            case 0: return projectTask.getId();
            case 1: return projectTask.getCreatedOn();
            case 2: return projectTask.getTask();
            case 3: return projectTask.getAssignee();
            case 4: return projectTask.getStatus();
            case 5: return projectTask.getAuthor();
            default: return "n/a";
        }
    }
    
    public void addProjectTask(ProjectTask projectTask) {
        projectTasks.add(projectTask);
        fireTableRowsInserted(projectTasks.size()-1, projectTasks.size()-1);
    }
    
    public ProjectTask getProjectTaskAt(int row) {
        return projectTasks.get(row);
    }
    
}

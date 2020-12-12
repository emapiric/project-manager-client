/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.form.component.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import projectmanager.domain.Project;
import projectmanager.domain.User;

/**
 *
 * @author Ema
 */
public class ProjectTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID","Name","Author"};
    private final List<Project> projects;

    public ProjectTableModel(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String getColumnName(int column) {
        if (column>columnNames.length) return "n/a";
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        if (projects == null) return 0;
        return projects.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Project project = projects.get(rowIndex);
        switch(columnIndex) {
            case 2:
                project.setOwner((User) value);
                break;
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Project project = projects.get(rowIndex);
        switch(columnIndex) {
            case 0: return project.getId();
            case 1: return project.getName();
            case 2: return project.getOwner();
            default: return "n/a";
        }
    }
    
    public void addProject(Project project){
        projects.add(project);
        fireTableRowsInserted(projects.size()-1, projects.size()-1);
    }
    
    public Project getProjectAt(int row) {
        return projects.get(row);
    }
    
    
}

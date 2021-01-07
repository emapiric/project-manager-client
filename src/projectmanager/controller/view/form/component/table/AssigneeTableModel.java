/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.form.component.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import projectmanager.domain.Project;
import projectmanager.domain.User;

/**
 *
 * @author Ema
 */
public class AssigneeTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID","Username"};
    private final List<User> assignees;

    public AssigneeTableModel(List<User> assignees) {
        this.assignees = assignees;
    }

    @Override
    public String getColumnName(int column) {
        if (column>columnNames.length) return "n/a";
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        if (assignees == null) return 0;
        return assignees.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        User assignee = assignees.get(rowIndex);
//        switch(columnIndex) {
//            case 2:
//                project.setOwner((User) value);
//                break;
//        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User assignee = assignees.get(rowIndex);
        switch(columnIndex) {
            case 0: return assignee.getId();
            case 1: return assignee.getUsername();
            default: return "n/a";
        }
    }
    
    public void addAssignee(User assignee){
        if (!assignees.contains(assignee)) {
            assignees.add(assignee);
            fireTableRowsInserted(assignees.size()-1, assignees.size()-1);
        }
    }
    
    public User getAssigneeAt(int row) {
        return assignees.get(row);
    }

    public List<User> getAssignees() {
        return assignees;
    }
    
    
}

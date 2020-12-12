/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.component.validator.impl;
import javax.swing.JPasswordField;
import projectmanager.controller.view.component.exception.ValidationException;
import projectmanager.controller.view.component.validator.Validator;

/**
 *
 * @author EMA
 */
public class PasswordValidator implements Validator{

    @Override
    public void validate(Object object) throws ValidationException {
        JPasswordField passwordField = (JPasswordField)object;
        if (passwordField.getPassword().length == 0) throw new ValidationException("Field is empty!");
    }
    
}

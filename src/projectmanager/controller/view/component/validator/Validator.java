/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.component.validator;

import projectmanager.controller.view.component.exception.ValidationException;

/**
 *
 * @author EMA
 */
public interface Validator {
    void validate(Object object) throws ValidationException;
}

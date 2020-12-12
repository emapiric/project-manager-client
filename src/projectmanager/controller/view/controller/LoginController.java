/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import projectmanager.communication.Communication;
import projectmanager.controller.view.constant.Constants;
import projectmanager.controller.view.coordinator.MainCoordinator;
import projectmanager.controller.view.form.FrmLogin;
import projectmanager.domain.User;

/**
 *
 * @author EMA
 */
public class LoginController {
    private final FrmLogin frmLogin;

    public LoginController(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListener();
    }

    public void openForm() {
        frmLogin.setVisible(true);
    }

    private void addActionListener() {
        frmLogin.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginUser(actionEvent);
            }

            private void loginUser(ActionEvent actionEvent) {
                try {
                    String username = frmLogin.getInputUsername().getValue().toString();
                    String password = String.copyValueOf((char[]) frmLogin.getInputPassword().getValue());

                    validateForm(username, password);

                    User user = Communication.getInstance().login(username, password);
                    JOptionPane.showMessageDialog(
                            frmLogin,
                            "Welcome " + user.getFirstname() + " " + user.getLastname(),
                            "Login", JOptionPane.INFORMATION_MESSAGE
                    );
                    frmLogin.dispose();
                    MainCoordinator.getInstance().addParam(Constants.CURRENT_USER, user);
                    MainCoordinator.getInstance().openAllProjectsForm();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmLogin, e.getMessage(), "Login error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void validateForm(String username, String password) throws Exception {
                String errorMessage = "";
                if (username.isEmpty()) {
                    frmLogin.getInputUsername().getLblErrorValue().setText("Username can not be empty!");
                    errorMessage += "Username can not be empty!\n";
                }
                if (password.isEmpty()) {
                    frmLogin.getInputPassword().getLblErrorValue().setText("Password can not be empty!");
                    errorMessage += "Password can not be empty!\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }
        });
    }
}

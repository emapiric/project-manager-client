/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager.controller.view.component;

import javax.swing.JLabel;
import javax.swing.JTextField;
import projectmanager.controller.view.component.exception.ValidationException;
import projectmanager.controller.view.component.validator.Validator;

/**
 *
 * @author EMA
 */
public class InputTextFieldPanel extends javax.swing.JPanel implements GetValue{
    private Validator validator;

    public InputTextFieldPanel() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblText = new javax.swing.JLabel();
        txtValue = new javax.swing.JTextField();
        lblErrorValue = new javax.swing.JLabel();

        lblText.setText("Text:");

        txtValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValueActionPerformed(evt);
            }
        });

        lblErrorValue.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorValue.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblText, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblErrorValue, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtValue)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblText)
                    .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorValue))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValueActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblErrorValue;
    private javax.swing.JLabel lblText;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables

     public JLabel getLblErrorValue() {
        return lblErrorValue;
    }

    public JLabel getLblText() {
        return lblText;
    }

    public JTextField getTxtValue() {
        return txtValue;
    }
    
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
    
    @Override
    public Object getValue() throws Exception {
        lblErrorValue.setText("");
        try {
            if (validator != null) validator.validate(txtValue.getText().trim());
            return txtValue.getText().trim();
        } catch(ValidationException ve){
            lblErrorValue.setText(ve.getMessage());
            throw new Exception(ve.getMessage());
        }
    }
    
}

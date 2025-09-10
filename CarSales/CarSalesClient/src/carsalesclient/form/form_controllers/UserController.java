/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_controllers;

import carsalesclient.controller.ClientController;
import carsalesclient.form.AddUserForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_coordinator.Coordinator;
import carsalesclient.form.modes.AddFormMode;
import domain.User;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalBorders;

/**
 *
 * @author user
 */
public class UserController {
    private final AddUserForm addUserForm;

    public UserController(AddUserForm addUserForm) {
        this.addUserForm = addUserForm;
        addListeners();
    }
    
    public void openForm(AddFormMode formMode){
        prepareForm(formMode);
        addUserForm.setVisible(true);
    }

    private void addListeners() {
        addUserForm.btnEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareForm(AddFormMode.UPDATE_FORM);
            }
        });
        
        addUserForm.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
            private void update() {
                try {
                    if(!emptyFields()){
                        User user = new User(null, addUserForm.getTxtUsername().getText(), addUserForm.getTxtPassword().getText(), addUserForm.getTxtFirstName().getText(), addUserForm.getTxtLastName().getText());
                        if(JOptionPane.showConfirmDialog(addUserForm, "Are you sure you want to SAVE the following changes into the database: \n"+user.getFirstName()+" "+user.getLastName()+", "+user.getUsername() + ", " + user.getPassword(), "Update user", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            user.setIdUser(((User) Coordinator.getInstance().getParam(CoordinatorParamConsts.USER_DETAILS)).getIdUser());
                            ClientController.getInstance().updateUser(user);
                            JOptionPane.showMessageDialog(addUserForm, user.getFirstName() + " " + user.getLastName() + " has been successfully updated!");
                            addUserForm.dispose();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(addUserForm, "Fill all required fields");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                
            }
        });
        
        addUserForm.btnSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
            private void save() {
                try {
                    if(!emptyFields()){
                        User user = new User(null, addUserForm.getTxtUsername().getText(), addUserForm.getTxtPassword().getText(), addUserForm.getTxtFirstName().getText(), addUserForm.getTxtLastName().getText());
                        if(JOptionPane.showConfirmDialog(addUserForm, "Are you sure you want to SAVE the following user into the database: \n"+user.getFirstName()+" "+user.getLastName()+", "+user.getUsername() + ", " + user.getPassword(), "Save user", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            ClientController.getInstance().insertUser(user);
                            if(JOptionPane.showConfirmDialog(addUserForm, user.getFirstName()+" " + user.getLastName()+" has been successfully added to the database!\n\nAdd more users?", "Success", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                prepareForm(AddFormMode.ADD_FORM);
                            }
                            else{
                                addUserForm.dispose();
                            }
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(addUserForm, "Fill all required fields");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    JOptionPane.showMessageDialog(addUserForm, e.getMessage());
                }
                
            }
        });
        
        addUserForm.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = (User) Coordinator.getInstance().getParam(CoordinatorParamConsts.USER_DETAILS);
                try {
                    ClientController.getInstance().deleteUser(user);
                    JOptionPane.showMessageDialog(addUserForm, "System successfully deleted user");
                    addUserForm.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addUserForm, "System couldn't delete user");
                    ex.printStackTrace();
                }
            }
        });
        
        addUserForm.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUserForm.dispose();
            }
        });
    }

    private void prepareForm(AddFormMode formMode) {
        switch (formMode) {
            case AddFormMode.ADD_FORM:
                addUserForm.getTxtId().setVisible(false);
                addUserForm.getLblId().setVisible(false);
                addUserForm.getTxtFirstName().setText("");
                addUserForm.getTxtFirstName().setEnabled(true);
                addUserForm.getTxtLastName().setText("");
                addUserForm.getTxtLastName().setEnabled(true);
                addUserForm.getTxtUsername().setText("");
                addUserForm.getTxtUsername().setEnabled(true);
                addUserForm.getTxtPassword().setText("");
                addUserForm.getTxtPassword().setEnabled(true);
                
                addUserForm.getBtnEnableChanges().setVisible(false);
                addUserForm.getBtnEdit().setVisible(false);
                addUserForm.getBtnDelete().setVisible(false);
                addUserForm.getBtnSave().setVisible(true);
                addUserForm.getBtnCancel().setVisible(true);
                break;
            case AddFormMode.DETAILS_FORM:
                User user = (User) Coordinator.getInstance().getParam(CoordinatorParamConsts.USER_DETAILS);
                addUserForm.getTxtId().setVisible(true);
                addUserForm.getLblId().setVisible(true);
                addUserForm.getTxtId().setText(user.getIdUser().toString());
                addUserForm.getTxtFirstName().setText(user.getFirstName());
                addUserForm.getTxtFirstName().setEnabled(false);
                addUserForm.getTxtLastName().setText(user.getLastName());
                addUserForm.getTxtLastName().setEnabled(false);
                addUserForm.getTxtUsername().setText(user.getUsername());
                addUserForm.getTxtUsername().setEnabled(false);
                addUserForm.getTxtPassword().setText(user.getPassword());
                addUserForm.getTxtPassword().setEnabled(false);
                
                addUserForm.getBtnEnableChanges().setVisible(true);
                addUserForm.getBtnEnableChanges().setEnabled(true);
                addUserForm.getBtnDelete().setVisible(true);
                addUserForm.getBtnDelete().setEnabled(true);
                addUserForm.getBtnEdit().setVisible(true);
                addUserForm.getBtnEdit().setEnabled(false);
                addUserForm.getBtnSave().setVisible(false);
                addUserForm.getBtnCancel().setVisible(true);
                break;
                
            case AddFormMode.UPDATE_FORM:
                addUserForm.getTxtFirstName().setEnabled(true);
                addUserForm.getTxtLastName().setEnabled(true);
                addUserForm.getTxtUsername().setEnabled(true);
                addUserForm.getTxtPassword().setEnabled(true);
                
                addUserForm.getBtnEnableChanges().setEnabled(false);
                addUserForm.getBtnEdit().setEnabled(true);
                addUserForm.getBtnDelete().setEnabled(false);
                break;
            default:
                break;
        }
    }
    
    private boolean emptyFields(){
        Border border = new LineBorder(Color.red, 1,true);
        Font font = new Font("Gill Sans MT", 1, 8);
        boolean empty = false;
        
        
        if(addUserForm.getTxtFirstName().getText().isBlank()){
            addUserForm.getTxtFirstName().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
            empty = true;
        }
        else{
            addUserForm.getTxtFirstName().setBorder(new MetalBorders.TextFieldBorder());
                
        }
        if(addUserForm.getTxtLastName().getText().isBlank()){
            addUserForm.getTxtLastName().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
            empty = true;
        }
        else{
            addUserForm.getTxtLastName().setBorder(new MetalBorders.TextFieldBorder());
                
        }
        
        return empty;
    }
}

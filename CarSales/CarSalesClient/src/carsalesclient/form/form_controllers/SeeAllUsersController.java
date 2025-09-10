/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_controllers;

import carsalesclient.controller.ClientController;
import carsalesclient.form.UsersTableForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_coordinator.Coordinator;
import carsalesclient.form.modes.AddFormMode;
import carsalesclient.form.modes.TableFormMode;
import carsalesclient.form.tableModels.UsersTableModel;
import domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class SeeAllUsersController {
    private final UsersTableForm usersTableForm;

    public SeeAllUsersController(UsersTableForm usersTableForm) {
        this.usersTableForm = usersTableForm;
        addListeners();
    }
    
    public void openForm(TableFormMode formMode){
        prepareForm(formMode);
        fillTable();
        usersTableForm.setVisible(true);
    }
    
    private void addListeners() {
        usersTableForm.btnSearchAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchUsers();
            }
            private void searchUsers(){
                try {
                    if (usersTableForm.getTxtSearch().getText().isBlank()) {
                        JOptionPane.showMessageDialog(usersTableForm, "Please enter user's last name for search");
                        return;
                    }
                    User user = new User();
                    user.setSearchCondition("last_name");
                    user.setSearchConditionValue(usersTableForm.getTxtSearch().getText());
                    List<User> users = ClientController.getInstance().searchUsers(user);
                    usersTableForm.getTblUsers().setModel(new UsersTableModel(users));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    JOptionPane.showMessageDialog(usersTableForm, "System couldn't find users with entered last name \n" + e.getMessage());
                }
            }
        });
        usersTableForm.getTxtSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersTableForm.getBtnSearch().doClick();
            }
        });
        
        usersTableForm.btnShowAllAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllUsers();
            }

            private void showAllUsers() {
                fillTable();
                usersTableForm.getTxtSearch().setText("");
            }
        });
        
        usersTableForm.btnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = usersTableForm.getTblUsers().getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(usersTableForm, "Please select user");
                    return;
                }
                User user = ((UsersTableModel)usersTableForm.getTblUsers().getModel()).getUserAt(row);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.USER_DETAILS, user);
                Coordinator.getInstance().openAddUserForm(AddFormMode.DETAILS_FORM);
            }
        });
        
        usersTableForm.btnSelectAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowId = usersTableForm.getTblUsers().getSelectedRow();
                if (rowId < 0) {
                    JOptionPane.showMessageDialog(usersTableForm, "Please select salesperson!");
                    return;
                }
                User user = ((UsersTableModel) usersTableForm.getTblUsers().getModel()).getUserAt(rowId);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_USER, user);
                usersTableForm.dispose();
            }
        });
        
        usersTableForm.btnAddNewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openAddUserForm(AddFormMode.ADD_FORM);
            }
        });
        
        usersTableForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                if (!"".equals(usersTableForm.getTxtSearch().getText())) {
                    usersTableForm.getBtnSearch().doClick();
                }
                else{
                    fillTable();
                }
            }
            
        });
    }
    
    private void fillTable() {
        try {
            List<User> users = ClientController.getInstance().getAllUsers();
            usersTableForm.getTblUsers().setModel(new UsersTableModel(users));
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    private void prepareForm(TableFormMode formMode) {
        switch (formMode) {
            case SEE_ALL_ITEMS:
                usersTableForm.getBtnSelect().setVisible(false);
                usersTableForm.getBtnAddNew().setVisible(true);
                usersTableForm.getBtnDetails().setVisible(true);
                break;
            case SELECT_ITEM:
                usersTableForm.getBtnSelect().setVisible(true);
                usersTableForm.getBtnAddNew().setVisible(false);
                usersTableForm.getBtnDetails().setVisible(false);
                break;
            default:
                throw new AssertionError();
        }
    }
}

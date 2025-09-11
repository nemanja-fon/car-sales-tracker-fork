/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_controllers;

import carsalesclient.controller.ClientController;
import carsalesclient.form.CustomersTableForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_coordinator.Coordinator;
import carsalesclient.form.modes.AddFormMode;
import carsalesclient.form.modes.TableFormMode;
import carsalesclient.form.tableModels.CustomersTableModel;
import domain.Company;
import domain.Customer;
import domain.Individual;
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
public class SeeAllCustomersController {
    private final CustomersTableForm customersTableForm;
    private String customerType;

    public SeeAllCustomersController(CustomersTableForm customersTableForm) {
        this.customersTableForm = customersTableForm;
        addListeners();
    }
    
    public void openForm(TableFormMode formMode){
        prepareForm(formMode);
        customersTableForm.setVisible(true);
    }

    private void addListeners() {
        customersTableForm.cbCustomerTypeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseCustomerType();
            }

            private void chooseCustomerType() {
                customerType = customersTableForm.getCbCustomerType().getSelectedItem().toString();
                switch (customerType) {
                    case "Individual":
                        fillTable(new Individual());
                        customersTableForm.getLblSearchBy().setText("Search by Last Name");
                        break;
                    case "Company":
                        fillTable(new Company());
                        customersTableForm.getLblSearchBy().setText("Search by Company Name");
                        break;
                    default:
                        throw new AssertionError();
                }
                customersTableForm.getPanelCustomers().setVisible(true);
                customersTableForm.getPanelButtons().setVisible(true);
            }
        });
        
        customersTableForm.btnSearchAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCustomers();
            }

            private void searchCustomers() {
                try {
                    String conditionValue = customersTableForm.getTxtSearch().getText();
                    if ("".equals(conditionValue)) {
                        JOptionPane.showMessageDialog(customersTableForm, "Please enter text to search");
                        return;
                    }
                    Customer customer = null;
                    switch (customerType) {
                        case "Individual":
                            customer = new Individual();
                            customer.setSearchCondition("last_name");
                            break;
                        case "Company":
                            customer = new Company();
                            customer.setSearchCondition("company_name");
                            break;
                        default:
                            throw new AssertionError();
                    }
                    customer.setSearchConditionValue(conditionValue);
                    List<Customer> customers = ClientController.getInstance().searchCustomers(customer);
                    customersTableForm.getTblCustomers().setModel(new CustomersTableModel(customers));
                } catch (Exception e) {
                    System.out.println("Error: "+e.getMessage());
                }
            }
        });
        customersTableForm.getTxtSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customersTableForm.getBtnSearch().doClick();
            }
        });
        
        customersTableForm.btnShowAllAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllCustomers();
            }

            private void showAllCustomers() {
                switch (customerType) {
                    case "Individual":
                        fillTable(new Individual());
                        break;
                    case "Company":
                        fillTable(new Company());
                        break;
                    default:
                        throw new AssertionError();
                }
                customersTableForm.getTxtSearch().setText("");
            }
        });
        
        customersTableForm.btnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectCustomer();
            }

            private void selectCustomer() {
                int rowId = customersTableForm.getTblCustomers().getSelectedRow();
                if (rowId < 0) {
                    JOptionPane.showMessageDialog(customersTableForm, "The system cannot find the customer");
                    return;
                }
                Customer customer = ((CustomersTableModel) customersTableForm.getTblCustomers().getModel()).getCustomerAt(rowId);
                if (customer != null) {
                    JOptionPane.showMessageDialog(customersTableForm, "The system has found the customer");
                }
                else{
                    JOptionPane.showMessageDialog(customersTableForm, "The system cannot find the customer");
                }
                Coordinator.getInstance().addParam(CoordinatorParamConsts.CUSTOMER_DETAILS, customer);
                Coordinator.getInstance().openAddCustomerForm(AddFormMode.DETAILS_FORM);
            }
        });
        
        customersTableForm.btnAddNewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openAddCustomerForm(AddFormMode.ADD_FORM);
            }
        });
        
        customersTableForm.btnSelectAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowId = customersTableForm.getTblCustomers().getSelectedRow();
                if (rowId < 0) {
                    JOptionPane.showMessageDialog(customersTableForm, "Please select customer!");
                    return;
                }
                Customer customer = ((CustomersTableModel) customersTableForm.getTblCustomers().getModel()).getCustomerAt(rowId);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_CUSTOMER, customer);
                customersTableForm.dispose();
            }
        });
        
        customersTableForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                if (!"".equals(customersTableForm.getTxtSearch().getText())) {
                    customersTableForm.getBtnSearch().doClick();
                }
                else{
                    if (customerType != null) {
                        switch (customerType) {
                            case "Individual":
                                fillTable(new Individual());
                                break;
                            case "Company":
                                fillTable(new Company());
                                break;
                            default:
                                throw new AssertionError();
                        }
                    }
                }
            }
        });
    }

    private void fillTable(Customer customer) {
        try {
            List<Customer> customers = ClientController.getInstance().getAllCustomers(customer);
            customersTableForm.getTblCustomers().setModel(new CustomersTableModel(customers));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void prepareForm(TableFormMode formMode) {
        switch (formMode) {
            case TableFormMode.SEE_ALL_ITEMS:
                customersTableForm.getPanelCustomers().setVisible(false);
                customersTableForm.getPanelButtons().setVisible(false);
                customersTableForm.getBtnSelect().setVisible(false);
                customersTableForm.getBtnDetails().setVisible(true);
                break;
            case TableFormMode.SELECT_ITEM:
                customersTableForm.getPanelCustomers().setVisible(false);
                customersTableForm.getPanelButtons().setVisible(false);
                customersTableForm.getBtnSelect().setVisible(true);
                customersTableForm.getBtnDetails().setVisible(false);
                break;
            default:
                break;
        }
    }
}

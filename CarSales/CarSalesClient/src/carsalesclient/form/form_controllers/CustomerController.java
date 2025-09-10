/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_controllers;

import carsalesclient.controller.ClientController;
import carsalesclient.form.AddCustomerForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_coordinator.Coordinator;
import carsalesclient.form.modes.AddFormMode;
import domain.Company;
import domain.Customer;
import domain.Individual;
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
public class CustomerController {
    private final AddCustomerForm customerForm;
    private String customerType;

    public CustomerController(AddCustomerForm customerForm) {
        this.customerForm = customerForm;
        addListeners();
    }
    
    public void openForm(AddFormMode formMode){
        prepareForm(formMode);
        customerForm.setVisible(true);
    }

    private void addListeners() {
        customerForm.btnEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareForm(AddFormMode.UPDATE_FORM);
            }
        });
        
        customerForm.cbCustomerTypeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseCustomerType();
            }

            private void chooseCustomerType() {
                customerType = customerForm.getCbCustomerType().getSelectedItem().toString();
                switch (customerType) {
                    case "Individual":
                        customerForm.getPanelCompany().setVisible(false);
                        customerForm.getPanelIndividual().setVisible(true);
                        break;
                    case "Company":
                        customerForm.getPanelCompany().setVisible(true);
                        customerForm.getPanelIndividual().setVisible(false);
                        break;
                    default:
                        throw new AssertionError();
                }
                customerForm.getPanelButtons().setVisible(true);
            }
        });
        
        customerForm.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }

            private void update() {
                try {
                    if(!emptyFields()){
                        Customer c = (Customer) Coordinator.getInstance().getParam(CoordinatorParamConsts.CUSTOMER_DETAILS);
                        Customer customer = null;
                        if (c instanceof Individual) {
                            customer = new Individual(null, customerForm.getTxtFirstName().getText(), customerForm.getTxtLastName().getText(), customerForm.getTxtJmbg().getText(), customerForm.getTxtIdCardNum().getText(), customerForm.getTxtPhoneI().getText(), customerForm.getTxtEmailI().getText(), customerForm.getTxtAddressI().getText());
                            }
                        else{
                            customer = new Company(null, customerForm.getTxtCompanyName().getText(), customerForm.getTxtTaxNum().getText(), customerForm.getTxtRegNum().getText(), customerForm.getTxtAuthPerson().getText(), customerForm.getTxtPhoneC().getText(), customerForm.getTxtEmailC().getText(), customerForm.getTxtAddressC().getText());
                            }
                        if(JOptionPane.showConfirmDialog(customerForm, "Are you sure you want to UPDATE this customer? \nPlease check all the data before clicking yes!", "Update customer", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            customer.setIdCustomer(((Customer) Coordinator.getInstance().getParam(CoordinatorParamConsts.CUSTOMER_DETAILS)).getIdCustomer());
                            ClientController.getInstance().updateCustomer(customer);
                            JOptionPane.showMessageDialog(customerForm, (customer instanceof Individual ? ((Individual) customer).getFirstName() +" "+ ((Individual) customer).getLastName() : ((Company) customer).getCompanyName())+" has been successfully updated!");
                            customerForm.dispose();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(customerForm, "Fill all required fields");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
                
            }
        });
        
        customerForm.btnSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }

            private void save() {
                try {
                    if(!emptyFields()){
                        Customer customer = null;
                        switch (customerType) {
                            case "Individual":
                                customer = new Individual(null, customerForm.getTxtFirstName().getText(), customerForm.getTxtLastName().getText(), customerForm.getTxtJmbg().getText(), customerForm.getTxtIdCardNum().getText(), customerForm.getTxtPhoneI().getText(), customerForm.getTxtEmailI().getText(), customerForm.getTxtAddressI().getText());
                                break;
                            case "Company":
                                customer = new Company(null, customerForm.getTxtCompanyName().getText(), customerForm.getTxtTaxNum().getText(), customerForm.getTxtRegNum().getText(), customerForm.getTxtAuthPerson().getText(), customerForm.getTxtPhoneC().getText(), customerForm.getTxtEmailC().getText(), customerForm.getTxtAddressC().getText());
                                break;
                            default:
                                throw new AssertionError();
                        }
                        if(JOptionPane.showConfirmDialog(customerForm, "Are you sure you want to INSERT the following customer into the database: \n"+ (customerType.equals("Individual") ? ((Individual) customer).getFirstName() +" "+ ((Individual) customer).getLastName() : ((Company) customer).getCompanyName()) +", "+customer.getPhone()+", "+customer.getEmail(), "Add customer", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            ClientController.getInstance().insertCustomer(customer);
                            if(JOptionPane.showConfirmDialog(customerForm, (customerType.equals("Individual") ? ((Individual) customer).getFirstName() +" "+ ((Individual) customer).getLastName() : ((Company) customer).getCompanyName())+" has been successfully added to the database!\n\nAdd more customers?", "Success", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                prepareForm(AddFormMode.ADD_FORM);
                            }
                            else{
                                customerForm.dispose();
                            }
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(customerForm, "Fill all required fields");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        
        customerForm.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }

            private void delete() {
                Customer customer = (Customer) Coordinator.getInstance().getParam(CoordinatorParamConsts.CUSTOMER_DETAILS);
                try {
                    ClientController.getInstance().deleteCustomer(customer);
                    JOptionPane.showMessageDialog(customerForm, "System successfully deleted customer");
                    customerForm.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(customerForm, "System couldn't delete selected customer");
                    e.printStackTrace();
                }
            }
        });
        
        customerForm.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerForm.dispose();
            }
        });
        
    }

    private void prepareForm(AddFormMode formMode) {
        Customer customer = (Customer) Coordinator.getInstance().getParam(CoordinatorParamConsts.CUSTOMER_DETAILS);
        switch(formMode){
            case AddFormMode.ADD_FORM:
                customerForm.getLblCustomerType().setVisible(true);
                customerForm.getCbCustomerType().setVisible(true);
                customerForm.getCbCustomerType().setEnabled(true);
                
                customerForm.getPanelId().setVisible(false);
                customerForm.getTxtId().setEnabled(false);
                
                customerForm.getPanelCompany().setVisible(false);
                customerForm.getTxtCompanyName().setText("");
                customerForm.getTxtCompanyName().setEnabled(true);
                customerForm.getTxtTaxNum().setText("");
                customerForm.getTxtTaxNum().setEnabled(true);
                customerForm.getTxtRegNum().setText("");
                customerForm.getTxtRegNum().setEnabled(true);
                customerForm.getTxtPhoneC().setText("");
                customerForm.getTxtPhoneC().setEnabled(true);
                customerForm.getTxtEmailC().setText("");
                customerForm.getTxtEmailC().setEnabled(true);
                customerForm.getTxtAddressC().setText("");
                customerForm.getTxtAddressC().setEnabled(true);
                customerForm.getTxtAuthPerson().setText("");
                customerForm.getTxtAuthPerson().setEnabled(true);
                
                customerForm.getPanelIndividual().setVisible(false);
                customerForm.getTxtFirstName().setText("");
                customerForm.getTxtFirstName().setEnabled(true);
                customerForm.getTxtLastName().setText("");
                customerForm.getTxtLastName().setEnabled(true);
                customerForm.getTxtPhoneI().setText("");
                customerForm.getTxtPhoneI().setEnabled(true);
                customerForm.getTxtEmailI().setText("");
                customerForm.getTxtEmailI().setEnabled(true);
                customerForm.getTxtAddressI().setText("");
                customerForm.getTxtAddressI().setEnabled(true);
                customerForm.getTxtJmbg().setText("");
                customerForm.getTxtJmbg().setEnabled(true);
                customerForm.getTxtIdCardNum().setText("");
                customerForm.getTxtIdCardNum().setEnabled(true);
                
                customerForm.getPanelButtons().setVisible(false);
                customerForm.getBtnEnableChanges().setVisible(false);
                customerForm.getBtnEdit().setVisible(false);
                customerForm.getBtnDelete().setVisible(false);
                customerForm.getBtnSave().setVisible(true);
                customerForm.getBtnSave().setEnabled(true);
                break;
                
            case AddFormMode.DETAILS_FORM:
                customerForm.getLblCustomerType().setVisible(false);
                customerForm.getCbCustomerType().setVisible(false);
                customerForm.getCbCustomerType().setEnabled(false);
                
                customerForm.getPanelId().setVisible(true);
                customerForm.getTxtId().setText(customer.getIdCustomer().toString());
                customerForm.getTxtId().setEnabled(false);
                
                if (customer instanceof Company) {
                    Company company = (Company) customer;
                    customerForm.getPanelCompany().setVisible(true);
                    customerForm.getTxtCompanyName().setText(company.getCompanyName());
                    customerForm.getTxtCompanyName().setEnabled(false);
                    customerForm.getTxtTaxNum().setText(company.getTaxNumber());
                    customerForm.getTxtTaxNum().setEnabled(false);
                    customerForm.getTxtRegNum().setText(company.getRegistrationNumber());
                    customerForm.getTxtRegNum().setEnabled(false);
                    customerForm.getTxtPhoneC().setText(company.getPhone());
                    customerForm.getTxtPhoneC().setEnabled(false);
                    customerForm.getTxtEmailC().setText(company.getEmail());
                    customerForm.getTxtEmailC().setEnabled(false);
                    customerForm.getTxtAddressC().setText(company.getAddress());
                    customerForm.getTxtAddressC().setEnabled(false);
                    customerForm.getTxtAuthPerson().setText(company.getAuthorizedPerson());
                    customerForm.getTxtAuthPerson().setEnabled(false);
                    
                    customerForm.getPanelIndividual().setVisible(false);
                }
                else{
                    Individual i = (Individual) customer;
                    customerForm.getPanelIndividual().setVisible(true);
                    customerForm.getTxtFirstName().setText(i.getFirstName());
                    customerForm.getTxtFirstName().setEnabled(false);
                    customerForm.getTxtLastName().setText(i.getLastName());
                    customerForm.getTxtLastName().setEnabled(false);
                    customerForm.getTxtPhoneI().setText(i.getPhone());
                    customerForm.getTxtPhoneI().setEnabled(false);
                    customerForm.getTxtEmailI().setText(i.getEmail());
                    customerForm.getTxtEmailI().setEnabled(false);
                    customerForm.getTxtAddressI().setText(i.getAddress());
                    customerForm.getTxtAddressI().setEnabled(false);
                    customerForm.getTxtJmbg().setText(i.getJmbg());
                    customerForm.getTxtJmbg().setEnabled(false);
                    customerForm.getTxtIdCardNum().setText(i.getIdCardNumber());
                    customerForm.getTxtIdCardNum().setEnabled(false);
                    
                    customerForm.getPanelCompany().setVisible(false);
                }
                
                customerForm.getPanelButtons().setVisible(true);
                customerForm.getBtnEnableChanges().setVisible(true);
                customerForm.getBtnEnableChanges().setEnabled(true);
                customerForm.getBtnDelete().setVisible(true);
                customerForm.getBtnDelete().setEnabled(true);
                customerForm.getBtnEdit().setVisible(true);
                customerForm.getBtnEdit().setEnabled(false);
                customerForm.getBtnSave().setVisible(false);
                break;
                
            case AddFormMode.UPDATE_FORM:
                customerForm.getPanelId().setVisible(true);
                customerForm.getTxtId().setEnabled(false);
                
                if (customer instanceof Company) {
                    customerForm.getLblId().setSize(135, 30);
                    customerForm.getPanelCompany().setVisible(true);
                    customerForm.getTxtCompanyName().setEnabled(true);
                    customerForm.getTxtTaxNum().setEnabled(true);
                    customerForm.getTxtRegNum().setEnabled(true);
                    customerForm.getTxtPhoneC().setEnabled(true);
                    customerForm.getTxtEmailC().setEnabled(true);
                    customerForm.getTxtAddressC().setEnabled(true);
                    customerForm.getTxtAuthPerson().setEnabled(true);
                    
                    customerForm.getPanelIndividual().setVisible(false);
                }
                else{
                    customerForm.getLblId().setSize(116, 30);
                    customerForm.getPanelIndividual().setVisible(true);
                    customerForm.getTxtFirstName().setEnabled(true);
                    customerForm.getTxtLastName().setEnabled(true);
                    customerForm.getTxtPhoneI().setEnabled(true);
                    customerForm.getTxtEmailI().setEnabled(true);
                    customerForm.getTxtAddressI().setEnabled(true);
                    customerForm.getTxtJmbg().setEnabled(true);
                    customerForm.getTxtIdCardNum().setEnabled(true);
                    
                    customerForm.getPanelCompany().setVisible(false);
                }
                
                customerForm.getPanelButtons().setVisible(true);
                customerForm.getBtnEnableChanges().setVisible(true);
                customerForm.getBtnEnableChanges().setEnabled(false);
                customerForm.getBtnDelete().setVisible(true);
                customerForm.getBtnDelete().setEnabled(false);
                customerForm.getBtnEdit().setEnabled(true);
                break;
                
            default:
                break;
        }
    }
    
    private boolean emptyFields(){
        Border border = new LineBorder(Color.red, 1,true);
        Font font = new Font("Gill Sans MT", 1, 8);
        boolean empty = false;
        
        Customer c = (Customer) Coordinator.getInstance().getParam(CoordinatorParamConsts.CUSTOMER_DETAILS);
        if (c instanceof Individual || "Individual".equals(customerType)) {
            if(customerForm.getTxtFirstName().getText().isBlank()){
                customerForm.getTxtFirstName().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtFirstName().setBorder(new MetalBorders.TextFieldBorder());

            }
            
            if(customerForm.getTxtLastName().getText().isBlank()){
                customerForm.getTxtLastName().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtLastName().setBorder(new MetalBorders.TextFieldBorder());
            }
            
            if(customerForm.getTxtPhoneI().getText().isBlank()){
                customerForm.getTxtPhoneI().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtPhoneI().setBorder(new MetalBorders.TextFieldBorder());
            }
            
            if(customerForm.getTxtEmailI().getText().isBlank()){
                customerForm.getTxtEmailI().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                if (customerForm.getTxtEmailI().getText().contains("@")) {
                    customerForm.getTxtEmailI().setBorder(new MetalBorders.TextFieldBorder());
                }
                else{
                    customerForm.getTxtEmailI().setBorder(new TitledBorder(border, "Invalid email", 0, 0, font, Color.RED));
                    empty = true;
                }
                
            }
            
            if(customerForm.getTxtAddressI().getText().isBlank()){
                customerForm.getTxtAddressI().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtAddressI().setBorder(new MetalBorders.TextFieldBorder());
            }
            
            if(customerForm.getTxtJmbg().getText().isBlank()){
                customerForm.getTxtJmbg().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtJmbg().setBorder(new MetalBorders.TextFieldBorder());
            }
            
            if(customerForm.getTxtIdCardNum().getText().isBlank()){
                customerForm.getTxtIdCardNum().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtIdCardNum().setBorder(new MetalBorders.TextFieldBorder());
            }
        }
        else if (c instanceof Company || "Company".equals(customerType)) {
            if(customerForm.getTxtCompanyName().getText().isBlank()){
                customerForm.getTxtCompanyName().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtCompanyName().setBorder(new MetalBorders.TextFieldBorder());

            }
            
            if(customerForm.getTxtTaxNum().getText().isBlank()){
                customerForm.getTxtTaxNum().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtTaxNum().setBorder(new MetalBorders.TextFieldBorder());
            }
            
            if(customerForm.getTxtPhoneC().getText().isBlank()){
                customerForm.getTxtPhoneC().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtPhoneC().setBorder(new MetalBorders.TextFieldBorder());
            }
            
            if(customerForm.getTxtEmailC().getText().isBlank()){
                customerForm.getTxtEmailC().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                if (customerForm.getTxtEmailC().getText().contains("@")) {
                    customerForm.getTxtEmailC().setBorder(new MetalBorders.TextFieldBorder());
                }
                else{
                    customerForm.getTxtEmailC().setBorder(new TitledBorder(border, "Invalid email", 0, 0, font, Color.RED));
                    empty = true;
                }
            }
            
            if(customerForm.getTxtAddressC().getText().isBlank()){
                customerForm.getTxtAddressC().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtAddressC().setBorder(new MetalBorders.TextFieldBorder());
            }
            
            if(customerForm.getTxtRegNum().getText().isBlank()){
                customerForm.getTxtRegNum().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtRegNum().setBorder(new MetalBorders.TextFieldBorder());
            }
            
            if(customerForm.getTxtAuthPerson().getText().isBlank()){
                customerForm.getTxtAuthPerson().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
                empty = true;
            }
            else{
                customerForm.getTxtAuthPerson().setBorder(new MetalBorders.TextFieldBorder());
            }
        }
        
        
        return empty;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_controllers;

import carsalesclient.controller.ClientController;
import carsalesclient.form.AddInvoiceForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_coordinator.Coordinator;
import carsalesclient.form.modes.AddFormMode;
import carsalesclient.form.modes.TableFormMode;
import carsalesclient.form.tableModels.InvoiceItemsTableModel;
import carsalesclient.form.tableModels.UsersTableModel;
import domain.Company;
import domain.Customer;
import domain.Individual;
import domain.Invoice;
import domain.InvoiceItem;
import domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class InvoiceController {
    private final AddInvoiceForm invoiceForm;
    private Customer customer;
    private List<InvoiceItem> editedItems;

    public InvoiceController(AddInvoiceForm invoiceForm) {
        this.invoiceForm = invoiceForm;
        addListeners();
    }
    
    public void openForm(AddFormMode mode){
        prepareForm(mode);
        invoiceForm.setVisible(true);
    }

    private void addListeners() {
        invoiceForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillItemsTable();
                fillSelectedCustomer();
                fillSelectedSalesperson();
            }

            private void fillItemsTable() {
                List<InvoiceItem> itemsParam = (List<InvoiceItem>) Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_ITEMS);
                List<InvoiceItem> items = (itemsParam != null) ? new ArrayList<>(itemsParam) : new ArrayList<>();
                    
                if (editedItems == null){
                    if (!items.isEmpty()) {
                        int i = 1;
                        for (InvoiceItem item : items) {
                            item.setNum(i++);
                        }
                        invoiceForm.getTblInvoiceItems().setModel(new InvoiceItemsTableModel(items));
                        fillTotalAmount();
                    }
                }
                else{
                    if (!items.isEmpty()) {
                        for (InvoiceItem item : editedItems) {
                            Long invoiceId = ((Invoice) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_DETAILS)).getIdInvoice();
                            if (!invoiceId.equals(item.getInvoiceId())) {
                                item.setInvoiceId(invoiceId);
                            }
                            if (item.getNum() == 0) {
                                int num = editedItems.stream()
                                    .mapToInt(InvoiceItem::getNum)
                                    .max()
                                    .orElse(0);
                                item.setNum(num + 1);
                            }
                        }
                    }
                    InvoiceItem i = (InvoiceItem) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS);
                    
                    if (i != null) {
                        for (int idx = 0; idx < editedItems.size(); idx++) {
                            if (i.equals(editedItems.get(idx))) {
                                editedItems.set(idx, i);
                                break;
                            }
                        }
                    }
                    invoiceForm.getTblInvoiceItems().setModel(new InvoiceItemsTableModel(editedItems));
                    fillTotalAmount();
                    for (InvoiceItem editedItem : editedItems) {
                        if (editedItem.getStatus() == null) {
                            System.out.println(editedItem.getNum() + " " + editedItem.getCar());
                        }
                        else{
                            System.out.println(editedItem.getNum() + " " + editedItem.getCar() + " " + editedItem.getStatus());
                        }
                    }
                    System.out.println("\n\n\n\n");
                }
            }

            private void fillSelectedCustomer() {
                if ((Customer)Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_CUSTOMER) == null && (Invoice) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_DETAILS) == null){
                    invoiceForm.getTxtSelectedCustomer().setText("");
                }
                else{
                    if ((Customer)Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_CUSTOMER) != null) {
                        customer = (Customer)Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_CUSTOMER);
                    }
                    else {
                        Invoice i = (Invoice) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_DETAILS);
                        customer = i.getCustomer();
                    }
                    
                    if (customer instanceof Individual) {
                        Individual i = (Individual) customer;
                        invoiceForm.getTxtSelectedCustomer().setText(i.getFirstName() + " " + i.getLastName());
                    }
                    else{
                        invoiceForm.getTxtSelectedCustomer().setText(((Company) customer).getCompanyName());
                    }    
                }
            }

            private void fillSelectedSalesperson() {
                User user = (User) Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_USER);
                if (user != null) {
                    List<User> users = new ArrayList<>(){{
                        add(user);
                    }};
                    invoiceForm.getTblSalesman().setModel(new UsersTableModel(users));
                }
            }
        });
        
        invoiceForm.btnSelectCustomerAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openCustomersTableForm(TableFormMode.SELECT_ITEM);
            }
        });
        
        invoiceForm.btnSelectSalespersonAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openUsersTableForm(TableFormMode.SELECT_ITEM);
            }
        });
        
        invoiceForm.btnAddItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openCarsTableForm(TableFormMode.SELECT_ITEM);
            }
        });
        
        invoiceForm.btnRemoveItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeInvoiceItem();
            }
            
            private void removeInvoiceItem(){
                int rowId = invoiceForm.getTblInvoiceItems().getSelectedRow();
                if(rowId < 0){
                    JOptionPane.showMessageDialog(invoiceForm, "Please select item to remove from invoice");
                    return;
                }
                InvoiceItemsTableModel model = (InvoiceItemsTableModel) invoiceForm.getTblInvoiceItems().getModel();
                
                if (editedItems == null) {
                    model.removeInvoiceItem(rowId);
                }
                else{
                    model.deleteInvoiceItem(rowId);
                }
                System.out.println(editedItems);
                fillTotalAmount();
            }
        });
        
        invoiceForm.btnEditItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowId = invoiceForm.getTblInvoiceItems().getSelectedRow();
                if (rowId < 0) {
                    JOptionPane.showMessageDialog(invoiceForm, "Please select item!");
                    return;
                }
                InvoiceItem item = ((InvoiceItemsTableModel) invoiceForm.getTblInvoiceItems().getModel()).getInvoiceItemAt(rowId);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS, item);
                
                Coordinator.getInstance().openCarsTableForm(TableFormMode.EDIT_INVOICE_ITEM);
            }
        });
        
        invoiceForm.btnSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveInvoice();
            }
            
            private void saveInvoice(){
                try {
                    if(invoiceForm.getTxtSelectedCustomer().getText().isEmpty()){
                        JOptionPane.showMessageDialog(invoiceForm, "Please select customer");
                        return;
                    }
                    if(invoiceForm.getTblInvoiceItems().getRowCount() == 0){
                        JOptionPane.showMessageDialog(invoiceForm, "Please select car(s)");
                        return;
                    }
                    SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
                    Date d = formater.parse(invoiceForm.getTxtDate().getText());
                    List<InvoiceItem> items = ((InvoiceItemsTableModel) invoiceForm.getTblInvoiceItems().getModel()).getInvoiceItems();
                    Invoice invoice = new Invoice(null, Long.valueOf(invoiceForm.getTxtInvoiceNumber().getText()), d, Double.valueOf(invoiceForm.getTxtTotalAmount().getText()),((User) Coordinator.getInstance().getParam(CoordinatorParamConsts.LOGGED_IN_USER)), customer, items);
                    
                    if(JOptionPane.showConfirmDialog(invoiceForm, "Are you sure you want to CREATE and INSERT this invoice into the database? \n Please check all the data before clicking Yes!", "Create invoice", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        ClientController controller = ClientController.getInstance();
                        controller.insertInvoice(invoice);
                        
                        Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_ITEMS, null);
                        Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_CUSTOMER, null);
                        
                        if(JOptionPane.showConfirmDialog(invoiceForm, "Invoice has been successfully added to the database!!! \n\n Create more invoices?", "Success", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION){
                            prepareForm(AddFormMode.ADD_FORM);
                            customer = null;
                            invoiceForm.getTxtSelectedCustomer().setText("");
                            invoiceForm.getTxtTotalAmount().setText("");
                        }
                        else{
                            invoiceForm.dispose();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error: "+e.getMessage());
                }
                
            }
        });
        
        invoiceForm.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Invoice invoice = (Invoice) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_DETAILS);
                try {
                    ClientController.getInstance().deleteInvoice(invoice);
                    JOptionPane.showMessageDialog(invoiceForm, "System successfully deleted invoice");
                    invoiceForm.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(invoiceForm, "System couldn't delete selected invoice");
                    ex.printStackTrace();
                }
            }
        });
        
        invoiceForm.btnEnableChangesCustomerAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareForm(AddFormMode.UPDATE_FORM);
            }
        });
        
        invoiceForm.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInvoice();
            }

            private void updateInvoice() {
                try {
                    if(invoiceForm.getTxtSelectedCustomer().getText().isEmpty()){
                        JOptionPane.showMessageDialog(invoiceForm, "Please select customer");
                        return;
                    }
                    if(invoiceForm.getTblInvoiceItems().getRowCount() == 0){
                        JOptionPane.showMessageDialog(invoiceForm, "Please select car(s)");
                        return;
                    }
                    SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
                    formater.setLenient(false);
                    Date d;
                    try {
                        d = formater.parse(invoiceForm.getTxtDate().getText());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(invoiceForm, "Neispravan datum");
                        return;
                    }
                    Invoice i =(Invoice) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_DETAILS);
                    User user = ((UsersTableModel) invoiceForm.getTblSalesman().getModel()).getUserAt(0);
                    Invoice invoice = new Invoice(i.getIdInvoice(), Long.valueOf(invoiceForm.getTxtInvoiceNumber().getText()), d, Double.valueOf(invoiceForm.getTxtTotalAmount().getText()), user, customer, editedItems);
                    
                    if(JOptionPane.showConfirmDialog(invoiceForm, "Are you sure you want to UPDATE this invoice into the database? \n Please check all the data before clicking Yes!", "Create invoice", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        ClientController controller = ClientController.getInstance();
                        controller.updateInvoice(invoice);
                        
                        Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_ITEMS, null);
                        Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_CUSTOMER, null);
                        Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_USER, null);
                        Coordinator.getInstance().addParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS, null);
                        
                        JOptionPane.showMessageDialog(invoiceForm, "Invoice has been successfully updated!!!");
                        invoiceForm.dispose();
                    }
                } catch (Exception e) {
                    System.out.println("Error: "+e.getMessage());
                }
            }
        });
        
        invoiceForm.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_ITEMS, null);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_USER, null);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_CUSTOMER, null);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS, null);
                invoiceForm.dispose();
            }
        });
    }
    
    public void fillTotalAmount() {
        Double total = 0.0;
        InvoiceItemsTableModel tm = (InvoiceItemsTableModel) invoiceForm.getTblInvoiceItems().getModel();
        for (InvoiceItem item : tm.getInvoiceItems()) {
            if (item.getStatus() == null || item.getStatus() != InvoiceItem.Status.DELETED) {
                total += item.getPrice();
            }
        }
        invoiceForm.getTxtTotalAmount().setText(Double.toString(total));
    }

    private void prepareForm(AddFormMode mode){
        switch (mode) {
            case ADD_FORM:
                try {
                    invoiceForm.getTxtDate().setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
                    invoiceForm.getTxtDate().setEnabled(false);
                    List<Invoice> invoices = ClientController.getInstance().getAllInvoices();
                    Long num = invoices.stream()
                     .map(Invoice::getInvoiceNum)
                     .max(Long::compare)                       
                     .orElse(null);
                    invoiceForm.getTxtInvoiceNumber().setText(Long.toString(num+1));

                    List<User> salesman = new ArrayList<>(){{
                        add((User) Coordinator.getInstance().getParam(CoordinatorParamConsts.LOGGED_IN_USER));
                    }};
                    invoiceForm.getTblSalesman().setModel(new UsersTableModel(salesman));

                    invoiceForm.getTblInvoiceItems().setModel(new InvoiceItemsTableModel(new ArrayList<>()));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
                invoiceForm.getTxtTotalAmount().setEnabled(false);
                
                invoiceForm.getBtnSelectCustomer().setEnabled(true);
                invoiceForm.getBtnSelectSalesperson().setVisible(false);
                
                invoiceForm.getBtnAddItem().setEnabled(true);
                invoiceForm.getBtnRemoveItem().setEnabled(true);
                invoiceForm.getBtnEditItem().setVisible(false);
               
                invoiceForm.getBtnEnableChanges().setVisible(false);
                invoiceForm.getBtnEdit().setVisible(false);
                invoiceForm.getBtnDelete().setVisible(false);
                invoiceForm.getBtnSave().setVisible(true);
                break;
                
            case DETAILS_FORM:
                Invoice invoice = (Invoice) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_DETAILS);
                invoiceForm.getTxtInvoiceNumber().setText(invoice.getInvoiceNum().toString());
                invoiceForm.getTxtDate().setText(new SimpleDateFormat("dd.MM.yyyy").format(invoice.getDateOfIssue()));
                invoiceForm.getTxtDate().setEnabled(false);
                List<User> salesman = new ArrayList<>(){{
                    add(invoice.getUser());
                }};
                invoiceForm.getTblSalesman().setModel(new UsersTableModel(salesman));
                
                invoiceForm.getTblInvoiceItems().setModel(new InvoiceItemsTableModel(invoice.getInvoiceItems()));
                invoiceForm.getTxtTotalAmount().setText(invoice.getTotalAmount().toString());
                invoiceForm.getTxtTotalAmount().setEnabled(false);
                
                invoiceForm.getBtnSelectCustomer().setEnabled(false);
                invoiceForm.getBtnSelectSalesperson().setVisible(true);
                invoiceForm.getBtnSelectSalesperson().setEnabled(false);
                
                invoiceForm.getBtnAddItem().setEnabled(false);
                invoiceForm.getBtnRemoveItem().setEnabled(false);
                invoiceForm.getBtnEditItem().setVisible(true);
                invoiceForm.getBtnEditItem().setEnabled(false);
               
                invoiceForm.getBtnEnableChanges().setVisible(true);
                invoiceForm.getBtnEnableChanges().setEnabled(true);
                invoiceForm.getBtnEdit().setVisible(true);
                invoiceForm.getBtnEdit().setEnabled(false);
                invoiceForm.getBtnDelete().setVisible(true);
                invoiceForm.getBtnDelete().setEnabled(true);
                invoiceForm.getBtnSave().setVisible(false);
                break;
                
            case UPDATE_FORM:
                editedItems = ((Invoice) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_DETAILS)).getInvoiceItems();
                Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_ITEMS, editedItems);
                
                invoiceForm.getTxtDate().setEnabled(true);
                invoiceForm.getTxtTotalAmount().setEnabled(true);
                
                invoiceForm.getBtnSelectCustomer().setEnabled(true);
                invoiceForm.getBtnSelectSalesperson().setVisible(true);
                invoiceForm.getBtnSelectSalesperson().setEnabled(true);
                
                invoiceForm.getBtnAddItem().setEnabled(true);
                invoiceForm.getBtnRemoveItem().setEnabled(true);
                invoiceForm.getBtnEditItem().setVisible(true);
                invoiceForm.getBtnEditItem().setEnabled(true);
               
                invoiceForm.getBtnEnableChanges().setVisible(true);
                invoiceForm.getBtnEnableChanges().setEnabled(false);
                invoiceForm.getBtnEdit().setVisible(true);
                invoiceForm.getBtnEdit().setEnabled(true);
                invoiceForm.getBtnDelete().setVisible(true);
                invoiceForm.getBtnDelete().setEnabled(false);
                invoiceForm.getBtnSave().setVisible(false);
                break;
            default:
                throw new AssertionError();
        }
    }
}

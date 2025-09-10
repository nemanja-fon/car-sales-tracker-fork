/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_controllers;

import carsalesclient.controller.ClientController;
import carsalesclient.form.CarsTableForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_coordinator.Coordinator;
import carsalesclient.form.modes.AddFormMode;
import carsalesclient.form.modes.TableFormMode;
import carsalesclient.form.tableModels.CarsTableModel;
import domain.Car;
import domain.CarStatus;
import domain.Company;
import domain.Customer;
import domain.Individual;
import domain.InvoiceItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author user
 */
public class SeeAllCarsController {
    private final CarsTableForm carsTableForm;
    private Customer customer;
    private List<InvoiceItem> selectedCars = new ArrayList<>();

    public SeeAllCarsController(CarsTableForm carsTableForm) {
        this.carsTableForm = carsTableForm;
        addListeners();
    }
    
    public void openForm(TableFormMode formMode){
        prepareForm(formMode);
        carsTableForm.setVisible(true);
    }
    
    private void addListeners(){
        carsTableForm.cbBrandAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectBrand();
            }
            private void selectBrand(){
                try {
                    String conditionValue = carsTableForm.getCbBrand().getSelectedItem().toString();
                    if(conditionValue.equals("All brands")){
                        fillTable(null);
                    }
                    else{
                        Car car = new Car();
                        car.setSearchCondition("brand");
                        car.setSearchConditionValue(conditionValue);
                        List<Car> cars = ClientController.getInstance().searchCars(car);
                        fillTable(cars);
                    }
                } catch (Exception e) {
                    System.out.println("Error: "+e.getMessage());
                }
            }
        });
        
        
        carsTableForm.btnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowId = carsTableForm.getTblCars().getSelectedRow();
                if (rowId < 0) {
                    JOptionPane.showMessageDialog(carsTableForm, "Please select car!");
                    return;
                }
                Car car = ((CarsTableModel) carsTableForm.getTblCars().getModel()).getCarAt(rowId);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.CAR_DETAILS, car);
                Coordinator.getInstance().openAddCarForm(AddFormMode.DETAILS_FORM);
            }
        });
        
        carsTableForm.btnAddNewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openAddCarForm(AddFormMode.ADD_FORM);
            }
        });
        
        carsTableForm.getTblCars().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    if (Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS) == null) {
                        int rowId = carsTableForm.getTblCars().getSelectedRow();
                        if(rowId != -1){
                            Car car = ((CarsTableModel) carsTableForm.getTblCars().getModel()).getCarAt(rowId);
                            carsTableForm.getTxtNote().setEnabled(true);
                        }
                        else{
                            carsTableForm.getTxtNote().setEnabled(false);
                            carsTableForm.getTxtNote().setText("");
                        }
                    }
                    else{
                        int rowId = carsTableForm.getTblCars().getSelectedRow();
                        if(rowId != -1){
                            Car car = ((CarsTableModel) carsTableForm.getTblCars().getModel()).getCarAt(rowId);
                            carsTableForm.getTxtPrice().setText(Double.toString(car.getPrice()));
                        }
                    }
                }
            }
        });
        
        carsTableForm.btnSelectAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectCar();
            }

            private void selectCar() {
                int rowId = carsTableForm.getTblCars().getSelectedRow();
                if (rowId < 0) {
                    JOptionPane.showMessageDialog(carsTableForm, "Please select car!");
                    return;
                }
                Car car = ((CarsTableModel) carsTableForm.getTblCars().getModel()).getCarAt(rowId);
                if (!selectedCars.isEmpty()) {
                    for (InvoiceItem selectedCar : selectedCars) {
                        if (selectedCar.getCar().equals(car)) {
                            JOptionPane.showMessageDialog(carsTableForm, "Selected car is already selected");
                            return;
                        }
                    }
                }
                if (car.getStatus() != CarStatus.AVAILABLE) {
                    JOptionPane.showMessageDialog(carsTableForm, "Selected car is not available for sale!");
                    return;
                }
                String note = carsTableForm.getTxtNote().getText();
                List<InvoiceItem> allSelectedItems = (List<InvoiceItem>) Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_ITEMS);
                if (allSelectedItems != null) {
                    for (InvoiceItem item : allSelectedItems) {
                        if (item.getCar().equals(car)) {
                            JOptionPane.showMessageDialog(carsTableForm, "Selected car is already selected");
                            return;
                        }
                    }
                }
                InvoiceItem item = new InvoiceItem(null, 0, car.getPrice(), note, car);
                item.setStatus(InvoiceItem.Status.ADDED);
                selectedCars.add(item);
                if (JOptionPane.showConfirmDialog(carsTableForm, "Selected Cars:\n"+selectedCars.toString() + "\n Select more cars?", "Select more cars?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    return;
                }
                if (allSelectedItems != null) {
                    allSelectedItems.addAll(selectedCars);
                    Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_ITEMS, allSelectedItems);
                }
                else{
                    Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_ITEMS, selectedCars);
                }
                carsTableForm.dispose();
            }
        });
        
        carsTableForm.btnEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carsTableForm.getBtnEnableChanges().setEnabled(false);
                carsTableForm.getTxtNote().setEnabled(true);
                carsTableForm.getTxtPrice().setEnabled(true);
                carsTableForm.getBtnEdit().setEnabled(true);
                carsTableForm.getCbBrand().setEnabled(true);
                fillTable(null);
                InvoiceItem invoiceItem = (InvoiceItem) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS);
                CarsTableModel model = (CarsTableModel) carsTableForm.getTblCars().getModel();
                int index = 0;
                for (int row = 0; row < carsTableForm.getTblCars().getRowCount(); row++) {
                    if (model.getCarAt(row).equals(invoiceItem.getCar())) {
                        index = row;
                    }
                }
                carsTableForm.getTblCars().setRowSelectionInterval(index, index);
            }
        });
        
        carsTableForm.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editItem();
            }

            private void editItem() {
                int rowId = carsTableForm.getTblCars().getSelectedRow();
                if (rowId < 0) {
                    JOptionPane.showMessageDialog(carsTableForm, "Please select car!");
                    return;
                }
                Car car = ((CarsTableModel) carsTableForm.getTblCars().getModel()).getCarAt(rowId);
                InvoiceItem invoiceItem = (InvoiceItem) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS);
                if (car.getStatus() != CarStatus.AVAILABLE && !car.equals(invoiceItem.getCar())) {
                    JOptionPane.showMessageDialog(carsTableForm, "Selected car is not available for sale!");
                    return;
                }
                List<InvoiceItem> items = (List<InvoiceItem>) Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_ITEMS);
                for (InvoiceItem item : items) {
                    if (item.getCar().equals(car)) {
                        JOptionPane.showMessageDialog(carsTableForm, "Selected car is already selected!");
                        return;
                    }
                }
                if (carsTableForm.getTxtPrice().getText().isBlank()) {
                    JOptionPane.showMessageDialog(carsTableForm, "Please enter price");
                    return;
                }
                String note = carsTableForm.getTxtNote().getText();
                InvoiceItem i = (InvoiceItem) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS);
                Double price;
                try {
                    price = Double.valueOf(carsTableForm.getTxtPrice().getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(carsTableForm, "Invalid price input");
                    return;
                }
                InvoiceItem item = new InvoiceItem(i.getInvoiceId(), i.getNum(), price, note, car);
                item.setStatus(InvoiceItem.Status.UPDATED);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS, item);
                JOptionPane.showMessageDialog(carsTableForm, "InvoiceItem is successfully edited");
                carsTableForm.dispose();
            }
        });
        
        carsTableForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                if (Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS) == null) {
                    carsTableForm.getCbBrand().setSelectedIndex(carsTableForm.getCbBrand().getSelectedIndex());
                }
            }
        });
    }

    private void fillTable(List<Car> c) {
        try {
            if(c == null){
                List<Car> cars = ClientController.getInstance().getAllCars();
                carsTableForm.getTblCars().setModel(new CarsTableModel(cars));
            }
            else{
                carsTableForm.getTblCars().setModel(new CarsTableModel(c));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void prepareForm(TableFormMode formMode) {
        try {
            List<Car> cars = ClientController.getInstance().getAllCars();
            
            List<String> brands = new ArrayList<>();
            for (Car car : cars) {
                if (!brands.contains(car.getBrand())) {
                    brands.add(car.getBrand());
                }
            }
            DefaultComboBoxModel cbm = new DefaultComboBoxModel(brands.toArray());
            cbm.insertElementAt("All brands", 0);
            carsTableForm.getCbBrand().setModel(cbm);
            carsTableForm.getCbBrand().setSelectedIndex(0);
            
        } catch (Exception ex) {
            Logger.getLogger(SeeAllCarsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        switch (formMode) {
            case TableFormMode.SEE_ALL_ITEMS:
                carsTableForm.getBtnSelect().setVisible(false);
                carsTableForm.getBtnEdit().setVisible(false);
                carsTableForm.getBtnEnableChanges().setVisible(false);
                carsTableForm.getLblNote().setVisible(false);
                carsTableForm.getTxtNote().setVisible(false);
                carsTableForm.getLblPrice().setVisible(false);
                carsTableForm.getTxtPrice().setVisible(false);
                carsTableForm.getBtnDetails().setVisible(true);
                fillTable(null);
                break;
            case TableFormMode.SELECT_ITEM:
                selectedCars.clear();
                carsTableForm.getBtnSelect().setVisible(true);
                carsTableForm.getBtnEdit().setVisible(false);
                carsTableForm.getBtnEnableChanges().setVisible(false);
                carsTableForm.getLblNote().setVisible(true);
                carsTableForm.getTxtNote().setVisible(true);
                carsTableForm.getLblPrice().setVisible(false);
                carsTableForm.getTxtPrice().setVisible(false);
                carsTableForm.getBtnDetails().setVisible(false);
                fillTable(null);
                break;
            case TableFormMode.EDIT_INVOICE_ITEM:
                InvoiceItem invoiceItem = (InvoiceItem) Coordinator.getInstance().getParam(CoordinatorParamConsts.INVOICE_ITEM_DETAILS);
                carsTableForm.getBtnSelect().setVisible(false);
                
                List<Car> c = new ArrayList<>(){{
                    add(invoiceItem.getCar());
                }};
                
                fillTable(c);
                carsTableForm.getTblCars().setRowSelectionInterval(0, 0);
                carsTableForm.getCbBrand().setEnabled(false);
                
                carsTableForm.getBtnEnableChanges().setVisible(true);
                carsTableForm.getBtnEdit().setVisible(true);
                carsTableForm.getBtnEdit().setEnabled(false);
                carsTableForm.getLblNote().setVisible(true);
                carsTableForm.getTxtNote().setVisible(true);
                carsTableForm.getTxtNote().setEnabled(false);
                carsTableForm.getTxtNote().setText(invoiceItem.getNote());
                carsTableForm.getTxtPrice().setVisible(true);
                carsTableForm.getTxtPrice().setEnabled(false);
                carsTableForm.getTxtPrice().setText(Double.toString(invoiceItem.getPrice()));
                
                carsTableForm.getBtnDetails().setVisible(false);
                break;
            default:
                break;
        }
    }
}

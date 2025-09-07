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
        fillTable(null);
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
                List<InvoiceItem> allSelectedItems = (List<InvoiceItem>) Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_CAR);
                if (allSelectedItems != null) {
                    for (InvoiceItem item : allSelectedItems) {
                        if (item.getCar().equals(car)) {
                            JOptionPane.showMessageDialog(carsTableForm, "Selected car is already selected");
                            return;
                        }
                    }
                }
                selectedCars.add(new InvoiceItem(null, 0, car.getPrice(), note, car));
                if (JOptionPane.showConfirmDialog(carsTableForm, "Selected Cars:\n"+selectedCars.toString() + "\n Select more cars?", "Select more cars?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    return;
                }
                if (allSelectedItems != null) {
                    allSelectedItems.addAll(selectedCars);
                    Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_CAR, allSelectedItems);
                }
                else{
                    Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_CAR, selectedCars);
                }
                carsTableForm.dispose();
            }
        });
        
        carsTableForm.btnSelectCustomerAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectCustomer();
            }

            private void selectCustomer() {
                Coordinator.getInstance().openCustomersTableForm(TableFormMode.SELECT_ITEM);
                customer = (Customer)Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_CUSTOMER);
                if (customer != null) {
                    if (customer instanceof Individual) {
                        Individual i = (Individual) customer;
                        carsTableForm.getTxtCustomer().setText(i.getFirstName() + " " + i.getLastName());
                    }
                    else{
                        carsTableForm.getTxtCustomer().setText(((Company) customer).getCompanyName());
                    }
                }
                else{
                    carsTableForm.getTxtCustomer().setText("");
                }
            }
        });
        
        carsTableForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                carsTableForm.getCbBrand().setSelectedIndex(carsTableForm.getCbBrand().getSelectedIndex());
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
            
            carsTableForm.getTblCars().setModel(new CarsTableModel(cars));
        } catch (Exception ex) {
            Logger.getLogger(SeeAllCarsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        switch (formMode) {
            case TableFormMode.SEE_ALL_ITEMS:
                carsTableForm.getBtnSelect().setVisible(false);
                carsTableForm.getLblNote().setVisible(false);
                carsTableForm.getTxtNote().setVisible(false);
                carsTableForm.getLblCustomer().setVisible(false);
                carsTableForm.getTxtCustomer().setVisible(false);
                carsTableForm.getBtnSelectCustomer().setVisible(false);
                carsTableForm.getBtnDetails().setVisible(true);
                break;
            case TableFormMode.SELECT_ITEM:
                selectedCars.clear();
                carsTableForm.getBtnSelect().setVisible(true);
                carsTableForm.getLblNote().setVisible(true);
                carsTableForm.getTxtNote().setVisible(true);
                carsTableForm.getLblCustomer().setVisible(false);
                carsTableForm.getTxtCustomer().setVisible(false);
                carsTableForm.getBtnSelectCustomer().setVisible(false);
                carsTableForm.getBtnDetails().setVisible(false);
                break;
            default:
                break;
        }
    }
}

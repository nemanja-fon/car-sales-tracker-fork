/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_controllers;

import carsalesclient.controller.ClientController;
import carsalesclient.form.ShiftsTableForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_coordinator.Coordinator;
import carsalesclient.form.modes.AddFormMode;
import carsalesclient.form.modes.TableFormMode;
import carsalesclient.form.tableModels.ShiftsTableModel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import domain.Shift;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author shift
 */
public class SeeAllShiftsController {
    private final ShiftsTableForm shiftsTableForm;

    public SeeAllShiftsController(ShiftsTableForm shiftsTableForm) {
        this.shiftsTableForm = shiftsTableForm;
        addListeners();
    }
    
    public void openForm(TableFormMode formMode){
        prepareForm(formMode);
        fillTable();
        shiftsTableForm.setVisible(true);
    }
    
    private void addListeners() {
        shiftsTableForm.getDatePicker().addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dce) {
                try {
                    String d = shiftsTableForm.getDatePicker().getDateStringOrEmptyString();
                    shiftsTableForm.getTxtDate().setText(d);
                    Shift shift = new Shift();
                    shift.setSearchCondition("date");
                    shift.setSearchConditionValue(d);
                    List<Shift> shifts = ClientController.getInstance().searchShifts(shift);
                    if (shifts.isEmpty()) {
                        shiftsTableForm.getTblShifts().setModel(new ShiftsTableModel(new ArrayList<>()));
                    }
                    else{
                        shiftsTableForm.getTblShifts().setModel(new ShiftsTableModel(shifts));
                    }
                    
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
                
            }
        });
        
        shiftsTableForm.btnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = shiftsTableForm.getTblShifts().getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(shiftsTableForm, "Please select shift");
                    return;
                }
                Shift shift = ((ShiftsTableModel)shiftsTableForm.getTblShifts().getModel()).getShiftAt(row);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.SHIFT_DETAILS, shift);
                Coordinator.getInstance().openAddShiftForm(AddFormMode.DETAILS_FORM);
            }
        });
        
        shiftsTableForm.btnSelectAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowId = shiftsTableForm.getTblShifts().getSelectedRow();
                if (rowId < 0) {
                    JOptionPane.showMessageDialog(shiftsTableForm, "Please select salesperson!");
                    return;
                }
                Shift shift = ((ShiftsTableModel) shiftsTableForm.getTblShifts().getModel()).getShiftAt(rowId);
                Coordinator.getInstance().addParam(CoordinatorParamConsts.SELECTED_SHIFT, shift);
                shiftsTableForm.dispose();
            }
        });
        
        shiftsTableForm.btnAddNewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openAddShiftForm(AddFormMode.ADD_FORM);
            }
        });
        
        shiftsTableForm.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shiftsTableForm.dispose();
            }
        });
    }
    
    private void fillTable() {
        try {
            List<Shift> shifts = ClientController.getInstance().getAllShifts();
            shiftsTableForm.getTblShifts().setModel(new ShiftsTableModel(shifts));
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    private void prepareForm(TableFormMode formMode) {
        try {
            DatePickerSettings settings = shiftsTableForm.getDatePicker().getSettings();
            settings.setColor(DatePickerSettings.DateArea.DatePickerTextDisabled, Color.YELLOW);
            settings.setDateRangeLimits(LocalDate.MIN, LocalDate.now());
            settings.setFormatForDatesCommonEra("dd.MM.yyyy");
            settings.setVisibleDateTextField(false);
            
            List<Shift> shifts = ClientController.getInstance().getAllShifts();
            shiftsTableForm.getTblShifts().setModel(new ShiftsTableModel(shifts));
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        switch (formMode) {
            case SEE_ALL_ITEMS:
                shiftsTableForm.getBtnSelect().setVisible(false);
                shiftsTableForm.getBtnAddNew().setVisible(true);
                shiftsTableForm.getBtnDetails().setVisible(true);
                break;
            case SELECT_ITEM:
                shiftsTableForm.getBtnSelect().setVisible(true);
                shiftsTableForm.getBtnAddNew().setVisible(false);
                shiftsTableForm.getBtnDetails().setVisible(false);
                break;
            default:
                throw new AssertionError();
        }
    }
}

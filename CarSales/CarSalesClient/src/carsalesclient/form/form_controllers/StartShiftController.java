/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_controllers;

import carsalesclient.controller.ClientController;
import carsalesclient.form.StartShiftForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_coordinator.Coordinator;
import carsalesclient.form.modes.AddFormMode;
import carsalesclient.form.modes.TableFormMode;
import carsalesclient.form.tableModels.ShiftsTableModel;
import domain.SalesmanShift;
import domain.Shift;
import domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author salesmanShift
 */
public class StartShiftController {
    private final StartShiftForm startShiftForm;
    LocalTime checkInTime;
    LocalTime checkOutTime;

    public StartShiftController(StartShiftForm startShiftForm) {
        this.startShiftForm = startShiftForm;
        addListeners();
    }
    
    public void openForm(){
        prepareForm();
        startShiftForm.setVisible(true);
    }

    private void addListeners() {
        startShiftForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillSelectedShift();
            }
            private void fillSelectedShift() {
                Shift shift = (Shift) Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_SHIFT);
                if (shift != null) {
                    List<Shift> shifts = new ArrayList<>(){{
                        add(shift);
                    }};
                    startShiftForm.getTblShifts().setModel(new ShiftsTableModel(shifts));
                    startShiftForm.getBtnCheckIn().setEnabled(true);
                }
                else{
                    startShiftForm.getTblShifts().setModel(new ShiftsTableModel(new ArrayList<>()));
                }
            }
        });
        
        startShiftForm.btnSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
            private void save() {
                try {
                    User user = (User) Coordinator.getInstance().getParam(CoordinatorParamConsts.LOGGED_IN_USER);
                    Shift shift = ((ShiftsTableModel) startShiftForm.getTblShifts().getModel()).getShiftAt(0);
                    SalesmanShift salesmanShift = new SalesmanShift(user, shift, checkInTime, checkOutTime, (int) startShiftForm.getTxtSoldcars().getValue());
                    if(JOptionPane.showConfirmDialog(startShiftForm, "Are you sure you want to SAVE the salesmanShift into the database", "Save salesmanShift", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        ClientController.getInstance().insertSalesmanShift(salesmanShift);
                        JOptionPane.showMessageDialog(startShiftForm, "System successfully added salesman-shift connection to the database!");   
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    JOptionPane.showMessageDialog(startShiftForm, e.getMessage());
                }
                
            }
        });
        
        startShiftForm.btnSelectShiftAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openShiftsTableForm(TableFormMode.SELECT_ITEM);
            }
        });
        
        startShiftForm.btnCheckInAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shift shift = (Shift) Coordinator.getInstance().getParam(CoordinatorParamConsts.SELECTED_SHIFT);
                Date d = shift.getShiftDate();
                
                if (!isToday(d, new Date()) && !(!LocalTime.now().isBefore(shift.getStartTime()) && !LocalTime.now().isAfter(shift.getEndTime()))) {
                    JOptionPane.showMessageDialog(startShiftForm, "You can only check in for a shift that is currently active");
                    return;
                }
                checkInTime = LocalTime.now();
                startShiftForm.getBtnCheckIn().setEnabled(false);
                startShiftForm.getBtnCheckOut().setEnabled(true);
                startShiftForm.getTblShifts().setEnabled(true);
                startShiftForm.getTxtSoldcars().setEnabled(true);
                startShiftForm.getBtnSelectShift().setEnabled(false);
            }
        });
        
        startShiftForm.btnCheckOutAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkOutTime = LocalTime.now();
                startShiftForm.getBtnCheckIn().setEnabled(false);
                startShiftForm.getBtnCheckOut().setEnabled(false);
                startShiftForm.getBtnSave().setEnabled(true);
                startShiftForm.getTxtSoldcars().setEnabled(false);
            }
        });
   }

    private void prepareForm() {
        startShiftForm.getBtnSelectShift().setEnabled(true);
        startShiftForm.getBtnCheckIn().setEnabled(false);
        startShiftForm.getBtnCheckOut().setEnabled(false);
        startShiftForm.getBtnSave().setEnabled(false);
        startShiftForm.getTblShifts().setEnabled(false);
        startShiftForm.getTxtSoldcars().setEnabled(false);
    }
    
    private boolean isToday(Date d1, Date d2){
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
               c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
               c1.get(Calendar.DATE) == c2.get(Calendar.DATE);
    }
}

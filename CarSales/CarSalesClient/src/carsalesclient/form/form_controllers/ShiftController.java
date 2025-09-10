/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_controllers;

import carsalesclient.controller.ClientController;
import carsalesclient.form.AddShiftForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_coordinator.Coordinator;
import carsalesclient.form.modes.AddFormMode;
import domain.Shift;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalBorders;

/**
 *
 * @author shift
 */
public class ShiftController {
    private final AddShiftForm addShiftForm;

    public ShiftController(AddShiftForm addShiftForm) {
        this.addShiftForm = addShiftForm;
        addListeners();
    }
    
    public void openForm(AddFormMode formMode){
        prepareForm(formMode);
        addShiftForm.setVisible(true);
    }

    private void addListeners() {
        addShiftForm.btnEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareForm(AddFormMode.UPDATE_FORM);
            }
        });
        
        addShiftForm.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
            private void update() {
                try {
                    if(!emptyFields()){
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        Date d;
                        LocalTime start;
                        LocalTime end;
                        try {
                            d = sdf.parse(addShiftForm.getTxtDate().getText());
                            start = LocalTime.parse(addShiftForm.getTxtStartTime().getText(), formatter);
                            end = LocalTime.parse(addShiftForm.getTxtEndTime().getText(), formatter);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(addShiftForm, "Date or time pattern is incorect");
                            return;
                        }
                        Shift shift = new Shift(null, d, start, end);
                        if(JOptionPane.showConfirmDialog(addShiftForm, "Are you sure you want to SAVE the following changes into the database: \n"+shift.getShiftDate()+" "+shift.getStartTime()+", "+shift.getEndTime(), "Update shift", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            shift.setIdShift(((Shift) Coordinator.getInstance().getParam(CoordinatorParamConsts.SHIFT_DETAILS)).getIdShift());
                            ClientController.getInstance().updateShift(shift);
                            JOptionPane.showMessageDialog(addShiftForm, shift.getShiftDate() + " " + shift.getStartTime() + " has been successfully updated!");
                            addShiftForm.dispose();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(addShiftForm, "Fill all required fields");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                
            }
        });
        
        addShiftForm.btnSaveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
            private void save() {
                try {
                    if(!emptyFields()){
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        Date d;
                        LocalTime start;
                        LocalTime end;
                        try {
                            d = sdf.parse(addShiftForm.getTxtDate().getText());
                            start = LocalTime.parse(addShiftForm.getTxtStartTime().getText(), formatter);
                            end = LocalTime.parse(addShiftForm.getTxtEndTime().getText(), formatter);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(addShiftForm, "Date or time pattern is incorect");
                            return;
                        }
                        Shift shift = new Shift(null, d, start, end);
                        if(JOptionPane.showConfirmDialog(addShiftForm, "Are you sure you want to SAVE the following shift into the database: \n"+sdf.format(shift.getShiftDate())+" - "+shift.getStartTime().format(formatter)+" - "+shift.getEndTime().format(formatter), "Save shift", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            ClientController.getInstance().insertShift(shift);
                            if(JOptionPane.showConfirmDialog(addShiftForm, sdf.format(shift.getShiftDate())+" - " + shift.getStartTime().format(formatter) + " - " + shift.getEndTime().format(formatter) +" has been successfully added to the database!\n\nAdd more shifts?", "Success", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                                prepareForm(AddFormMode.ADD_FORM);
                            }
                            else{
                                addShiftForm.dispose();
                            }
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(addShiftForm, "Fill all required fields");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    JOptionPane.showMessageDialog(addShiftForm, e.getMessage());
                }
                
            }
        });
        
        addShiftForm.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shift shift = (Shift) Coordinator.getInstance().getParam(CoordinatorParamConsts.SHIFT_DETAILS);
                try {
                    ClientController.getInstance().deleteShift(shift);
                    JOptionPane.showMessageDialog(addShiftForm, "System successfully deleted shift");
                    addShiftForm.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addShiftForm, "System couldn't delete shift");
                    ex.printStackTrace();
                }
            }
        });
        
        addShiftForm.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addShiftForm.dispose();
            }
        });
    }

    private void prepareForm(AddFormMode formMode) {
        switch (formMode) {
            case AddFormMode.ADD_FORM:
                addShiftForm.getTxtId().setVisible(false);
                addShiftForm.getLblId().setVisible(false);
                addShiftForm.getTxtDate().setText("");
                addShiftForm.getTxtDate().setEnabled(true);
                addShiftForm.getTxtStartTime().setText("");
                addShiftForm.getTxtStartTime().setEnabled(true);
                addShiftForm.getTxtEndTime().setText("");
                addShiftForm.getTxtEndTime().setEnabled(true);
                
                addShiftForm.getBtnEnableChanges().setVisible(false);
                addShiftForm.getBtnEdit().setVisible(false);
                addShiftForm.getBtnDelete().setVisible(false);
                addShiftForm.getBtnSave().setVisible(true);
                addShiftForm.getBtnCancel().setVisible(true);
                break;
            case AddFormMode.DETAILS_FORM:
                Shift shift = (Shift) Coordinator.getInstance().getParam(CoordinatorParamConsts.SHIFT_DETAILS);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                addShiftForm.getTxtId().setVisible(true);
                addShiftForm.getLblId().setVisible(true);
                addShiftForm.getTxtId().setText(shift.getIdShift().toString());
                addShiftForm.getTxtDate().setText(sdf.format(shift.getShiftDate()));
                addShiftForm.getTxtDate().setEnabled(false);
                addShiftForm.getTxtStartTime().setText(shift.getStartTime().format(formatter));
                addShiftForm.getTxtStartTime().setEnabled(false);
                addShiftForm.getTxtEndTime().setText(shift.getEndTime().format(formatter));
                addShiftForm.getTxtEndTime().setEnabled(false);
                
                addShiftForm.getBtnEnableChanges().setVisible(true);
                addShiftForm.getBtnEnableChanges().setEnabled(true);
                addShiftForm.getBtnDelete().setVisible(true);
                addShiftForm.getBtnDelete().setEnabled(true);
                addShiftForm.getBtnEdit().setVisible(true);
                addShiftForm.getBtnEdit().setEnabled(false);
                addShiftForm.getBtnSave().setVisible(false);
                addShiftForm.getBtnCancel().setVisible(true);
                break;
                
            case AddFormMode.UPDATE_FORM:
                addShiftForm.getTxtDate().setEnabled(true);
                addShiftForm.getTxtStartTime().setEnabled(true);
                addShiftForm.getTxtEndTime().setEnabled(true);
                
                addShiftForm.getBtnEnableChanges().setEnabled(false);
                addShiftForm.getBtnEdit().setEnabled(true);
                addShiftForm.getBtnDelete().setEnabled(false);
                break;
            default:
                break;
        }
    }
    
    private boolean emptyFields(){
        Border border = new LineBorder(Color.red, 1,true);
        Font font = new Font("Gill Sans MT", 1, 8);
        boolean empty = false;
        
        
        if(addShiftForm.getTxtDate().getText().isBlank()){
            addShiftForm.getTxtDate().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
            empty = true;
        }
        else{
            addShiftForm.getTxtDate().setBorder(new MetalBorders.TextFieldBorder());
                
        }
        if(addShiftForm.getTxtStartTime().getText().isBlank()){
            addShiftForm.getTxtStartTime().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
            empty = true;
        }
        else{
            addShiftForm.getTxtStartTime().setBorder(new MetalBorders.TextFieldBorder());
                
        }
        if(addShiftForm.getTxtEndTime().getText().isBlank()){
            addShiftForm.getTxtEndTime().setBorder(new TitledBorder(border, "Required Field", 0, 0, font, Color.RED));
            empty = true;
        }
        else{
            addShiftForm.getTxtEndTime().setBorder(new MetalBorders.TextFieldBorder());
                
        }
        
        return empty;
    }
}

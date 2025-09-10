/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.tableModels;

import domain.Shift;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author shift
 */
public class ShiftsTableModel extends AbstractTableModel{
    private List<Shift> shifts;

    public ShiftsTableModel(List<Shift> shifts) {
        this.shifts = shifts;
    }
    
    

    @Override
    public int getRowCount() {
        return shifts.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        switch (columnIndex) {
            case 0:
                return sdf.format(shifts.get(rowIndex).getShiftDate());
            case 1:
                return shifts.get(rowIndex).getStartTime().format(formatter);
            case 2:
                return shifts.get(rowIndex).getEndTime().format(formatter);
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        String[] columns = {"Date","Start time","End time"};
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }
    
    public Shift getShiftAt(int row){
        return shifts.get(row);
    }
}

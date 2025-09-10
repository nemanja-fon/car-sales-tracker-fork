/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.tableModels;

import carsalesclient.controller.ClientController;
import domain.Car;
import domain.DefaultDomainObject;
import domain.InvoiceItem;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author user
 */
public class InvoiceItemsTableModel extends AbstractTableModel{
    private final List<InvoiceItem> items;

    public InvoiceItemsTableModel(List<InvoiceItem> items) {
        this.items = items;
    }
    
    private List<InvoiceItem> getVisibleItems() {
        return items.stream()
                .filter(i -> i.getStatus() == null || i.getStatus() != InvoiceItem.Status.DELETED)
                .toList();
    }

    @Override
    public int getRowCount() {
        return getVisibleItems().size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return getVisibleItems().get(rowIndex).getNum();
            case 1:
                return getVisibleItems().get(rowIndex).getCar().getBrand();
            case 2:
                return getVisibleItems().get(rowIndex).getCar().getModel();
            case 3:
                return getVisibleItems().get(rowIndex).getNote();
            case 4:
                return getVisibleItems().get(rowIndex).getPrice()  + " €";
            default:
                break;
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        String columns[] = {"Num","Brand","Model","Note","Price"};
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }
    
    public InvoiceItem getInvoiceItemAt(int rowId){
        return getVisibleItems().get(rowId);
    }

    public List<InvoiceItem> getInvoiceItems() {
        return items;
    }

    public void removeInvoiceItem(int rowId) {
        items.remove(rowId);
        for (int i = rowId; i < items.size(); i++) {
            items.get(i).setNum(i+1);
        }
        fireTableRowsDeleted(items.size() - 1, items.size() - 1);
    }

    public void deleteInvoiceItem(int rowId) {
        getVisibleItems().get(rowId).setStatus(InvoiceItem.Status.DELETED);
        fireTableDataChanged();
    }
}

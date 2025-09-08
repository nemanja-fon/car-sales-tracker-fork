/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.invoice;

import domain.CarStatus;
import domain.Invoice;
import domain.InvoiceItem;
import static domain.InvoiceItem.Status.ADDED;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class UpdateInvoiceSO extends AbstractSO {

    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Invoice)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        Invoice invoice = (Invoice) o;
        for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
            switch (invoiceItem.getStatus()) {
                case ADDED:
                    invoiceItem.getCar().setStatus(CarStatus.SOLD);
                    dbBroker.updateRow(invoiceItem.getCar());
                    dbBroker.insertRow(invoiceItem);
                    break;
                case DELETED:
                    invoiceItem.getCar().setStatus(CarStatus.AVAILABLE);
                    dbBroker.updateRow(invoiceItem.getCar());
                    dbBroker.deleteRow(invoiceItem);
                    break;
                case UPDATED:
                    invoiceItem.setSearchCondition("rb");
                    invoiceItem.setSearchConditionValue(Integer.toString(invoiceItem.getNum()));
                    InvoiceItem old =(InvoiceItem) dbBroker.getOneByCondition(invoiceItem);
                    
                    if (!old.getCar().equals(invoiceItem.getCar())) {
                        old.getCar().setStatus(CarStatus.AVAILABLE);
                        dbBroker.updateRow(old.getCar());
                        
                        invoiceItem.getCar().setStatus(CarStatus.SOLD);
                        dbBroker.updateRow(invoiceItem.getCar());
                    }
                    dbBroker.updateRow(invoiceItem);
                    break;
                default:
                    throw new AssertionError();
            }
        }
        dbBroker.updateRow(invoice);
    }

    @Override
    protected void commit() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void rollback() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

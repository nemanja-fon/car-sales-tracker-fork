/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.invoice;

import domain.CarStatus;
import domain.DefaultDomainObject;
import domain.Invoice;
import domain.InvoiceItem;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class InsertInvoiceSO extends AbstractSO {

    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Invoice)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        Invoice invoice = (Invoice) o;
        
        List<DefaultDomainObject> invoices = dbBroker.getAll(new Invoice());
        for (DefaultDomainObject i : invoices) {
            if (invoice.getIdInvoice().equals(((Invoice) i).getIdInvoice()) && invoice.getInvoiceNum().equals(((Invoice) i).getInvoiceNum())) {
                throw new Exception("Invoice already in db");
            }
        }
        
        Long invoiceId = dbBroker.insertRowAndGetId(invoice);
        for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
            invoiceItem.getCar().setStatus(CarStatus.SOLD);
            dbBroker.updateRow(invoiceItem.getCar());
            
            invoiceItem.setInvoiceId(invoiceId);
            dbBroker.insertRow(invoiceItem);
            
        }
    }

}

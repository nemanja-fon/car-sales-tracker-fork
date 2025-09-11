/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.invoice;

import domain.Car;
import domain.CarStatus;
import domain.DefaultDomainObject;
import domain.Invoice;
import domain.InvoiceItem;
import static domain.InvoiceItem.Status.ADDED;
import java.util.ArrayList;
import java.util.List;
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
        dbBroker.updateRow(invoice);
        
        for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
            if (invoiceItem.getStatus() == null) {continue;}
            switch (invoiceItem.getStatus()) {
                case InvoiceItem.Status.ADDED:
                    invoiceItem.getCar().setStatus(CarStatus.SOLD);
                    dbBroker.updateRow(invoiceItem.getCar());
                    dbBroker.insertRow(invoiceItem);
                    break;
                case InvoiceItem.Status.DELETED:
                    invoiceItem.getCar().setStatus(CarStatus.AVAILABLE);
                    dbBroker.updateRow(invoiceItem.getCar());
                    dbBroker.deleteRow(invoiceItem);
                    break;
                case InvoiceItem.Status.UPDATED:
                    InvoiceItem old = new InvoiceItem();
                    
                    InvoiceItem invItem = new InvoiceItem();
                    invItem.setSearchCondition("invoice_id");
                    invItem.setSearchConditionValue(Long.toString(invoice.getIdInvoice()));
                    List<DefaultDomainObject> items = dbBroker.getByCondition(invItem);
                    for (DefaultDomainObject item : items) {
                        InvoiceItem i = (InvoiceItem) item;
                        if (i.getNum() == invoiceItem.getNum()) {
                            i.getCar().setSearchCondition("id");
                            i.getCar().setSearchConditionValue(i.getCar().getIdCar().toString());
                            i.setCar((Car) dbBroker.getOneByCondition(i.getCar()));
                            old = i;
                            break;
                        }
                    }
                    
                    if (!old.getCar().equals(invoiceItem.getCar())) {
                        old.getCar().setStatus(CarStatus.AVAILABLE);
                        dbBroker.updateRow(old.getCar());

                        invoiceItem.getCar().setStatus(CarStatus.SOLD);
                        dbBroker.updateRow(invoiceItem.getCar());
                    }
                    dbBroker.updateRow(invoiceItem);
                    break;
                default:
                    break;
            }
        }
    }    
}

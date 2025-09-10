/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.invoice;

import domain.Car;
import domain.Customer;
import domain.DefaultDomainObject;
import domain.Invoice;
import domain.InvoiceItem;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class GetAllInvoicesSO extends AbstractSO {
    private List<DefaultDomainObject> invoices;

    @Override
    protected void validate(Object o) throws Exception {
    }

    @Override
    protected void execute(Object o) throws Exception {
        invoices = dbBroker.getAll(new Invoice());
        for (DefaultDomainObject invoice : invoices) {
            Invoice inv = (Invoice) invoice;
            
            inv.getUser().setSearchCondition("id");
            inv.getUser().setSearchConditionValue(inv.getUser().getIdUser().toString());
            inv.setUser((User) dbBroker.getByCondition(inv.getUser()).getFirst());
            
            inv.getCustomer().setSearchCondition("id");
            inv.getCustomer().setSearchConditionValue(inv.getCustomer().getIdCustomer().toString());
            inv.setCustomer((Customer) dbBroker.getByCondition(inv.getCustomer()).getFirst());
            
            InvoiceItem invItem = new InvoiceItem();
            invItem.setSearchCondition("invoice_id");
            invItem.setSearchConditionValue(inv.getIdInvoice().toString());
            List<DefaultDomainObject> items = dbBroker.getByCondition(invItem);
            List<InvoiceItem> invItems = new ArrayList<>();
            for (DefaultDomainObject item : items) {
                InvoiceItem i = (InvoiceItem) item;
                i.getCar().setSearchCondition("id");
                i.getCar().setSearchConditionValue(i.getCar().getIdCar().toString());
                i.setCar((Car) dbBroker.getByCondition(i.getCar()).getFirst());
                invItems.add(i);
            }
            inv.setInvoiceItems(invItems);
        }
    }


    public List<DefaultDomainObject> getInvoices() {
        return invoices;
    }
    
}

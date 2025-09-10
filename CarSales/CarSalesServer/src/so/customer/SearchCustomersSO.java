/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer;

import domain.Customer;
import domain.DefaultDomainObject;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class SearchCustomersSO extends AbstractSO {
    private List<DefaultDomainObject> customers;
    
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Customer)){
            throw new Exception("Wrong object type used");
}
    }

    @Override
    protected void execute(Object o) throws Exception {
        customers = dbBroker.getByCondition((Customer) o);
    }

    public List<DefaultDomainObject> getCustomers() {
        return customers;
    }
    
}

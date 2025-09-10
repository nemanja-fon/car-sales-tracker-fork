/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer;

import domain.Company;
import domain.Customer;
import domain.DefaultDomainObject;
import domain.Individual;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class GetAllCustomersSO extends AbstractSO {
    private List<DefaultDomainObject> customers;

    @Override
    protected void validate(Object o) throws Exception {
    }

    @Override
    protected void execute(Object o) throws Exception {
        customers = dbBroker.getAll((Customer) o);
    }

    public List<DefaultDomainObject> getCustomers() {
        return customers;
    }
    
}

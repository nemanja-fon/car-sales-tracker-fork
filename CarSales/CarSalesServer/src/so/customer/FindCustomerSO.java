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
import so.customer.company.FindCompanySO;
import so.customer.individual.FindIndividualSO;

/**
 *
 * @author user
 */
public class FindCustomerSO extends AbstractSO {
    private DefaultDomainObject customer;
    
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Customer)){
            throw new Exception("Wrong object type used");
}
    }

    @Override
    protected void execute(Object o) throws Exception {
        Customer c = (Customer) o;
        if (c instanceof Individual) {
            FindIndividualSO so = new FindIndividualSO();
            so.executeSO((Individual) c);
            customer = so.getIndividual();
        }
        else if (c instanceof Company) {
            FindCompanySO so = new FindCompanySO();
            so.executeSO((Company) c);
            customer = so.getCompany();
        }
        else{
            throw new Exception("Sistem ne moze da nadje kupca");
        }
    }

    @Override
    protected void commit() {
    }

    @Override
    protected void rollback() {
    }

    public DefaultDomainObject getCustomer() {
        return customer;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer;

import domain.Company;
import domain.Customer;
import domain.Individual;
import so.AbstractSO;
import so.customer.company.UpdateCompanySO;
import so.customer.individual.UpdateIndividualSO;

/**
 *
 * @author user
 */
public class UpdateCustomerSO extends AbstractSO {
    
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
            UpdateIndividualSO so = new UpdateIndividualSO();
            so.executeSO((Individual) c);
        }
        if (c instanceof Company) {
            UpdateCompanySO so = new UpdateCompanySO();
            so.executeSO((Company) c);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer;

import domain.Company;
import domain.Customer;
import domain.Individual;
import so.AbstractSO;
import so.customer.company.InsertCompanySO;
import so.customer.individual.InsertIndividualSO;

/**
 *
 * @author user
 */
public class InsertCustomerSO extends AbstractSO {
    
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Customer)){
            throw new Exception("Wrong object type used");
}
    }

    @Override
    protected void execute(Object o) throws Exception {
        Customer customer = (Customer) o;
        if (customer instanceof Individual) {
            InsertIndividualSO so = new InsertIndividualSO();
            so.executeSO((Individual) customer);
        }
        if (customer instanceof Company) {
            InsertCompanySO so = new InsertCompanySO();
            so.executeSO((Company) customer);
        }
    }

}

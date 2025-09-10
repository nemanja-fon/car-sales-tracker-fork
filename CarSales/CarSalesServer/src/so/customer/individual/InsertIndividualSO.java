/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer.individual;

import domain.Customer;
import domain.DefaultDomainObject;
import domain.Individual;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class InsertIndividualSO extends AbstractSO {
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Individual)){
            throw new Exception("Wrong object type used");
}
    }

    @Override
    protected void execute(Object o) throws Exception {
        Individual individual = (Individual) o;
        List<DefaultDomainObject> allIndividuals = dbBroker.getAll(new Individual());
        for (DefaultDomainObject ind : allIndividuals) {
            if (individual.equals((Individual) ind)) {
                throw new Exception("Individual already exists in db");
            }
        }
        individual.getCustomer().setType(Customer.CustomerType.INDIVIDUAL);
        Long idCustomer = dbBroker.insertRowAndGetId(individual.getCustomer());
        individual.setIdCustomer(idCustomer);
        dbBroker.insertRow(individual);
    }

}

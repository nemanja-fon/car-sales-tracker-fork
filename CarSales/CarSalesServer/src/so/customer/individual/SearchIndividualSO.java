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
public class SearchIndividualSO extends AbstractSO {
    private List<DefaultDomainObject> individuals;
    
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Individual)){
            throw new Exception("Wrong object type used");
}
    }

    @Override
    protected void execute(Object o) throws Exception {
        individuals = dbBroker.getByCondition((Individual) o);
    }


    public List<DefaultDomainObject> getIndividuals() {
        return individuals;
    }
}

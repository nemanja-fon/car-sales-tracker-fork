/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer.individual;

import domain.DefaultDomainObject;
import domain.Individual;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class FindIndividualSO extends AbstractSO {
    private DefaultDomainObject individual;
    
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Individual)){
            throw new Exception("Wrong object type used");
}
    }

    @Override
    protected void execute(Object o) throws Exception {
        individual = dbBroker.getOneByCondition((Individual) o);
    }

    @Override
    protected void commit() {
    }

    @Override
    protected void rollback() {
    }

    public DefaultDomainObject getIndividual() {
        return individual;
    }
}

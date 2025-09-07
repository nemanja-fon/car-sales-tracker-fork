/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer.company;

import domain.Company;
import domain.DefaultDomainObject;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class FindCompanySO extends AbstractSO {
    private DefaultDomainObject company;
    
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Company)){
            throw new Exception("Wrong object type used");
}
    }

    @Override
    protected void execute(Object o) throws Exception {
        company = dbBroker.getOneByCondition((Company) o);
    }

    @Override
    protected void commit() {
    }

    @Override
    protected void rollback() {
    }

    public DefaultDomainObject getCompany() {
        return company;
    }
}

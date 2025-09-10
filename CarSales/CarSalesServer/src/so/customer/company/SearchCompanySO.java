/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer.company;

import domain.Company;
import domain.DefaultDomainObject;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class SearchCompanySO extends AbstractSO {
    private List<DefaultDomainObject> companies;
    
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Company)){
            throw new Exception("Wrong object type used");
}
    }

    @Override
    protected void execute(Object o) throws Exception {
        companies = dbBroker.getByCondition((Company) o);
    }


    public List<DefaultDomainObject> getCompanies() {
        return companies;
    }
}

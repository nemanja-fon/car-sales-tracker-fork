/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer.company;

import domain.Company;
import domain.Customer;
import domain.DefaultDomainObject;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class InsertCompanySO extends AbstractSO {
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Company)){
            throw new Exception("Wrong object type used");
}
    }

    @Override
    protected void execute(Object o) throws Exception {
        Company company = (Company) o;
        
        List<DefaultDomainObject> allCompanies = dbBroker.getAll(new Company());
        for (DefaultDomainObject com : allCompanies) {
            if (company.equals((Company) com)) {
                throw new Exception("Company already exists in db");
            }
        }
        
        company.getCustomer().setType(Customer.CustomerType.COMPANY);
        Long idCustomer = dbBroker.insertRowAndGetId(company.getCustomer());
        company.setIdCustomer(idCustomer);
        dbBroker.insertRow((Company) o);
    }

}

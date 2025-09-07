/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer.company;

import domain.Company;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class DeleteCompanySO extends AbstractSO {
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Company)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        dbBroker.deleteRow((Company) o);
    }

    @Override
    protected void commit() {
    }

    @Override
    protected void rollback() {
    }
}

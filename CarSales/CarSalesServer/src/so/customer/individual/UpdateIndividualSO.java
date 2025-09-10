/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.customer.individual;

import domain.Individual;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class UpdateIndividualSO extends AbstractSO {
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Individual)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        Individual i = (Individual) o;
        dbBroker.updateRow(i.getCustomer());
        dbBroker.updateRow(i);
    }
}

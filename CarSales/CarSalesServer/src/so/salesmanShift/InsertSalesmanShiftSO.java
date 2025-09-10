/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.salesmanShift;

import so.shift.*;
import domain.DefaultDomainObject;
import domain.SalesmanShift;
import domain.Shift;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class InsertSalesmanShiftSO extends AbstractSO {

    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof SalesmanShift)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        List<DefaultDomainObject> salesmanShifts = dbBroker.getAll(new SalesmanShift());
        for (DefaultDomainObject salesmanShift : salesmanShifts) {
            if (((SalesmanShift) salesmanShift).getSalesman().equals(((SalesmanShift) o).getSalesman()) && ((SalesmanShift) salesmanShift).getShift().equals(((SalesmanShift) o).getShift()) ) {
                throw new Exception("SalesmanShift connection already in db");
            }
        }
        dbBroker.insertRow((SalesmanShift) o);
    }
    
}

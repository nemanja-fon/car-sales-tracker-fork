/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.shift;

import domain.DefaultDomainObject;
import domain.Shift;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class InsertShiftSO extends AbstractSO {

    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Shift)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        List<DefaultDomainObject> shifts = dbBroker.getAll(new Shift());
        for (DefaultDomainObject shift : shifts) {
            if (((Shift) shift).getShiftDate().equals(((Shift) o).getShiftDate()) && ((Shift) shift).getStartTime().equals(((Shift) o).getStartTime()) ) {
                throw new Exception("Shift already in db");
            }
        }
        dbBroker.insertRow((Shift) o);
    }
    
}

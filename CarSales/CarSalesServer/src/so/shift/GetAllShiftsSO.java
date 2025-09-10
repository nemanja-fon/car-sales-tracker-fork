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
public class GetAllShiftsSO extends AbstractSO {
    private List<DefaultDomainObject> shifts;

    @Override
    protected void validate(Object o) throws Exception {
    }

    @Override
    protected void execute(Object o) throws Exception {
        shifts = dbBroker.getAll(new Shift());
    }
    
    public List<DefaultDomainObject> getShifts() {
        return shifts;
    }
}

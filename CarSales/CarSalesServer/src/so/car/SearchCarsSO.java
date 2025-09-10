/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.car;

import domain.Car;
import domain.DefaultDomainObject;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class SearchCarsSO extends AbstractSO {
    private List<DefaultDomainObject> cars;

    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Car)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        cars = dbBroker.getByCondition((Car) o);
    }


    public List<DefaultDomainObject> getCars() {
        return cars;
    }
}

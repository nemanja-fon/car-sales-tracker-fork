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
public class InsertCarSO extends AbstractSO {

    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Car)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        Car car = (Car) o;
        List<DefaultDomainObject> cars = dbBroker.getAll(new Car());
        for (DefaultDomainObject c : cars) {
            if (car.equals((Car) c)) {
                throw new Exception("Car already exists in db");
            }
        }
        dbBroker.insertRow(car);
    }

    
}

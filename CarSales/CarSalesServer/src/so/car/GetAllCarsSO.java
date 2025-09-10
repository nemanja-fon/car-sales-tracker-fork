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
public class GetAllCarsSO extends AbstractSO{
    private List<DefaultDomainObject> cars;

    @Override
    protected void validate(Object o) throws Exception {
    }

    @Override
    protected void execute(Object o) throws Exception {
        cars = dbBroker.getAllOrdered(new Car());
    }


    public List<DefaultDomainObject> getCars() {
        return cars;
    }
}

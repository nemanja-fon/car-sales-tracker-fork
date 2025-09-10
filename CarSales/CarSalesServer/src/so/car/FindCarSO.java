/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.car;

import domain.Car;
import domain.DefaultDomainObject;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class FindCarSO extends AbstractSO {
    private DefaultDomainObject car;

    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof Car)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        car = dbBroker.getOneByCondition((Car) o);
    }


    public DefaultDomainObject getCar() {
        return car;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.user;

import domain.DefaultDomainObject;
import domain.User;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class GetAllUsersSO extends  AbstractSO{
    private List<DefaultDomainObject> users;

    @Override
    protected void validate(Object o) throws Exception {
    }

    @Override
    protected void execute(Object o) throws Exception {
        users = dbBroker.getAllOrdered(new User());
    }


    public List<DefaultDomainObject> getUsers() {
        return users;
    }
  
}

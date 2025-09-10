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
public class SearchUsersSO extends AbstractSO{
    private List<DefaultDomainObject> users;

    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof User)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        users = dbBroker.getByCondition((User) o);
    }

    public List<DefaultDomainObject> getUsers() {
        return users;
    }
    
}

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
public class InsertUserSO extends AbstractSO {

    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof User)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        List<DefaultDomainObject> users = dbBroker.getAll(new User());
        for (DefaultDomainObject user : users) {
            if (((User) user).getUsername().equals(((User) o).getUsername())) {
                throw new Exception("Selected username is taken");
            }
        }
        dbBroker.insertRow((User) o);
    }

    
}

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
public class FindUserSO extends AbstractSO {
    private DefaultDomainObject user;

    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof User)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        user = dbBroker.getOneByCondition((User) o);
    }

    @Override
    protected void commit() {
    }

    @Override
    protected void rollback() {
    }

    public DefaultDomainObject getUsers() {
        return user;
    }
}

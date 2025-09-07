/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.user;

import domain.User;
import so.AbstractSO;

/**
 *
 * @author user
 */
public class DeleteUserSO extends AbstractSO {
    @Override
    protected void validate(Object o) throws Exception {
        if(!(o instanceof User)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception {
        dbBroker.deleteRow((User) o);
    }

    @Override
    protected void commit() {
    }

    @Override
    protected void rollback() {
    }
}

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
public class LoginUserSO extends AbstractSO{
    private User loggedInUser;

    @Override
    protected void validate(Object o) throws Exception{
        if(!(o instanceof User)){
            throw new Exception("Wrong object type used");
        }
    }

    @Override
    protected void execute(Object o) throws Exception{
        List<DefaultDomainObject> users = dbBroker.getAll(new User());
        for (DefaultDomainObject u : users) {
            User user = (User) u;
            if(user.getUsername().equals(((User) o).getUsername()) && user.getPassword().equals(((User) o).getPassword())){
                loggedInUser = user;
                return;
            }
        }
        throw new Exception("Wrong username or password");
    }

    
    public User getLoggedInUser(){
        return loggedInUser;
    }
    
}

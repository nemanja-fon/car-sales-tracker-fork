/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import database.DatabaseBroker;
import java.sql.SQLException;


/**
 *
 * @author user
 */
public abstract class AbstractSO {
    protected DatabaseBroker dbBroker;

    public AbstractSO() {
        this.dbBroker = new DatabaseBroker();
    }
    
    public synchronized void executeSO(Object o) throws Exception{
        try {
            validate(o);
            try {
                execute(o);
                commit();
            } catch (Exception e) {
                rollback();
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    protected abstract void validate(Object o) throws Exception;

    protected abstract void execute(Object o) throws Exception;

    protected void commit() throws SQLException {
        dbBroker.commit();
    }

    protected void rollback() throws SQLException {
        dbBroker.rollback();
    }
}

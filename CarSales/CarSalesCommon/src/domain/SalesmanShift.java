/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class SalesmanShift extends DefaultDomainObject implements  Serializable{
    private User salesman;
    private Shift shift;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private int carsSold;

    public SalesmanShift(User salesman, Shift shift, LocalTime checkIn, LocalTime checkOut, int carsSold) {
        this.salesman = salesman;
        this.shift = shift;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.carsSold = carsSold;
    }

    public SalesmanShift() {
    }

    public User getSalesman() {
        return salesman;
    }

    public void setSalesman(User salesman) {
        this.salesman = salesman;
    }
    
    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    public int getCarsSold() {
        return carsSold;
    }

    public void setCarsSold(int carsSold) {
        this.carsSold = carsSold;
    }
    
    @Override
    public List<DefaultDomainObject> returnList(ResultSet rs) throws SQLException {
        List<DefaultDomainObject> salesmanShifts = new ArrayList<>();
        try {
            while(rs.next()){
                User u = new User();
                u.setIdUser(rs.getLong("user_id"));
                Shift s= new Shift();
                s.setIdShift(rs.getLong("shift_id"));
                salesmanShifts.add(new SalesmanShift(u, s, rs.getTime("check_in").toLocalTime(), rs.getTime("check_out").toLocalTime(), rs.getInt("cars_sold")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Shift.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return salesmanShifts;
    }

    @Override
    public String getGetAllQuery() {
        return "SELECT * FROM salesman_shift";
    }

    @Override
    public String getGetAllOrderedQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getGetByConditionQuery() {
        return "SELECT * FROM salesman_shift WHERE "+ searchCondition +" LIKE '"+ searchConditionValue +"%'";
    }

    @Override
    public String getInsertQuery() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return "INSERT INTO salesman_shift "+
                "(user_id, shift_id, check_in, check_out, cars_sold) "+
                "values("+ salesman.getIdUser() +", " + shift.getIdShift() + ", '"+ checkIn.format(formatter) +"', '"+ checkOut.format(formatter)+"', "+ carsSold +")";
    }

    @Override
    public String getUpdateQuery() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return "UPDATE salesman_shift "+
                "SET check_in = '"+ checkIn.format(formatter) +"', check_out = '"+ checkOut.format(formatter)+"', cars_sold = "+ carsSold +" "+
                "WHERE user_id = " + salesman.getIdUser() + " AND shift_id = " + shift.getIdShift();
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM salesman_shift WHERE user_id = " + salesman.getIdUser() + " AND shift_id = " + shift.getIdShift();
    }

    
    
}

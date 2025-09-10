/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shift
 */
public class Shift extends DefaultDomainObject implements  Serializable{
    private Long idShift;
    private Date shiftDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public Shift() {
    }
    
    public Shift(Long idShift, Date shiftDate, LocalTime startTime, LocalTime endTime) {
        this.idShift = idShift;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getIdShift() {
        return idShift;
    }

    public void setIdShift(Long idShift) {
        this.idShift = idShift;
    }

    
    

    @Override
    public List<DefaultDomainObject> returnList(ResultSet rs) throws SQLException {
        List<DefaultDomainObject> shifts = new ArrayList<>();
        try {
            while(rs.next()){
                shifts.add(new Shift(rs.getLong("id"), rs.getDate("shift_date"), rs.getTime("start_time").toLocalTime(), rs.getTime("end_time").toLocalTime()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Shift.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return shifts;
    }

    @Override
    public String getGetAllQuery() {
        return "SELECT * FROM shift";
    }

    @Override
    public String getGetAllOrderedQuery() {
        return "SELECT * FROM shift ORDER BY shift_date";
    }

    @Override
    public String getGetByConditionQuery() {
        return "SELECT * FROM shift WHERE "+ searchCondition +" LIKE '"+ searchConditionValue +"%'"; 
    }

    @Override
    public String getInsertQuery() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return "INSERT INTO shift "+
                "(shift_date, start_time, end_time) "+
                "values('"+ sdf.format(shiftDate) +"', '"+ startTime.format(formatter) +"', '"+ endTime.format(formatter) +"')";
    }

    @Override
    public String getUpdateQuery() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return "UPDATE shift "+
                "SET shift_date = '"+ sdf.format(shiftDate) +"', start_time = '"+ startTime.format(formatter) +"', end_time = '"+ endTime.format(formatter) +"' "+
                "WHERE id = " + idShift;
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM shift WHERE id = " + idShift;
    }

    public Date getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(Date shiftDate) {
        this.shiftDate = shiftDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    
}

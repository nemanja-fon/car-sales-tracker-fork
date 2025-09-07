/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Customer extends DefaultDomainObject implements Serializable{
    protected Long idCustomer;
    protected String phone;
    protected String email;
    protected String address;

    public Customer(Long idCustomer, String phone, String email, String address) {
        this.idCustomer = idCustomer;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Customer() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public List<DefaultDomainObject> returnList(ResultSet rs) throws SQLException {
        List<DefaultDomainObject> customers = new ArrayList<>();
        try {
            while(rs.next()){
                Customer c = null;
                if ("individual".equals(rs.getString("c.type"))) {
                    c = new Individual(rs.getLong("c.id"), rs.getString("i.first_name"), rs.getString("i.last_name"), rs.getString("i.jmbg"), rs.getString("i.id_card_number"), rs.getString("c.phone"), rs.getString("c.email"), rs.getString("c.address"));
                }
                if("company".equals(rs.getString("c.type"))) {
                    c = new Company(rs.getLong("c.id"), rs.getString("co.company_name"), rs.getString("co.PIB"), rs.getString("co.MB"), rs.getString("co.authorized_person"), rs.getString("c.phone"), rs.getString("c.email"), rs.getString("c.address"));
                }
                customers.add(c);
            }
        } catch (SQLException e) {
            throw e;
        }
        return customers;
    }

    @Override
    public String getGetAllQuery() {
        return "SELECT * FROM Customer c "+
                "LEFT JOIN Individual i ON c.id = i.id "+
                "LEFT JOIN Company co ON c.id = co.id";
    }

    @Override
    public String getGetAllOrderedQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getGetByConditionQuery() {
        return "SELECT * FROM Customer c "+
                "LEFT JOIN Individual i ON c.id = i.id "+
                "LEFT JOIN Company co ON c.id = co.id "+
                "WHERE c."+ searchCondition +" LIKE '"+ searchConditionValue +"%'";
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO customer "+
                "(phone, email, address, id_card_number) "+
                "values('"+ phone +"', '"+ email +"', '"+ address +"')";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE customer "+
                "SET phone = '"+ phone +"', email = '"+ email +"', address = '"+ address +"' "+
                "WHERE id = " + idCustomer;
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM customer WHERE id = "+idCustomer;
    }
}

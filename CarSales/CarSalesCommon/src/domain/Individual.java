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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Individual extends Customer implements Serializable{
    private String firstName;
    private String lastName;
    private String jmbg;
    private String idCardNumber;
    private Customer customer;

    public Individual(Long idCustomer, String firstName, String lastName, String jmbg, String idCardNumber, String phone, String email, String address) {
        super(idCustomer, phone, email, address);
        this.customer = new Customer();
        this.firstName = firstName;
        this.lastName = lastName;
        this.jmbg = jmbg;
        this.idCardNumber = idCardNumber;
    }

    public Individual() {
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }
    
    public Customer getCustomer() {
        customer.setIdCustomer(idCustomer);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Individual other = (Individual) obj;
        if (!Objects.equals(this.idCustomer, other.idCustomer)) {
            return false;
        }
        if (!Objects.equals(this.jmbg, other.jmbg)) {
            return false;
        }
        return Objects.equals(this.idCardNumber, other.idCardNumber);
    }
    
    
    
    @Override
    public List<DefaultDomainObject> returnList(ResultSet rs) throws SQLException {
        List<DefaultDomainObject> individuals = new ArrayList<>();
        try {
            while(rs.next()){
                individuals.add(new Individual(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("jmbg"), rs.getString("id_card_number"), rs.getString("phone"), rs.getString("email"), rs.getString("address")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return individuals;
    }

    @Override
    public String getGetAllQuery() {
        return "SELECT * FROM individual i JOIN customer c ON i.id = c.id";
    }

    @Override
    public String getGetAllOrderedQuery() {
        return "SELECT * FROM individual i JOIN customer c ON i.id = c.id ORDER BY i.last_name";
    }

    @Override
    public String getGetByConditionQuery() {
        return "SELECT * FROM individual i JOIN customer c ON i.id = c.id WHERE i."+ searchCondition +" LIKE '"+ searchConditionValue +"%'";
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO individual "+
                "(id, first_name, last_name, jmbg, id_card_number) "+
                "values("+ idCustomer +", '"+ firstName +"', '"+ lastName +"', '"+ jmbg +"', '"+ idCardNumber +"')";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE individual "+
                "SET first_name = '"+ firstName +"', last_name = '"+ lastName +"', jmbg = '"+ jmbg +"', id_card_number = '"+ idCardNumber +"' "+
                "WHERE id = "+ idCustomer;
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM individual WHERE id = "+idCustomer;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import database.DatabaseBroker;
import domain.Car;
import domain.Company;
import domain.Customer;
import domain.DefaultDomainObject;
import domain.Individual;
import domain.Invoice;
import domain.InvoiceItem;
import domain.SalesmanShift;
import domain.Shift;
import domain.User;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import so.AbstractSO;
import so.car.DeleteCarSO;
import so.car.GetAllCarsSO;
import so.car.InsertCarSO;
import so.car.SearchCarsSO;
import so.car.UpdateCarSO;
import so.customer.DeleteCustomerSO;
import so.customer.GetAllCustomersSO;
import so.customer.InsertCustomerSO;
import so.customer.SearchCustomersSO;
import so.customer.UpdateCustomerSO;
import so.customer.individual.InsertIndividualSO;
import so.customer.individual.UpdateIndividualSO;
import so.invoice.DeleteInvoiceSO;
import so.invoice.GetAllInvoicesSO;
import so.invoice.InsertInvoiceSO;
import so.invoice.SearchInvoicesSO;
import so.invoice.UpdateInvoiceSO;
import so.salesmanShift.InsertSalesmanShiftSO;
import so.shift.DeleteShiftSO;
import so.shift.GetAllShiftsSO;
import so.shift.InsertShiftSO;
import so.shift.SearchShiftsSO;
import so.shift.UpdateShiftSO;
import so.user.DeleteUserSO;
import so.user.GetAllUsersSO;
import so.user.InsertUserSO;
import so.user.LoginUserSO;
import so.user.SearchUsersSO;
import so.user.UpdateUserSO;

/**
 *
 * @author user
 */
public class ServerController {
    private static ServerController instance;
    private final DatabaseBroker dbBroker;
    private final List<User> loggedInUsers;

    private ServerController() {
        dbBroker = new DatabaseBroker();
        loggedInUsers = new ArrayList<>();
    }
    
    public static ServerController getInstance(){
        if(instance == null){
            instance = new ServerController();
        }
        return instance;
    }
    
    public User login(String username, String password) throws Exception {
        LoginUserSO lu = new LoginUserSO();
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        lu.executeSO(u);
        User loggedInUser = lu.getLoggedInUser();
        if (!loggedInUsers.contains(loggedInUser)) {
            loggedInUsers.add(loggedInUser);
            return loggedInUser;
        }
        throw new Exception("You are already logged in");
    }
    
    public void logout(User user){
        if (loggedInUsers.contains(user)) {
            loggedInUsers.remove(user);
        }
    }
    
    
    
    
    
    public List<DefaultDomainObject> getAllUsers() throws Exception{
        GetAllUsersSO so = new GetAllUsersSO();
        so.executeSO(null);
        return so.getUsers();
    }
    
    public List<DefaultDomainObject> getAllCars() throws Exception{
        GetAllCarsSO so = new GetAllCarsSO();
        so.executeSO(null);
        return so.getCars();
    }
    
    public List<DefaultDomainObject> getAllCustomers(Customer customer) throws Exception{
        GetAllCustomersSO so = new GetAllCustomersSO();
        so.executeSO(customer);
        return so.getCustomers();
    }
    
    public List<DefaultDomainObject> getAllInvoices() throws Exception{
        GetAllInvoicesSO so = new GetAllInvoicesSO();
        so.executeSO(null);
        return so.getInvoices();
    }
    
    public List<DefaultDomainObject> getAllShifts() throws Exception {
        GetAllShiftsSO so = new GetAllShiftsSO();
        so.executeSO(null);
        return so.getShifts();
    }

    
    
    
    
    
    public List<DefaultDomainObject> searchUsers(User u) throws Exception {
        SearchUsersSO so = new SearchUsersSO();
        so.executeSO(u);
        return so.getUsers();
    }
    
    public List<DefaultDomainObject> searchCars(Car car) throws Exception {
        SearchCarsSO so = new SearchCarsSO();
        so.executeSO(car);
        return so.getCars();
    }
    
    public List<DefaultDomainObject> searchCustomers(Customer customer) throws Exception {
        SearchCustomersSO so = new SearchCustomersSO();
        so.executeSO(customer);
        return so.getCustomers();
    }
    
    public List<DefaultDomainObject> searchInvoices(Invoice invoice) throws Exception {
        SearchInvoicesSO so = new SearchInvoicesSO();
        so.executeSO(invoice);
        return so.getInvoices();
    }
    
    public List<DefaultDomainObject> searchShifts(Shift shift) throws Exception {
        SearchShiftsSO so = new SearchShiftsSO();
        so.executeSO(shift);
        return so.getShifts();
    }
    
    
    
    
    
    
    public void insertUser(User user) throws Exception {
        InsertUserSO so = new InsertUserSO();
        so.executeSO(user);
    }
    
    public void insertCustomer(Customer customer) throws Exception {
        InsertCustomerSO so = new InsertCustomerSO();
        so.executeSO(customer);
    }
    
    public void insertCar(Car car) throws Exception {
        InsertCarSO so = new InsertCarSO();
        so.executeSO(car);
    }
    
    public void insertInvoice(Invoice invoice) throws Exception {
        InsertInvoiceSO so = new InsertInvoiceSO();
        so.executeSO(invoice);
    }
    
    public void insertShift(Shift shift) throws Exception {
        InsertShiftSO so = new InsertShiftSO();
        so.executeSO(shift);
    }

    public void insertSalesmanShift(SalesmanShift salesmanShift) throws Exception {
        InsertSalesmanShiftSO so = new InsertSalesmanShiftSO();
        so.executeSO(salesmanShift);
    }
    

    
    
    
    
    public void deleteCar(Car car) throws Exception {
        DeleteCarSO so = new DeleteCarSO();
        so.executeSO(car);
    }
    
    public void deleteCustomer(Customer customer) throws Exception {
        DeleteCustomerSO so = new DeleteCustomerSO();
        so.executeSO(customer);
    }

    public void deleteInvoice(Invoice invoice) throws Exception {
        DeleteInvoiceSO so = new DeleteInvoiceSO();
        so.executeSO(invoice);
    }
    
    public void deleteUser(User user) throws Exception {
        DeleteUserSO so = new DeleteUserSO();
        so.executeSO(user);
    }
    
    public void deleteShift(Shift shift) throws Exception {
        DeleteShiftSO so = new DeleteShiftSO();
        so.executeSO(so);
    }

    

    
    
    public void updateCustomer(Customer customer) throws Exception {
        UpdateCustomerSO so = new UpdateCustomerSO();
        so.executeSO(customer);
    }
    
    public void updateCar(Car car) throws Exception {
        UpdateCarSO so = new UpdateCarSO();
        so.executeSO(car);
    }
    
    public void updateUser(User user) throws Exception {
        UpdateUserSO so = new UpdateUserSO();
        so.executeSO(user);
    }

    public void updateInvoice(Invoice invoice) throws Exception {
        UpdateInvoiceSO so = new UpdateInvoiceSO();
        so.executeSO(invoice);
    }
    
    public void updateShift(Shift shift) throws Exception {
        UpdateShiftSO so = new UpdateShiftSO();
        so.executeSO(shift);
    }
    
    
    
    
    public void closeCon(){
        dbBroker.closeCon();
    }

    

    
}

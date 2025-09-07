/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.controller;

import communication.Operation;
import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import domain.Car;
import domain.Customer;
import domain.Invoice;
import domain.User;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author user
 */
public class ClientController {
    private static ClientController instance;
    private Socket socket;
    private Sender sender;
    private Receiver receiver;

    private ClientController() throws IOException {
        socket = new Socket("127.0.0.1", 9000);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }
    
    public static ClientController getInstance() throws IOException{
        if(instance == null){
            instance = new ClientController();
        }
        return instance;
    }
    
    public User login(User user) throws Exception {
        Request request = new Request(Operation.LOGIN, user);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (User) response.getResult();
        }
        else{
            throw response.getException();
        }
    }
    
    
    
    
    
    public List<User> getAllUsers() throws Exception{
        Request request = new Request(Operation.GET_ALL_USERS, null);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
            return (List<User>) response.getResult();
        }
        else{
            throw response.getException();
        }
    }
    
    public List<Car> getAllCars() throws Exception{
        Request request = new Request(Operation.GET_ALL_CARS, null);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
            return (List<Car>) response.getResult();
        }
        else{
            throw response.getException();
        }
    }
    
    public List<Customer> getAllCustomers(Customer customer) throws Exception{
        Request request = new Request(Operation.GET_ALL_CUSTOMERS, customer);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
            return (List<Customer>) response.getResult();
        }
        else{
            throw response.getException();
        }
    }
    
    public List<Invoice> getAllInvoices() throws Exception{
        Request request = new Request(Operation.GET_ALL_INVOICES, null);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
            return (List<Invoice>) response.getResult();
        }
        else{
            throw response.getException();
        }
    }

   

    
    
    
    
    
    public List<User> searchUsers(User u) throws Exception {
        Request request = new Request(Operation.SEARCH_USERS, u);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
            return (List<User>) response.getResult();
        }
        else{
            throw response.getException();
        }
    }
    
    public List<Car> searchCars(Car car) throws Exception {
        Request request = new Request(Operation.SEARCH_CARS, car);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
            return (List<Car>) response.getResult();
        }
        else{
            throw response.getException();
        }
    }
    
    public List<Customer> searchCustomers(Customer customer) throws Exception {
        Request request = new Request(Operation.SEARCH_CUSTOMERS, customer);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
            return (List<Customer>) response.getResult();
        }
        else{
            throw response.getException();
        }
    }
    
    public List<Invoice> searchInvoices(Invoice invoice) throws Exception {
        Request request = new Request(Operation.SEARCH_INVOICES, invoice);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() == null){
            return (List<Invoice>) response.getResult();
        }
        else{
            throw response.getException();
        }
    }
    
    
    
    
    
    
    
    
    public void insertUser(User user) throws Exception {
        Request request = new Request(Operation.INSERT_USER, user);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() != null){
            throw response.getException();
        }
    }
    
    public void insertCustomer(Customer customer) throws Exception {
        Request request = new Request(Operation.INSERT_CUSTOMER, customer);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() != null){
            throw response.getException();
        }
    }
    
    public void insertCar(Car car) throws Exception {
        Request request = new Request(Operation.INSERT_CAR, car);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() != null){
            throw response.getException();
        }
    }

    public void insertInvoice(Invoice invoice) throws Exception {
        Request request = new Request(Operation.INSERT_INVOICE, invoice);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() != null){
            throw response.getException();
        }
    }

   

    
    
    
    
    
    
    
    // U SUSTINI NE BI TREBALO DA BRISEMO NISTA IZ BAZE, POGOTOVO KUPCE, DANAS JE BAZA KORISNIKA VREDNIJA OD ZLATA
    // ZA KOLA SAMO MENJATI STATUS DOSTUPNO/PRODATO
    // POSTO JE PROJEKAT ZA FAKS OSTAVICU KAO POKAZNI PRIMER
    
    public void deleteUser(User user) throws Exception {
        Request request = new Request(Operation.DELETE_USER, user);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() != null){
            throw response.getException();
        }
    }
    
    
    
    
    
    
    
    
    
    public void updateCustomer(Customer customer) throws Exception {
        Request request = new Request(Operation.UPDATE_CUSTOMER, customer);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() != null){
            throw response.getException();
        }
    }
    
    public void updateCar(Car car) throws Exception {
        Request request = new Request(Operation.UPDATE_CAR, car);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() != null){
            throw response.getException();
        }
    }
    
    public void updateUser(User user) throws Exception {
        Request request = new Request(Operation.UPDATE_USER, user);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() != null){
            throw response.getException();
        }
    }
    

    
    
    
    
    
    
    
    
    
    
    
    public void closeCon() throws Exception{
        Request request = new Request(Operation.CLOSE_CON, null);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() != null){
            throw response.getException();
        }
    }

    public void logout(User user) throws Exception {
        Request request = new Request(Operation.LOG_OUT, user);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException() != null){
            throw response.getException();
        }
    }

}

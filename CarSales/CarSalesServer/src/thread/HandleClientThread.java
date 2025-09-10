/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thread;

import communication.Operation;
import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import controller.ServerController;
import domain.Car;
import domain.Customer;
import domain.Invoice;
import domain.SalesmanShift;
import domain.Shift;
import domain.User;
import form.coordinator.Coordinator;
import java.net.Socket;

/**
 *
 * @author user
 */
public class HandleClientThread extends Thread{
    
    private Socket socket;
    private ServerThread serverThread;
    private final Sender sender;
    private final Receiver receiver;
    
    
    public HandleClientThread(Socket socket, ServerThread serverThread){
        this.socket = socket;
        this.serverThread = serverThread;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }
    
    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                Request request = (Request) receiver.receive();
                Response response = new Response();
                try {
                    ServerController controller = ServerController.getInstance();
                    switch(request.getOperation()){
                        case Operation.LOGIN:
                            User user = (User) request.getArgument();
                            User loggedInUser = controller.login(user.getUsername(), user.getPassword());
                            response.setResult(loggedInUser);
                            login(loggedInUser);
                            break;
                        case Operation.LOG_OUT:
                            User loggedOutUser = (User) request.getArgument();
                            controller.logout(loggedOutUser);
                            logout(loggedOutUser);
                            break;
                            
                            
                            
                        case Operation.GET_ALL_USERS:
                            response.setResult(controller.getAllUsers());
                            break;
                        case Operation.GET_ALL_CARS:
                            response.setResult(controller.getAllCars());
                            break;
                        case Operation.GET_ALL_CUSTOMERS:
                            response.setResult(controller.getAllCustomers((Customer) request.getArgument()));
                            break;
                        case Operation.GET_ALL_INVOICES:
                            response.setResult(controller.getAllInvoices());
                            break;
                        case Operation.GET_ALL_SHIFTS:
                            response.setResult(controller.getAllShifts());
                            break;
                        
                            
                            
                            
                        case Operation.SEARCH_USERS:
                            response.setResult(controller.searchUsers((User) request.getArgument()));
                            break;
                        case Operation.SEARCH_CARS:
                            response.setResult(controller.searchCars((Car) request.getArgument()));
                            break;
                        case Operation.SEARCH_CUSTOMERS:
                            response.setResult(controller.searchCustomers((Customer) request.getArgument()));
                            break;
                        case Operation.SEARCH_INVOICES:
                            response.setResult(controller.searchInvoices((Invoice) request.getArgument()));
                            break;
                        case Operation.SEARCH_SHIFTS:
                            response.setResult(controller.searchShifts((Shift) request.getArgument()));
                            break;
                        
                        
                        
                        case Operation.INSERT_USER:
                            controller.insertUser((User) request.getArgument());
                            break;
                        case Operation.INSERT_CUSTOMER:
                            controller.insertCustomer((Customer) request.getArgument());
                            break;
                        case Operation.INSERT_CAR:
                            controller.insertCar((Car) request.getArgument());
                            break;
                        case Operation.INSERT_INVOICE:
                            controller.insertInvoice((Invoice) request.getArgument());
                            break;
                        case Operation.INSERT_SHIFT:
                            controller.insertShift((Shift) request.getArgument());
                            break;
                        case Operation.INSERT_SALESMAN_SHIFT:
                            controller.insertSalesmanShift((SalesmanShift) request.getArgument());
                            break;
                        
                        
                        
                        case Operation.DELETE_USER:
                            controller.deleteUser((User) request.getArgument());
                            break;
                        case Operation.DELETE_CAR:
                            controller.deleteCar((Car) request.getArgument());
                            break;
                        case Operation.DELETE_CUSTOMER:
                            controller.deleteCustomer((Customer) request.getArgument());
                            break;
                        case Operation.DELETE_INVOICE:
                            controller.deleteInvoice((Invoice) request.getArgument());
                            break;
                        case Operation.DELETE_SHIFT:
                            controller.deleteShift((Shift) request.getArgument());
                            break;
                        
                        
                        
                        case Operation.UPDATE_CUSTOMER:
                            controller.updateCustomer((Customer) request.getArgument());
                            break;
                        case Operation.UPDATE_CAR:
                            controller.updateCar((Car) request.getArgument());
                            break;
                        case Operation.UPDATE_USER:
                            controller.updateUser((User) request.getArgument());
                            break;
                        case Operation.UPDATE_INVOICE:
                            controller.updateInvoice((Invoice) request.getArgument());
                            break;
                        case Operation.UPDATE_SHIFT:
                            controller.updateShift((Shift) request.getArgument());
                            break;
                        
                        
                        case Operation.CLOSE_CON:
                            controller.closeCon();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setException(e);
                }
                sender.send(response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Socket is closed!");
    }

    public Socket getSocket() {
        return socket;
    }

    private void login(User user) {
        serverThread.login(this);
        Coordinator.getInstance().getServerFormController().addLoggedInUser(user);
    }

    private void logout(User user) {
        serverThread.logout(this);
        Coordinator.getInstance().getServerFormController().removeLoggedInUser(user);
   }
}

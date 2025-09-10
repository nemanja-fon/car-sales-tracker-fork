/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carsalesclient.form.form_coordinator;

import carsalesclient.form.AddCarForm;
import carsalesclient.form.AddCustomerForm;
import carsalesclient.form.AddInvoiceForm;
import carsalesclient.form.AddShiftForm;
import carsalesclient.form.AddUserForm;
import carsalesclient.form.CarsTableForm;
import carsalesclient.form.CustomersTableForm;
import carsalesclient.form.InvoiceItemsTableForm;
import carsalesclient.form.InvoicesTableForm;
import carsalesclient.form.LoginForm;
import carsalesclient.form.MainForm;
import carsalesclient.form.ShiftsTableForm;
import carsalesclient.form.StartShiftForm;
import carsalesclient.form.UsersTableForm;
import carsalesclient.form.constants.CoordinatorParamConsts;
import carsalesclient.form.form_controllers.CarController;
import carsalesclient.form.form_controllers.CustomerController;
import carsalesclient.form.form_controllers.LoginController;
import carsalesclient.form.form_controllers.MainController;
import carsalesclient.form.form_controllers.InvoiceController;
import carsalesclient.form.form_controllers.SeeAllCarsController;
import carsalesclient.form.form_controllers.SeeAllCustomersController;
import carsalesclient.form.form_controllers.SeeAllInvoicesController;
import carsalesclient.form.form_controllers.SeeAllShiftsController;
import carsalesclient.form.form_controllers.SeeAllUsersController;
import carsalesclient.form.form_controllers.SeeItemsController;
import carsalesclient.form.form_controllers.ShiftController;
import carsalesclient.form.form_controllers.StartShiftController;
import carsalesclient.form.form_controllers.UserController;
import carsalesclient.form.modes.AddFormMode;
import carsalesclient.form.modes.TableFormMode;
import domain.InvoiceItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class Coordinator {
    private static Coordinator instance;
    private final MainController mainController;
    private final Map<CoordinatorParamConsts, Object> params;

    private Coordinator() {
        mainController = new MainController(new MainForm());
        params = new HashMap<>();
    }
    
    public static Coordinator getInstance(){
        if (instance == null) {
            instance = new Coordinator();
        }
        return instance;
    }
    
    public void openLoginForm(){
        LoginController loginController = new LoginController(new LoginForm());
        loginController.openForm();
    }
    
    public void openMainForm(){
        mainController.openForm();
    }
    
    public MainController getMainController(){
        return mainController;
    }
    
    public void addParam(CoordinatorParamConsts name, Object key){
        params.put(name, key);
    }
    
    public Object getParam(CoordinatorParamConsts name){
        return params.get(name);
    }

    public void openAddInvoiceForm(AddFormMode mode) {
        InvoiceController controller = new InvoiceController(new AddInvoiceForm(mainController.getMainForm(), true));
        controller.openForm(mode);
    }

    public void openInvoicesTableForm() {
        SeeAllInvoicesController controller = new SeeAllInvoicesController(new InvoicesTableForm(mainController.getMainForm(), true));
        controller.openForm();
    }
    
    public void openInvoiceItemsTableForm(List<InvoiceItem> items) {
        SeeItemsController controller = new SeeItemsController(new InvoiceItemsTableForm(mainController.getMainForm(), true));
        controller.openForm(items);
    }
    
    public void openAddUserForm(AddFormMode formMode) {
        UserController controller = new UserController(new AddUserForm(mainController.getMainForm(), true));
        controller.openForm(formMode);
    }

    public void openUsersTableForm(TableFormMode formMode) {
        SeeAllUsersController controller = new SeeAllUsersController(new UsersTableForm(mainController.getMainForm(), true));
        controller.openForm(formMode);
    }

    public void openAddCarForm(AddFormMode formMode) {
        CarController controller = new CarController(new AddCarForm(mainController.getMainForm(), true));
        controller.openForm(formMode);
    }

    public void openCarsTableForm(TableFormMode formMode) {
        SeeAllCarsController controller = new SeeAllCarsController(new CarsTableForm(mainController.getMainForm(), true));
        controller.openForm(formMode);
    }

    public void openAddCustomerForm(AddFormMode formMode) {
        CustomerController controller = new CustomerController(new AddCustomerForm(mainController.getMainForm(), true));
        controller.openForm(formMode);
    }

    public void openCustomersTableForm(TableFormMode formMode) {
        SeeAllCustomersController controller = new SeeAllCustomersController(new CustomersTableForm(mainController.getMainForm(), true));
        controller.openForm(formMode);
    }

    public void openAddShiftForm(AddFormMode addFormMode) {
        ShiftController controller = new ShiftController(new AddShiftForm(mainController.getMainForm(), true));
        controller.openForm(addFormMode);
    }

    public void openShiftsTableForm(TableFormMode tableFormMode) {
        SeeAllShiftsController controller = new SeeAllShiftsController(new ShiftsTableForm(mainController.getMainForm(), true));
        controller.openForm(tableFormMode);
    }

    public void openStartShiftForm() {
        StartShiftController controller = new StartShiftController(new StartShiftForm(mainController.getMainForm(), true));
        controller.openForm();
    }
}

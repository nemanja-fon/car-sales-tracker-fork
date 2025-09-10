/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package communication;

/**
 *
 * @author user
 */
public enum Operation {
    LOGIN,
    LOG_OUT,
    
    GET_ALL_USERS,
    GET_ALL_CARS,
    GET_ALL_CUSTOMERS,
    GET_ALL_INVOICES,
    
    SEARCH_USERS,
    SEARCH_CARS,
    SEARCH_CUSTOMERS,
    SEARCH_INVOICES,
    
    INSERT_USER,
    INSERT_CUSTOMER,
    INSERT_CAR,
    INSERT_INVOICE,
    
    DELETE_USER,
    DELETE_CAR, 
    DELETE_CUSTOMER,
    DELETE_INVOICE, 
     
    UPDATE_CUSTOMER,
    UPDATE_CAR,
    UPDATE_USER,
    UPDATE_INVOICE,
    
    CLOSE_CON, UPDATE_SHIFT, INSERT_SHIFT, DELETE_SHIFT, SEARCH_SHIFTS, GET_ALL_SHIFTS, INSERT_SALESMAN_SHIFT, 
}

package controller.customerController;

import javafx.collections.ObservableList;
import model.dto.Customer;

public interface CustomerManagementService {
    void addCustomerDetails(Customer customer);
    void updateCustomerDetails(Customer customer);
     ObservableList<Customer> getAllCustomers();
    void deleteCustomerDetails(String custId);
    String getLastID();
}



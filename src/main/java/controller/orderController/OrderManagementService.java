package controller.orderController;

import javafx.collections.ObservableList;
import model.dto.Customer;
import model.dto.Item;
import model.dto.OrderDetail;

public interface OrderManagementService {

    String getLastID();

    double customerTotalValue(String custId);
    Customer getCustomer(String customerID);

    ObservableList<String> getOrdersInSameCustID(String custId);

    ObservableList<OrderDetail> getOrderDetailsInSameOrderID(String orderId);

    Item getItem(String itemId);
    ObservableList<String> getItems();

    void updatStockCount(String itemID,int sellQTY);
}

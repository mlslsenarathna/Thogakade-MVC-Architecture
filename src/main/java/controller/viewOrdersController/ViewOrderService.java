package controller.viewOrdersController;

import javafx.collections.ObservableList;
import model.dto.OrdersHistory;

public interface ViewOrderService {
    ObservableList<OrdersHistory> getOrdersHistory();
}

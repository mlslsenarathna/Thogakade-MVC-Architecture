package controller.viewOrdersController;

import controller.itemsController.ItemManagementController;
import controller.itemsController.ItemManagementService;
import controller.orderController.OrderManagementController;
import controller.orderController.OrderManagementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Item;
import model.dto.Order;
import model.dto.OrderDetail;
import model.dto.OrdersHistory;

public class ViewOrderController implements ViewOrderService{
    ItemManagementService itemManagementService=new ItemManagementController();
    OrderManagementService orderManagementService=new OrderManagementController();
    ObservableList<OrdersHistory> ordersHistories= FXCollections.observableArrayList();

    @Override
    public ObservableList<OrdersHistory> getOrdersHistory() {
        ObservableList<Item> itemList=itemManagementService.getAllItems();
        ObservableList<Order> orderList=orderManagementService.getOrders();
        ObservableList<OrderDetail> orderDetailList=orderManagementService.getOrderDetails();
        for(Order order:orderList){
            for(OrderDetail orderDetail:orderDetailList){
                if(order.getOrderId().equals(orderDetail.getOrderId())){
                    Item item=orderManagementService.getItem(orderDetail.getItemCode());
                    ordersHistories.add(new OrdersHistory(
                            order.getOrderId(),
                            order.getOrderId(),
                            order.getOrderDate(),
                            item.getItemCode(),
                            item.getDescription(),
                            item.getPackSize(),
                            orderDetail.getOrderQty(),
                            (item.getUnitPrice()*orderDetail.getOrderQty())
                    ));
                }
            }
        }
        return ordersHistories;

    }
}

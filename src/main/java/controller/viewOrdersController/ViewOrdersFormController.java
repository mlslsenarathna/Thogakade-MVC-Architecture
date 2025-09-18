package controller.viewOrdersController;

import controller.itemsController.ItemManagementController;
import controller.itemsController.ItemManagementService;
import controller.orderController.OrderManagementController;
import controller.orderController.OrderManagementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.Item;
import model.dto.Order;
import model.dto.OrdersHistory;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewOrdersFormController implements Initializable {
    ItemManagementService itemManagementService=new  ItemManagementController();
    OrderManagementService orderManagementService=new OrderManagementController();
    ObservableList<OrdersHistory> ordersHistories= FXCollections.observableArrayList();
    ViewOrderController viewOrderController=new ViewOrderController();


    @FXML
    private Button btnBack;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    private TableColumn<?, ?> colCustID;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemID;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colPackageSize;
    
    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableView<OrdersHistory> tblOrderHistory;

    @FXML
    void btnBack(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        colCustID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackageSize.setCellValueFactory(new PropertyValueFactory<>("packageSize"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("orderQTY"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadOrdersHistory();
        setTotal();

    }

    private void loadOrdersHistory() {
        ObservableList<Item> itemList=itemManagementService.getAllItems();
        ObservableList<Order> orderList=orderManagementService.getOrders();
        ordersHistories=viewOrderController.getOrdersHistory();

        tblOrderHistory.setItems(ordersHistories);

    }

    private void setTotal(){
        ordersHistories=viewOrderController.getOrdersHistory();
        double totalprice=0;
        for(OrdersHistory orderhistory:ordersHistories){
            totalprice+=orderhistory.getPrice();
        }
       txtTotalPrice.setText("Rs."+String.valueOf(totalprice)+"0");


    }
}

package controller.orderController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Customer;
import model.dto.Item;
import model.dto.Order;
import model.dto.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderManagementController implements OrderManagementService {
    @Override
    public String getLastID() {
        try {
            Connection connection  = DBConnection.getInstance().getConnection();

            String SQL="SELECT OrderID FROM orders ORDER BY OrderID  DESC LIMIT 1;";
            PreparedStatement preparedStatement= connection.prepareStatement(SQL);
            ResultSet resultset= preparedStatement.executeQuery();
            return resultset.next() ? resultset.getString("OrderID") : null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double customerTotalValue(String custId) {
        double totalPrice=0;
        ObservableList<String> orderList=getOrdersInSameCustID(custId);

        for(String id :orderList){
            ObservableList<OrderDetail> orderDetailsList=getOrderDetailsInSameOrderID(id);
            for(OrderDetail orderDetail : orderDetailsList) {
                totalPrice+=(orderDetail.getOrderQty()*getItem(orderDetail.getItemCode()).getUnitPrice());

            }

        }



    return totalPrice;
    }


    @Override
    public Item getItem(String itemId){
        String SQL="SELECT*FROM item WHERE  ItemCode=?";
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            preparedStatement.setObject(1,itemId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                return new Item(resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                        );
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;


    }

    @Override
    public ObservableList<String> getItems() {
        ObservableList<String> itemList=FXCollections.observableArrayList();
        String SQL="SELECT ItemCode FROM item";
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                 itemList.add(resultSet.getString("ItemCode")
                       );
            }
            return itemList;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updatStockCount(String itemID,int sellQTY) {
        String SQL="UPDATE item SET QtyOnHand = ? WHERE ItemCode = ?";
        Item item=getItem(itemID);

        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            preparedStatement.setObject(1,(item.getQtyOnHand())-sellQTY);
            preparedStatement.setObject(2,itemID);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Order> getOrders() {
        ObservableList<Order> orderList=FXCollections.observableArrayList();
        String SQL="SELECT*FROM orders";
        //  |   |  |
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                orderList.add(new Order(
                        resultSet.getString("OrderID"),
                        resultSet.getDate("OrderDate").toLocalDate(),
                        resultSet.getString("CustID")
                ));
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<OrderDetail> getOrderDetails() {
        ObservableList<OrderDetail> orderDetailList=FXCollections.observableArrayList();
        String SQL="SELECT*FROM orderDetail";

        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                orderDetailList.add(new OrderDetail(
                        resultSet.getString("OrderID"),
                        resultSet.getString("ItemCode"),
                        resultSet.getInt("OrderQTY"),
                        resultSet.getDouble("Discount")
                ));
            }
            return orderDetailList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<OrderDetail> getOrderDetailsInSameOrderID(String orderId){
        ObservableList<OrderDetail> orderDetailsList=FXCollections.observableArrayList();

        orderDetailsList.clear();
        String SQL="SELECT*FROM orderDetail WHERE OrderID=?";
            try {
                Connection  connection = DBConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
                preparedStatement.setObject(1,orderId );
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                     orderDetailsList.add(new OrderDetail(
                            resultSet.getString("OrderID"),
                            resultSet.getString("ItemCode"),
                            resultSet.getInt("OrderQTY"),
                            resultSet.getDouble("Discount")
                    ));
                }
                return orderDetailsList;


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }


    @Override
    public ObservableList<String> getOrdersInSameCustID(String custId){
        ObservableList<String> orderList= FXCollections.observableArrayList();
        orderList.clear();
        String SQL="SELECT OrderID FROM orders WHERE custID=?";
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            preparedStatement.setObject(1,custId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){

                orderList.add(
                        resultSet.getString("OrderID")

                );

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderList;

    }
    @Override
    public Customer getCustomer(String customerID){
        String SQL="SELECT*FROM customer WHERE CustID=?";
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            preparedStatement.setObject(1,customerID);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                // CustID | CustTitle | CustName | DOB        | salary   | CustAddress     | City      | Province | PostalCode
                return new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("PostalCode")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }



}

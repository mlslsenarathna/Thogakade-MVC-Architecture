package controller.itemsController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Item;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemManagementController implements ItemManagementService{
    @Override
    public void addItemDetails(Item item) {
        String SQL = "Insert into item values(?,?,?,?,?)";
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);

            preparedStatement.setObject(1, item.getItemCode());
            preparedStatement.setObject(2, item.getDescription());
            preparedStatement.setObject(3, item.getPackSize());
            preparedStatement.setObject(4, item.getUnitPrice());
            preparedStatement.setObject(5,item.getQtyOnHand());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null,"Item Added SuccessFully..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void updateItemDetails(Item item) {

        String SQL = "UPDATE item SET  Description =?,PackSize=?,UnitPrice=?, QtyOnHand =? Where ItemCode=?;";
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);


            preparedStatement.setObject(1, item.getDescription());
            preparedStatement.setObject(2, item.getPackSize());
            preparedStatement.setObject(3, item.getUnitPrice());
            preparedStatement.setObject(4,item.getQtyOnHand());
            preparedStatement.setObject(5, item.getItemCode());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null,"Item Updated SuccessFully..");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Item> getAllItems() {

        ObservableList<Item> itemList= FXCollections.observableArrayList();
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT*FROM item");
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()){
                Item item=new Item(

                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                );
                itemList.add(item);
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void deleteItemDetails(String itemId) {


         String SQL="DELETE FROM item WHERE ItemCode= ?";

        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            preparedStatement.setObject(1,itemId);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Deleted SuccessFully!!!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getLastID() {
        try {
            Connection connection  = DBConnection.getInstance().getConnection();

            String SQL="SELECT ItemCode FROM Item ORDER BY ItemCode  DESC LIMIT 1;";
            PreparedStatement preparedStatement= connection.prepareStatement(SQL);
            ResultSet resultset= preparedStatement.executeQuery();
            return resultset.next() ? resultset.getString("ItemCode") : null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

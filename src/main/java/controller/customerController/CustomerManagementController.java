package controller.customerController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Customer;

import javax.swing.*;
import java.sql.*;

public class CustomerManagementController implements CustomerManagementService{
    @Override
    public void addCustomerDetails(Customer customer) {
        //|
        String SQL="INSERT INTO customer(CustID, CustTitle,CustName,DOB,salary, CustAddress, City , Province,PostalCode) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);

            preparedStatement.setObject(1,customer.getCustId());
            preparedStatement.setObject(2,customer.getTitle());
            preparedStatement.setObject(3,customer.getName());
            preparedStatement.setObject(4,customer.getDob());
            preparedStatement.setObject(5,customer.getSalary());
            preparedStatement.setObject(6,customer.getAddress());
            preparedStatement.setObject(7,customer.getCity());
            preparedStatement.setObject(8,customer.getProvince());
            preparedStatement.setObject(9,customer.getPostalCode());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Added SuccessFully!!!");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void updateCustomerDetails(Customer customer) {
        //
        String SQL="UPDATE customer SET CustTitle= ?, CustName = ?,DOB = ?, salary =?,CustAddress =?,  City= ?, Province =?,PostalCode =? WHERE CustID = ?";
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            preparedStatement.setObject(1,customer.getTitle());
            preparedStatement.setObject(2,customer.getName());
            preparedStatement.setObject(3,customer.getDob());
            preparedStatement.setObject(4,customer.getSalary());
            preparedStatement.setObject(5,customer.getAddress());
            preparedStatement.setObject(6,customer.getCity());
            preparedStatement.setObject(7,customer.getProvince());
            preparedStatement.setObject(8,customer.getPostalCode());
            preparedStatement.setObject(9,customer.getCustId());
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Updated  SuccessFully!!!");



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
       ObservableList<Customer> customerList= FXCollections.observableArrayList();
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("Select*from customer;");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                customerList.add(new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("PostalCode")
                ));
            }
            return customerList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void deleteCustomerDetails(String custID) {
        String SQL="DELETE FROM customer WHERE CustID = ?";

        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            preparedStatement.setObject(1,custID);
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
        String SQL="SELECT CustID FROM Customer ORDER BY CustID DESC LIMIT 1;";
        PreparedStatement preparedStatement= connection.prepareStatement(SQL);
        ResultSet resultset= preparedStatement.executeQuery();
        return resultset.next() ? resultset.getString("CustID") : null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}

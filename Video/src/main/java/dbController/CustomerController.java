package dbController;

import model.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerController {
    private Connection connection;

    public CustomerController(Connection connection){
        this.connection = connection;
    }

    public boolean insert(CustomerDTO customerDTO){
        String query = "INSERT INTO `customer`(`first_name`, `last_name`, `email`, `create_date`) VALUES(?,?,?,NOW())";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, customerDTO.getFirst_name());
            pstmt.setString(2,customerDTO.getLast_name());
            pstmt.setString(3, customerDTO.getEmail());


            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {

            return false;
        }
        return true;
    }

    public void update(CustomerDTO customerDTO){
        String query = "UPDATE `customer` SET `first_name`=?, `last_name` = ?, `last_update` = NOW() WHERE `customer_id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, customerDTO.getFirst_name());
            pstmt.setString(2,customerDTO.getLast_name());
            pstmt.setInt(3,customerDTO.getCustomer_id());

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id){
        String query = "DELETE FROM `customer`  WHERE `customer_id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<CustomerDTO> selectAll(){
        ArrayList<CustomerDTO> list = new ArrayList<>();

        String query = "SELECT * FROM `customer`  ORDER BY  `customer`.`customer_id` ASC ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                CustomerDTO c = new CustomerDTO();
                c.setCustomer_id(resultSet.getInt("customer_id"));
                c.setFirst_name(resultSet.getString("first_name"));
                c.setLast_name(resultSet.getString("last_name"));
                c.setEmail(resultSet.getString("email"));
                c.setCreate_date(resultSet.getTimestamp("create_date"));
                c.setLast_update(resultSet.getTimestamp("last_update"));

                list.add(c);
            }
            pstmt.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public CustomerDTO selectOne(int id){
        CustomerDTO c = null;
        String query = "SELECT * FROM `customer` WHERE `customer_id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet resultSet = pstmt.executeQuery();

            if(resultSet.next()){
                c = new CustomerDTO();
                c.setCustomer_id(resultSet.getInt("customer_id"));
                c.setFirst_name(resultSet.getString("first_name"));
                c.setLast_name(resultSet.getString("last_name"));
                c.setEmail(resultSet.getString("email"));
                c.setCreate_date(resultSet.getTimestamp("create_date"));
                c.setLast_update(resultSet.getTimestamp("last_update"));
            }
            pstmt.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println("해당 회원은 존재하지 않습니다.");
        }

        return c;
    }

    public ArrayList<CustomerDTO> selectName(String first_name, String last_name){
        ArrayList<CustomerDTO> list = new ArrayList<>();

        String query = "SELECT * FROM `customer`  WHERE `first_name` = ? AND`last_name` = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,first_name);
            pstmt.setString(2,last_name);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                CustomerDTO c = new CustomerDTO();
                c.setCustomer_id(resultSet.getInt("customer_id"));
                c.setFirst_name(resultSet.getString("first_name"));
                c.setLast_name(resultSet.getString("last_name"));
                c.setEmail(resultSet.getString("email"));
                c.setCreate_date(resultSet.getTimestamp("create_date"));
                c.setLast_update(resultSet.getTimestamp("last_update"));

                list.add(c);
            }
            pstmt.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<CustomerDTO> selectEmail(String email){
        ArrayList<CustomerDTO> list = new ArrayList<>();

        String query = "SELECT * FROM `customer`  WHERE `email` = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,email);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                CustomerDTO c = new CustomerDTO();
                c.setCustomer_id(resultSet.getInt("customer_id"));
                c.setFirst_name(resultSet.getString("first_name"));
                c.setLast_name(resultSet.getString("last_name"));
                c.setEmail(resultSet.getString("email"));
                c.setCreate_date(resultSet.getTimestamp("create_date"));
                c.setLast_update(resultSet.getTimestamp("last_update"));

                list.add(c);
            }
            pstmt.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}

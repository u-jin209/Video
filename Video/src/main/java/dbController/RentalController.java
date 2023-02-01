package dbController;


import model.RentalDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentalController {

    private Connection connection;

    public RentalController(Connection connection){
        this.connection = connection;
    }
    public boolean insert(RentalDTO rentalDTO) {
        String query ="";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);



            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    public ArrayList<RentalDTO> selectAll(){
        ArrayList<RentalDTO> list = new ArrayList<>();

        String query = "SELECT * FROM `rental`";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                RentalDTO r = new RentalDTO();


                list.add(r);
            }
            pstmt.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public RentalDTO selectOne(int id){
        RentalDTO  r = null;
        String query ="";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet resultSet = pstmt.executeQuery();

            if(resultSet.next()){
                r = new RentalDTO();


            }
            pstmt.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("해당 영화는 존재하지 않습니다.");
        }

        return r;
    }
}

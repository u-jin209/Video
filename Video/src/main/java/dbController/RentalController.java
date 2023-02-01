package dbController;

import model.MovieDTO;
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
                MovieDTO m = new MovieDTO();
                m.setFilm_id(resultSet.getInt("film_id"));
                m.setTitle(resultSet.getString("title"));
                m.setDescription(resultSet.getString("description"));
                m.setRelease_year(resultSet.getShort("release_year"));
                m.setLanguage_id(resultSet.getInt("language_id"));
                m.setRental_duration(resultSet.getInt("rental_duration"));
                m.setRental_rate(resultSet.getInt("rental_rate"));
                m.setLength(resultSet.getInt("length"));
                m.setRating(resultSet.getString("rating"));
                m.setRating(resultSet.getString("special_features"));
                m.setLast_update(resultSet.getTimestamp("last_update"));
                m.setActor_Name(resultSet.getString("actor_name"));


                list.add(m);
            }
            pstmt.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public MovieDTO selectOne(int id){
        RentalDTO  r = null;
        String query =
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet resultSet = pstmt.executeQuery();

            if(resultSet.next()){
                m = new MovieDTO();

                m.setTitle(resultSet.getString("title"));
                m.setDescription(resultSet.getString("description"));
                m.setRelease_year(resultSet.getShort("release_year"));
                m.setLanguage_id(resultSet.getInt("language_id"));
                m.setRental_duration(resultSet.getInt("rental_duration"));
                m.setRental_rate(resultSet.getInt("rental_rate"));
                m.setLength(resultSet.getInt("length"));
                m.setRating(resultSet.getString("rating"));
                m.setRating(resultSet.getString("special_features"));
                m.setLast_update(resultSet.getTimestamp("last_update"));
                m.setActor_Name(resultSet.getString("actor_name"));

            }
            pstmt.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("해당 영화는 존재하지 않습니다.");
        }

        return m;
    }
}

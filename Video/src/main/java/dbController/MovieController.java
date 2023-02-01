package dbController;



import model.MovieDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieController {

    private Connection connection;

    public MovieController(Connection connection){
        this.connection = connection;
    }
    public boolean insert(MovieDTO movieDTO){
        String query = "INSERT INTO `film`(`title`, `description`, `release_year`,`rental_duration`,`rental_rate`,`rating`," +
                "`special_features`,`language_id`,`last_update`) VALUES(?,?,?,?,?,?,?,1, NOW())" ;



        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, movieDTO.getTitle());
            pstmt.setString(2,movieDTO.getDescription());
            pstmt.setShort(3, movieDTO.getRelease_year());
            pstmt.setInt(4, movieDTO.getRental_duration());
            pstmt.setInt(5, movieDTO.getRental_rate());
            pstmt.setString(6, movieDTO.getRating());
            pstmt.setString(7,movieDTO.getSpecial_features());

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertActor(MovieDTO movieDTO){
        String query = "INSERT INTO  `actor`(`first_name`,`last_name`)VALUES (?,?)";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1,movieDTO.getFirst_name());
            pstmt.setString(2,movieDTO.getLast_name());


            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean insertFilmActor(MovieDTO movieDTO){
        String query = "INSERT INTO  `film_actor`(`actor_id`,`film_id`)VALUES (?,?)";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1,movieDTO.getActor_id());
            pstmt.setInt(2,movieDTO.getFilm_id());


            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void update(MovieDTO movieDTO){
        String query = "UPDATE `film`" +
                " SET `title` = ?, `description` =?, `release_year`=?," +
                "`rental_duration`=?,`rental_rate`=?,`rating`=?  ,`special_features`=?," +
                "`last_update` = NOW() WHERE `film_id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, movieDTO.getTitle());
            pstmt.setString(2,movieDTO.getDescription());
            pstmt.setInt(3,movieDTO.getRelease_year());
            pstmt.setInt(4,movieDTO.getRental_duration());
            pstmt.setInt(5,movieDTO.getRental_rate());
            pstmt.setString(6,movieDTO.getRating());
            pstmt.setString(7,movieDTO.getSpecial_features());
            pstmt.setInt(8,movieDTO.getFilm_id());



            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int id){
        String query = "DELETE FROM `film`  WHERE `film_id` = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<MovieDTO> selectAll(){
        ArrayList<MovieDTO> list = new ArrayList<>();

        String query = "SELECT `film`.* , group_concat(first_name,\" \",last_name) as actor_name FROM `film`" +
                "JOIN `film_actor` ON  `film`.`film_id` = `film_actor`.`film_id`" +
                "JOIN `actor` ON  `film_actor`.`actor_id` = `actor`.`actor_id` " +
                "group by  `film`.`film_id`  ORDER BY  `film`.`film_id` ASC ";

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
        MovieDTO m = null;
        String query = "SELECT `film`.* , group_concat(first_name,\" \",last_name) as actor_name FROM `film` " +
                "JOIN `film_actor` ON  `film`.`film_id` = `film_actor`.`film_id`" +
                "JOIN `actor` ON  `film_actor`.`actor_id` = `actor`.`actor_id` " +
                "WHERE `film`.`film_id` = ? group by `film`.`film_id` ";
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


    public ArrayList<MovieDTO> selectTitle(String title) {
        ArrayList<MovieDTO> list = new ArrayList<>();

        String query = "SELECT `film`.* , group_concat(first_name,\" \",last_name) as actor_name FROM `film` " +
                "JOIN `film_actor` ON  `film`.`film_id` = `film_actor`.`film_id`" +
                "JOIN `actor` ON  `film_actor`.`actor_id` = `actor`.`actor_id` " +
                "WHERE  `title` = ?"+
                "group by  `film`.`film_id`  ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,title);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                MovieDTO m = new MovieDTO();
                m.setFilm_id(resultSet.getInt("film_id"));
                m.setTitle(resultSet.getString("title"));
                m.setDescription(resultSet.getString("description"));
                m.setRelease_year(resultSet.getShort("release_year"));
                m.setRental_duration(resultSet.getInt("Rental_duration"));
                m.setRental_rate(resultSet.getInt("rental_rate"));
                m.setRating(resultSet.getString("rating"));
                m.setSpecial_features(resultSet.getString("special_features"));
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

    public ArrayList<MovieDTO> selectName(String actor_name) {
        ArrayList<MovieDTO> list = new ArrayList<>();

        String query = "SELECT `film`.* , group_concat(first_name,\" \",last_name) as actor_name FROM `film` " +
                "JOIN `film_actor` ON  `film`.`film_id` = `film_actor`.`film_id`" +
                "JOIN `actor` ON  `film_actor`.`actor_id` = `actor`.`actor_id` " +
                "group by  `film`.`film_id`  "+"HAVING `actor_name` Like ? "
              ;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,"%"+actor_name+"%");



            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                MovieDTO m = new MovieDTO();
                m.setFilm_id(resultSet.getInt("film_id"));
                m.setTitle(resultSet.getString("title"));
                m.setDescription(resultSet.getString("description"));
                m.setRelease_year(resultSet.getShort("release_year"));
                m.setRental_duration(resultSet.getInt("Rental_duration"));
                m.setRental_rate(resultSet.getInt("rental_rate"));
                m.setRating(resultSet.getString("rating"));
                m.setSpecial_features(resultSet.getString("special_features"));
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

    public ArrayList<MovieDTO> selectActor(String first_name, String last_name) {
        ArrayList<MovieDTO> list = new ArrayList<>();

        String query = "SELECT * FROM `actor` where `first_name` =? And `last_name` = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,first_name);
            pstmt.setString(2,last_name);



            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                MovieDTO m = new MovieDTO();

                m.setFirst_name(resultSet.getString("first_name"));
                m.setLast_name(resultSet.getString("last_name"));
                list.add(m);
            }
            pstmt.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getFilmId() {

        String query = "SELECT `film_id` FROM `film_actor` order by `film_id` desc LIMIT 1";
        int filmid =-1;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);


            ResultSet resultSet = pstmt.executeQuery();


            while (resultSet.next()){
                filmid = resultSet.getInt("film_id");
            }
            pstmt.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return filmid;

    }

    public int getActorId(){
        String query = "SELECT `actor_id` FROM `actor` order by `actor_id` desc LIMIT 1";
        int actorId =-1;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);


            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                actorId = resultSet.getInt("actor_id");
            }pstmt.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return actorId;
    }

}

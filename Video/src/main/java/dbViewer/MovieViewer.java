package dbViewer;

import dbController.MovieController;
import day01.VideoMVC;
import dbConn.ConnectionMaker;


import model.MovieDTO;
import util.ScannerUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieViewer {

    private final Scanner SCANNER;
    private Connection connection;

    private String[] ratingList = {"G", "PG", "PG-13", "R", "NC-17"};
    private String[] special_featuresList={"Trailers", "Commentaries", "Deleted Scenes", "Behind the Scenes"};

    private String[] category ={"Action","Animation","Children","Classics","Comedy","Documentary",
            "Drama","Family","Foreign","Games","Horror","Music","New","Sci-Fi","Sports","Travel"};

    public MovieViewer(ConnectionMaker connectionMaker) {
        SCANNER = new Scanner(System.in);
        connection = connectionMaker.makeConnection();

    }

    public void showIndex() {
        System.out.println("= 영화 LIST ============================================");
        printAll();
        System.out.println("=======================================================");
        String message = "1. 영화 등록  2. 영화 수정  3. 영화 삭제  4. 영화 검색 5. 뒤로 가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message,1,5);
            int choiceId;
            if (userChoice == 1) {
                register();
                showIndex();
            } else if (userChoice == 2) {
                message = " 수정할 영화의 번호를 입력하세요. ";
                choiceId = ScannerUtil.nextInt(SCANNER, message);

                modify(choiceId);
                showIndex();

            } else if (userChoice ==3) {
                message = " 삭제할 영화의 번호를 입력하세요. ";
                choiceId = ScannerUtil.nextInt(SCANNER, message);
                delete(choiceId);

                showIndex();

            } else if (userChoice == 4) {
                searchMenu();

            } else if (userChoice == 5) {

                VideoMVC.mainMenu();

            }
        }
    }

    private void searchMenu() {

        String message = "1. 제목으로 검색  2. 배우 이름으로 검색  3. 카테고리로 검색  4. 뒤로가기";

        while (true) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message,1,4);
            if(userChoice ==1 || userChoice ==2 || userChoice ==3){
                printMovie(userChoice);

            } else if (userChoice==4) {
                showIndex();
            }
        }
    }

    private void printMovie(int mode) {
        ArrayList<MovieDTO> list = null;

        if(mode ==1) {
            String message = " 제목을 입력하세요 ";
            String title = ScannerUtil.nextLine(SCANNER, message);

            MovieController movieController = new MovieController(connection);
            list = movieController.selectTitle(title);

        } else if (mode ==2) {
            String message = " 배우이름을 입력하세요 ";
            String name = ScannerUtil.nextLine(SCANNER, message);

            MovieController movieController = new MovieController(connection);
            list = movieController.selectName(name);
        } else if (mode ==3) {
            String message = "카테고리를 입력해 주세요\n" +
                    "1. Action  2. Animation  3. Children  4. Classics  5. Comedy  6. Documentary  7. Drama  8. Family\n" +
                    "9. Foreign  10. Games  11. Horror  12. Music  13. New  14. Sci-Fi  15. Sports 16. Travel";
            String Category = category[ScannerUtil.nextInt(SCANNER,message,1,16)-1];


            MovieController movieController = new MovieController(connection);
            list = movieController.selectCategory(Category);

        }
        if (!list.isEmpty()){
            for (MovieDTO m : list){

                System.out.printf("=영화 정보==================================================================================== NO. %d ==\n",m.getFilm_id());
                System.out.printf("영화 제목 : %s  개봉 년도 : %d 영화 등급 : %s 카테고리 : %s\n" +
                                "출연 배우 : %s\n" +
                        "대여 가능 기간 : %d 대여 비용 : %d \n" +
                                "special_features : %s\n",
                         m.getTitle(),m.getRelease_year(),m.getRating(),m.getCategory(),m.getActor_Name(),
                        m.getRental_duration(), m.getRental_rate(),m.getSpecial_features());
                System.out.println("=====================================================================================================");
            }
        }else {
            System.out.println("해당하는 영화를 찾을 수 없습니다.");
        }
    }

    private void delete(int id) {

        String message = "정말로 삭제하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(SCANNER, message);

        if (yesNo.equalsIgnoreCase("Y")) {

            MovieController movieController =new MovieController(connection);
            if (movieController.selectOne(id) != null) {
                movieController.delete(id);
                System.out.println("삭제가 완료되었습니다.");
            }
        }
    }


    private void modify(int id) {

        String message;

        message ="바꿀 영화 제목을 입력해 주세요";
       String title = ScannerUtil.nextLine(SCANNER,message);

        message ="바꿀 영화 줄거리를 입력해 주세요";
        String description = ScannerUtil.nextLine(SCANNER,message);

        message = "바꿀 개봉 년도를 입력해주세요";
        Short year = (short)ScannerUtil.nextInt(SCANNER,message);

        message = "바꿀 대여 기간을 입력해주세요";
        int Rental_duration =ScannerUtil.nextInt(SCANNER,message);
        message = "바꿀 대여 비용을 입력해주세요";
        int Rental_rate = ScannerUtil.nextInt(SCANNER,message);

        message = "바꿀 등급을 입력해 주세요\n" +
                "1. G   2. PG  3. PG-13  4. R  5. NC-17";
        String Rating = ratingList[ScannerUtil.nextInt(SCANNER,message,1,5)-1];


        message = "카테고리를 입력해 주세요\n" +
                "1.Action  2. Animation  3. Children  4. Classics  5. Comedy  6. Documentary  7. Drama  8. Family\n" +
                "9. Foreign  10. Games  11. Horror  12. Music  13. New  14. Sci-Fi  15. Sports 16. Travel";
        int category_id = ScannerUtil.nextInt(SCANNER,message,1,16) ;

        String Category = category[category_id-1];


        String special_features ="";

        for(int i =0; i < special_featuresList.length;i++){
            message = "- 바꿀 special_features 입력 -\n" +special_featuresList[i]+"를 등록하시겠습니까? (Y/N) ";
            String yesNo = ScannerUtil.nextLine(SCANNER,message);
            if (yesNo.equalsIgnoreCase("Y")){
                if(i !=0){
                    special_features = special_features+","+special_featuresList[i] ;
                }else {
                    special_features += special_featuresList[i];
                }
            }
        }

        MovieController movieController = new MovieController(connection);
        if (movieController.selectOne(id) != null){
            MovieDTO m = new MovieDTO();
            m.setFilm_id(id);
            m.setTitle(title);
            m.setDescription(description);
            m.setRelease_year(year);
            m.setRental_duration(Rental_duration);
            m.setRental_rate(Rental_rate);
            m.setRating(Rating);
            m.setCategory(Category);
            m.setCategory_id(category_id);
            m.setSpecial_features(special_features);

            movieController.update(m);
            System.out.println("성공적으로 영화 정보를 수정하였습니다.");
        }else {
            System.out.println("영화 정보 변경에 실패하였습니다");

        }
    }

    private void register() {
        MovieDTO m = new MovieDTO();
        MovieController movieController = new MovieController(connection);
        String message;

        message ="영화 제목을 입력해 주세요";
        m.setTitle(ScannerUtil.nextLine(SCANNER,message));

        message ="영화 줄거리를 입력해 주세요";
        m.setDescription(ScannerUtil.nextLine(SCANNER,message));

        message = "개봉 년도를 입력해주세요";
        m.setRelease_year((short) ScannerUtil.nextInt(SCANNER,message));

        message = "대여 기간을 입력해주세요";
        m.setRental_duration( ScannerUtil.nextInt(SCANNER,message));
        message = "대여 비용을 입력해주세요";

        int rate =-1;
        while(rate==-1){
            rate = ScannerUtil.nextInt(SCANNER,message);
        }
        m.setRental_rate(rate);


        message = "등급을 입력해 주세요\n" +
                "1. G   2. PG  3. PG-13  4. R  5. NC-17";
        m.setRating(ratingList[ScannerUtil.nextInt(SCANNER,message,1,5)-1]);


        message = "카테고리를 입력해 주세요\n" +
                "1.Action  2. Animation  3. Children  4. Classics  5. Comedy  6. Documentary  7. Drama  8. Family\n" +
                "9. Foreign  10. Games  11. Horror  12. Music  13. New  14. Sci-Fi  15. Sports 16. Travel";
        int category_id = ScannerUtil.nextInt(SCANNER,message,1,16) ;
        m.setCategory(category[category_id-1]);
        m.setCategory_id(category_id);





        String special_features ="";

        for(int i =0; i < special_featuresList.length;i++){
            message = "- special_features 입력 -\n" +special_featuresList[i]+"를 등록하시겠습니까? (Y/N) ";
            String yesNo = ScannerUtil.nextLine(SCANNER,message);
            if (yesNo.equalsIgnoreCase("Y")){
                if(i != 0){
                    special_features = special_features+","+special_featuresList[i] ;
                }else {
                    special_features += special_featuresList[i];
                }
            }else {
                special_features +="";
            }
        }

        m.setSpecial_features(special_features);

        String actors = "";
        while (true){
            message = "배우의 성을 입력해주세요";
            String first_name = ScannerUtil.nextLine(SCANNER,message);

            message = "배우의 이름을 입력해주세요";
            String last_name = ScannerUtil.nextLine(SCANNER,message);

            actors =first_name+" "+last_name + " ";

            if (movieController.selectActor(first_name,last_name) != null){
                m.setFirst_name(first_name);
                m.setLast_name(last_name);
                m.setFilm_id(movieController.getFilmId()+1);
                m.setActor_id(movieController.getActorId()+1);


                if (!movieController.insertActor(m) ){
                    System.out.println("새로운 배우 등록에 실패하였습니다.");
                    message =" 배우를 다시 추가 하시겠습니까? Y/N";
                    String yesNo = ScannerUtil.nextLine(SCANNER,message);
                    if (!yesNo.equalsIgnoreCase("Y")){
                        continue;
                    }
                }

            }

            message =" 배우를 추가 하시겠습니까? Y/N";
            String yesNo = ScannerUtil.nextLine(SCANNER,message);
            if (!yesNo.equalsIgnoreCase("Y")){
                break;
            }
        }
        m.setActor_Name(actors);


        if(!movieController.insert(m)){
            System.out.println("새로운 영화 등록에 실패하였습니다\n");
            message= "새로 등록을 시도하시겠습니까? Y/N";
            String yesNo = ScannerUtil.nextLine(SCANNER, message);
            if (yesNo.equalsIgnoreCase("Y")){
                register();
            }
        }
        movieController.insertFilmActor(m);
        movieController.insertCategory(m);
    }

    private void printAll() {

        MovieController movieController= new MovieController(connection);
        ArrayList<MovieDTO> list = movieController.selectAll();

        for(MovieDTO m:list){

            System.out.printf("%d. %s\n",m.getFilm_id(),m.getTitle());
        }
    }

}

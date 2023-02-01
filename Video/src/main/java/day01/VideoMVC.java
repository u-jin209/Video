package day01;

import dbConn.ConnectionMaker;
import dbConn.MySqlConnectionMaker;
import dbViewer.CustomerViewer;
import dbViewer.MovieViewer;
import dbViewer.RentalViewer;
import util.ScannerUtil;

import java.util.Scanner;

public class VideoMVC {

    private static Scanner scanner;
    private static ConnectionMaker connectionMaker= new MySqlConnectionMaker();
    private static CustomerViewer customerViewer=new CustomerViewer(connectionMaker);
    private static MovieViewer movieViewer = new MovieViewer(connectionMaker);
    private static RentalViewer rentalViewer = new RentalViewer(connectionMaker);

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        mainMenu();


    }

    public static void mainMenu(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            String message =" 1. 고객 관리 2. 비디오 관리 3. 대여/반납 4. 프로그램 종료";
            int choice= ScannerUtil.nextInt(scanner,message,1,4);

            if (choice == 1){
                customerViewer.showIndex();
            } else if (choice ==2) {
                movieViewer.showIndex();
            } else if (choice ==3) {
                rentalViewer.showIndex();
            } else if (choice == 4) {
                System.out.println("사용해주셔서 감사합니다.");
                break;
            }
        }
        scanner.close();

    }
}

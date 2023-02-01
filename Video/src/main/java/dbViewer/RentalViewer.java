package dbViewer;

import day01.VideoMVC;
import dbConn.ConnectionMaker;
import util.ScannerUtil;

import java.sql.Connection;
import java.util.Scanner;

public class RentalViewer {
    private final Scanner SCANNER;
    private Connection connection;

    public RentalViewer(ConnectionMaker connectionMaker) {
        SCANNER = new Scanner(System.in);
        connection = connectionMaker.makeConnection();
    }

    public void showIndex() {
        String message = "1. 대여  2. 반납 3. 뒤로 가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message,1,5);
            int choiceId;
            if (userChoice == 1) {

            } else if (userChoice == 2) {


            } else if (userChoice == 3) {

                VideoMVC.mainMenu();

            }
        }
    }
}

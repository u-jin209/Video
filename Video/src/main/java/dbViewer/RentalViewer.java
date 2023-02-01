package dbViewer;

import day01.VideoMVC;
import dbConn.ConnectionMaker;
import dbController.CustomerController;
import model.CustomerDTO;
import model.RentalDTO;
import util.ScannerUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class RentalViewer {
    private final Scanner SCANNER;
    private Connection connection;
    private CustomerViewer customerViewer;

    public RentalViewer(ConnectionMaker connectionMaker) {
        SCANNER = new Scanner(System.in);
        connection = connectionMaker.makeConnection();
        customerViewer = new CustomerViewer(connectionMaker);
    }

    public void showIndex() {
        String message = "1. 대여  2. 반납 3. 뒤로 가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message,1,5);
            int choiceId;
            if (userChoice == 1) {
                rentalVideo();

            } else if (userChoice == 2) {

                returnVideo();
            } else if (userChoice == 3) {

                VideoMVC.mainMenu();

            }
        }
    }

    private void returnVideo() {
    }

    private void rentalVideo() {
        RentalDTO r = new RentalDTO();

        String message = "대여할 회원을 검색 하세요.\n" +
                "1. 이름으로 검색  2. 이메일로 검색  3. 뒤로 가기";

        while (true) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message,1,3);
            if(userChoice ==1 || userChoice ==2){
                if(!customerViewer.printCustomer(userChoice)){
                    message = "다시 검색하시겠습니까? Y/N";
                    String yesNo = ScannerUtil.nextLine(SCANNER, message);
                    if(yesNo.equalsIgnoreCase("Y")){
                        customerViewer.printCustomer(userChoice);
                    }else{
                        showIndex();
                    }
                }else {
                    message = "대여할 회원의 회원 번호를 입력하세요";
                    r.setCustomer_id(ScannerUtil.nextInt(SCANNER,message));



                }




            } else if (userChoice==3) {
                showIndex();
            }
        }



    }
}

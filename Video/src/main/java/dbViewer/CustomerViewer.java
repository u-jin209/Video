package dbViewer;

import day01.VideoMVC;
import dbConn.ConnectionMaker;
import dbController.CustomerController;
import model.CustomerDTO;
import util.ScannerUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerViewer {
    private final Scanner SCANNER;

    private Connection connection;

    public CustomerViewer(ConnectionMaker connectionMaker) {
        SCANNER = new Scanner(System.in);
        connection = connectionMaker.makeConnection();
    }

    public void showIndex() {
        System.out.println("= 회원 LIST ============================================");
        printAll();
        System.out.println("======================================================");
        String message = "1. 회원 등록  2. 회원 수정  3. 회원 삭제  4. 회원 검색 5. 뒤로 가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message,1,5);
            int choiceId;
            if (userChoice == 1) {
                register();
                showIndex();
            } else if (userChoice == 2) {
                message = " 수정할 회원의 번호를 입력하세요. ";
                choiceId = ScannerUtil.nextInt(SCANNER, message);

                modify(choiceId);
                System.out.println("수정이 완료되었습니다.");
                showIndex();

            } else if (userChoice ==3) {
                message = " 삭제할 회원의 번호를 입력하세요. ";
                choiceId = ScannerUtil.nextInt(SCANNER, message);
                delete(choiceId);
                System.out.println("삭제가 완료되었습니다.");
                showIndex();

            } else if (userChoice == 4) {
                searchMenu();

            } else if (userChoice == 5) {

                VideoMVC.mainMenu();


            }
        }
    }

    private void searchMenu() {
        String message = "1. 이름으로 검색  2. 이메일로 검색  3. 뒤로 가기";

        while (true) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message,1,3);
            if(userChoice ==1 || userChoice ==2){
                printCustomer(userChoice);

            } else if (userChoice==3) {
                showIndex();
            }
        }
    }

    private void printCustomer(int mode) {
        ArrayList<CustomerDTO> list = null;

        if (mode ==1){
            String message = " 회원의 성을 입력하세요 ";
            String f_name = ScannerUtil.nextLine(SCANNER, message);

            message = " 회원의 이름을 입력하세요 ";
            String l_name = ScannerUtil.nextLine(SCANNER, message);

            CustomerController customerController =new CustomerController(connection);
            list = customerController.selectName(f_name,l_name);

        }else {
            String message = " 회원의 이메일을 입력하세요 ";
            String email = ScannerUtil.nextLine(SCANNER, message);

            CustomerController customerController =new CustomerController(connection);
            list = customerController.selectEmail(email);

        }

        if (!list.isEmpty()){
            for (CustomerDTO c : list){
                System.out.println("=회원 정보=============================");
                System.out.printf("회원 번호 : %d  이름 : %s%s  \n" +
                        "이메일 : %s\n",c.getCustomer_id(), c.getFirst_name(),c.getLast_name(),c.getEmail());
                System.out.println("=======================================");
            }
        }else {
            System.out.println("회원을 찾을 수 없습니다.");
        }




    }

    private void delete(int id) {

        String message = "정말로 삭제하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(SCANNER, message);

        if (yesNo.equalsIgnoreCase("Y")) {

            CustomerController customerController =new CustomerController(connection);
            if (customerController.selectOne(id) != null) {
                customerController.delete(id);
            }
        }


    }

    private void modify(int id) {

        String message = "새로운 성을 입력해주세요.";
        String newFirstname = ScannerUtil.nextLine(SCANNER, message);

        message = "새로운 이름을 입력해주세요.";
        String newLastname = ScannerUtil.nextLine(SCANNER, message);


        CustomerController customerController =new CustomerController(connection);
        if (customerController.selectOne(id) != null) {
            CustomerDTO c = new CustomerDTO();
            c.setCustomer_id(id);
            c.setFirst_name(newFirstname);
            c.setLast_name(newLastname);

            customerController.update(c);
        } else {
            System.out.println("회원 정보 변경에 실패하였습니다.");
        }

    }

    private void register() {

        CustomerDTO c =new CustomerDTO();
        String message;

        message = "사용자의 성을 입력해주세요.";
        c.setFirst_name(ScannerUtil.nextLine(SCANNER, message));

        message = "사용자의 이름을 입력해주세요.";
        c.setLast_name(ScannerUtil.nextLine(SCANNER, message));

        message = "사용자의 이메일을 입력해주세요.";
        c.setEmail(ScannerUtil.nextLine(SCANNER, message));

        CustomerController customerController =new CustomerController(connection);
        if(!customerController.insert(c)){
            System.out.println("중복된 이메일 입니다.\n");
            message= "새로운 이메일로 등록을 시도하시겠습니까? Y/N";
            String yesNo = ScannerUtil.nextLine(SCANNER, message);
            if (yesNo.equalsIgnoreCase("Y")){
                register();
            }
        }

    }

    public void printAll(){
        CustomerController customerController = new CustomerController(connection);
        ArrayList<CustomerDTO> list = customerController.selectAll();

        for(CustomerDTO c:list){

            System.out.printf("%d. %s%s\n",c.getCustomer_id(),c.getFirst_name(),c.getLast_name());
        }
    }
}

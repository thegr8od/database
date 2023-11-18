package DB;

import java.io.*;
import java.sql.*;


import java.util.Calendar;
import java.util.Random;


public class APPLICATION {
    protected static void MyPage(String id, boolean role) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        // 사용자 정보를 데이터베이스에서 가져오기
        try {
            ResultSet rsUser = SQLx.Selectx("*", "USERS", "ID_NUMBER = '" + id + "'", "");
            ResultSet rsMember = SQLx.Selectx("*", "MEMBER", "ID_NUMBER = '" + id + "'", "");

            if (rsUser.next() && rsMember.next()) { // 사용자 정보가 있다면
                // 사용자 정보 출력
                System.out.println("----------------------------------------------------");
                System.out.println("User Info:");
                System.out.println("ID: " + rsUser.getString("ID_NUMBER"));
                System.out.println("Sex: " + rsUser.getString("SEX"));
                System.out.println("Year of Birth: " + rsUser.getString("YOB"));
                System.out.println("Job: " + rsUser.getString("JOB"));
                System.out.println("Cash: " + rsMember.getInt("PREPAID_MONEY"));
            } else {
                System.out.println("----------------------------------------------------");
                System.out.println("No user information available.");
                System.out.println("----------------------------------------------------");
            }
            rsUser.close();
            rsMember.close();
        } catch (SQLException e) {
            System.out.println("An error occurred while fetching user information.");
            e.printStackTrace();
        }

        while (true) {
            System.out.println("----------------------------------------------------");
            System.out.println("1. Change My Info");
            System.out.println("2. Cash Charge");
            System.out.println("3. Secession");
            System.out.println("4. Exit");
            System.out.println("----------------------------------------------------");
            System.out.print("Enter the number: ");


            try {
                int opt = Integer.parseInt(bf.readLine());
                switch (opt) {
                    case 1:
                        System.out.println("----------------------------------------------------");
                        System.out.println("Choose an option to update:");
                        System.out.println("1. Password");
                        System.out.println("2. Sex");
                        System.out.println("3. Year of Birth");
                        System.out.println("4. Job");
                        System.out.println("----------------------------------------------------");
                        System.out.print("select your choice (1-4): ");
                        int changeOption = Integer.parseInt(bf.readLine());


                        String newValue1, newValue2;
                        do {
                            System.out.println("----------------------------------------------------");
                            System.out.print("Enter the new value for the chosen option: ");
                            newValue1 = bf.readLine();
                            System.out.println("----------------------------------------------------");
                            System.out.print("Re-enter the new value to confirm: ");
                            newValue2 = bf.readLine();
                            //무결성 처리 해야함
                            if (!newValue1.equals(newValue2)) {
                                System.out.println("The entered values do not match. Please try again.");
                            }
                        } while (!newValue1.equals(newValue2));
                        ChangeMyInfo(id, changeOption, newValue1);
                        break;
                    case 2:
                        System.out.println("----------------------------------------------------");
                        System.out.print("Enter the amount to charge: ");
                        try {
                            int amount = Integer.parseInt(bf.readLine());
                            CashCharge(id, amount);
                            System.out.println("----------------------------------------------------");
                            System.out.println("Charge completed successfully.");
                        } catch (NumberFormatException e) {
                            System.out.println("----------------------------------------------------");
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                        break;
                    case 3:
                        System.out.println("----------------------------------------------------");
                        System.out.println("Are you sure you want to delete your account? Type 'I want to delete my account' to confirm.");
                        System.out.println("----------------------------------------------------");
                        String confirmation = bf.readLine();
                        System.out.print("Type : ");
                        if ("I want to delete my account".equals(confirmation)) {
                            Secession(id);
                            System.out.println("Your account has been successfully deleted.");
                            return; // 탈퇴 후 메뉴 종료
                        } else {
                            System.out.println("Account deletion cancelled.");
                        }
                        break;
                    case 4:
                        System.out.println("Exiting MyPage...");
                        return; // 루프 종료
                    default:
                        System.out.println("Wrong number! Re-enter.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading from input.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    protected static void UserEval(String id) {
        // UPDATE AND INSERT MANAGER -> MEMBER EVALUATION
    }

    protected static void Screen(String id, boolean role, int opt) {
        // false -> Manager, true -> Member
        // opt = 2. Training, 3. Match, 4. Team
        // TRAINING, MATCH, TEAM relation control
        Make();
        Cancel();
        Delete();
        Apply();
    }

    private static void ChangeMyInfo(String userId, int option, String newValue) {
        String targetField;
        boolean isValidInput = true; // 입력 값이 유효한지 확인하는 플래그

        // 입력 값 검증
        switch (option) {
            case 1: // Password
                targetField = "PASSWD";
                if (newValue.length() < 10) {
                    System.out.println("Invalid password. It must be at least 10 characters long.");
                    isValidInput = false;
                }
                break;
            case 2: // Sex
                targetField = "SEX";
                if (!newValue.equals("M") && !newValue.equals("F")) {
                    System.out.println("Invalid sex. Please enter 'M' for Male or 'F' for Female.");
                    isValidInput = false;
                }
                break;
            case 3: // Year of Birth
                targetField = "YOB";
                try {
                    int yearOfBirth = Integer.parseInt(newValue);
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    if (yearOfBirth < 1900 || yearOfBirth >= currentYear) {
                        System.out.println("Invalid Year of Birth. Please enter a year between 1900 and " + (currentYear - 1) + ".");
                        isValidInput = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Year of Birth. Please enter a valid year.");
                    isValidInput = false;
                }
                break;
            case 4: // Job
                targetField = "JOB";
                // Job 필드에 대한 추가적인 유효성 검사가 필요하다면 여기에 로직을 추가하세요.
                break;
            default:
                System.out.println("Invalid option");
                return;
        }

        if (!isValidInput) {
            return; // 유효하지 않은 입력이면 업데이트를 중단합니다.
        }

        // 데이터베이스 업데이트
        try {
            String[] key = {userId}; // 키 배열에는 변경할 사용자 ID를 지정
            SQLx.Updatex("USERS", targetField, newValue, key); // 업데이트 실행
            System.out.println(targetField + " updated for user: " + userId);
        } catch (SQLException e) {
            System.out.println("Error updating " + targetField + " for user: " + userId);
            e.printStackTrace();
        }
    }

    private static void CashCharge(String memberId, int amount) {
        try {
            // 기존의 PREPAID_MONEY 값을 먼저 조회
            ResultSet rs = SQLx.Selectx("PREPAID_MONEY", "MEMBER", "ID_NUMBER = '" + memberId + "'", "");
            if (rs.next()) {
                int currentAmount = rs.getInt("PREPAID_MONEY");
                int newAmount = currentAmount + amount;

                // key 배열에는 타겟이 되는 행의 기본키나 조건을 지정합니다.
                String[] key = {memberId};
                // SQLx 클래스의 Updatex 메소드를 사용하여 PREPAID_MONEY 업데이트
                SQLx.Updatex("MEMBER", "PREPAID_MONEY", String.valueOf(newAmount), key);
                System.out.println("Your new prepaid money balance is: " + newAmount + " units.");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// ONLY FOR USER, UPDATE PREPAID_MONEY

    private static void Secession(String userId) {
        try {
            // key 배열에는 삭제할 행의 기본키나 조건을 지정합니다.
            String[] key = {userId};
            // SQLx 클래스의 Deletex 메소드를 사용하여 사용자 삭제
            SQLx.Deletex("USERS", key);
            System.out.println("User deleted: " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// DELETE USER ON CASCADE

    private static void Make() {

    } // insert entity rela on cascade

    private static void Cancel() {

    } // delete relationship rela -> PREPAID_MONEY CHANGE REFELCT

    private static void Delete() {

    } // delete entitiy rela on cascade

    private static void Apply() {

    } // insert relationship rela -> PREPAID_MONEY CHANGE REFELCT

    private static void Check() {
    } // select where id_num in ~ ex) my teams, my matches
}
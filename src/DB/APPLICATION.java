package DB;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.Calendar;
import java.util.Random;


public class APPLICATION {
    protected static void MyPage(String id, boolean role) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        // 사용자 정보를 데이터베이스에서 가져오기

        while (true) {
            System.out.println("----------------------------------------------------");
            if (role) { // 멤버일 경우
                System.out.println("1. Change My Info");
                System.out.println("2. Cash Charge");
                System.out.println("3. Check My Info");
                System.out.println("4. Check My Team");
                System.out.println("5. Secession");
                System.out.println("6. Exit");
            } else { // 매니저일 경우
                System.out.println("1. Change My Info");
                System.out.println("2. Check My Info");
                System.out.println("3. Secession");
                System.out.println("4. Exit");
            }
            System.out.println("----------------------------------------------------");
            System.out.print("Enter the number: ");

            try {
                int opt = Integer.parseInt(bf.readLine());
                if (role) { // 멤버용 로직
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
                            // 자신의 정보 확인 (Check 메서드 사용)
                            Check(1, id);
                            break;
                        case 4:
                            // 자신이 속한 팀 확인 (Check 메서드 사용)
                            Check(3, id);
                            break;
                        case 5:
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
                        case 6:
                            System.out.println("Exiting MyPage...");
                            return; // 루프 종료
                    }
                } else { // 매니저용 로직
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
                            // 은행 계좌 확인 (Check 메서드 사용)
                            Check(2, id);
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
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IOException e) {
                System.out.println("Error reading from input.");
            }
        }
    }


    protected static void UserEval(String managerId) throws IOException, SQLException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date()); // 현재 날짜

        System.out.println("----------------------------------------------------");
        System.out.println("Manager Evaluation");
        System.out.print("Enter Member ID for evaluation: ");
        String memberId = bf.readLine();

        System.out.print("Enter Evaluation Tier (A/B/C/D): ");
        String evalTier = bf.readLine().toUpperCase();

        // 유효성 검사
        if (!(evalTier.equals("A") || evalTier.equals("B") || evalTier.equals("C") || evalTier.equals("D"))) {
            System.out.println("Invalid Evaluation Tier. Please enter A, B, C, or D.");
            return;
        }

        // 평가 존재 여부 확인
        ResultSet rs = SQLx.Selectx("*", "MAN_EVAL_MEM", "MEM_ID = '" + memberId + "' AND MAN_ID = '" + managerId + "'", "");

        if (rs.next()) { // 평가가 이미 존재하면 업데이트
            String[] key = {memberId, managerId};
            SQLx.Updatex("MAN_EVAL_MEM", "EVAL_TIER", evalTier, key);
            SQLx.Updatex("MAN_EVAL_MEM", "UPDATE_TIME", currentDate, key); // 현재 날짜 사용
            System.out.println("Evaluation Updated.");
        } else { // 새로운 평가면 삽입
            String[] data = {memberId, managerId, evalTier, currentDate}; // 현재 날짜 포함
            SQLx.Insertx("MAN_EVAL_MEM", data);
            System.out.println("Evaluation Inserted.");
        }
        rs.close();
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
            case 4:
                targetField = "JOB";
                break;
            default:
                System.out.println("Invalid option");
                return;
        }

        if (!isValidInput) {
            return; // 유효하지 않은 입력이면 업데이트를 중단
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

                // key 배열에는 타겟이 되는 행의 기본키나 조건을 지정
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
            // key 배열에는 삭제할 행의 기본키나 조건을 지정
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

    private static void Check(int opt, String id) {
        try {
            switch (opt) {
                case 1: // Member의 자기 정보 및 캐시 정보 조회
                    String attr = "U.*, M.PREPAID_MONEY";
                    String tbl = "USERS U INNER JOIN MEMBER M ON U.ID_NUMBER = M.ID_NUMBER";
                    String where = "U.ID_NUMBER = '" + id + "'";
                    ResultSet rsMember = SQLx.Selectx(attr, tbl, where, "");
                    if (rsMember.next()) {
                        System.out.println("Member Information:");
                        System.out.println("ID: " + rsMember.getString("ID_NUMBER"));
                        System.out.println("Name: " + rsMember.getString("NAME"));
                        System.out.println("Sex: " + rsMember.getString("SEX"));
                        System.out.println("Year of Birth: " + rsMember.getString("YOB"));
                        System.out.println("Job: " + rsMember.getString("JOB"));
                        System.out.println("Cash: " + rsMember.getString("PREPAID_MONEY"));
                        // 다른 필요한 멤버 정보 추가
                    } else {
                        System.out.println("No member information available.");
                    }
                    rsMember.close();
                    break;

                case 2: // Manager의 자기 정보 조회
                    ResultSet rsManager = SQLx.Selectx("*", "USERS U INNER JOIN MANAGER M ON U.ID_NUMBER = M.ID_NUMBER", "U.ID_NUMBER = '" + id + "'", "");
                    if (rsManager.next()) {
                        System.out.println("Manager Information:");
                        System.out.println("ID: " + rsManager.getString("ID_NUMBER"));
                        System.out.println("Name: " + rsManager.getString("NAME"));
                        System.out.println("Sex: " + rsManager.getString("SEX"));
                        System.out.println("Year of Birth: " + rsManager.getString("YOB"));
                        System.out.println("Job: " + rsManager.getString("JOB"));
                        System.out.println("Bank Account: " + rsManager.getString("BANK_ACCOUNT"));
                        // 다른 필요한 매니저 정보 추가
                    } else {
                        System.out.println("No manager information available.");
                    }
                    rsManager.close();
                    break;
                case 3: // Member가 속한 Team 조회
                    ResultSet rsTeam = SQLx.Selectx("T.TEAM_NAME", "TEAM T INNER JOIN TEAM_MEM TM ON T.TEAM_ID = TM.TEAM_ID", "TM.MEM_ID = '" + id + "'", "");
                    if (rsTeam.next()) {
                        System.out.println("Team Information:");
                        System.out.println("Team Name: " + rsTeam.getString("TEAM_NAME"));
                        // 필요한 팀 정보 추가
                    } else {
                        System.out.println("No team information available.");
                    }
                    rsTeam.close();
                    break;
            }
        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();
        }
    }
}
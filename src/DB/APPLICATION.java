package DB;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.Calendar;
import java.util.Random;

import static DB.SQLx.*;
import static DB.ProjectMain.bf;

public class APPLICATION {
    protected static void MyPage(String id, boolean role) {
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
        ResultSet rs = Selectx("*", "MAN_EVAL_MEM", "MEM_ID = '" + memberId + "' AND MAN_ID = '" + managerId + "'", "");

        if (rs.next()) { // 평가가 이미 존재하면 업데이트
            String[] key = {memberId, managerId};
            SQLx.Updatex("MAN_EVAL_MEM", "EVAL_TIER", evalTier, key);
            SQLx.Updatex("MAN_EVAL_MEM", "UPDATE_TIME", currentDate, key); // 현재 날짜 사용
            System.out.println("Evaluation Updated.");
        } else { // 새로운 평가면 삽입
            String[] data = {memberId, managerId, evalTier, currentDate}; // 현재 날짜 포함
            Insertx("MAN_EVAL_MEM", data);
            System.out.println("Evaluation Inserted.");
        }
        rs.close();
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
            ResultSet rs = Selectx("PREPAID_MONEY", "MEMBER", "ID_NUMBER = '" + memberId + "'", "");
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
            Deletex("USERS", key);
            System.out.println("User deleted: " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// DELETE USER ON CASCADE

    protected static void Screen(String id, boolean role, int opt) throws IOException, SQLException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        // false -> Manager, true -> Member
        // opt = 2. Training, 3. Match, 4. Team
        // TRAINING, MATCH, TEAM relation control

        if(role) {
            if(opt ==2){
                System.out.println("Enter the option which u want to do");
                System.out.println("=====================================");
                System.out.println("1. make training"); // training에 만들고자 하는 트레이닝 insert
                System.out.println("2. delete training"); // training에 없애고자 하는 트레이팅 delete
                System.out.println("3. apply training"); // training id와 member id를 training 테이블에 새로운 튜플로 insert
                System.out.println("4. cancel training"); // traniing id와 member id를 가지는 튜플을 delete
                System.out.println("=====================================");
                System.out.println("Enter the number: ");
                while(true) {
                    String detail = bf.readLine();
                    if (detail.equals("1")) {
                        Make_training(id);
                        break;
                    } else if (detail.equals("2")) {
                        Delete_training(id);
                        break;
                    } else if (detail.equals("3")) {
                        Apply_training(id);
                        break;
                    } else if (detail.equals("4")) {
                        Cancel_training(id);
                        break;
                    }
                    else
                        System.out.printf("Re Enter the number: ");
                }
            }
            else if(opt == 3){
                System.out.println("Enter the option which u want to do");
                System.out.println("=====================================");
                System.out.println("1. apply match");  // match id를 받아서 match_app_member에 insert, cost 어케 할거임?
                System.out.println("2. cancel match"); // match id와 member id를 통해 match_app_member에서 해당값 delete
                System.out.println("=====================================");
                System.out.printf("Enter the number: ");
                while(true) {
                    String detail = bf.readLine();
                    if (detail.equals("1")) {
                        Apply_match(id);
                        break;
                    }
                    else if (detail.equals("2")) {
                        Cancel_match(id);
                        break;
                    }
                    else
                        System.out.printf("Re Enter the number: ");
                }

            }
            else if(opt == 4){
                System.out.println("Enter the option which u want to do");
                System.out.println("=====================================");
                System.out.println("1. make team"); // team_id 랜덤으로 만들어주고 team_id와 team_name을 team에 insert, temam_mem에 만든 사람 자동 삽입
                System.out.println("2. delete team"); // team, team_mem 둘 다 delete (cascade 한다면)
                System.out.println("3. apply team"); // team_mem에서 team_id와 mem_id를 delete
                System.out.println("4. cancel team"); // team_mem에서 team_id와 mem_id를 insert
                System.out.println("=====================================");
                System.out.printf("Enter the number: ");
                while(true) {
                    String detail = bf.readLine();
                    if (detail.equals("1")) {
                        Make_team(id);
                        break;
                    } else if (detail.equals("2")) {
                        Delete_team(id);
                        break;
                    } else if (detail.equals("3")) {
                        Apply_team(id);
                        break;
                    } else if (detail.equals("4")) {
                        Cancel_team(id);
                        break;
                    }
                    else
                        System.out.printf("Re Enter the number: ");
                }
            }

        }
        else{
            // manager match apply
            Apply(id);
        }

    }

    public static void Make_training(String tutor_id) throws IOException, SQLException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder(); // class_id
        sb.append("C").append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10));
        sb.append("-").append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10));
        sb.append("-").append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10));

        System.out.printf("Enter the Date_Time ex) 1998-08-31 : ");
        String date_time = bf.readLine();
        System.out.printf("Enter the RECOMMEND_TIER: ");
        String rec_tier = bf.readLine();
        System.out.printf("Enter the SUBJECT: ");
        String subject = bf.readLine();
        System.out.printf("Enter the Place: ");
        String place = bf.readLine();
        System.out.printf("Enter the maximum number of tutee: ");
        String max_num = bf.readLine();
        System.out.printf("Enter the wage: ");
        String wage = bf.readLine();
        try {
            String[] result = new String[8];
            result[0] = String.valueOf(sb);
            result[1] = date_time;
            result[2] = tutor_id;
            result[3] = rec_tier;
            result[4] = subject;
            result[5] = place;
            result[6] = max_num;
            result[7] = wage;

            Insertx("TRAINING", result);
            System.out.println("Training data inserted successfully!");
            System.out.println("Your Class ID is "+sb);
        } catch (SQLException e) {
            System.err.println("Error inserting training data: " + e.getMessage());
        }
    }


    private static void Delete_training(String id) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.printf("Enter the Class_ID which you want to delete: ");
        String Class_id = bf.readLine();
        try {
            String[] key = new String[1];
            key[0] = Class_id;
            Deletex("TRAINING", key);
            System.out.println("Training tuple deleted successfully!");
            String[] key2 = new String[2];
            key2[0] = Class_id;
            ResultSet rs1 = Selectx("tutee_ID","TRAIN_ENROLLS","where class_id = '"+id+"'");
            while(rs1.next()) {
                key2[1] = rs1.getString("tutee_id");
                Deletex("TRAIN_ENROLLS", key2);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting training tuple: " + e.getMessage());
        }
    }

    private static void Apply_training(String id)throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.printf("Enter the Class_ID which you want to apply: ");
        String Class_id = bf.readLine();
        try {
            String[] data = new String[2];
            data[0] = Class_id;
            data[1] = id;
            Insertx("TRAIN_ENROLLS", data);
            System.out.println("Training is successfully applied!");
        } catch (SQLException e) {
            System.err.println("Error applying for training: " + e.getMessage());
        }
    }
    private static void Cancel_training(String id) throws IOException {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("Enter the Class_ID which you want to cancel: ");
            String Class_id = bf.readLine();

            String[] key = new String[2];
            key[0] = Class_id;
            key[1] = id;
            Deletex("TRAIN_ENROLLS", key);

            System.out.println("Training cancellation successful!");
        } catch (IOException | SQLException e) {
            System.err.println("Error canceling training: " + e.getMessage());
        }
    }
    private static void Apply_match(String id) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            String cost = "6000";
            int cost_i = 6000;
            // cost는 어떻게 계산?

            // 1. 멤버의 prepaid_money를 갱신하는 쿼리를 PreparedStatement로 작성
            String updatePrepaidMoneyQuery = "UPDATE member SET prepaid_money = prepaid_money - ? WHERE id_number = ?";

            // 2. PrepareStatement 객체 생성
            try (PreparedStatement updatePrepaidMoneyStmt = ProjectMain.conn.prepareStatement(updatePrepaidMoneyQuery)) {
                // 3. PreparedStatement에 매개변수 할당
                updatePrepaidMoneyStmt.setInt(1, cost_i);
                updatePrepaidMoneyStmt.setString(2, id);

                // 4. prepaid_money 갱신 실행
                int updateResult = updatePrepaidMoneyStmt.executeUpdate();

                if (updateResult > 0) {
                    // 5. prepaid_money 갱신이 성공하면 MATCH_APP_MEMBER에 데이터 삽입
                    System.out.printf("Enter the Match_ID which you want to apply: ");
                    String[] key = new String[3];
                    key[0] = bf.readLine();
                    key[1] = id;
                    key[2] = cost;
                    Insertx("MATCH_APP_MEMBER", key);

                    System.out.println("Apply Match successful!");
                } else {
                    System.out.println("Error applying match: Failed to update prepaid_money for member " + id);
                }
            }
        } catch (IOException | SQLException e) {
            System.err.println("Error Apply Match: " + e.getMessage());
        }
    }

    private static void Cancel_match(String id) throws IOException {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            String cost = "6000";
            int cost_i = 6000;
            // cost는 어떻게 계산?

            // 1. 멤버의 prepaid_money를 갱신하는 쿼리를 PreparedStatement로 작성
            String updatePrepaidMoneyQuery = "UPDATE member SET prepaid_money = prepaid_money + ? WHERE id_number = ?";

            // 2. PrepareStatement 객체 생성
            try (PreparedStatement updatePrepaidMoneyStmt = ProjectMain.conn.prepareStatement(updatePrepaidMoneyQuery)) {
                // 3. PreparedStatement에 매개변수 할당
                updatePrepaidMoneyStmt.setInt(1, cost_i);
                updatePrepaidMoneyStmt.setString(2, id);

                // 4. prepaid_money 갱신 실행
                int updateResult = updatePrepaidMoneyStmt.executeUpdate();
                ProjectMain.conn.commit();

                if (updateResult > 0) {
                    // 5. prepaid_money 갱신이 성공하면 MATCH_APP_MEMBER의 데이터 제거
                    System.out.printf("Enter the Class_ID which you want to delete: ");
                    String[] key = new String[2];
                    key[0] = bf.readLine();
                    key[1] = id;
                    Deletex("MATCH_APP_MEMBER", key);
                    System.out.println("Cancel Match successful!");
                } else {
                    System.out.println("Error applying match: Failed to update prepaid_money for member " + id);
                }
            }
        } catch (IOException | SQLException e) {
            System.err.println("Error Apply Match: " + e.getMessage());
        }
    }

    private static void Apply(String id) throws SQLException {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("Enter the Match_ID which you want to apply: ");
            String match_id = bf.readLine();

            String updatePrepaidMoneyQuery = "UPDATE MATCH SET MANAGER_ID = ? WHERE MATCH_ID = ?";
            PreparedStatement updatePrepaidMoneyStmt = ProjectMain.conn.prepareStatement(updatePrepaidMoneyQuery);
            updatePrepaidMoneyStmt.setString(1, id);
            updatePrepaidMoneyStmt.setString(2, match_id);

            updatePrepaidMoneyStmt.executeUpdate();
            ProjectMain.conn.commit();

            System.out.println("Apply Match successful!");

        } catch (IOException | SQLException e) {
            System.err.println("Error Apply Match: " + e.getMessage());
        }
    }
    private static void Make_team(String id) throws IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder(); // class_id
        sb.append("T").append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10));
        sb.append("-").append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10));
        sb.append("-").append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10)).append(Math.abs(rand.nextInt() % 10));


        try {

            String team_id = sb.toString();

            System.out.printf("Enter the Team name : ");
            String team_name = bf.readLine();
            String[] key = new String[2];
            key[0]=team_id;
            key[1]=team_name;
            Insertx("TEAM",key);


            String[] key2 = new String[2];
            key2[0] = sb.toString();
            key2[1] = id;
            System.out.println(key2[0]);
            System.out.println(key2[1]);

            Insertx("TEAM_MEM",key2);
        } catch (SQLException e) {
            System.err.println("Error make team tuple: " + e.getMessage());
        }
        System.out.println("Team data inserted successfully!");
        System.out.println("Your Team ID is "+sb);


    }
    private static void Delete_team(String id) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.printf("Enter the Team_ID which you want to delete: ");
        String team_id = bf.readLine();
        try {
            String[] key = new String[1];
            key[0] = team_id;
            Deletex("TEAM", key);
            System.out.println("Delete team successfully!");
            String[] key2 = new String[2];
            key2[0] = team_id;
            ResultSet rs1 = Selectx("mem_id", "TEAM_MEM", "where team_id = '" + team_id + "'");
            while (rs1.next()) {
                key2[1] = rs1.getString("mem_id");
                Deletex("TEAM_MEM", key2);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting team tuple: " + e.getMessage());
        }
    }
    private static void Apply_team(String id) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the Team_ID which you want to apply: ");
        String team_id = bf.readLine();
        String[] key2 = new String[2];
        key2[0] = team_id;
        key2[1] = id;
        try {
            Insertx("TEAM_MEM",key2);
            System.out.println("Apply team successfully!");
        } catch (SQLException e) {
            System.err.println("Error apply team tuple: " + e.getMessage());
        }
    }
    private static void Cancel_team(String id) throws IOException {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("Enter the Team ID which you want to cancel: ");
            String team_id = bf.readLine();

            String[] key = new String[2];
            key[0] = team_id;
            key[1] = id;
            Deletex("TEAM_MEM", key);

            System.out.println("Team cancellation successful!");
        } catch (IOException | SQLException e) {
            System.err.println("Error canceling training: " + e.getMessage());
        }
    }

    private static void Check(int opt, String id) {
        try {
            switch (opt) {
                case 1: // Member의 자기 정보 및 캐시, 평가 등급 정보 조회
                    String attr = "U.*, M.PREPAID_MONEY, E.TIER";
                    String tbl = "USERS U INNER JOIN MEMBER M ON U.ID_NUMBER = M.ID_NUMBER LEFT JOIN MEMBER_EVAL_VIEW E ON U.ID_NUMBER = E.MEM_ID";
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
                        System.out.println("Evaluation Tier: " + rsMember.getString("TIER"));
                        // 다른 필요한 멤버 정보 추가
                    } else {
                        System.out.println("No member information available.");
                    }
                    rsMember.close();
                    break;

                case 2: // Manager의 자기 정보 조회
                    ResultSet rsManager = Selectx("*", "USERS U INNER JOIN MANAGER M ON U.ID_NUMBER = M.ID_NUMBER", "U.ID_NUMBER = '" + id + "'", "");
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
                case 3: // Member가 속한 Team 및 팀의 평가 등급 조회
                    String attrT = "T.TEAM_NAME, E.TEAM_TIER";
                    String atlT = "TEAM T INNER JOIN TEAM_MEM TM ON T.TEAM_ID = TM.TEAM_ID LEFT JOIN TEAM_EVAL_VIEW E ON T.TEAM_ID = E.TEAM_ID";
                    String whereT = "TM.MEM_ID = '" + id + "'";
                    ResultSet rsTeam = SQLx.Selectx(attrT, atlT, whereT, "");
                    if (rsTeam.next()) {
                        System.out.println("Team Information:");
                        System.out.println("Team Name: " + rsTeam.getString("TEAM_NAME"));
                        System.out.println("Team Tier: " + rsTeam.getString("TEAM_TIER"));
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
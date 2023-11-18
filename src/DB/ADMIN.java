package DB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class ADMIN {
    protected static void Screen(int option) throws IOException, SQLException {
        // opt = 1. user, 2. team, 3. owner, 4. field, 5.match, 6. training
        System.out.println("----------------------------------------------------");
        System.out.println("Admin Screen for management");
        while (true){
            System.out.println("----------------------------------------------------");
            System.out.println("1. Update");
            System.out.println("2. Delete");
            //System.out.println("3. Select"); // application에서 check로 필요한 것만 조회하도록 구현
            System.out.println("3. Log Out");
            System.out.println("----------------------------------------------------");
            System.out.print("Enter the number : ");
            int opt = Integer.parseInt(ProjectMain.bf.readLine());
            switch (opt){
                case 1 : Update(option); break;
                case 2 : Delete(option); break;
                //case 3 : Select(option); break;
                case 3 : return;
                default: System.out.println("Wrong number!, Re-enter");
            }
        }
    }
    private static void Update(int opt) throws IOException, SQLException {
        String[] tbls = {"USERS", "TEAM", "OWNER", "FIELD", "MATCH", "TRAINING"};
        String[] key = new String[1];
        String[] attr;
        ResultSet rs;
        int target;
        String value;
        switch (opt){
            case 1 :
                attr = new String[6];
                attr[0] = "ID_NUMBER";
                attr[1] = "NAME";
                attr[2] = "SEX";
                attr[3] = "YOB";
                attr[4] = "JOB";
                attr[5] = "PASSWD";
                System.out.print("input target user id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], attr[0] + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", attr[0], key[0]);
                    return;
                }
                System.out.println("attribute ");
                System.out.printf("1. %s 2. %s 3. %s \n", attr[0], attr[1], attr[2]);
                System.out.printf("4. %s 5. %s 6. %s \n", attr[3], attr[4], attr[5]);
                System.out.println("any other number. exit ");
                System.out.print("input target attribute number(only 1) : ");
                target = Integer.parseInt(ProjectMain.bf.readLine());
                if(target<1 || target>6) return;
                System.out.printf("selected attribute : %s\n", attr[target-1]);
                System.out.printf("now value : %s\n", rs.getString(target));
                System.out.print("input new value : ");
                value = ProjectMain.bf.readLine();
                SQLx.Updatex(tbls[opt-1], attr[target-1], value, key);
                break;
            case 2 :
                attr = new String[2];
                attr[0] = "TEAM_ID";
                attr[1] = "TEAM_NAME";
                System.out.print("input target team id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], attr[0] + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", attr[0], key[0]);
                    return;
                }
                System.out.println("attribute ");
                System.out.printf("1. %s 2. %s \n", attr[0], attr[1]);
                System.out.println("any other number. exit ");
                System.out.print("input target attribute number(only 1) : ");
                target = Integer.parseInt(ProjectMain.bf.readLine());
                if(target<1 || target>2) return;
                System.out.printf("selected attribute : %s\n", attr[target-1]);
                System.out.printf("now value : %s\n", rs.getString(target));
                System.out.print("input new value : ");
                value = ProjectMain.bf.readLine();
                SQLx.Updatex(tbls[opt-1], attr[target-1], value, key);
                break;
            case 3 :
                attr = new String[2];
                attr[0] = "OWNER_HP";
                attr[1] = "NAME";
                System.out.print("input target owner telephone number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], attr[0] + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", attr[0], key[0]);
                    return;
                }
                System.out.println("attribute ");
                System.out.printf("1. %s 2. %s \n", attr[0], attr[1]);
                System.out.println("any other number. exit ");
                System.out.print("input target attribute number(only 1) : ");
                target = Integer.parseInt(ProjectMain.bf.readLine());
                if(target<1 || target>2) return;
                System.out.printf("selected attribute : %s\n", attr[target-1]);
                System.out.printf("now value : %s\n", rs.getString(target));
                System.out.print("input new value : ");
                value = ProjectMain.bf.readLine();
                SQLx.Updatex(tbls[opt-1], attr[target-1], value, key);
                break;
            case 4 :
                attr = new String[5];
                attr[0] = "FIELD_ID";
                attr[1] = "NAME";
                attr[2] = "FIELD_HP";
                attr[3] = "OWNER_HP";
                attr[4] = "ADDRESS";
                System.out.print("input target field id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], attr[0] + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", attr[0], key[0]);
                    return;
                }
                System.out.println("attribute ");
                System.out.printf("1. %s 2. %s 3. %s ", attr[0], attr[1], attr[2]);
                System.out.printf("4. %s 5. %s\n", attr[3], attr[4]);
                System.out.println("any other number. exit ");
                System.out.print("input target attribute number(only 1) : ");
                target = Integer.parseInt(ProjectMain.bf.readLine());
                if(target<1 || target>5) return;
                System.out.printf("selected attribute : %s\n", attr[target-1]);
                System.out.printf("now value : %s\n", rs.getString(target));
                System.out.print("input new value : ");
                value = ProjectMain.bf.readLine();
                SQLx.Updatex(tbls[opt-1], attr[target-1], value, key);
                break;
            case 5 :
                attr = new String[7];
                attr[0] = "MATCH_ID";
                attr[1] = "DATE_TIME";
                attr[2] = "PLACE_ID";
                attr[3] = "TYPE";
                attr[4] = "SEX_CONSTRAINT";
                attr[5] = "MANAGER_ID";
                attr[6] = "WAGE";
                System.out.print("input target match id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], attr[0] + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", attr[0], key[0]);
                    return;
                }
                System.out.println("attribute ");
                System.out.printf("1. %s 2. %s 3. %s 4. %s\n", attr[0], attr[1], attr[2], attr[3]);
                System.out.printf("5. %s 6. %s 7. %s \n", attr[4], attr[5], attr[6]);
                System.out.println("any other number. exit ");
                System.out.print("input target attribute number(only 1) : ");
                target = Integer.parseInt(ProjectMain.bf.readLine());
                if(target<1 || target>7) return;
                System.out.printf("selected attribute : %s\n", attr[target-1]);
                System.out.printf("now value : %s\n", rs.getString(target));
                System.out.print("input new value : ");
                value = ProjectMain.bf.readLine();
                SQLx.Updatex(tbls[opt-1], attr[target-1], value, key);
                break;
            case 6 :
                attr = new String[8];
                attr[0] = "CLASS_ID";
                attr[1] = "DATE_TIME";
                attr[2] = "TUTOR_ID";
                attr[3] = "RECOMMEND_TIER";
                attr[4] = "SUBJECT";
                attr[5] = "PLACE";
                attr[6] = "MAX_NUM";
                attr[7] = "WAGE";
                System.out.print("input target class id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], attr[0] + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", attr[0], key[0]);
                    return;
                }
                System.out.println("attribute ");
                System.out.printf("1. %s 2. %s 3. %s 4. %s\n", attr[0], attr[1], attr[2], attr[3]);
                System.out.printf("5. %s 6. %s 7. %s 8. %s\n", attr[4], attr[5], attr[6], attr[7]);
                System.out.println("any other number. exit ");
                System.out.print("input target attribute number(only 1) : ");
                target = Integer.parseInt(ProjectMain.bf.readLine());
                if(target<1 || target>8) return;
                System.out.printf("selected attribute : %s\n", attr[target-1]);
                System.out.printf("now value : %s\n", rs.getString(target));
                System.out.print("input new value : ");
                value = ProjectMain.bf.readLine();
                SQLx.Updatex(tbls[opt-1], attr[target-1], value, key);
                break;
        }
    }
    private static void Delete(int opt) throws SQLException, IOException {
        String[] tbls = {"USERS", "TEAM", "OWNER", "FIELD", "MATCH", "TRAINING"};
        String[] key = new String[1];
        String keyAttr;
        ResultSet rs;
        String value;
        switch (opt){
            case 1 :
                keyAttr = "ID_NUMBER";
                System.out.print("input target user id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], keyAttr + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", keyAttr, key[0]);
                    return;
                }
                SQLx.Deletex(tbls[opt-1], key);
                break;
            case 2 :
                keyAttr = "TEAM_ID";
                System.out.print("input target user id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], keyAttr + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", keyAttr, key[0]);
                    return;
                }
                SQLx.Deletex(tbls[opt-1], key);
                break;
            case 3 :
                keyAttr = "OWNER_HP";
                System.out.print("input target user id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], keyAttr + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", keyAttr, key[0]);
                    return;
                }
                SQLx.Deletex(tbls[opt-1], key);
            case 4 :
                keyAttr = "FIELD_ID";
                System.out.print("input target user id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], keyAttr + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", keyAttr, key[0]);
                    return;
                }
                SQLx.Deletex(tbls[opt-1], key);
            case 5 :
                keyAttr = "MATCH_ID";
                System.out.print("input target user id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], keyAttr + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", keyAttr, key[0]);
                    return;
                }
                SQLx.Deletex(tbls[opt-1], key);
            case 6 :
                keyAttr = "CLASS_ID";
                System.out.print("input target user id number : ");
                key[0] = ProjectMain.bf.readLine().toUpperCase().toUpperCase();
                rs = SQLx.Selectx("*", tbls[opt-1], keyAttr + " = " + key[0], "");
                rs.last();
                if(rs.getRow()==0){
                    System.out.printf("There is no %s : %s\n", keyAttr, key[0]);
                    return;
                }
                SQLx.Deletex(tbls[opt-1], key);
        }
    }
    private static void Select(int opt){
        switch (opt){
            case 1 : break;
            case 2 : break;
            case 3 : break;
            case 4 : break;
            case 5 : break;
            case 6 : break;
        }
    }

}
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class Insert2DB {

    public static void main(String[] args) {
        // connect to DB
        String username = "root";
        String password = "Wailord1426"; // your password
        String dbname = "TeamInfo";
        
        Connection connDB = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connectionString = String.format("jdbc:mysql://localhost:3306/%s?autoReconnect=true&useSSL=false", dbname);
            connDB = DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            System.out.println("DB connection error: "+ e);
        }
        
        // get fname and lname from keyboard:
        @SuppressWarnings("resource")
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter firstname?");
        String fname = kb.nextLine();
        System.out.println("Enter lastname?");
        String lname = kb.nextLine();
        
        // create SQL command:
        String sqlstr = "INSERT INTO myTeam VALUES('"+fname+"', '"+lname+"');";
        try{
            Statement stmnt = connDB.createStatement();
                      
            System.out.println(sqlstr);
            if (stmnt.executeUpdate(sqlstr) > 0)
                System.out.println("Success!");
            else
                System.out.println("Failed to insert!");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

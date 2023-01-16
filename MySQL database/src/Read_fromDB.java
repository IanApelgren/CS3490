import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Read_fromDB {

    public static void main(String[] args) {
        // connect to DB
        String username = "root";
        String password = "Wailord1426";
        String dbname = "TeamInfo";
        
        Connection connDB = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connectionString = String.format("jdbc:mysql://localhost:3306/%s?autoReconnect=true&useSSL=false", dbname);
            connDB = DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            System.out.println("DB connection error: "+ e);
        }
        
        // create SQL command:
        String sqlstr = "SELECT * FROM myTeam;";
        try{
            Statement stmnt = connDB.createStatement();
                      
            System.out.println(sqlstr);
            
            ResultSet rs = stmnt.executeQuery(sqlstr);
            
            while (rs.next()) {
                System.out.println(rs.getString(1)+"\t"+rs.getString(2));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}

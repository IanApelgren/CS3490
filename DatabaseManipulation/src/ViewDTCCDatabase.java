// Needed for JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewDTCCDatabase {

	public static void main(String[] args) {
		// Create a named constant for the URL.
		// NOTE: This value is specific for Java DB.
		final String DB_URL = "jdbc:derby:DTCCDatabase;create=true";

		try
		{
			// Create a connection to the database.
			Connection conn = DriverManager.getConnection(DB_URL);

			// view data
			viewDTCCTable(conn);
			
			// modify record
			modDTCCTable(conn);
			
			viewDTCCTable(conn);
			
			// Close the connection.
			conn.close();

		}
		catch (Exception ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}


	}  // end main



	public static void viewDTCCTable(Connection conn)
	{
		//create var for result set
		ResultSet resultset = null;

		try
		{
			// Get a Statement object.
			Statement stmt = conn.createStatement();

			// View the table.
			resultset = stmt.executeQuery("SELECT * FROM DTCC");

			// process results
			ResultSetMetaData metaData = resultset.getMetaData();
			
			System.out.println("Data from DTCC Table:");

			int numberOfColumns = metaData.getColumnCount();
			// for loop to field names
			for (int i = 1; i <= numberOfColumns; i++){
				System.out.printf("%s\t", metaData.getColumnName(i));
			}
			System.out.println();
			
			// while loop to display data
			while (resultset.next()){
				for (int i = 1; i <= numberOfColumns; i++){
					System.out.printf("%s\t", resultset.getObject(i));
				}
				System.out.println();
			}

		}
		catch (SQLException ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}
	}	


	public static void modDTCCTable(Connection conn)
	{
		//create var for result set
		ResultSet resultset = null;

		try
		{
			// Get a Statement object.
			Statement stmt = conn.createStatement();

			// update record
			stmt.executeUpdate("UPDATE DTCC SET FirstName = 'Ellis' WHERE Student_ID = '807223230'");
			stmt.executeUpdate("UPDATE DTCC SET GPA = 4.0 WHERE Student_ID = '807223230'");
			stmt.executeUpdate("DELETE FROM DTCC WHERE Student_ID = '899090111'");
			System.out.println("Record updated");

		}
		catch (SQLException ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}
	}	

}  // end class

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Ian Apelgren
 * 2/9/19
 * taken from class code
 * This program creates the DTCC database.
 */
public class DTCCDatabase {
	public static void main(String[] args)
	{
		// Create a named constant for the URL.
		// NOTE: This value is specific for Java DB.
		final String DB_URL = "jdbc:derby:DTCCDatabase;create=true";

		try
		{
			// Create a connection to the database.
			Connection conn = DriverManager.getConnection(DB_URL);

			// If the DB already exists, drop the tables.
			dropTables(conn);

			buildDTCCTable(conn);

			conn.close();
			
			System.out.println("DB creation success!");
		}
		catch (Exception ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}
	} // end main
	
	/**
	 * The buildDTCCTable method creates the
	 * DTCC table and adds some rows to it.
	 */
	public static void buildDTCCTable(Connection conn)
	{
		try
		{
			// Get a Statement object.
			Statement stmt = conn.createStatement();

			// Create the table.
			stmt.execute( "CREATE TABLE DTCC (Student_ID CHAR(10)  NOT NULL PRIMARY KEY, LastName CHAR(20), FirstName CHAR(15), GPA DOUBLE)" );

			// Insert Ben Rothlisberger.
			stmt.execute(  "INSERT INTO DTCC VALUES ('899090111', 'Rothlisberger', 'Ben', 'CIT', 3.7 )"   );

			// Insert Peyton Manning.
			stmt.execute(  "INSERT INTO DTCC VALUES ( '129020202', 'Manning', 'Peyton', 'Computer Programming', 3.8 )"   );

			// Insert Tom Brady.
			stmt.execute(  "INSERT INTO DTCC VALUES ( '890101030', 'Brady', 'Tom', 'Accounting', 3.4 )"    );
			
			// Insert Arron Rodgers.
			stmt.execute(  "INSERT INTO DTCC VALUES ( '980191919', 'Rodgers', 'Aaron', 'Networking', 3.2 )");
			
			// Insert Eli Manning.
			stmt.execute(  "INSERT INTO DTCC VALUES ( '807223230', 'Manning', 'Eli', 'Securities', 3.7 )");
						
			System.out.println("DTCC table created.");
		}
		catch (SQLException ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
		}
	} // end build
	
	
	/**
	 * The dropTables method drops any existing
	 * in case the database already exists.
	 */
	public static void dropTables(Connection conn)
	{
		System.out.println("Checking for existing table.");

		try
		{
			// Get a Statement object.
			Statement stmt  = conn.createStatement();;

			try
			{
				// Drop the DTCC table.
				stmt.execute("DROP TABLE DTCC");
				System.out.println("DTCC table dropped.");
			}
			catch(SQLException ex)
			{
				// No need to report an error.
				// The table simply did not exist.
			}
		}
		catch(SQLException ex)
		{
			System.out.println("ERROR: " + ex.getMessage());
			ex.printStackTrace();
		}
	} // end drop tables
	
}  // end class
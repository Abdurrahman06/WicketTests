package test;

import java.sql.*;

public class ReadingDatabaseExm
{
    public static void main( String args[] )
    {
        try
        {
            String arg;
            Connection con;

            // Select the JDBC driver and create a connection.
            // May throw a SQLException.
            // Choices are:
            // 1. jConnect driver
            // 2. SQL Anywhere JDBC 3.0 driver
            // 3. SQL Anywhere JDBC 4.0 driver
            arg = "jconnect";
            if( args.length > 0 ) arg = args[0];
            if( arg.compareToIgnoreCase( "jconnect" ) == 0 )
            {
                // con = DriverManager.getConnection(
                  //  "jdbc:sybase:Tds:localhost:2638", "DBA", "sql");
            	 con = DriverManager.getConnection("jdbc:sybase:Tds:localhost:2638", "DBA", "sql");
            	// con = DriverManager.getConnection("jdbc:sybase:Tds:10.1.1.52:2638", "DBA", "sql");
            }
            else if( arg.compareToIgnoreCase( "jdbc3" ) == 0 )
            {
                DriverManager.registerDriver( (Driver)
                    Class.forName(
                    "sybase.jdbc.sqlanywhere.IDriver").newInstance()
                    );
                con = DriverManager.getConnection(
                    "jdbc:sqlanywhere:uid=DBA;pwd=sql" );
            }
            else
            {
                con = DriverManager.getConnection(
                    "jdbc:sqlanywhere:uid=DBA;pwd=sql" );
            }

            // System.out.println("Using "+arg+" driver");
            
            // Create a statement object, the container for the SQL
            // statement. May throw a SQLException.
            Statement stmt = con.createStatement();

            // Create a result set object by executing the query.
            // May throw a SQLException.
            ResultSet rs = stmt.executeQuery(
                "SELECT ID,HESAPKOD,BORC,ALACAK FROM mu_yevmiyepre");
            System.out.println("ID\t"+"AccountCode\t\t"+"AmounDebit\t"+"AmounCredit\t");
            
            // Process the result set.
            while (rs.next())
            {
                int value = rs.getInt(1);
                String AccountCode = rs.getString(2);
                String AmounDebit= rs.getString(3);
                String AmounCredit = rs.getString(4);
                System.out.println(value+"\t"+AccountCode+"\t\t"+AmounDebit+"\t"+AmounCredit);
            }
            rs.close();
            stmt.close();
            con.close();
        }
        catch (SQLException sqe)
        {
            System.out.println("Unexpected exception : " +
                                sqe.toString() + ", sqlstate = " +
                                sqe.getSQLState());
            System.exit(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }
}
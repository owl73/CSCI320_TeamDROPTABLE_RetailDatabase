/**
 * Created by Qasim on 3/22/2018.
 */
package pdm.h2.demo;
import pdm.h2.demo.objects.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Timestamp;

public class CustomerTable {

    public static void populateCustomerTableFromCSV(Connection conn,
                                                 String fileName)
            throws SQLException, ParseException {
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        ArrayList<Customer> people = new ArrayList<Customer>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
            	String[] split = line.split(",");
            	Date parsedTimeStamp = (Date) dateFormat.parse(split[2]);
            	Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
                people.add(new Customer(Integer.parseInt(split[0]), split[1], timestamp, split[3], split[4]));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /**
         * Creates the SQL query to do a bulk add of all people
         * that were read in. This is more efficent then adding one
         * at a time
         */
        String sql = createCustomerInsertSQL(people);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static void createCustomerTable(Connection conn) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Customer ( \n" +
                    "    ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL, Name VARCHAR(32),\n" +
                    "    Date Datetime, Phone1 varchar(15), Phone2 varchar(15));";

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static String createCustomerInsertSQL(ArrayList<Customer> people){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO customer (ID, Name, Date, Phone1, Phone2) VALUES");

        /**
         * For each Brand append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last Brand add a comma to seperate
         *
         * If it is the last Brand add a semi-colon to end the statement
         */
        for(int i = 0; i < people.size(); i++){
        	Customer p = people.get(i);
            sb.append(String.format(" (\'%s\',\'%s\',\'%s\')",
                    p.getId(), p.getName(), p.getDate(), p.getPhone1(), p.getPhone2()));
            if( i != people.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public static void addCustomer(Connection conn,
                                int id, String name, Timestamp date, String p1, String p2){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO customer "
                        + "VALUES(\'%s\',\'%s\',\'%s\',\'%s\',\'%s\');",
                id, name, date, p1, p2);
        try {
            /**
             * create and execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateCustomerTable(Connection conn, int id, String name, Timestamp date, String p1, String p2){
        StringBuilder updateQuery = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        updateQuery.append(String.format("UPDATE customer SET Name=\'%s\', Date=\'%s\', Phone1=\'%s\', Phone2=\'%s\'", name, date, p1, p2));
        updateQuery.append(String.format("WHERE ID = %d", id));
        updateQuery.append(";");

        try {
            /**
             * create and execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(updateQuery.toString());
        } catch (SQLException e) {
            System.err.println("Customer Update SQL Query failed.");
            System.err.println(String.format("Name %s, Date %s, Phone1 %s, Phone2 %s", name, date, p1, p2));
            e.printStackTrace();
        }
    }

    /**
     * Makes a query to the Vendor table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryCustomerTable(Connection conn,
                                            ArrayList<String> columns,
                                            ArrayList<String> whereClauses){
        StringBuilder sb = new StringBuilder();

        /**
         * Start the select query
         */
        sb.append("SELECT ");

        /**
         * If we gave no columns just give them all to us
         *
         * other wise add the columns to the query
         * adding a comma top seperate
         */
        if(columns.isEmpty()){
            sb.append("* ");
        }
        else{
            for(int i = 0; i < columns.size(); i++){
                if(i != columns.size() - 1){
                    sb.append(columns.get(i) + ", ");
                }
                else{
                    sb.append(columns.get(i) + " ");
                }
            }
        }

        /**
         * Tells it which table to get the data from
         */
        sb.append("FROM Customer ");

        /**
         * If we gave it conditions append them
         * place an AND between them
         */
        if(!whereClauses.isEmpty()){
            sb.append("WHERE ");
            for(int i = 0; i < whereClauses.size(); i++){
                if(i != whereClauses.size() -1){
                    sb.append(whereClauses.get(i) + " AND ");
                }
                else{
                    sb.append(whereClauses.get(i));
                }
            }
        }

        /**
         * close with semi-colon
         */
        sb.append(";");

        //Print it out to verify it made it right
        System.out.println("Query: " + sb.toString());
        try {
            /**
             * Execute the query and return the result set
             */
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Queries and print the table
     * @param conn
     */
    public static void printCustomerTable(Connection conn){
        String query = "SELECT * FROM Customer;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("ID %s , Name %s, Date %s, Phone1 %s, Phone2 %s \n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
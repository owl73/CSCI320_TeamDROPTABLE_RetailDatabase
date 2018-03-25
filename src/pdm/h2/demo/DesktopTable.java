package pdm.h2.demo;

import pdm.h2.demo.objects.*;
import pdm.h2.demo.objects.Products.*;
import pdm.h2.demo.objects.Products.Computers.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Daniyal Iqbal on 3/22/2018.
 */
public class DesktopTable {


    public static void populateDesktopTableFromCSV(Connection conn,
                                                    String fileName)
            throws SQLException {

        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Desktop> desktops = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                desktops.add(new Desktop(Integer.parseInt(split[0]), split[1], split[2]));
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
        String sql = createDesktopInsertSQL(desktops);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createDesktopInsertSQL(ArrayList<Desktop> desktops){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO Desktop (UPC, powersupply, card) VALUES");

        for(int i = 0; i < desktops.size(); i++){
            Desktop c = desktops.get(i);
            sb.append(String.format(" (%d, \'%s\', \'%s\')",
                    c.getUPC(), c.getPowersupply(), c.getCard()));
            if( i != desktops.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public static void addDesktop(Connection conn,
                                   int UPC,
                                   String powersupply,
                                   String card) {

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO Desktop "
                        + "VALUES(%d, \'%s\', \'%s\');",
                UPC, powersupply, card);
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

    public static void createDesktopTable(Connection conn) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Desktop ( \n" +
                    "    UPC INT PRIMARY KEY AUTO_INCREMENT NOT NULL, PowerSupply VARCHAR(32),\n" +
                    "    Card varchar(32));";

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateDesktopTable(Connection conn,
                                           int UPC,
                                           String powersupply,
                                           String card) {

        StringBuilder updateQuery = new StringBuilder();

        updateQuery.append(String.format("UPDATE Desktop SET " +
                        "powersupply = \'%s\'," +
                        "card = \'%s\'",
                powersupply, card));
        updateQuery.append(String.format("WHERE UPC = %d", UPC));
        updateQuery.append(";");

        try {
            /**
             * create and execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(updateQuery.toString());
        }
        catch (SQLException e) {
            System.err.println("Desktop Update SQL Query failed.");
            System.err.println(String.format("UPC %d, powersupply %s, card %s", UPC, powersupply, card));
            e.printStackTrace();
        }

    }

    /**
     * Makes a query to the Desktop table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryDesktopTable(Connection conn,
                                               ArrayList<String> columns,
                                               ArrayList<String> whereClauses) {
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
        sb.append("FROM Desktop ");

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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Queries and print the table
     * @param conn
     */
    public static void printDesktopTable(Connection conn){
        String query = "SELECT * FROM Desktop;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("UPC %d , powersupply %s, card %s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

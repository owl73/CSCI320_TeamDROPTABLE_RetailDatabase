/**
 * Created by Qasim on 3/22/2018.
 */
package pdm.h2.demo;
import pdm.h2.demo.objects.Products.Monitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MonitorTable {

    public static void populateVendorTableFromCSV(Connection conn,
                                                 String fileName)
            throws SQLException {
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Monitor> monitors = new ArrayList<Monitor>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                monitors.add(new Monitor(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), split[3]));
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
        String sql = createMonitorInsertSQL(monitors);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createMonitorInsertSQL(ArrayList<Monitor> monitors){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO Monitor (UPC, Size, PPI, PanelType) VALUES");

        /**
         * For each Brand append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last Brand add a comma to seperate
         *
         * If it is the last Brand add a semi-colon to end the statement
         */
        for(int i = 0; i < monitors.size(); i++){
            Monitor p = monitors.get(i);
            sb.append(String.format(" (%d,%d,%d,\'%s\')",
                    p.getUPC(), p.getSize(), p.getPpi(), p.getPanal_type()));
            if( i != monitors.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public static void addMonitor(Connection conn,
                                int upc, int size, int ppi, String panel){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO Monitor "
                        + "VALUES(%d,%d,%d,\'%s\');",
                        upc, size, ppi, panel);
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

    public static void updateMonitorTable(Connection conn, int upc, int size, int ppi, String panel){
        StringBuilder updateQuery = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        updateQuery.append(String.format("UPDATE Monitor SET Size = %d, PPI = %d, PanelType = \'%s\' ", size, ppi, panel));
        updateQuery.append(String.format("WHERE UPC = %d", upc));
        updateQuery.append(";");

        try {
            /**
             * create and execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(updateQuery.toString());
        } catch (SQLException e) {
            System.err.println("Monitor Update SQL Query failed.");
            System.err.println(String.format("UPC = %d, Size = %d, PPI = %d, PanelType = \'%s\' \n", upc, size, ppi, panel));
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
    public static ResultSet queryMonitorTable(Connection conn,
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
        sb.append("FROM Monitor ");

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
    public static void printMonitorTable(Connection conn){
        String query = "SELECT * FROM Monitor;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("UPC %s Size %s PPI %s PanelType %s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
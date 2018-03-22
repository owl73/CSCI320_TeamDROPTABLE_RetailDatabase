/**
 * Created by Oscar on 3/22/2018.
 */

import objects.Brand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BrandTable {

    public static void populateBrandTableFromCSV(Connection conn,
                                                  String fileName)
            throws SQLException {
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Brand> people = new ArrayList<Brand>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                people.add(new Brand(split));
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
        String sql = createBrandInsertSQL(people);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }


    public static void createBrandTable(Connection conn) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS brand ( \n" +
                    "    Name VARCHAR(10) PRIMARY KEY,\n" +
                    "    Vendor VARCHAR(20));";

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBrand(Connection conn,
                                 String Name, String Vendor){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO brand "
                        + "VALUES(\'%s\',\'%s\');",
                Name, Vendor);
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

    /**
     * This creates an sql statement to do a bulk add of people
     *
     * @param people: list of Brand objects to add
     *
     * @return
     */
    public static String createBrandInsertSQL(ArrayList<Brand> brand){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO brand (Name, Vendor) VALUES");

        /**
         * For each Brand append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last Brand add a comma to seperate
         *
         * If it is the last Brand add a semi-colon to end the statement
         */
        for(int i = 0; i < brand.size(); i++){
            Brand p = brand.get(i);
            sb.append(String.format(" (\'%s\',\'%s\')",
                    p.getName(), p.getVendor()));
            if( i != brand.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public static void updateBrandTable(Connection conn, String Name, String newVendor){
        StringBuilder updateQuery = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        updateQuery.append(String.format("UPDATE brand SET Vendor = \'%s\' ", newVendor));
        updateQuery.append(String.format("WHERE Name = \'%s\'", Name));
        updateQuery.append(";");

        try {
            /**
             * create and execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(updateQuery.toString());
        } catch (SQLException e) {
            System.err.println("Brand Update SQL Query failed.");
            System.err.println(String.format("Brand %s, Vendor %s", Name, newVendor));
            e.printStackTrace();
        }



    }

    /**
     * Makes a query to the Brand table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryBrandTable(Connection conn,
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
        sb.append("FROM brand ");

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
    public static void printBrandTable(Connection conn){
        String query = "SELECT * FROM brand;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("Brand %s , Vendor %s\n",
                        result.getString(1),
                        result.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
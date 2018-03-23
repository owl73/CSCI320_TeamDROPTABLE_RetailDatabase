package pdm.h2.demo;

import pdm.h2.demo.objects.*;
import pdm.h2.demo.objects.Products.*;
import pdm.h2.demo.objects.Products.Computers.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Daniyal Iqbal on 3/22/2018.
 */
public class ComputerTable {


    public static void populateComputerTableFromCSV(Connection conn,
                                                    String fileName)
            throws SQLException {

        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Computer> computers = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                computers.add(new Computer(Integer.parseInt(split[0]), Integer.parseInt(split[1]), split[2], split[3]));
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
        String sql = createComputerInsertSQL(computers);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createComputerInsertSQL(ArrayList<Computer> computers){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO Computer (UPC, ram, processor, disk) VALUES");

        for(int i = 0; i < computers.size(); i++){
            Computer c = computers.get(i);
            sb.append(String.format(" (%d, %d, \'%s\', \'%s\')",
                    c.getUPC(), c.getRam(), c.getProcessor(), c.getDisk()));
            if( i != computers.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public static void addComputer(Connection conn,
                                   int UPC,
                                   int ram,
                                   String processor,
                                   String disk) {

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO Computer "
                        + "VALUES(%d, %d, \'%s\', \'%s\');",
                UPC, ram, processor, disk);
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

    public static void updateComputerTable(Connection conn,
                                           int UPC,
                                           int ram,
                                           String processor,
                                           String disk) {

        StringBuilder updateQuery = new StringBuilder();

        updateQuery.append(String.format("UPDATE Computer SET " +
                        "ram = %d, " +
                        "processor = \'%s\'," +
                        "disk = \'%s\'",
                ram, processor, disk));
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
            System.err.println("Computer Update SQL Query failed.");
            System.err.println(String.format("UPC %d, ram %d, processor %s, disk %s", UPC, ram, processor, disk));
            e.printStackTrace();
        }

    }

    /**
     * Makes a query to the Computer table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryComputerTable(Connection conn,
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
        sb.append("FROM Computer ");

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
    public static void printComputerTable(Connection conn){
        String query = "SELECT * FROM Computer;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("UPC %d , ram %d, processor %s, disk %s\n",
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

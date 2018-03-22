import objects.Store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Oscar on 3/22/2018.
 */
public class StoreTable {

    public static void populateStoreTableFromCSV(Connection conn,
                                                  String fileName)
            throws SQLException {
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Store> stores = new ArrayList<Store>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                stores.add(new Store(split[0], Date.valueOf(split[1]), Double.parseDouble(split[2]), split[3]));
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
        String sql = createStoreInsertSQL(stores);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createStoreInsertSQL(ArrayList<Store> store){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO Store (Phone_Number, Date_Opened, Budget, Address) VALUES");

        /**
         * For each Store append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last Store add a comma to seperate
         *
         * If it is the last Store add a semi-colon to end the statement
         */
        for(int i = 0; i < store.size(); i++){
            Store p = store.get(i);
            sb.append(String.format(" (\'%s\',DATE(\'%s\'),%f,\'%s\')",
                   p.getPhone(), p.getDate_opened().toString(), p.getBudget(), p.getAddress()));
            if( i != store.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public static void addStore(Connection conn,
                                 String phone, Date date, double budget, String address){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO Store "
                        + "VALUES(\'%s\',DATE(\'%s\'),%f,\'%s\');",
                phone, date.toString(), budget, address);
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

    public static void updateStoreTable(Connection conn, int id, String phone, double budget){

        StringBuilder updateQuery = new StringBuilder();

        updateQuery.append(String.format("UPDATE Store SET " +
                "Phone = \'%s\', " +
                "Budget = %f", phone, budget));
        updateQuery.append(String.format("WHERE ID = %d", id));
        updateQuery.append(";");

        try {
            /**
             * create and execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(updateQuery.toString());
        } catch (SQLException e) {
            System.err.println("Store Update SQL Query failed.");
            System.err.println(String.format("Store %d, Phone %s, Budget %f", id, phone, budget));
            e.printStackTrace();
        }



    }

    /**
     * Makes a query to the Store table
     * with given columns and conditions
     *
     * @param conn
     * @param columns: columns to return
     * @param whereClauses: conditions to limit query by
     * @return
     */
    public static ResultSet queryStoreTable(Connection conn,
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
        sb.append("FROM Store ");

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
    public static void printStoreTable(Connection conn){
        String query = "SELECT * FROM Store;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("Store %s , Phone %s, DateOpened %s, Budget %s, Address %s\n",
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

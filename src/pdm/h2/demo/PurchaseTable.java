/**
 * Created by Qasim on 3/22/2018.
 */
package pdm.h2.demo;
import pdm.h2.demo.objects.Purchase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PurchaseTable {

    public static void populatePurchaseTableFromCSV(Connection conn,
                                                 String fileName)
            throws SQLException, ParseException {
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
            	Date parsedTimeStamp = (Date) dateFormat.parse(split[1]);
            	Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
            	purchases.add(new Purchase(Integer.parseInt(split[0]), timestamp, Double.parseDouble(split[2]),split[3], 
                		Integer.parseInt(split[4]), Integer.parseInt(split[5])));
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
        String sql = createPurchaseInsertSQL(purchases);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createPurchaseInsertSQL(ArrayList<Purchase> purchases){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO Purchase (ID, Date, Total, paymentMethod, storeID, customerID) VALUES");

        /**
         * For each Brand append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last Brand add a comma to seperate
         *
         * If it is the last Brand add a semi-colon to end the statement
         */
        for(int i = 0; i < purchases.size(); i++){
            Purchase p = purchases.get(i);
            sb.append(String.format(" (%d,\'%s\',\'%s\',\'%s\',%d,%d)",
                    p.getID(), p.getDate(), p.getTotal(), p.getPaymentMethod(), p.getStoreID(), p.getCustomerID()));
            if( i != purchases.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public static void addPurchase(Connection conn,
                                int id, Timestamp date, double tot, String payment, int s_id, int c_id){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO Purchase "
                        + "VALUES(%d,\'%s\',\'%s\',\'%s\',%d,%d);",
                        id, date, tot, payment, s_id, c_id);
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

    public static void updatePurchaseTable(Connection conn, int id, Timestamp date, double tot, String payment, int s_id, int c_id){
        StringBuilder updateQuery = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        updateQuery.append(String.format("UPDATE Purchase SET Date = \'%s\', Total = \'%s\' , paymentType = \'%s\', StoreID = %d, CustomerID = %d",
        		date, tot, payment, s_id, c_id));
        updateQuery.append(String.format("WHERE ID = %d", id));
        updateQuery.append(";");

        try {
            /**
             * create and execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(updateQuery.toString());
        } catch (SQLException e) {
            System.err.println("Purchase Update SQL Query failed.");
            System.err.println(String.format("ID %d Date \\'%s\\' Total \\'%s\\' paymentType \\'%s\\' StoreID %d CustomerID %d",
            			id, date, tot, payment, s_id, c_id));
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
    public static ResultSet queryPurchaseTable(Connection conn,
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
        sb.append("FROM Purchase ");

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
    public static void printVendorTable(Connection conn){
        String query = "SELECT * FROM Purchase;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("ID %d Date %s Total %s paymentType %s StoreID %d CustomerID %d\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
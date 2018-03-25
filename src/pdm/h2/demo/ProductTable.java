package pdm.h2.demo;
import pdm.h2.demo.objects.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Qasim on 3/22/2018.
 */
public class ProductTable {

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
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                products.add(new Product(Integer.parseInt(split[0]), split[1], split[2]));
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
        String sql = createProductInsertSQL(products);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createProductInsertSQL(ArrayList<Product> product){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO Product (UPC, Desc, Brand) VALUES");

        /**
         * For each Store append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last Store add a comma to seperate
         *
         * If it is the last Store add a semi-colon to end the statement
         */
        for(int i = 0; i < product.size(); i++){
            Product p = product.get(i);
            sb.append(String.format(" (%d,\'%s\',\'%s\')",
                   p.getUPC(), p.getDesc(), p.getBrand()));
            if( i != product.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }

        return sb.toString();
    }

    public static void createProductTable(Connection conn) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Product ( \n" +
                    "    UPC INT PRIMARY KEY AUTO_INCREMENT NOT NULL, Desc VARCHAR(32),\n" +
                    "    Brand VARCHAR(32));";

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void addProduct(Connection conn,
                                 int UPC, String desc, String brand){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO Product "
                        + "VALUES(%d,\'%s\',\'%s\');",
                UPC, desc, brand);
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

    public static void updateProductTable(Connection conn, int UPC, String desc, String brand){

        StringBuilder updateQuery = new StringBuilder();

        updateQuery.append(String.format("UPDATE Product SET " +
                "Desc = \'%s\', " +
                "Brand = \'%s\'", desc, brand));
        updateQuery.append(String.format("WHERE UPC = %d", UPC));
        updateQuery.append(";");

        try {
            /**
             * create and execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(updateQuery.toString());
        } catch (SQLException e) {
            System.err.println("Product Update SQL Query failed.");
            System.err.println(String.format("UPC %d Desc \'%s\' Brand \'%s\'", UPC, desc, brand));
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
    public static ResultSet queryProductTable(Connection conn,
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
        sb.append("FROM Product ");

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
    public static void printProductTable(Connection conn){
        String query = "SELECT * FROM Product;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("UPC %s , Desc %s, Brand %s\n",
                        result.getString(1),
                        result.getString(2),
                        result.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    
    
    
}

package pdm.h2.demo;

import java.io.IOException;
import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class RetailCMI {

    //The connection to the database
    private Connection conn;

    /**
     * Create a database connection with the given params
     * @param location: path of where to place the database
     * @param user: user name for the owner of the database
     * @param password: password of the database owner
     */
    public void createConnection(String location,
                                 String user,
                                 String password){
        try {

            //This needs to be on the front of your location
            String url = "jdbc:h2:" + location;

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            conn = DriverManager.getConnection(url,
                    user,
                    password);
        } catch (SQLException e) {
            System.err.println("SQL threw and error when getting connection.");
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            System.err.println("Could not find Class when getting connection.");
            e.printStackTrace();
        }
    }

    /**
     * just returns the connection
     * @return: returns class level connection
     */
    public Connection getConnection(){
        return conn;
    }

    /**
     * Used for exiting the program
     */
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts and runs the database
     * @param args: not used but you can use them
     */
    public static void main(String[] args) throws Exception{

        RetailCMI demo = new RetailCMI();

        //Hard drive location of the database
        String location = "./db_location/h2demo";
        String user = "scj";
        String password = "password";
        System.out.println("Yeahhh");
        //Create the database connections, basically makes the database
        demo.createConnection(location, user, password);
        
        //Create the reader for command line arguments
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String command = "";

        BrandTable.createBrandTable(demo.getConnection());
        BrandTable.printBrandTable(demo.getConnection());
        
        while(true){

            try{
                command = br.readLine();
                String[] comArgs = command.split(" ");
                int argc = comArgs.length;
                
                if (comArgs[0].toLowerCase().equals("add")){
                    if (comArgs[1].toLowerCase().equals("store")){
                        // todo add store
                    	System.out.println("Yeahhh");
                    }
                }
                else if (comArgs[0].toLowerCase().equals("q"))
                {
                	demo.getConnection().close();
                	System.exit(0);
                }

            } catch (IOException e){
                System.err.println("Could not read last command, please try again.");
                e.printStackTrace();
            }

        }
    }
}

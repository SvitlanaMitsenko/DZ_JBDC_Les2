package org.example.withDAO.DAOImpl;

import org.example.withDAO.DAO.ICookieDAO;
import org.example.withDAO.DAO.ICandyDAO;
import org.example.withDAO.DAO.IDAOFactory;
import org.example.withDAO.DAO.IPresentDAO;

import java.sql.*;

public class DAOFactory implements IDAOFactory {
    private static final String nameDB = "presentdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost/presentdb";
    private static final String URL_BEGIN = "jdbc:mysql://localhost";
    private static Connection connection = null;
    private boolean fillTablesNeed = true;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        getConnection();
    }

    @Override
    public ICandyDAO getCandyDAO() {
        return new CandyJDBCDao(USERNAME, PASSWORD, URL);
    }

    @Override
    public ICookieDAO getCookieDAO() {
        return null;
    }
    @Override
    public IPresentDAO getPresentDAO() {return new PresentJDBCDao(USERNAME, PASSWORD, URL);
    }
    private static IDAOFactory factory;

    private DAOFactory() {
    }

    public static synchronized IDAOFactory getInstance() {
        if (factory == null) {
            factory = new DAOFactory();
        }
        return factory;
    }


    public static void createDB() {

        try {
            connection = DriverManager.getConnection(URL_BEGIN, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + nameDB + ";");
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null && statement != null) {

                try {
                    connection.close();
                    connection = null;
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void createTables() {
        getConnectionWithDBName();
        Statement statement = null;

        String tableShape = "CREATE TABLE Shape\n" +
                "(\nid INT AUTO_INCREMENT NOT NULL primary key,\n" +
                "name NVARCHAR(30) UNIQUE\n);";
        String tableWrapper = "CREATE TABLE Wrapper\n" +
                "(\nid INT AUTO_INCREMENT NOT NULL primary key,\n" +
                "name NVARCHAR(30) UNIQUE\n);";
        String tableCandyType = "CREATE TABLE CandyType\n" +
                "(\nid INT AUTO_INCREMENT NOT NULL primary key,\n" +
                "name NVARCHAR(30) UNIQUE\n);";
        String tableFilling = "CREATE TABLE Filling\n" +
                "(\nid INT AUTO_INCREMENT NOT NULL primary key,\n" +
                "name NVARCHAR(30) UNIQUE\n);";
        String tableCandies = "CREATE TABLE Candies \n" +
                "(\n" +
                "    id INT AUTO_INCREMENT NOT NULL primary key,\n" +
                "     name NVARCHAR(30),\n" +
                "     sugarContent FLOAT(4,2),\n" +
                "\t weightOneItem FLOAT(5,2),\n" +
                "     stickLength smallint, \n" +
                "     shape_id INT NOT NULL, FOREIGN KEY (shape_id) REFERENCES Shape(id),\n" +
                "\t wrapper_id INT NOT NULL, FOREIGN KEY (wrapper_id) REFERENCES Wrapper(id),\n" +
                "     candytype_id INT NOT NULL, FOREIGN KEY (candytype_id) REFERENCES CandyType(id),\n" +
                "     filling_id INT NOT NULL, FOREIGN KEY (filling_id) REFERENCES Filling(id),\n" +
                "     glaze_id INT, FOREIGN KEY (glaze_id) REFERENCES Filling(id)        \n" +
                ");";
        String tableCookies = "CREATE TABLE Cookies \n" +
                "(\n" +
                "    id INT AUTO_INCREMENT NOT NULL primary key,\n" +
                "     name NVARCHAR(30),\n" +
                "     sugarContent FLOAT(4,2),\n" +
                "\t weightOneItem FLOAT(5,2),    \n" +
                "     shape_id INT NOT NULL, FOREIGN KEY (shape_id) REFERENCES Shape(id),\n" +
                "\t ingredients NVARCHAR(100) \n" +
                ");";
        String tablePresents = "CREATE TABLE Presents\n" +
                "(\n" +
                "\tid INT AUTO_INCREMENT NOT NULL primary key,\n" +
                "    name NVARCHAR(30) UNIQUE\n" +
                ");";
        String tablePresentItems = "CREATE TABLE PresentItems \n" +
                "(\n" +
                "\tid INT AUTO_INCREMENT NOT NULL primary key,\n" +
                "  present_id INT NOT NULL, FOREIGN KEY (present_id) REFERENCES Presents(id),\n" +
                "  candy_id INT, FOREIGN KEY (candy_id) REFERENCES Candies(id),\n" +
                "  cookie_id INT, FOREIGN KEY (cookie_id) REFERENCES Cookies(id),     \n" +
                "  weight FLOAT(5,2)  \n" +
                "  );";
        try {
            statement = connection.createStatement();
            statement.addBatch(tableShape);
            statement.addBatch(tableWrapper);
            statement.addBatch(tableCandyType);
            statement.addBatch(tableFilling);
            statement.addBatch(tableCandies);
            statement.addBatch(tableCookies);
            statement.addBatch(tablePresents);
            statement.addBatch(tablePresentItems);
            statement.executeBatch();
            System.out.println("Tables created successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null && statement != null) {

                try {
                    connection.close();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void fillTables() {
        getConnectionWithDBName();
        Statement statement = null;
        String tableShape = "INSERT INTO Shape\n" +
                "(name) \n" +
                "VALUES ('CUBE'), ('PYRAMID'), ('OVAL'), ('SPHERE'), ('RECTANGLE'), ('STAR'), ('CIRCLE');";

        String tableWrapper = "INSERT INTO Wrapper\n" +
                "(name) \n" +
                "VALUES ('Paper'), ('Foil'), ('Transparent');";

        String tableCandyType = "INSERT INTO  CandyType\n" +
                "(name) \n" +
                "VALUES ('CHOCOLATE'), ('JELLY'), ('LOLLIPOP');\n";

        String tableFilling = "INSERT INTO Filling\n" +
                "(name) \n" +
                "VALUES ('MO'), ('MILK_CHOCOLATE'), ('DARK_CHOCOLATE'), ('DRAGEE'), ('WAFFLES'), ('HAZELNUT'), ('ALMOND'), ('LIQUOR'), ('JELLY'), ('LOLLIPOP'), ('CARAMEL');";
        String tableCandies = "INSERT INTO Candies\n" +
                "(name, sugarContent, weightOneItem, stickLength, shape_id, wrapper_id, candytype_id, filling_id, glaze_id)\n" +
                "VALUES  ('Bee', 30, 10, 0, 1, 3, 2, 9, 1),\n" +
                "\t\t('Chocolate night', 50, 20, 0, 1, 3, 2, 6, 1),\n" +
                "\t\t('Chupa-Chups', 20, 30, 15, 4, 1, 3, 10, 1),\n" +
                "\t\t('Barbaris', 20.5, 8, 0, 3, 1, 3, 10, 1),\n" +
                "        ('Egypt', 23.5, 27, 0, 2, 2, 1, 8, 2);";
        String tableCookies = "INSERT INTO Cookies\n" +
                "(name, sugarContent, weightOneItem, shape_id, ingredients)\n" +
                "VALUES  ('Biscuit', 10, 16, 5, 'Butter, nuts'),\n" +
                "\t\t('Star', 10, 23, 6, 'Butter, palm oil'),\n" +
                "\t\t('Honey', 25, 30, 5, 'Butter, nuts, honey'),\n" +
                "\t\t('Nuts', 40, 40, 5, 'Butter, nuts, milk'),\n" +
                "        ('Napoleon', 30, 20, 7, 'Butter, sour cream');";
        String tablePresents = "INSERT INTO  Presents\n" +
                "(name) \n" +
                "VALUES ('Present 1'), ('Present 2');";

        String tablePresentItems = "INSERT INTO PresentItems\n" +
                "(present_id, candy_id,  cookie_id, weight)\n" +
                "VALUES  (1, 1, null, 0.5),\n" +
                "\t\t(1, 2, null, 0.2),\n" +
                "\t\t(1, 5, null, 0.5),\n" +
                "\t\t(1, null, 1, 0.4),\n" +
                "        (1, null, 4, 0.6),\n" +
                "\t\t(2, 3, null, 0.5),\n" +
                "        (2, 4, null, 0.4),\n" +
                "\t\t(2, null, 2, 0.3),\n" +
                "        (2, null, 3, 0.4),\n" +
                "\t\t(2, null, 5, 0.3);";

        try {
            statement = connection.createStatement();
            statement.addBatch(tableShape);
            statement.addBatch(tableWrapper);
            statement.addBatch(tableCandyType);
            statement.addBatch(tableFilling);
            statement.addBatch(tableCandies);
            statement.addBatch(tableCookies);
            statement.addBatch(tablePresents);
            statement.addBatch(tablePresentItems);
            statement.executeBatch();
            System.out.println("Tables filled successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null && statement != null) {

                try {
                    connection.close();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            //  e.printStackTrace();
            createDB();
            createTables();
            fillTables();
        }
    }

    private static void getConnectionWithDBName() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}



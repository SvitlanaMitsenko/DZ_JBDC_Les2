package org.example.withDAO.DAOImpl;

import org.example.withDAO.DAO.IPresentDAO;
import org.example.withDAO.Entities.Candy;
import org.example.withDAO.Entities.Cookie;
import org.example.withDAO.Entities.Present;
import org.example.withDAO.Entities.Sweet;
import org.example.withDAO.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PresentJDBCDao implements IPresentDAO {
    private String USERNAME = "root";
    private String PASSWORD = "root";
    private String URL = "jdbc:mysql://localhost/presentdb";
    private Connection connection = null;

    public PresentJDBCDao(String userName, String password, String url) {
        this.USERNAME = userName;
        this.PASSWORD = password;
        this.URL = url;
    }
    @Override
    public List<Present> getFoolInfo() {
        getConnection();
        if (connection == null) return null;
        Statement statement = null;
        List<Present> presents;
        try {
            statement = connection.createStatement();
            String sql = "SELECT p.id AS item, present_id, candy_id, cookie_id, weight,\n" +
                    " p2.name AS name_present,\n" +
                    " c.*,\n" +
                    " co.name AS name_cookie, co.sugarContent AS sugarContent_cookie,\n" +
                    " co.weightOneItem AS weightOneItem_cookie, co.shape_id AS shape_id_cookie,\n" +
                    " co.ingredients\n" +
                    "FROM presentitems AS p\n" +
                    "LEFT JOIN presents AS p2\n" +
                    "ON p.present_id = p2.id \n" +
                    "LEFT JOIN candies AS c\n" +
                    "ON p.candy_id = c.id\n" +
                    "LEFT JOIN cookies AS co\n" +
                    "ON p.cookie_id = co.id ORDER BY p.id;\n";
            ResultSet resultSet = statement.executeQuery(sql);

            int present_id_tmp = 0;
            int present_id = 0;
            presents = new ArrayList<Present>();
            HashMap<Sweet, Float> presentItems = null;
            Present present = null;

            while (resultSet.next()) {
                present_id = resultSet.getInt("present_id");
                if (present_id != present_id_tmp) {
                    // sweets = new ArrayList<>();
                    presentItems = new HashMap<>();
                    present = new Present();
                    present.setPresentItems(presentItems);
                    present.setName(resultSet.getString("name_present"));
                    presents.add(present);
                }
                int candy_id = resultSet.getInt("candy_id");
                int cookie_id = resultSet.getInt("cookie_id");
                if (candy_id > 0) {
                    Candy candy = createCandyFromResultSet(resultSet);
                    presentItems.put(candy, resultSet.getFloat("weight"));
                }
                if (cookie_id > 0) {
                    Cookie cookie = createCookieFromResultSet(resultSet);
                    presentItems.put(cookie, resultSet.getFloat("weight"));
                }
                present_id_tmp = present_id;
            }
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
        return presents;
    }

    private void getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Candy createCandyFromResultSet(ResultSet resultSet) {
        Candy candy = new Candy();
        try {
            candy.setId(resultSet.getInt("candy_id"));
            candy.setName(resultSet.getString("name"));
            candy.setSugarContent(resultSet.getFloat("sugarContent"));
            candy.setWeightOneItem(resultSet.getFloat("WeightOneItem"));
            candy.setStickLength(resultSet.getShort("stickLength"));
            candy.setShape(Main.Shape.values()[resultSet.getInt("shape_id") - 1]);
            candy.setWrapper(Main.Wrapper.values()[resultSet.getInt("wrapper_id") - 1]);
            candy.setType(Main.CandyType.values()[resultSet.getInt("candyType_id") - 1]);
            candy.setFilling(Main.Filling.values()[resultSet.getInt("filling_id") - 1]);
            candy.setGlaze(Main.Filling.values()[resultSet.getInt("glaze_id") - 1]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candy;
    }

    private Cookie createCookieFromResultSet(ResultSet resultSet) {
        Cookie cookie = new Cookie();
        try {
            cookie.setId(resultSet.getInt("cookie_id"));
            cookie.setName(resultSet.getString("name_cookie"));
            cookie.setSugarContent(resultSet.getFloat("sugarContent_cookie"));
            cookie.setWeightOneItem(resultSet.getFloat("WeightOneItem_cookie"));

            cookie.setShape(Main.Shape.values()[resultSet.getInt("shape_id_cookie") - 1]);
            cookie.setIngredients(resultSet.getString("ingredients"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cookie;
    }
}

package org.example.withDAO.DAOImpl;


import org.example.withDAO.DAO.ICandyDAO;
import org.example.withDAO.Entities.Candy;
import org.example.withDAO.Main;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandyJDBCDao implements ICandyDAO {
    private String USERNAME = "root";
    private String PASSWORD = "root";
    private String URL = "jdbc:mysql://localhost/presentdb";
    private Connection connection = null;

    public CandyJDBCDao(String userName, String password, String url) {
        this.USERNAME = userName;
        this.PASSWORD = password;
        this.URL = url;
    }

    @Override
    public void add(Candy candy) {
        getConnection();
        PreparedStatement preparedStatement = null;
        if (connection == null) return;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO candies VALUES (?,?,?,?,?,?,?,?,?,?)");
            setCandyInPreparedStatement(preparedStatement, candy, false);
            int res = preparedStatement.executeUpdate();
            String str = (res > 0) ? "Цукерка успішно записана до таблиці БД" : "Щось пішло не так!";
            System.out.println(str);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null && preparedStatement != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Candy getById(int id) {
        getConnection();
        if (connection == null) return null;
        Candy candy = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM candies WHERE id = ? ");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            candy = createCandyFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null && preparedStatement != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return candy;
        }
    }

    @Override
    public List<Candy> getAll() {
        List<Candy> candies = new ArrayList<>();

        getConnection();
        if (connection == null) return null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from candies");
            while (resultSet.next()) {
                candies.add(createCandyFromResultSet(resultSet));
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
        return candies;
    }


    @Override
    public void updateSugareContent(float sugareContent, int candyId) {
        getConnection();
        PreparedStatement preparedStatement = null;
        if (connection == null) return;
        try {
            preparedStatement = connection.prepareStatement("UPDATE candies SET sugarContent=? WHERE id=?");
            preparedStatement.setFloat(1, sugareContent);
            preparedStatement.setInt(2, candyId);
            int res = preparedStatement.executeUpdate();
            String str = (res > 0) ? "Вміст цукру у Цукерки з id=" + candyId + " успішно оновлена в таблиці БД" : "Щось пішло не так!";
            System.out.println(str);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null && preparedStatement != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(Candy candy) {
        getConnection();
        PreparedStatement preparedStatement = null;
        if (connection == null) return;
        try {
            preparedStatement = connection.prepareStatement("UPDATE candies SET name=?, sugarContent=?, weightOneItem=?, stickLength=?, shape_id=?, wrapper_id=?, candytype_id=?, filling_id=?, glaze_id=? WHERE id=?");
            setCandyInPreparedStatement(preparedStatement, candy, true);
            int res = preparedStatement.executeUpdate();
            String str = (res > 0) ? "Цукерка з id=" + candy.getId() + "успішно оновлена в таблиці БД" : "Щось пішло не так!";
            System.out.println(str);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null && preparedStatement != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        getConnection();
        if (connection == null) return;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM candies WHERE id = ? ");
            preparedStatement.setInt(1, id);
            int res = preparedStatement.executeUpdate();
            String str = (res > 0) ? "Цукерка з id=" + id + " " + "успішно видалена з таблиці БД" : "Щось пішло не так!";
            System.out.println(str);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null && preparedStatement != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
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
            candy.setId(resultSet.getInt(1));
            candy.setName(resultSet.getString("name"));
            candy.setSugarContent(resultSet.getFloat(3));
            candy.setWeightOneItem(resultSet.getFloat(4));
            candy.setStickLength(resultSet.getShort(5));
            candy.setShape(Main.Shape.values()[resultSet.getInt(6) - 1]);
            candy.setWrapper(Main.Wrapper.values()[resultSet.getInt(7) - 1]);
            candy.setType(Main.CandyType.values()[resultSet.getInt(8) - 1]);
            candy.setFilling(Main.Filling.values()[resultSet.getInt(9) - 1]);
            candy.setGlaze(Main.Filling.values()[resultSet.getInt(10) - 1]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candy;
    }

    private void setCandyInPreparedStatement(PreparedStatement preparedStatement, Candy candy, boolean idLast) {
        int num = 1;
        try {
            if (idLast == false) preparedStatement.setInt(num++, candy.getId());
            preparedStatement.setString(num++, candy.getName());
            preparedStatement.setFloat(num++, candy.getSugarContent());
            preparedStatement.setFloat(num++, candy.getWeightOneItem());
            preparedStatement.setShort(num++, candy.getStickLength());
            preparedStatement.setInt(num++, candy.getShape().ordinal() + 1);
            preparedStatement.setInt(num++, candy.getWrapper().ordinal() + 1);
            preparedStatement.setInt(num++, candy.getType().ordinal() + 1);
            preparedStatement.setInt(num++, candy.getFilling().ordinal() + 1);
            preparedStatement.setInt(num++, candy.getGlaze().ordinal() + 1);
            if (idLast == true) preparedStatement.setInt(num++, candy.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

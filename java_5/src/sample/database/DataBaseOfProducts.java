package sample.database;

import javafx.collections.ObservableList;
import sample.pojo.Product;

import java.sql.*;

public class DataBaseOfProducts {

  private Connection connectionToDataBase;

  public DataBaseOfProducts() throws SQLException, ClassNotFoundException {
    String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String url = "jdbc:mysql://localhost:3306/mysql_db" + timezone;
    String username = "root";
    String password = "1234";
    String driver = "com.mysql.cj.jdbc.Driver";
    Class.forName(driver);
    this.connectionToDataBase = DriverManager.getConnection(url, username, password);
    Statement statement = connectionToDataBase.createStatement();
    statement.executeUpdate("DROP TABLE IF EXISTS products");
    statement.executeUpdate("CREATE TABLE IF NOT EXISTS products(id INTEGER NOT NULL, prodId INTEGER NOT NULL, title CHAR(30) NOT NULL, price INTEGER NOT NULL)");
    statement.close();
  }

  public boolean addProduct(int id, int prodId, String title, int price) throws SQLException {
    PreparedStatement preparedStatement = connectionToDataBase.prepareStatement("SELECT * FROM products WHERE title = ?");
    preparedStatement.setString(1, title);
    ResultSet resultSet = preparedStatement.executeQuery();
    if(!resultSet.next()) {
      preparedStatement = connectionToDataBase.prepareStatement("INSERT INTO products (id, prodId, title, price) VALUES (?, ?, ?, ?)");
      preparedStatement.setInt(1, id);
      preparedStatement.setInt(2, prodId);
      preparedStatement.setString(3, title);
      preparedStatement.setInt(4, price);
      preparedStatement.executeUpdate();
      resultSet.close();
      preparedStatement.close();
      return true;
    } else {
      resultSet.close();
      preparedStatement.close();
      return false;
    }
  }

  public boolean removeProduct(String title) throws SQLException {
    PreparedStatement preparedStatement = connectionToDataBase.prepareStatement("SELECT * FROM products WHERE title = ?");
    preparedStatement.setString(1, title);
    ResultSet resultSet = preparedStatement.executeQuery();
    if(resultSet.next()) {
      preparedStatement = connectionToDataBase.prepareStatement("DELETE FROM products WHERE title = ?");
      preparedStatement.setString(1, title);
      preparedStatement.executeUpdate();
      resultSet.close();
      preparedStatement.close();
      return true;
    } else {
      resultSet.close();
      preparedStatement.close();
      return false;
    }
  }

  public void getAllProducts(ObservableList<Product> productsData) throws SQLException {
    Statement statement = connectionToDataBase.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
    while(resultSet.next()){
      productsData.add(new Product(resultSet.getInt("id"), resultSet.getInt("prodId"), resultSet.getString("title"), resultSet.getInt("price")));
    }
    resultSet.close();
    statement.close();
  }

  public boolean getPriceOfProduct(ObservableList<Product> productsData, String title) throws SQLException {
    PreparedStatement preparedStatement = connectionToDataBase.prepareStatement("SELECT * FROM products WHERE title = ?");
    preparedStatement.setString(1, title);
    ResultSet resultSet = preparedStatement.executeQuery();
    if(resultSet.next()) {
      productsData.add(new Product(resultSet.getInt("id"), resultSet.getInt("prodId"), resultSet.getString("title"), resultSet.getInt("price")));
      resultSet.close();
      preparedStatement.close();
      return true;
    } else {
      resultSet.close();
      preparedStatement.close();
      return false;
    }
  }

  public boolean changePriceOfProduct(String title, int price) throws SQLException {
    PreparedStatement preparedStatement = connectionToDataBase.prepareStatement("SELECT * FROM products WHERE title = ?");
    preparedStatement.setString(1, title);
    ResultSet resultSet = preparedStatement.executeQuery();
    if(resultSet.next()) {
      preparedStatement = connectionToDataBase.prepareStatement("UPDATE products SET price = ? WHERE title = ?");
      preparedStatement.setInt(1, price);
      preparedStatement.setString(2, title);
      preparedStatement.executeUpdate();
      resultSet.close();
      preparedStatement.close();
      return true;
    } else {
      resultSet.close();
      preparedStatement.close();
      return false;
    }
  }

  public void filterProductsByPrice(ObservableList<Product> productsData, int leftEdge, int rightEdge) throws SQLException {
    PreparedStatement preparedStatement = connectionToDataBase.prepareStatement("SELECT * FROM products WHERE (price >= ?) AND (price <= ? ) ");
    preparedStatement.setInt(1, leftEdge);
    preparedStatement.setInt(2, rightEdge);
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){
      productsData.add(new Product(resultSet.getInt("id"), resultSet.getInt("prodId"), resultSet.getString("title"), resultSet.getInt("price")));
    }
    resultSet.close();
    preparedStatement.close();
  }

}

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class DataBaseOfProducts {

    private static int prodIdCounter = 1;
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

    public void addProduct(String title, int price) throws SQLException {
        if(!checkTitleOfProduct(title)) {
            System.out.println("Title is incorrect");
            return;
        }

        PreparedStatement preparedStatement = connectionToDataBase.prepareStatement("SELECT * FROM products WHERE title = ?");
        preparedStatement.setString(1, title);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            System.out.println("There is already such product");
        } else {
            preparedStatement = connectionToDataBase.prepareStatement("INSERT INTO products (id, prodId, title, price) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, Math.abs(new Random().nextInt()));
            preparedStatement.setInt(2, prodIdCounter++);
            preparedStatement.setString(3, title);
            preparedStatement.setInt(4, price);
            preparedStatement.executeUpdate();
            System.out.println("Added " + " title : " + title + " price : " + price);
        }

        resultSet.close();
        preparedStatement.close();
    }

    public void removeProduct(String title) throws SQLException {
        if (!checkTitleOfProduct(title)) {
            System.out.println("Title of product is incorrect");
            return;
        }

        PreparedStatement preparedStatement = connectionToDataBase.prepareStatement("SELECT * FROM products WHERE title = ?");
        preparedStatement.setString(1, title);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            System.out.println("There is no such product");
        } else {
            preparedStatement = connectionToDataBase.prepareStatement("DELETE FROM products WHERE title = ?");
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Deleted " + " title : " + title);
        }

        resultSet.close();
        preparedStatement.close();
    }

    public void showAllProducts() throws SQLException {
        Statement statement = connectionToDataBase.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
        while(resultSet.next()){
            System.out.print("id : " + resultSet.getString("id"));
            System.out.print(" prodId : " + resultSet.getString("prodId"));
            System.out.print(" title : " + resultSet.getString("title"));
            System.out.println(" price : " + resultSet.getInt("price"));
        }
        resultSet.close();
        statement.close();
    }

    public void getPriceOfProduct(String title) throws SQLException {
        if(!checkTitleOfProduct(title)){
            System.out.println("Title of product is incorrect");
            return;
        }

        PreparedStatement preparedStatement = connectionToDataBase.prepareStatement("SELECT * FROM products WHERE title = ?");
        preparedStatement.setString(1, title);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            System.out.println("title : " + title + " price : " + resultSet.getString("price"));
        } else {
            System.out.println("There is no such product");
        }
        resultSet.close();
        preparedStatement.close();
    }

    public void changePriceOfProduct(String title, int price) throws SQLException {
        if(!checkTitleOfProduct(title)){
            System.out.println("Title is incorrect");
            return;
        }

        PreparedStatement preparedStatement = connectionToDataBase.prepareStatement("UPDATE products SET price = ? WHERE title = ?");
        preparedStatement.setInt(1, price);
        preparedStatement.setString(2, title);
        preparedStatement.executeUpdate();
        Statement statement = connectionToDataBase.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM products WHERE title = '" + title + "'");
        if(resultSet.next()) {
            System.out.println("Changed " + " title : " + title + " price : " + resultSet.getInt("price"));
        } else {
            System.out.println("There is no such product");
        }
        resultSet.close();
        preparedStatement.close();
    }

    public void filterByPrice(int leftEdge, int rightEdge) throws SQLException {
        PreparedStatement preparedStatement = connectionToDataBase.prepareStatement("SELECT * FROM products WHERE (price >= ?) AND (price <= ? ) ");
        preparedStatement.setInt(1, leftEdge);
        preparedStatement.setInt(2, rightEdge);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println("title : " + resultSet.getString("title") + " price : " + resultSet.getInt("price"));
        }
        resultSet.close();
        preparedStatement.close();
    }

    public void addKNumberOfProducts() throws SQLException {
        for (int i = 1; i <= 10; i++) {
            addProduct("product" + i, i * 10);
        }
    }

    private boolean checkTitleOfProduct(String title) {
        if(title == null || title.equals("")) {
            return false;
        }

        return true;
    }
}

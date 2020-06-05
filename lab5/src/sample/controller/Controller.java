package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;

import java.util.Random;
import java.sql.SQLException;

import sample.pojo.Product;
import sample.database.DataBaseOfProducts;

public class Controller {

    public TextField title;
    public TextField price;
    public TextField leftEdge;
    public TextField rightEdge;

    private ObservableList<Product> productsData = FXCollections.observableArrayList();
    private DataBaseOfProducts dataBaseOfProducts;

    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, Integer> prodIdColumn;

    @FXML
    private TableColumn<Product, String> titleColumn;

    @FXML
    private TableColumn<Product, Integer> priceColumn;

    public Controller() throws SQLException, ClassNotFoundException {
    }

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        dataBaseOfProducts = new DataBaseOfProducts();
        for (int i = 1; i <= 10; i++) {
            dataBaseOfProducts.addProduct(Math.abs(new Random().nextInt()), i, "pr" + i, i * 10);
        }
        dataBaseOfProducts.getAllProducts(productsData);
        idColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        prodIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("prodId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        tableProducts.setItems(productsData);
    }

    public void addProduct(ActionEvent actionEvent) throws SQLException {
        tableProducts.getItems().clear();
        productsData.clear();
        try {
            String titleText = title.getText();
            if (titleText == null || titleText.trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Can not add the product");
                alert.setHeaderText(null);
                alert.setContentText("Empty title");
                alert.showAndWait();
            } else {
                int priceText = Integer.parseInt(price.getText());
                if (!(dataBaseOfProducts.addProduct(Math.abs(new Random().nextInt()), Math.abs(new Random().nextInt()), titleText, priceText))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Can not add the product");
                    alert.setHeaderText(null);
                    alert.setContentText("There is already such product");
                    alert.showAndWait();
                }
            }
            dataBaseOfProducts.getAllProducts(productsData);
            tableProducts.setItems(productsData);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("NumberFormatException");
            alert.setHeaderText(null);
            alert.setContentText("Price must be a number");
            dataBaseOfProducts.getAllProducts(productsData);
            tableProducts.setItems(productsData);
            alert.showAndWait();
        }
    }

    public void deleteProduct(ActionEvent actionEvent) throws SQLException {
        tableProducts.getItems().clear();
        productsData.clear();
        String titleText = title.getText();
        if (titleText == null || titleText.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can not add the product");
            alert.setHeaderText(null);
            alert.setContentText("Empty title");
            alert.showAndWait();
        } else {
            if (!(dataBaseOfProducts.removeProduct(titleText))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Can not delete the product");
                alert.setHeaderText(null);
                alert.setContentText("There is no such product");
                alert.showAndWait();
            }
        }
        dataBaseOfProducts.getAllProducts(productsData);
        tableProducts.setItems(productsData);
    }

    public void getPriceOfProduct(ActionEvent actionEvent) throws SQLException {
        tableProducts.getItems().clear();
        productsData.clear();
        String titleText = title.getText();
        if (titleText == null || titleText.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Can not add the product");
            alert.setHeaderText(null);
            alert.setContentText("Empty title");
            alert.showAndWait();
        } else {
            if (!(dataBaseOfProducts.getPriceOfProduct(productsData, titleText))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Can not get the price of the product");
                alert.setHeaderText(null);
                alert.setContentText("There is no such product");
                tableProducts.getItems().clear();
                productsData.clear();
                dataBaseOfProducts.getAllProducts(productsData);
                alert.showAndWait();
            }
        }
        tableProducts.setItems(productsData);
    }

    public void changePriceOfProduct(ActionEvent actionEvent) throws SQLException {
        tableProducts.getItems().clear();
        productsData.clear();
        try {
            String titleText = title.getText();
            if (titleText == null || titleText.trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Can not add the product");
                alert.setHeaderText(null);
                alert.setContentText("Empty title");
                alert.showAndWait();
            } else {
                int priceText = Integer.parseInt(price.getText());
                if (!(dataBaseOfProducts.changePriceOfProduct(titleText, priceText))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Can not change the price of the product");
                    alert.setHeaderText(null);
                    alert.setContentText("There is no such product");
                    alert.showAndWait();
                }
            }
            dataBaseOfProducts.getAllProducts(productsData);
            tableProducts.setItems(productsData);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("NumberFormatException");
            alert.setHeaderText(null);
            alert.setContentText("Price must be a number");
            dataBaseOfProducts.getAllProducts(productsData);
            tableProducts.setItems(productsData);
            alert.showAndWait();
        }
    }

    public void filterProductsByPrice(ActionEvent actionEvent) throws SQLException {
        tableProducts.getItems().clear();
        productsData.clear();
        try {
            int leftEdgeText = Integer.parseInt(leftEdge.getText());
            int rightEdgeText = Integer.parseInt(rightEdge.getText());
            dataBaseOfProducts.filterProductsByPrice(productsData, leftEdgeText, rightEdgeText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("NumberFormatException");
            alert.setHeaderText(null);
            alert.setContentText("Both edges must be numbers");
            dataBaseOfProducts.getAllProducts(productsData);
            tableProducts.setItems(productsData);
            alert.showAndWait();
        }
    }

    public void refreshOutputData(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        tableProducts.getItems().clear();
        productsData.clear();
        dataBaseOfProducts.getAllProducts(productsData);
        tableProducts.setItems(productsData);
    }

    public void clearInputData(ActionEvent actionEvent) {
        title.setText("");
        price.setText("");
        leftEdge.setText("");
        rightEdge.setText("");
    }
}
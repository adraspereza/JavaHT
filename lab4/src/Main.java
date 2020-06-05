import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);

        try {
            DataBaseOfProducts dataBaseOfProducts = new DataBaseOfProducts();
            dataBaseOfProducts.addKNumberOfProducts();

            while(true) {
                String inputCommand = scanner.next();
                if(inputCommand.equals("exit")) {
                    break;
                }

                switch (inputCommand) {
                    case "add": {
                        try {
                            String titleOfProduct = scanner.next();
                            int priceOfProduct = scanner.nextInt();
                            dataBaseOfProducts.addProduct(titleOfProduct, priceOfProduct);
                        } catch (InputMismatchException e) {
                            System.out.println("Price is incorrect");
                        }
                        break;
                    }

                    case "delete": {
                        String titleOfProduct = scanner.next();
                        dataBaseOfProducts.removeProduct(titleOfProduct);
                        break;
                    }

                    case "showAll": {
                        dataBaseOfProducts.showAllProducts();
                        break;
                    }

                    case "price": {
                        String titleOfProduct = scanner.next();
                        dataBaseOfProducts.getPriceOfProduct(titleOfProduct);
                        break;
                    }

                    case "changePrice": {
                        try {
                            String titleOfProduct = scanner.next();
                            int priceOfProduct = scanner.nextInt();
                            dataBaseOfProducts.changePriceOfProduct(titleOfProduct, priceOfProduct);
                        } catch (InputMismatchException e) {
                            System.out.println("Price is incorrect");
                        }
                        break;
                    }

                    case "filterPrice": {
                        try {
                            int leftEdge = scanner.nextInt();
                            int rightEdge = scanner.nextInt();
                            dataBaseOfProducts.filterByPrice(leftEdge, rightEdge);
                        } catch (InputMismatchException e) {
                            System.out.println("One or two edges are incorrect");
                            scanner.reset();
                        }
                        break;
                    }
                }
            }
        } catch(SQLException e) {
            System.out.println("SQLException" + e);
            System.exit(1);
        }
    }
}

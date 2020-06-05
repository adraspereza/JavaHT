package sample.pojo;

public class Product {

    private int id;
    private int prodId;
    private String title;
    private int price;

    public Product(int id, int prodId, String title, int price) {
        this.id = id;
        this.prodId = prodId;
        this.title = title;
        this.price = price;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
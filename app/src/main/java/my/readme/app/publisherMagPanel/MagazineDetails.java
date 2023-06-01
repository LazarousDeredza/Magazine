package my.readme.app.publisherMagPanel;

public class MagazineDetails {

    public String title,quantity,price,description,imageURL,publisherid;
    // Alt+insert


    public MagazineDetails() {
    }

    public MagazineDetails(String title, String quantity, String price, String description, String imageURL, String publisherid) {
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
        this.publisherid = publisherid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPublisherid() {
        return publisherid;
    }

    public void setPublisherid(String publisherid) {
        this.publisherid = publisherid;
    }
}

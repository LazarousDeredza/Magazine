package my.readme.app.customerMagPanel;

public class UpdateMagazineModel {

    public String title,quantity,price,description,imageURL,publisherid;
    // Alt+insert
    String status;

    public UpdateMagazineModel(){

    }

    public UpdateMagazineModel(String title, String description, String quantity, String price, String imageURL, String publisherid) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.imageURL = imageURL;
        this.publisherid = publisherid;
    }

    public UpdateMagazineModel(String title, String description, String quantity, String price, String imageURL, String publisherid, String status) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.imageURL = imageURL;
        this.publisherid = publisherid;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


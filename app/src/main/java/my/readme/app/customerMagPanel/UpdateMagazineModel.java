package my.readme.app.customerMagPanel;

public class UpdateMagazineModel {

    String title,description,quantity,price,imageURL,publisherId;


    public UpdateMagazineModel(){

    }

    public UpdateMagazineModel(String title, String description, String quantity, String price, String imageURL, String publisherId) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.imageURL = imageURL;
        this.publisherId = publisherId;
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

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
}


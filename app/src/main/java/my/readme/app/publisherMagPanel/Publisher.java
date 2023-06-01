package my.readme.app.publisherMagPanel;

public class Publisher {

    private String area, city, confirmPassword, emailid, fname, house, lname, mobile, password, postcode, town;



    public Publisher() {
    }

    public Publisher(String area, String city, String confirmPassword, String emailid, String fname, String house, String lname, String mobile, String password, String postcode, String town) {
        this.area = area;
        this.city = city;
        this.confirmPassword = confirmPassword;
        this.emailid = emailid;
        this.fname = fname;
        this.house = house;
        this.lname = lname;
        this.mobile = mobile;
        this.password = password;
        this.postcode = postcode;
        this.town = town;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}


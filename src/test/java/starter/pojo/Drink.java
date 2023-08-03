package starter.pojo;

public class Drink {
    public String provider;
    public String title;
    public String url;
    public String brand;
    public Double price;
    public String unit;
    public Boolean isPromo;
    public String promoDetails;
    public String image;


    public Drink() {
    }

    @Override
    public String toString() {
        return "Drinks{" +
                "provider='" + provider + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", unit='" + unit + '\'' +
                ", isPromo=" + isPromo +
                ", promoDetails=" + promoDetails +
                ", image='" + image + '\'' +
                '}';
    }
}

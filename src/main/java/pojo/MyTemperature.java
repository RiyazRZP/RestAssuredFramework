package pojo;

public class MyTemperature {
    private String city;
    private int temp;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "MyTemperature{" +
                "city='" + city + '\'' +
                ", temp=" + temp +
                '}';
    }
}

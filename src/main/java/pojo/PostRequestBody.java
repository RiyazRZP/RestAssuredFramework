package pojo;

import java.util.List;

public class PostRequestBody {
    private String email;
    private String first_name;
    private String last_name;
    private List<String> city;
    private List<MyTemperature> temp;

    public List<MyTemperature> getTemp() {
        return temp;
    }

    public void setTemp(List<MyTemperature> temp) {
        this.temp = temp;
    }

    public List<String> getCity() {
        return city;
    }

    public void setCity(List<String> city) {
        this.city = city;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package pojo;


import java.util.List;

public class PostResponseBody {
    private String email;
    private String first_name;
    private String last_name;
    private List<String> city;
    private List<MyTemperature> temp;
    private String id;
    private String createdAt;
    private String updatedAt;
    private Meta _meta;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Meta get_meta() {
        return _meta;
    }

    public void set_meta(Meta _meta) {
        this._meta = _meta;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "PostResponseBody{" +
                "email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", city=" + city +
                ", temp=" + temp +
                ", id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", _meta=" + _meta +
                '}';
    }

}


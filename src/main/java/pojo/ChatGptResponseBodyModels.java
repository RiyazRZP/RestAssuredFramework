package pojo;

import java.util.List;

public class ChatGptResponseBodyModels {
    private String object;
    private List<Data> data;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}

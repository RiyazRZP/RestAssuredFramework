package pojo;


import java.util.List;

public class ReqBodyAddBook {
    private String userId;
    private List<CollectionOfIsbns> collectionOfIsbns;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CollectionOfIsbns> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(List<CollectionOfIsbns> collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }
}

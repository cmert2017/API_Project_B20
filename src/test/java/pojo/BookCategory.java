package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookCategory {

    @JsonProperty("id")
    private String category_id;
    @JsonProperty("name")
    private String category_name;

    public BookCategory() {
    }

    public BookCategory(String id, String name) {
        this.category_id = id;
        this.category_name = name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @Override
    public String toString() {
        return "BookCategory{" +
                "id='" + category_id + '\'' +
                ", name='" + category_name + '\'' +
                '}';
    }
}

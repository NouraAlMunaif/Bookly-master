package com.example.atheer.booklyv1;

public class cat {
    private String name;
    private  String imageUrl;
public cat(){

}

    public cat(String name,String imageUrl) {
        this.name = name;
        this.imageUrl=imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

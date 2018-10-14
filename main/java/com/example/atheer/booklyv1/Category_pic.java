package com.example.atheer.booklyv1;

public class Category_pic {
    private String image_title;
    private Integer image_id;

    public Category_pic(String image_title, Integer image_id) {
        this.image_title = image_title;
        this.image_id = image_id;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String android_version_name) {
        this.image_title = android_version_name;
    }

    public Integer getImage_ID() {
        return image_id;
    }

    public void setImage_ID(Integer android_image_url) {
        this.image_id = android_image_url;
    }

}
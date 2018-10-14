package com.example.atheer.booklyv1;




public class services {

    private int price;
    private String name;
    private int rating;


    public services(){

    }

    public services (String name){
        this.name=name;
    }

    public services (String name, int price , int rating ){
        this.name=name;
        this.price=price;
        this.rating=rating;


    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}




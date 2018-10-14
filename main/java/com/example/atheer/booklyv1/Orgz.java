package com.example.atheer.booklyv1;

public class Orgz {

    private String name;
    private String rating;
    private String noComs;
    private String Catg;

    public Orgz(String name, String rating, String noComs,String Catg) {
        this.name = name;
        this.rating = rating;
        this.noComs = noComs;
        this.Catg = Catg;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getNoComs() {
        return noComs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setNoComs(String noComs) {
        this.noComs = noComs;
    }

    public String getCatg() {
        return Catg;
    }

    public void setCatg(String catg) {
        Catg = catg;
    }
}

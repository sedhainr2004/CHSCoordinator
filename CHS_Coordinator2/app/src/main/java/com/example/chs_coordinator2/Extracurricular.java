package com.example.chs_coordinator2;

public class Extracurricular {
    private String title;
    private String sponsor;
    private String email;
    private String imageURL;
    private String twitterURL;
    private String igURl;

    public Extracurricular()
    {

    }

    public Extracurricular(String title, String sponsor, String email, String imageURL, String twitterURL, String igURl) {
        this.title = title;
        this.sponsor = sponsor;
        this.email = email;
        this.imageURL = imageURL;
        this.twitterURL = twitterURL;
        this.igURl = igURl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTwitterURL() {
        return twitterURL;
    }

    public void setTwitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
    }

    public String getIgURl() {
        return igURl;
    }

    public void setIgURl(String igURl) {
        this.igURl = igURl;
    }
}

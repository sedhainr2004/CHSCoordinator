package com.example.chs_coordinator2;

public class Sport {
    private String title;
    private String coach;
    private String email;
    private String twitterURL;
    private String maxPrepsURL;
    private String imageURL;




    public Sport(String title, String coach, String email, String twitterURL, String maxPrepsURL, String imageURL) {
        this.title = title;
        this.coach = coach;
        this.email = email;
        this.twitterURL = twitterURL;
        this.maxPrepsURL = maxPrepsURL;
        this.imageURL = imageURL;
    }
    public Sport ()
    {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTwitterURL() {
        return twitterURL;
    }

    public void setTwitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public String getMaxPrepsURL() {
        return maxPrepsURL;
    }

    public void setMaxPrepsURL(String maxPrepsURL) {
        this.maxPrepsURL = maxPrepsURL;
    }
}

package com.example.newsappjava;

//Data Class

/**
 * News class is a data class which is used to create custom data type for array.
 */
public class News {
    String title,author,url,imageURL;

    /**
     * @param title title of the news
     * @param author author of news
     * @param url url to the website
     * @param imageURL imageURL to give for the views in Recycler view
     */
    public News(String title, String author, String url, String imageURL) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.imageURL = imageURL;
    }
}

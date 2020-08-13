package com.example.sunny.maps1.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ArticlePojo implements Parcelable
{
    protected ArticlePojo(Parcel in) {
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
        content = in.readString();
    }


    public class Source
    {
        private String id;

        private String name;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    }
    private Source source;

    private String author;

    private String title;

    private String description;

    private String url;

    private String urlToImage;

    private String publishedAt;

    private String content;

    public void setSource(Source source){
        this.source = source;
    }
    public Source getSource(){
        return this.source;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setUrlToImage(String urlToImage){
        this.urlToImage = urlToImage;
    }
    public String getUrlToImage(){
        return this.urlToImage;
    }
    public void setPublishedAt(String publishedAt){
        this.publishedAt = publishedAt;
    }
    public String getPublishedAt(){
        return this.publishedAt;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }


    //make parcelable

    public static final Creator<ArticlePojo> CREATOR = new Creator<ArticlePojo>() {
        @Override
        public ArticlePojo createFromParcel(Parcel in) {
            return new ArticlePojo(in);
        }

        @Override
        public ArticlePojo[] newArray(int size) {
            return new ArticlePojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(urlToImage);
        dest.writeString(publishedAt);
        dest.writeString(content);
    }
}

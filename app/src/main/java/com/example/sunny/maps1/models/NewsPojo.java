package com.example.sunny.maps1.models;

import java.util.List;

public class NewsPojo
{
    private String status;

    private int totalResults;

    private List<ArticlePojo> articles;

    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setTotalResults(int totalResults){
        this.totalResults = totalResults;
    }
    public int getTotalResults(){
        return this.totalResults;
    }
    public void setArticles(List<ArticlePojo> articles){
        this.articles = articles;
    }
    public List<ArticlePojo> getArticles(){
        return this.articles;
    }
}

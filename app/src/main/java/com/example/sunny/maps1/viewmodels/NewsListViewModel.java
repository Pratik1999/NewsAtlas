package com.example.sunny.maps1.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sunny.maps1.dataStore.Repository;
import com.example.sunny.maps1.models.NewsPojo;

public class NewsListViewModel extends ViewModel {
    private MutableLiveData<NewsPojo> liveNews;
    private Repository repository;
    public void init(String countryCode)//if dont wanna attach listener
    {
        init(countryCode,null);
    }
    public void init(String countryCode, Repository.OnQueryFinishListener listener)
    {
        if(liveNews!=null)
            return;
        repository=Repository.getInstance();
        liveNews=repository.getNews(countryCode,listener);
    }
    public LiveData<NewsPojo> getNewsLive(){
        return liveNews ;
    }
}

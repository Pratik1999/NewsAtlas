package com.example.sunny.maps1.dataStore;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.example.sunny.maps1.Retrofit.ClientInstance;
import com.example.sunny.maps1.Retrofit.EndPoints;
import com.example.sunny.maps1.models.NewsPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    String TAG=this.getClass().getName();
    private static Repository instance;

    public Repository() {
    }
    public static Repository getInstance()
    {
        if(instance==null)
            instance=new Repository();
        return instance;
    }

    public MutableLiveData<NewsPojo> getNews(String code,OnQueryFinishListener listener)
    {
        if(listener!=null)
        {
            listener.startQuery();
        }
        MutableLiveData<NewsPojo> mutableLiveData=new MutableLiveData<>();
        EndPoints endPoints= ClientInstance.getRetrofit().create(EndPoints.class);
        Call<NewsPojo> call=endPoints.getAllNews(code);
        call.enqueue(new Callback<NewsPojo>() {
            @Override
            public void onResponse(Call<NewsPojo> call, Response<NewsPojo> response) {
                Log.i("retro","success");
                if(response.isSuccessful())
                {
                    mutableLiveData.setValue(response.body());
                    notifyListenr(listener,response.body().getTotalResults());
                }
                else
                    notifyListenr(listener,0);
            }

            @Override
            public void onFailure(Call<NewsPojo> call, Throwable t) {
                Log.i("retro","fail"+t.toString());
                mutableLiveData.setValue(null);
                notifyListenr(listener,0);
            }
        });
        return mutableLiveData;
    }

    public void notifyListenr(OnQueryFinishListener listener,int resCount)
    {
        if(listener!=null)
            listener.finish(resCount);
    }


    public interface OnQueryFinishListener{
        public void finish(int resCount);
        public void startQuery();
    }


}

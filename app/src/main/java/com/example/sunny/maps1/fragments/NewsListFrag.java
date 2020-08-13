package com.example.sunny.maps1.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sunny.maps1.Adapters.NewsListAdapter;
import com.example.sunny.maps1.R;
import com.example.sunny.maps1.dataStore.Repository;
import com.example.sunny.maps1.models.ArticlePojo;
import com.example.sunny.maps1.models.NewsPojo;
import com.example.sunny.maps1.viewmodels.NewsListViewModel;

import java.util.ArrayList;

public class NewsListFrag extends Fragment {

    public String TAG=this.getClass().getName();
    public static String ARG_1="countryCode";
    RecyclerView recyclerNewsList;
    RecyclerView.Adapter adapter;
    NewsListViewModel newsListViewModel;
    ArrayList<ArticlePojo> articleList;
    Context context;
    String countryCode;
    ProgressDialog mProgressDialog;
    TextView noNewsText;
    Repository.OnQueryFinishListener onQueryFinishListener = new Repository.OnQueryFinishListener() {
        @Override
        public void finish(int resCount) {
            dismissProgressBar();
            if(resCount==0)
            {
                Toast.makeText(context,"Sorry..",Toast.LENGTH_LONG).show();
                if(recyclerNewsList.getVisibility()==View.VISIBLE)
                    recyclerNewsList.setVisibility(View.GONE);
                if(noNewsText.getVisibility()==View.GONE)
                    noNewsText.setVisibility(View.VISIBLE);
            }
            else
            {
                if(recyclerNewsList.getVisibility()==View.GONE)
                    recyclerNewsList.setVisibility(View.VISIBLE);
                if(noNewsText.getVisibility()==View.VISIBLE)
                    noNewsText.setVisibility(View.GONE);
            }

        }

        @Override
        public void startQuery() {
            showProgressBar();
        }
    };
    public NewsListFrag() {
        articleList=new ArrayList<>();
    }
    public static NewsListFrag newInstance(String countryCode) {
        NewsListFrag fragment = new NewsListFrag();
        Bundle args = new Bundle();
        args.putString(ARG_1,countryCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_news_list, container, false);
        Log.i(TAG,"oncreateView");
        recyclerNewsList=view.findViewById(R.id.recyclerNewsList);
        noNewsText=view.findViewById(R.id.noNewsText);
        newsListViewModel=new ViewModelProvider(requireActivity()).get(NewsListViewModel.class);
        readBundle();//gets CountryCode
        newsListViewModel.init(countryCode,onQueryFinishListener);
        newsListViewModel.getNewsLive().observe(requireActivity(), new Observer<NewsPojo>() {
            @Override
            public void onChanged(NewsPojo newsPojo) {
                Log.i(TAG,"initSize"+newsPojo.getArticles().size());
                articleList.addAll(newsPojo.getArticles());
                adapter.notifyDataSetChanged();
            }
        });
        initRecycler();
        return view;
    }

    void initRecycler()
    {
            adapter = new NewsListAdapter(articleList, context,customOntemClickListener);
            recyclerNewsList.setLayoutManager(new LinearLayoutManager(context));
            recyclerNewsList.setAdapter(adapter);
            adapter.notifyDataSetChanged();

    }

    NewsListAdapter.CustomOntemClickListener customOntemClickListener=new NewsListAdapter.CustomOntemClickListener() {
        @Override
        public void onItemClicked(int pos, ImageView imageView, View containerView) {
            NewsDetailsFrag newsDetailsFrag=NewsDetailsFrag.newInstance(articleList.get(pos),ViewCompat.getTransitionName(imageView), ViewCompat.getTransitionName(containerView));
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.addToBackStack(null);
            ft.addSharedElement(imageView, ViewCompat.getTransitionName(imageView));
            ft.addSharedElement(containerView, ViewCompat.getTransitionName(containerView));
            ft.replace(R.id.mainFrame,newsDetailsFrag);
            ft.commit();
        }
    };
    public void readBundle()
    {
        Bundle bundle=getArguments();
        if(bundle!=null)
            this.countryCode=bundle.getString(ARG_1);
        else
            Log.i(TAG,"null");
    }

    void showProgressBar()
    {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
    void dismissProgressBar()
    {
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();

    }



}
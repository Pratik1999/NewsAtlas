package com.example.sunny.maps1.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.sunny.maps1.R;
import com.example.sunny.maps1.models.ArticlePojo;

public class NewsDetailsFrag extends Fragment {

    public static String ARG_KEY1="article";
    String TAG=this.getClass().getName();
    Context context;
    ArticlePojo articlePojo;
    TextView titleTextView,descriptionText,contentText;
    ImageView imageView;
    ImageButton imageButton;
    LinearLayout newsDetailsContainer;
    static String imageTransitionName,holderTransitionName;
    public NewsDetailsFrag() {
    }

    public static NewsDetailsFrag newInstance(ArticlePojo articlePojo,String imageTransitionName,String holderTransitionName) {
        NewsDetailsFrag fragment = new NewsDetailsFrag();
        NewsDetailsFrag.holderTransitionName=holderTransitionName;
        NewsDetailsFrag.imageTransitionName=imageTransitionName;
        Bundle args = new Bundle();
        args.putParcelable(ARG_KEY1,articlePojo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_news_details, container, false);
        readBundle();
        if(articlePojo==null)
        {
            Toast.makeText(context,"Some Error Occured",Toast.LENGTH_LONG);
            return view;
        }
        titleTextView=view.findViewById(R.id.titleText);
        descriptionText=view.findViewById(R.id.descriptionText);
        imageView=view.findViewById(R.id.imageView);
        contentText=view.findViewById(R.id.contentText);
        newsDetailsContainer=view.findViewById(R.id.detailsContainer);
        imageButton=view.findViewById(R.id.imageButton);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(imageTransitionName);
            newsDetailsContainer.setTransitionName(holderTransitionName);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInBrowser();
            }
        });
    }

    void displayData()
    {
        titleTextView.setText(articlePojo.getTitle());
        descriptionText.setText(articlePojo.getDescription());
        contentText.setText(articlePojo.getContent());
        Glide.with(context)
                .load(articlePojo.getUrlToImage())
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(Target.SIZE_ORIGINAL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(imageView);
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    public void readBundle()
    {
        Bundle bundle=getArguments();
        if(bundle!=null)
            this.articlePojo=bundle.getParcelable(ARG_KEY1);
        else
            Log.i(TAG,"null");
    }
    void openInBrowser()
    {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(articlePojo.getUrl()));
        startActivity(intent);
    }

}
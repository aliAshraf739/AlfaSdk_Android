package com.example.alfasdk.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.alfasdk.Adapters.LinksAdapter;
import com.example.alfasdk.MyMainActivity;
import com.example.alfasdk.Models.LinksModel.Link;
import com.example.alfasdk.Models.LinksModel.LinksResponse;
import com.example.alfasdk.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class LinksFragment extends Fragment {

    private static final String TAG = "LinksFragment";

//    @BindView(R.id.links_listView)
    ListView links_listView;

    public LinksFragment() {
        // Required empty public constructor
    }

    public static LinksFragment newInstance() {
        LinksFragment fragment = new LinksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_links, container, false);
//        ButterKnife.bind(this, view);
        links_listView=view.findViewById(R.id.links_listView);
        return view;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Links");
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MyMainActivity) getActivity()).linksRequest();
    }

    public void setResult(LinksResponse result) {

        if (result != null) {

            final ArrayList<Link> linkListLOL = (ArrayList<Link>) result.getResponse().getLinks();

            //Sorting list by titles
            final ArrayList<Link> mListSortedLinks= sortList(linkListLOL);

            links_listView.setAdapter(new LinksAdapter(getActivity(), mListSortedLinks));
            links_listView.setOnItemClickListener((parent, view, position, id) -> {
                String url = linkListLOL.get(position).getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            });
        }
    }

    private ArrayList<Link> sortList(ArrayList<Link> linkListLOL) {
        for (int i = 0; i < linkListLOL.size(); i++) {
            for (int j = 0; j < linkListLOL.size(); j++) {
                String title1=linkListLOL.get(i).getTitle();
                String title2=linkListLOL.get(j).getTitle();
                if(title1.length() < title2.length()){
                    Link link1 = linkListLOL.get(i);
                    Link link2 = linkListLOL.get(j);
                    linkListLOL.set(i, link2);
                    linkListLOL.set(j, link1);
                }
            }
        }

        Log.e(TAG, "Sorted List: "+ linkListLOL.toString());

        return linkListLOL;
    }
}

package com.ahmednmahran.moviesapp.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmednmahran.moviesapp.R;
import com.ahmednmahran.moviesapp.controller.adapter.MoviesRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by Ahmed Nabil on 07/08/2016.
 * email: Ahmed.mdeveloper@gmail.com
 * Mobile 1 : +2 010 13 1000 72
 * Mobile 2 : +2 011 44 333 595
 * A Fragment used to view the main list of movies
 *
 */
public class MainActivityFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private MoviesRecyclerAdapter moviesRecyclerAdapter;
    private ArrayList<String> dataList;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        dataList = new ArrayList<>();
        for (int i=0;i<20;i++){ // TODO: 12/08/2016 replace with real data
            dataList.add("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQMsemlhYWa4SOxevyRglegYZXjM2mvL0GDLlCi0MduxhaWeCiZjw");
        }
        // specify an adapter (see also next example)
        moviesRecyclerAdapter = new MoviesRecyclerAdapter(getContext(),dataList);
        mRecyclerView.setAdapter(moviesRecyclerAdapter);
        return rootView;
    }
}

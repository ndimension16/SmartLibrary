package com.ndimension.smartlibrary.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.adapter.ShelfAdapter;
import com.ndimension.smartlibrary.module.ShelfModule;

import java.util.ArrayList;

public class MonthlyFragment extends Fragment {
    View rootView;
    private ArrayList<ShelfModule> category1 = new ArrayList<>();
    private ArrayList<ShelfModule> category2 = new ArrayList<>();
    private ArrayList<ShelfModule> category3 = new ArrayList<>();
    private ArrayList<ShelfModule> category4 = new ArrayList<>();
    private ArrayList<ShelfModule> category5 = new ArrayList<>();
    private RecyclerView rvFeatured,rvStatistics,rvFinance,rvScience,rvEconomics;
    private ShelfAdapter shelfAdapter1,shelfAdapter2,shelfAdapter3,shelfAdapter4,shelfAdapter5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monthly, container, false);
        this.rootView = rootView;
        initialize();
        peformAction();

        return rootView;

    }

    private void initialize(){
        rvFeatured = (RecyclerView)rootView.findViewById(R.id.rvFeatured);
        rvFeatured.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rvStatistics = (RecyclerView)rootView.findViewById(R.id.rvStatistics);
        rvStatistics.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rvFinance = (RecyclerView)rootView.findViewById(R.id.rvFinance);
        rvFinance.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rvScience = (RecyclerView)rootView.findViewById(R.id.rvScience);
        rvScience.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rvEconomics = (RecyclerView)rootView.findViewById(R.id.rvEconomics);
        rvEconomics.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        category1.add(new ShelfModule(R.drawable.fb_1));
        category1.add(new ShelfModule(R.drawable.fb_2));
        category1.add(new ShelfModule(R.drawable.fb_3));
        category1.add(new ShelfModule(R.drawable.fb_4));
        category1.add(new ShelfModule(R.drawable.fb_5));

        shelfAdapter1 = new ShelfAdapter(getActivity(),category1);
        rvFeatured.setAdapter(shelfAdapter1);

        category2.add(new ShelfModule(R.drawable.st_1));
        category2.add(new ShelfModule(R.drawable.st_2));
        category2.add(new ShelfModule(R.drawable.st_3));
        category2.add(new ShelfModule(R.drawable.st_4));
        category2.add(new ShelfModule(R.drawable.st_5));

        shelfAdapter2 = new ShelfAdapter(getActivity(),category2);
        rvStatistics.setAdapter(shelfAdapter2);

        category3.add(new ShelfModule(R.drawable.fi_1));
        category3.add(new ShelfModule(R.drawable.fi_2));
        category3.add(new ShelfModule(R.drawable.fi_3));
        category3.add(new ShelfModule(R.drawable.fi_4));
        category3.add(new ShelfModule(R.drawable.fi_5));

        shelfAdapter3 = new ShelfAdapter(getActivity(),category3);
        rvFinance.setAdapter(shelfAdapter3);

        category4.add(new ShelfModule(R.drawable.sc_1));
        category4.add(new ShelfModule(R.drawable.sc_2));
        category4.add(new ShelfModule(R.drawable.sc_3));
        category4.add(new ShelfModule(R.drawable.sc_4));
        category4.add(new ShelfModule(R.drawable.sc_5));

        shelfAdapter4 = new ShelfAdapter(getActivity(),category4);
        rvScience.setAdapter(shelfAdapter4);



        category5.add(new ShelfModule(R.drawable.eco_1));
        category5.add(new ShelfModule(R.drawable.eco_2));
        category5.add(new ShelfModule(R.drawable.eco_3));
        category5.add(new ShelfModule(R.drawable.eco_4));
        category5.add(new ShelfModule(R.drawable.eco_5));

        shelfAdapter5 = new ShelfAdapter(getActivity(),category5);
        rvEconomics.setAdapter(shelfAdapter5);


    }

    private void peformAction(){

    }
}

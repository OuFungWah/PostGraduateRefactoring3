package com.example.crazywah.postgraduaterefactoring3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment {

    private static final String TAG = "MyFragment";

    private MainActivity.ActivityInfo activityInfo;

    private RecyclerView recyclerView;

    public MyFragment() {

    }

    @SuppressLint("ValidFragment")
    public MyFragment(MainActivity.ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(true);
        if (recyclerView.getAdapter() == null) {
            MyAdapter adapter = new MyAdapter();
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        if (recyclerView.getLayoutManager() == null) {
            final LinearLayoutManager linearLayoutManager;
            linearLayoutManager = new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }

                @Override
                public boolean isAutoMeasureEnabled() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if (i == 0) {
                TextView textView = new TextView(viewGroup.getContext());
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                return new MyViewHolder(textView);
            } else {
                final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.standar_viewpager_layout, viewGroup, false);
                return new MyFragmentHolder(view);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
            if (activityInfo.dataList.get(i).type == 0) {
                ((TextView) viewHolder.itemView).setText(activityInfo.dataList.get(i).name);
                ((TextView) viewHolder.itemView).setBackgroundColor(Color.YELLOW);
            } else {
                MyFragmentHolder holder = (MyFragmentHolder) viewHolder;
                if (holder.viewPager.getAdapter() == null) {
                    final List<MySubFragment> fragmentList = new ArrayList<>();
                    for (MainActivity.ActivityInfo info : activityInfo.dataList.get(i).activityInfoList) {
                        fragmentList.add(new MySubFragment(info));
                    }
                    holder.viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
                        @Override
                        public Fragment getItem(int i) {
                            return fragmentList.get(i);
                        }

                        @Override
                        public int getCount() {
                            return fragmentList.size();
                        }

                        @Nullable
                        @Override
                        public CharSequence getPageTitle(int position) {
                            return activityInfo.dataList.get(i).activityInfoList.get(position).name;
                        }
                    });
                } else {
                    holder.viewPager.getAdapter().notifyDataSetChanged();
                }
            }
        }

        @Override
        public int getItemCount() {
            return activityInfo.dataList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return activityInfo.dataList.get(position).type;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class MyFragmentHolder extends RecyclerView.ViewHolder {

        public MyViewPager viewPager;
        public TabLayout tabLayout;

        public MyFragmentHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.standar_view_pager);
            tabLayout = itemView.findViewById(R.id.standar_view_tab);
            tabLayout.setTabIndicatorFullWidth(false);
//            viewPager.setNestedpParent(recyclerView);
        }
    }

}

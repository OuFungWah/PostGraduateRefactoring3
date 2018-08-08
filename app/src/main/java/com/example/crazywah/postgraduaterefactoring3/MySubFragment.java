package com.example.crazywah.postgraduaterefactoring3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MySubFragment extends Fragment {

    private static final String TAG = "MySubFragment";

    private MainActivity.ActivityInfo activityInfo;

    private NoScrollRecycler recyclerView;

    public MySubFragment() {

    }

    @SuppressLint("ValidFragment")
    public MySubFragment(MainActivity.ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        if (recyclerView.getAdapter() == null) {
            MyAdapter adapter = new MyAdapter();
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        if (recyclerView.getLayoutManager() == null) {
            LinearLayoutManager linearLayoutManager;
            linearLayoutManager = new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }

                @Override
                public boolean isAutoMeasureEnabled() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(linearLayoutManager);
        }
//        Log.d(TAG, "onCreateView: recyclerViewRange = " + recyclerView.computeVerticalScrollRange());
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
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
            if (activityInfo.dataList.get(i).type == 0) {
                ((TextView) viewHolder.itemView).setText(activityInfo.dataList.get(i).name);
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

}

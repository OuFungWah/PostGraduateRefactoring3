package com.example.crazywah.postgraduaterefactoring3;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<ActivityInfo> activityInfoList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private MyPagerAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);

        for (int i = 0; i < 10; i++) {
            List<Data> dataList = new ArrayList<>();
            if (dataList.isEmpty())
                for (int j = 0; j < 30; j++) {
                    Data data = new Data();
                    data.name = "Data" + j;
                    dataList.add(data);
                }
            ActivityInfo activityInfo = new ActivityInfo();
            activityInfo.dataList = dataList;
            activityInfo.name = "Activity" + i;
            activityInfoList.add(activityInfo);
        }

        for (ActivityInfo info : activityInfoList) {
            info.dataList.get(14).type = 1;
            List<ActivityInfo> subActivityLIst = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                List<Data> dataList = new ArrayList<>();
                if (dataList.isEmpty())
                    for (int j = 0; j < 30; j++) {
                        Data data = new Data();
                        data.name = "Data" + j;
                        dataList.add(data);
                    }
                ActivityInfo activityInfo = new ActivityInfo();
                activityInfo.dataList = dataList;
                activityInfo.name = "Activity" + i;
                subActivityLIst.add(activityInfo);
            }
            info.dataList.get(14).activityInfoList.addAll(subActivityLIst);
        }

        for (ActivityInfo info : activityInfoList) {
            MyFragment fragment = new MyFragment(info);
            fragmentList.add(fragment);
        }

        fragmentAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(activityInfoList.size());
        tabLayout.setupWithViewPager(viewPager, true);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

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
            return activityInfoList.get(position).name;
        }
    }

    public class ActivityInfo {
        public String name;
        public List<Data> dataList;
    }

    public class Data {
        public int type = 0;
        public String name;
        public List<ActivityInfo> activityInfoList = new ArrayList<>();
    }

}

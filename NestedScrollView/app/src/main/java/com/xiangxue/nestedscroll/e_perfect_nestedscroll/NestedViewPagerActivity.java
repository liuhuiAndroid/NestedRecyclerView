package com.xiangxue.nestedscroll.e_perfect_nestedscroll;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xiangxue.common.viewpager.ViewPagerAdapter;
import com.xiangxue.conflict.R;
import com.xiangxue.conflict.databinding.ActivityNestedViewPagerBinding;
import com.xiangxue.common.fragment.RecyclerViewFragment;
import java.util.ArrayList;
import java.util.List;

public class NestedViewPagerActivity extends AppCompatActivity {
    ActivityNestedViewPagerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nested_view_pager);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, getPageFragments());
        binding.viewpagerView.setAdapter(pagerAdapter);
        final String[] labels = new String[]{"linear", "scroll", "recycler"};
        new TabLayoutMediator(binding.tablayout, binding.viewpagerView, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(labels[position]);
            }
        }).attach();
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("NestedViewPagerActivity", "Refresh started...");
                binding.getRoot().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
    }

    private List<Fragment> getPageFragments() {
        List<Fragment> data = new ArrayList<>();
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        return data;
    }
}

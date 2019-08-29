package com.aware.app.testx.presenter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aware.app.testx.R;

import java.util.List;

public class SectionsAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragments;
    private LinearLayout dotsLayout;
    private Button btnPrevious, btnNext;

    public SectionsAdapter(@NonNull FragmentManager fm, Context context, List<Fragment> fragments, LinearLayout dotsLayout,
                           Button btnPrevious, Button btnNext) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.dotsLayout = dotsLayout;
        this.btnPrevious = btnPrevious;
        this.btnNext = btnNext;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // update dots view
            updateDots(position);

            // update buttons text
            if (position == 0) {
                btnPrevious.setVisibility(View.INVISIBLE);
            } else {
                btnPrevious.setVisibility(View.VISIBLE);
            }

            if (position == fragments.size()-1) {
                btnNext.setText("CONTINUE");
            } else {
                btnNext.setText("NEXT");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void updateDots(int currentPage) {
        dotsLayout.removeAllViews();
        for (int i = 0; i < fragments.size(); i++) {
            TextView dot = new TextView(context);
            dot.setText(Html.fromHtml("&#8226;"));
            dot.setTextSize(35);
            if (i == currentPage) dot.setTextColor(context.getResources().getColor(R.color.dot_active));
            else dot.setTextColor(context.getResources().getColor(R.color.dot_inactive));
            dotsLayout.addView(dot);
        }
    }

}

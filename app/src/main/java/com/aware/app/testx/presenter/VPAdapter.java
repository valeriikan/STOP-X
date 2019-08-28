package com.aware.app.testx.presenter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aware.app.testx.R;

public class VPAdapter extends PagerAdapter {

    private Context context;
    private int[] layouts;
    private LinearLayout dotsLayout;
    private Button btnPrevious, btnNext;
    private View[] views;

    public VPAdapter(Context context, int[] layouts, LinearLayout dotsLayout,
                     Button btnPrevious, Button btnNext) {
        this.context = context;
        this.layouts = layouts;
        this.dotsLayout = dotsLayout;
        this.btnPrevious = btnPrevious;
        this.btnNext = btnNext;
        views = new View[layouts.length];
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);
        views[position] = view;

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view  = (View)object;
        container.removeView(view);
    }

    public void updateDots(int currentPage) {
        dotsLayout.removeAllViews();
        for (int i = 0; i < layouts.length; i++) {
            TextView dot = new TextView(context);
            dot.setText(Html.fromHtml("&#8226;"));
            dot.setTextSize(35);
            if (i == currentPage) dot.setTextColor(context.getResources().getColor(R.color.dot_active));
            else dot.setTextColor(context.getResources().getColor(R.color.dot_inactive));
            dotsLayout.addView(dot);
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    public View initView(int position, int id) {
        return views[position].findViewById(id);
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

            if (position == layouts.length-1) {
                btnNext.setText("CONTINUE");
            } else {
                btnNext.setText("NEXT");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
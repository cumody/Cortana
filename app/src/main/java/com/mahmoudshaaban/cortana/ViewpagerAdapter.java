package com.mahmoudshaaban.cortana;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewpagerAdapter extends PagerAdapter {

    Context mcontext;
    List<Viewpagerlist> mlist;

    public ViewpagerAdapter(Context mcontext, List<Viewpagerlist> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutscreen = (View) inflater.inflate(R.layout.viewpager_screen,null);
        ImageView imageSlide = layoutscreen.findViewById(R.id.banner_slide);
        imageSlide.setImageResource(mlist.get(position).getImage());

        Picasso.get()
                .load(mlist.get(position).getImage())
                .fit()
                .into(imageSlide);


        container.addView(layoutscreen);
        return layoutscreen;

    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object ;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

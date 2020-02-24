package com.zysoft.tjawshapingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.zysoft.tjawshapingapp.base.BaseLazyFragment;
import com.zysoft.tjawshapingapp.base.CustomBaseFragment;

import java.util.List;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<BaseLazyFragment> fragmentList;
    private List<String> list_Title;

    public CustomPagerAdapter(FragmentManager fm, Context context, List<BaseLazyFragment> fragmentList, List<String> list_Title) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.list_Title = list_Title;
    }

    @Override
    public int getItemPosition(Object object) {//最主要就是加了这个方法。
        return POSITION_NONE;
    }

    @Override
    public BaseLazyFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }

    /**
     * //此方法用来显示tab上的名字
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position);
    }
}

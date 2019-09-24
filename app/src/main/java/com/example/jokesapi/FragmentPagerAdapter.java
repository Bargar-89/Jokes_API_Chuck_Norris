package com.example.jokesapi;


import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {


    private final String TITLE_JOKES;
    private final String TITLE_API;

    public FragmentPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        TITLE_JOKES = mContext.getString(R.string.title_jokes);
        TITLE_API = mContext.getString(R.string.title_api);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FirstPageFragment();
            case 1: return new SecondPageFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position){
            case 0: title = TITLE_JOKES; break;
            case 1: title = TITLE_API; break;
        }
        return title;
    }
}

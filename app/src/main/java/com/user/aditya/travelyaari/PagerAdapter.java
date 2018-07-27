package com.user.aditya.travelyaari;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Aditya on 9/23/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int num;
    String uname;
    public PagerAdapter(FragmentManager fm,int nu) {
        super(fm);
        this.num=nu;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: Desc t=new Desc();
                Bundle bundle=new Bundle();
                bundle.putString("uname",uname);
                t.setArguments(bundle);
                    return t;
            case 1: Works t2=new Works();
                Bundle bund=new Bundle();
                bund.putString("uname",uname);
                t2.setArguments(bund);
                    return t2;
            default:return null;
        }
    }
    void getUsername(String n)
    {uname=n;}
    @Override
    public int getCount() {
        return num;
    }
}

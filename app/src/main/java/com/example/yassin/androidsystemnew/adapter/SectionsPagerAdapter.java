package com.example.yassin.androidsystemnew.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yassin.androidsystemnew.frag.PreviewTableFra;
import com.example.yassin.androidsystemnew.frag.ScanFra;

import static com.example.yassin.androidsystemnew.frag.PreviewTableFra.Activity;

/**
 * Created by yassin on 3/8/18.
 */

public class SectionsPagerAdapter   extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new ScanFra();
            case 1:
                PreviewTableFra previewTableFra =new PreviewTableFra();
                Bundle bundle =new Bundle();
                bundle.putString(Activity,"Adapter");
                previewTableFra.setArguments(bundle);
                return previewTableFra;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Scan Page";
            case 1:
                return "Table Page";

        }
        return null;
    }
}
package com.rmpader.android.noonplication.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rmpader.android.noonplication.fragment.BlockedFragment;
import com.rmpader.android.noonplication.fragment.ConversationFragment;
import com.rmpader.android.noonplication.fragment.DraftsFragment;
import com.rmpader.android.noonplication.fragment.SettingsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reynald on 4/4/2015.
 */
public class MessengerPagerAdapter extends FragmentPagerAdapter {

    private final Activity parent;
    private String[] titles = {
            "Conversations",
            "Draft Messages",
            "Blocked Messages",
            "Settings"
    };
    private List<Fragment> fragments = new ArrayList<>();

    public MessengerPagerAdapter(FragmentManager fm, Activity parent) {
        super(fm);
        this.parent = parent;

        Fragment conversationFragment = new ConversationFragment();

        fragments.add(0, conversationFragment);

        Fragment draftsFragment = new DraftsFragment();
        fragments.add(1, draftsFragment);

        Fragment blockedFragment = new BlockedFragment();
        fragments.add(2, blockedFragment);

        Fragment settingsFragment = new SettingsFragment();
        fragments.add(3, settingsFragment);

    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position > -1 || position < titles.length) {
            return titles[position];
        } else {
            return null;
        }
    }
}

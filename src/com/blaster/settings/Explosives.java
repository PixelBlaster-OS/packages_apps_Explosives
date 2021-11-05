/*
 * Copyright (C) 2021 PixelBlaster-OS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blaster.settings;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.provider.SearchIndexableResource;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.search.SearchIndexable;

import com.android.internal.logging.nano.MetricsProto;

import com.blaster.settings.Statusbar;
import com.blaster.settings.QuickSettings;
import com.blaster.settings.Lockscreen;
import com.blaster.settings.Notifications;
import com.blaster.settings.Miscellaneous;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SearchIndexable
public class Explosives extends SettingsPreferenceFragment { 

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.layout_explosives, container, false);

        final BottomNavigationView bottomNavigation = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);

    bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
	  public boolean onNavigationItemSelected(MenuItem item) {

             if (item.getItemId() == bottomNavigation.getSelectedItemId()) {
               return false;
             } else if (item.getItemId() == R.id.status_bar_view) {
                 switchFrag(new Statusbar());}
                else if (item.getItemId() == R.id.quick_settings_view) {
                 switchFrag(new QuickSettings());}
                else if (item.getItemId() == R.id.lockscreen_view) {
                 switchFrag(new Lockscreen());}
                else if (item.getItemId() == R.id.notifications_view) {
                 switchFrag(new Notifications());}
                else if (item.getItemId() == R.id.miscellaneous_view) {
                 switchFrag(new Miscellaneous());}
            return true;
            }
    });
        

        bottomNavigation.setSelectedItemId(R.id.status_bar_view);
        switchFrag(new Statusbar());
        bottomNavigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        return view;
    }

    private void switchFrag(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_frame, fragment).commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        getActivity().setTitle(R.string.blaster_settings_title);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.CUSTOM_SETTINGS;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(
                        Context context, boolean enabled) {
                    final SearchIndexableResource sir = new SearchIndexableResource(context);
                    return Arrays.asList(sir);
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    final List<String> keys = super.getNonIndexableKeys(context);
                    return keys;
                }
            };
}

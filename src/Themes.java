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

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.SwitchPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.search.SearchIndexable;

import com.android.internal.logging.nano.MetricsProto;

import com.blaster.support.colorpicker.ColorPickerPreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SearchIndexable
public class Themes extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

private String MONET_ENGINE_COLOR_OVERRIDE = "monet_engine_color_override";
private ColorPickerPreference mMonetColor;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.themes);
        PreferenceScreen prefSet = getPreferenceScreen();
        final ContentResolver resolver = getActivity().getContentResolver();

        mMonetColor = (ColorPickerPreference) prefSet.findPreference(MONET_ENGINE_COLOR_OVERRIDE);
        int intColor = Settings.Secure.getInt(resolver, MONET_ENGINE_COLOR_OVERRIDE, Color.WHITE);
        String hexColor = String.format("#%08x", (0xffffff & intColor));
        mMonetColor.setNewPreviewColor(intColor);
        mMonetColor.setSummary(hexColor);
        mMonetColor.setOnPreferenceChangeListener(this);        

    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.CUSTOM_SETTINGS;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mMonetColor) {
            String hex = ColorPickerPreference.convertToARGB(Integer
                .parseInt(String.valueOf(newValue)));
            preference.setSummary(hex);
            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.Secure.putInt(resolver,
                MONET_ENGINE_COLOR_OVERRIDE, intHex);
            return true;
        }
        return false;
    }    

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(
                        Context context, boolean enabled) {
                    final SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.themes;
                    return Arrays.asList(sir);
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    final List<String> keys = super.getNonIndexableKeys(context);
                    return keys;
                }
            };
}

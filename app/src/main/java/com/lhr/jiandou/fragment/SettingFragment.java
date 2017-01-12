package com.lhr.jiandou.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.lhr.jiandou.R;
import com.lhr.jiandou.activity.MainActivity;
import com.lhr.jiandou.utils.ClearCacheUtils;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.PreferncesUtils;
import com.lhr.jiandou.utils.ToastUtils;

import de.psdev.licensesdialog.LicensesDialog;


/**
 * Created by ChinaLHR on 2016/12/25.
 * Email:13435500980@163.com
 */

public class SettingFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {
    public static final String PREF_KEY_THEME = "pref_key_theme";
    public static final String PREF_KEY_AUTO_IMG = "pref_key_auto_img";
    public static final String PREF_KEY_CACHE = "pref_key_cache";
    public static final String PREF_KEY_CODE = "pref_key_code";
    public static final String PREF_KEY_PROTOCOL = "pref_key_protocol";
    public static final String PREF_KEY_FEEDBACK = "pref_key_feedback";

    private ListPreference ThemePreference;
    private CheckBoxPreference ImagePreference;
    private Preference Feedback;
    private Preference Protocol;
    private Preference Cache;
    private final Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {

            String key = preference.getKey();
            switch (key) {
                case PREF_KEY_THEME:
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    break;
                case PREF_KEY_AUTO_IMG:
                    Intent intent = new Intent(MainActivity.ACTION_LOCAL_SEND);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    break;


            }
            return true;
        }
    };

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        ThemePreference = (ListPreference) findPreference(PREF_KEY_THEME);
        ThemePreference.setOnPreferenceChangeListener(listener);

        ImagePreference = (CheckBoxPreference) findPreference(PREF_KEY_AUTO_IMG);
        ImagePreference.setOnPreferenceChangeListener(listener);

        Feedback = findPreference(PREF_KEY_FEEDBACK);
        Feedback.setOnPreferenceClickListener(this);

        Protocol = findPreference(PREF_KEY_PROTOCOL);
        Protocol.setOnPreferenceClickListener(this);

        Cache = findPreference(PREF_KEY_CACHE);
        Cache.setOnPreferenceClickListener(this);
        initPreferences();
    }

    private void initPreferences() {
        String nowtheme = PreferncesUtils.getString(getActivity(), Constants.PREF_KEY_THEME, "1");
        if (nowtheme.equals("1")) {
            ThemePreference.setSummary("白天");
        } else {
            ThemePreference.setSummary("晚上");
        }


            Cache.setSummary(ClearCacheUtils.getCacheSize());

    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case PREF_KEY_FEEDBACK:
                Intent emailintent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "13435500980@163.com", null));
                startActivity(Intent.createChooser(emailintent, "请选择邮件客户端"));
                break;
            case PREF_KEY_PROTOCOL:
                showApacheLicenseDialog();
                break;
            case PREF_KEY_CACHE:
                ClearCacheUtils.deleteDir(getActivity().getCacheDir());
                Cache.setSummary(ClearCacheUtils.getCacheSize());
                ToastUtils.show(getActivity(),"清除缓存成功");
                break;
        }
        return true;
    }

    private void showApacheLicenseDialog() {
        new LicensesDialog
                .Builder(getActivity())
                .setNotices(R.raw.notices)
                .setIncludeOwnLicense(true)
                .setDividerColorId(R.color.colorPrimary)
                .build()
                .showAppCompat();
    }
}

package ru.andrikeev.android.synoptic.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.andrikeev.android.synoptic.ui.activity.about.AboutActivity;
import ru.andrikeev.android.synoptic.ui.activity.city.CityActivity;
import ru.andrikeev.android.synoptic.ui.activity.main.MainActivity;
import ru.andrikeev.android.synoptic.ui.activity.settings.SettingsActivity;

/**
 * Вспомогательный класс для работы с Intent'ами.
 */
public class IntentHelper {

    public static void openMainActivity(@NonNull Context context) {
        context.startActivity(MainActivity.getIntent(context));
    }

    public static void openSettingsActivity(@NonNull Context context) {
        context.startActivity(SettingsActivity.getIntent(context));
    }

    public static void openAboutActivity(@NonNull Context context) {
        context.startActivity(AboutActivity.getIntent(context));
    }

    public static void openCityActivity(@NonNull Context context, boolean isFirstStart){
        context.startActivity(CityActivity.getIntent(context,isFirstStart));
    }

    public static boolean sendEmail(@NonNull Context context,
                                    @NonNull String email,
                                    @Nullable String title,
                                    @Nullable String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}

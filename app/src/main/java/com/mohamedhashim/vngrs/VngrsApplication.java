package com.mohamedhashim.vngrs;

import android.app.Application;
import android.content.Context;

import com.mohamedhashim.vngrs.data.ApplicationComponent;
import com.mohamedhashim.vngrs.data.ApplicationModule;
import com.mohamedhashim.vngrs.data.DaggerApplicationComponent;

/**
 * Created by Mohamed Hashim on 6/30/2018.
 */
public class VngrsApplication extends Application {
    public static VngrsApplication instance;
    protected ApplicationComponent component;

    public static ApplicationComponent getAppComponent(Context context) {
        VngrsApplication app = (VngrsApplication) context.getApplicationContext();
        if (app.component == null) {
            createComponent(app);
        }
        return app.component;
    }

    protected static void createComponent(VngrsApplication app) {
        app.component = DaggerApplicationComponent.builder()
                .applicationModule(app.getApplicationModule())
                .build();
    }

    public static void clearAppComponent(Context context) {
        VngrsApplication app = (VngrsApplication) context.getApplicationContext();
        app.component = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    protected ApplicationModule getApplicationModule() {
        return new ApplicationModule(this);
    }
}

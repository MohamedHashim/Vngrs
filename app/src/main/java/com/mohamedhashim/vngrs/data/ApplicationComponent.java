package com.mohamedhashim.vngrs.data;

import com.mohamedhashim.vngrs.VngrsApplication;
import com.mohamedhashim.vngrs.ui.activities.MainActivity;
import com.mohamedhashim.vngrs.ui.presenters.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {com.mohamedhashim.vngrs.data.ApplicationModule.class})
public interface ApplicationComponent {
    /* Application */
    void inject(VngrsApplication app);

    /* Activities */
    void inject(MainActivity activity);

    /* Presenters */
    void inject(MainPresenter presenter);
}
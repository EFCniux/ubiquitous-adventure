package es.niux.efc.demoapp.common.injection;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import es.niux.efc.demoapp.TheApplication;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        ApplicationBinderModule.class,
})
public interface ApplicationComponent {
    @Component.Builder interface Builder {
        @BindsInstance Builder application(TheApplication application);
        ApplicationComponent build();
    }

    void inject(TheApplication application);
}

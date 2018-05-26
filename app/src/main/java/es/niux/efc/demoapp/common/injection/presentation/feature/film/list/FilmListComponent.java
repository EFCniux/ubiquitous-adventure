package es.niux.efc.demoapp.common.injection.presentation.feature.film.list;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import es.niux.efc.demoapp.common.injection.scope.PerActivity;
import es.niux.efc.demoapp.presentation.feature.film.list.FilmListActivityImpl;

@PerActivity
@Subcomponent(modules = {
        FilmListModule.class
})
public interface FilmListComponent extends AndroidInjector<FilmListActivityImpl> {
    @Subcomponent.Builder abstract class Builder
            extends AndroidInjector.Builder<FilmListActivityImpl>
    { }
}

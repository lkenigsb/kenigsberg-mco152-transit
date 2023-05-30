package kenigsberg.transit;

import dagger.Component;

import javax.inject.Singleton;

public interface TransitComponent {
    @Singleton
    @Component(modules = {TranitServiceModule.class})
    public interface ForecastWeatherComponent {
        TransitFrame providesTransitFrame();
    }
}

package kenigsberg.transit;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {TransitServiceModule.class})
public interface TransitComponent {
    TransitFrame providesTransitFrame();
}


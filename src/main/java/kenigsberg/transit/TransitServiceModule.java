package kenigsberg.transit;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.swing.*;

@Module
public class TransitServiceModule {

    @Provides
    public TransitService providesTransitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bustime.mta.info/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        TransitService service = retrofit.create(TransitService.class);
        return service;
    }

    @Provides
    @Named("limitedInfo")
    @Singleton
    public JLabel providesLimitedInfo()
    {
        return new JLabel();
    }

    @Provides
    @Named("tableModel")
    @Singleton
    public JLabel providesTableModel()
    {
        return new JLabel();
    }
}

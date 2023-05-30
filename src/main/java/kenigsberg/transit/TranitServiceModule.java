package kenigsberg.transit;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class TranitServiceModule {

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
}

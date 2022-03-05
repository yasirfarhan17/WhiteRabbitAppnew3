package com.example.whiterabbitapp.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.networkmodule.api.EmployeeAPi
import com.example.networkmodule.database.EmployeeDao
import com.example.networkmodule.database.EmployeeDataBase
import com.example.networkmodule.network.HeaderInterceptor
import com.example.networkmodule.network.NetworkClient
import com.example.networkmodule.network.NetworkManager
import com.example.networkmodule.repository.EmployeeRepository
import com.example.networkmodule.repository.EmployeeRepositoryImpl
import com.example.networkmodule.usecase.EmployeeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): EmployeeAPi {
        return retrofit.create(EmployeeAPi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: EmployeeAPi): EmployeeRepository {
        return EmployeeRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideNetworkManager(
        @ApplicationContext context: Context
    ): NetworkManager {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return NetworkManager(connectivityManager)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        networkManager: NetworkManager
    ): OkHttpClient {
        return NetworkClient.provideOkHttp(
            headerInterceptor,
            networkManager
        )
    }

    @Provides
    @Singleton
    fun provideUseCase(
        repository: EmployeeRepository
    ): EmployeeUseCase {

        return EmployeeUseCase(repository)

    }

    @Singleton
    @Provides
    fun provideBaseAndroidDatabase(
        @ApplicationContext context: Context
    ): EmployeeDataBase {
        return Room.databaseBuilder(
            context,
            EmployeeDataBase::class.java,
            EmployeeDataBase.NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideEmployeeDao(employeeDataBase: EmployeeDataBase): EmployeeDao {
        return employeeDataBase.employeeDao
    }
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = NetworkClient.provideRetrofit(okHttpClient)
}
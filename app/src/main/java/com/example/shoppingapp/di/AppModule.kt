package com.example.shoppingapp.di


import com.example.shoppingapp.data.remote.api.ProductApi
import com.example.shoppingapp.data.remote.firebase.FavoriteRemoteDataSource
import com.example.shoppingapp.data.remote.firebase.FavoriteRemoteDataSourceImpl
import com.example.shoppingapp.data.repository.ProductRepositoryImpl
import com.example.shoppingapp.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFavoriteRemoteDataSource(
        firestore: FirebaseFirestore
    ): FavoriteRemoteDataSource = FavoriteRemoteDataSourceImpl(firestore)

    @Provides
    @Singleton
    fun provideProductRepository(
        api: ProductApi,
        favoriteRemoteDataSource: FavoriteRemoteDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(api, favoriteRemoteDataSource)
    }
}

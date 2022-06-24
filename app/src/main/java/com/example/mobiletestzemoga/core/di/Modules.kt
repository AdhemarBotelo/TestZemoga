package com.example.mobiletestzemoga.core.di

import android.content.Context
import androidx.room.Room
import com.example.mobiletestzemoga.data.db.PostDatabase
import com.example.mobiletestzemoga.data.db.PostStore
import com.example.mobiletestzemoga.data.db.PostStoreImpl
import com.example.mobiletestzemoga.data.network.APIPostService
import com.example.mobiletestzemoga.data.network.APIPostService.Companion.BASE_URL
import com.example.mobiletestzemoga.data.network.NetworkHandler
import com.example.mobiletestzemoga.data.network.PostService
import com.example.mobiletestzemoga.data.repository.PostRepository
import com.example.mobiletestzemoga.data.repository.PostRepositoryImpl
import com.example.mobiletestzemoga.ui.detail.DetailPostViewModel
import com.example.mobiletestzemoga.ui.favorite.FavoriteViewModel
import com.example.mobiletestzemoga.ui.post.PostViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mLocalModules = module {


}

val androidModule = module {

    fun provideDataBase(context: Context): PostDatabase {
        return Room.databaseBuilder(context, PostDatabase::class.java, "posts_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single() {
       provideDataBase(androidApplication())
    }

    single(){
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { NetworkHandler(androidContext()) }
    single<PostStore> { PostStoreImpl(get())  }
    single<APIPostService> { PostService(get())}
    single<PostRepository> { PostRepositoryImpl(get(),get(),get())  }
    viewModel {
        PostViewModel(get())
    }
    viewModel {
        DetailPostViewModel(get())
    }
    viewModel {
        FavoriteViewModel(get())
    }
}
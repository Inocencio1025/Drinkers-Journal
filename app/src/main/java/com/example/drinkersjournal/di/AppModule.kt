package com.example.drinkersjournal.di
/*
import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drinkersjournal.data.DrinkDatabase
import com.example.drinkersjournal.data.DrinkRepository
import com.example.drinkersjournal.data.DrinkRepositoryImpl
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
    fun provideDrinkDatabase(app: Application): DrinkDatabase{
        return Room.databaseBuilder(
            app,
            DrinkDatabase::class.java,
        "drink_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDrinkRepository(db: DrinkDatabase): DrinkRepository{
        return DrinkRepositoryImpl(db.dao)
    }

} */
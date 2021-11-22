package com.example.devops.database.devops

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.devops.database.devops.artist.Artist
import com.example.devops.database.devops.bet.Bet
import com.example.devops.database.devops.order.Order
import com.example.devops.database.devops.product.Product
import com.example.devops.database.devops.review.Review
import com.example.devops.database.devops.shoppingcart.ShoppingCart
import com.example.devops.database.devops.tag.Tag
import com.example.devops.database.devops.userart.UserArt

@Database(entities = [Artist::class, Bet::class, Order::class, Product::class, Review::class, ShoppingCart::class, Tag::class, UserArt::class], version = 1, exportSchema = false)
abstract class DevOpsDatabase : RoomDatabase() {

    //abstract val devOpsDatabaseDao: DevOpsDatabase

    companion object {

        @Volatile
        private var INSTANCE: DevOpsDatabase? = null

        fun getInstance(context: Context): DevOpsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DevOpsDatabase::class.java,
                        "dev_ops_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
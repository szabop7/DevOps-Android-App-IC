package com.example.devops.database.devops

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.devops.database.devops.artist.ArtistDao
import com.example.devops.database.devops.artist.ArtistDatabase
import com.example.devops.database.devops.bet.Bet
import com.example.devops.database.devops.bet.BetDao
import com.example.devops.database.devops.order.Order
import com.example.devops.database.devops.order.OrderDao
import com.example.devops.database.devops.product.*
import com.example.devops.database.devops.review.Review
import com.example.devops.database.devops.review.ReviewDao
import com.example.devops.database.devops.shoppingcart.ShoppingCart
import com.example.devops.database.devops.shoppingcart.ShoppingCartDao
import com.example.devops.database.devops.shoppingcart.ShoppingCartProductCrossRef
import com.example.devops.database.devops.tag.Tag
import com.example.devops.database.devops.tag.TagDao
import com.example.devops.database.devops.tag.TagProductCrossRef
import com.example.devops.database.devops.userart.*

@Database(entities = [ArtistDatabase::class, ProductTagCrossRef::class, ShoppingCartProductCrossRef::class, TagProductCrossRef::class, Bet::class, Order::class, ProductDatabase::class, Review::class, ShoppingCart::class, Tag::class, UserArt::class], version = 4, exportSchema = false)
abstract class DevOpsDatabase : RoomDatabase() {

    abstract val artistDao: ArtistDao
    abstract val betDao: BetDao
    abstract val orderDao: OrderDao
    abstract val productDao: ProductDao
    abstract val reviewDao: ReviewDao
    abstract val shoppingCartDao: ShoppingCartDao
    abstract val tagDao: TagDao
    abstract val userArtDao: UserArtDao

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
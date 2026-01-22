package seneca.pmugisha3.cosmotracker.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import seneca.pmugisha3.cosmotracker.data.local.dao.FavoriteEventDao
import seneca.pmugisha3.cosmotracker.data.local.entity.FavoriteEventEntity

@Database(
    entities = [FavoriteEventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CosmoDatabase : RoomDatabase() {

    abstract fun favoriteEventDao(): FavoriteEventDao

    companion object {
        @Volatile
        private var INSTANCE: CosmoDatabase? = null

        fun getDatabase(context: Context): CosmoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CosmoDatabase::class.java,
                    "cosmo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
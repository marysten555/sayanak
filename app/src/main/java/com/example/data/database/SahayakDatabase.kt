package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.CenterDao
import com.example.data.dao.ChatDao
import com.example.data.dao.ReportDao
import com.example.data.model.AnonymousChatMessage
import com.example.data.model.CenterEntity
import com.example.data.model.ReportEntity
import com.example.data.repository.SampleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ReportEntity::class, CenterEntity::class, AnonymousChatMessage::class], version = 2, exportSchema = false)
abstract class SahayakDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao
    abstract fun centerDao(): CenterDao
    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var INSTANCE: SahayakDatabase? = null

        fun getDatabase(context: Context): SahayakDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SahayakDatabase::class.java,
                    "sahayak_triage_db"
                ).fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed verified empanelled centers and initial triage demo cases
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getDatabase(context)
                            database.centerDao().insertAll(SampleData.defaultCenters)
                            SampleData.seedReports.forEach { report ->
                                database.reportDao().insertReport(report)
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

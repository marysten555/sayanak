package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.CenterEntity
import com.example.data.model.ReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    @Query("SELECT * FROM reports ORDER BY createdAt DESC")
    fun getAllReports(): Flow<List<ReportEntity>>

    @Query("SELECT * FROM reports WHERE trackingToken = :token LIMIT 1")
    suspend fun getReportByToken(token: String): ReportEntity?

    @Query("SELECT * FROM reports WHERE trackingToken = :token LIMIT 1")
    fun getReportByTokenFlow(token: String): Flow<ReportEntity?>

    @Query("SELECT * FROM reports WHERE id = :id LIMIT 1")
    suspend fun getReportById(id: Long): ReportEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ReportEntity): Long

    @Update
    suspend fun updateReport(report: ReportEntity)

    @Query("UPDATE reports SET status = :status, statusNotes = :notes, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateReportStatus(id: Long, status: String, notes: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE reports SET isThreatenedForReporting = :threatened, situationEscalated = :escalated, severityTier = :newTier, status = :newStatus, statusNotes = :newNotes, updatedAt = :updatedAt WHERE trackingToken = :token")
    suspend fun flagEscalation(
        token: String,
        threatened: Boolean,
        escalated: Boolean,
        newTier: String,
        newStatus: String,
        newNotes: String,
        updatedAt: Long = System.currentTimeMillis()
    )

    @Query("DELETE FROM reports WHERE id = :id")
    suspend fun deleteReport(id: Long)
}

@Dao
interface CenterDao {
    @Query("SELECT * FROM centers ORDER BY rating DESC")
    fun getAllCenters(): Flow<List<CenterEntity>>

    @Query("SELECT * FROM centers WHERE categorySupported = :category OR categorySupported = 'ALL' ORDER BY rating DESC")
    fun getCentersByCategory(category: String): Flow<List<CenterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(centers: List<CenterEntity>)

    @Query("SELECT COUNT(*) FROM centers")
    suspend fun getCenterCount(): Int
}

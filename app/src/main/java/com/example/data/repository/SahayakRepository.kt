package com.example.data.repository

import com.example.data.dao.CenterDao
import com.example.data.dao.ReportDao
import com.example.data.model.CaseStatus
import com.example.data.model.CenterEntity
import com.example.data.model.ReportEntity
import com.example.data.model.SeverityTier
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import kotlin.random.Random

class SahayakRepository(
    private val reportDao: ReportDao,
    private val centerDao: CenterDao
) {
    val allReports: Flow<List<ReportEntity>> = reportDao.getAllReports()
    val allCenters: Flow<List<CenterEntity>> = centerDao.getAllCenters()

    fun getReportByTokenFlow(token: String): Flow<ReportEntity?> {
        return reportDao.getReportByTokenFlow(token.trim().uppercase())
    }

    suspend fun getReportByToken(token: String): ReportEntity? {
        return reportDao.getReportByToken(token.trim().uppercase())
    }

    suspend fun submitReport(report: ReportEntity): Long {
        return reportDao.insertReport(report)
    }

    suspend fun updateReportStatus(id: Long, status: CaseStatus, notes: String) {
        reportDao.updateReportStatus(id, status.name, notes)
    }

    suspend fun flagPostReportEscalation(
        token: String,
        threatened: Boolean,
        escalated: Boolean,
        additionalNote: String = ""
    ) {
        val currentReport = reportDao.getReportByToken(token) ?: return

        val newTier = SeverityTier.URGENT_SOS.name
        val newStatus = CaseStatus.ESCALATED_PROTECTIVE.name
        val newNotes = buildString {
            append(currentReport.statusNotes)
            append("\n[CRITICAL POST-REPORT UPDATE - ")
            if (threatened) append("Reporter threatened for reporting! ")
            if (escalated) append("Situation actively escalated on the ground! ")
            if (additionalNote.isNotBlank()) append("Note: $additionalNote ")
            append("Automatic SOS Protective Escalation Activated]")
        }

        reportDao.flagEscalation(
            token = token,
            threatened = threatened || currentReport.isThreatenedForReporting,
            escalated = escalated || currentReport.situationEscalated,
            newTier = newTier,
            newStatus = newStatus,
            newNotes = newNotes
        )
    }

    fun getCentersByCategory(category: String): Flow<List<CenterEntity>> {
        return centerDao.getCentersByCategory(category)
    }

    suspend fun seedInitialCentersIfNeeded() {
        val count = centerDao.getCenterCount()
        if (count == 0) {
            centerDao.insertAll(SampleData.defaultCenters)
            SampleData.seedReports.forEach { reportDao.insertReport(it) }
        }
    }

    companion object {
        fun generateTrackingToken(): String {
            val part1 = Random.nextInt(1000, 9999)
            val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
            val part2 = (1..4).map { chars.random() }.joinToString("")
            return "SHK-$part1-$part2"
        }
    }
}

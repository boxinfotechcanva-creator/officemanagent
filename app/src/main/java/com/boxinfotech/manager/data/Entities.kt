package com.boxinfotech.manager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity
data class TeamMember(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val email: String,
    val role: String
)

@Entity
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val status: String, // Planned, InProgress, Paused, Completed
    val startDate: Long,
    val dueDate: Long
)

@Entity(
    foreignKeys = [
        ForeignKey(entity = Project::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = TeamMember::class,
            parentColumns = ["id"],
            childColumns = ["memberId"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class Assignment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val projectId: Long,
    val memberId: Long,
    val assignedAt: Long
)

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerName: String,
    val amount: Double,
    val status: String, // New, Processing, Shipped, Delivered, Cancelled
    val createdAt: Long
)

@Entity(
    foreignKeys = [
        ForeignKey(entity = Order::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE)
    ]
)
data class Invoice(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderId: Long,
    val invoiceNumber: String,
    val amount: Double,
    val dueDate: Long,
    val paid: Boolean
)
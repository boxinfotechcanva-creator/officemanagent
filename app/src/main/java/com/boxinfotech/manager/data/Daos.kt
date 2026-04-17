package com.boxinfotech.manager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamMemberDao {
    @Query("SELECT * FROM TeamMember ORDER BY name ASC")
    fun all(): Flow<List<TeamMember>>

    @Insert
    suspend fun insert(member: TeamMember): Long
}

@Dao
interface ProjectDao {
    @Query("SELECT * FROM Project ORDER BY dueDate ASC")
    fun all(): Flow<List<Project>>

    @Query("SELECT * FROM Project WHERE id = :id")
    fun byId(id: Long): Flow<Project?>

    @Insert
    suspend fun insert(project: Project): Long

    @Update
    suspend fun update(project: Project)
}

@Dao
interface AssignmentDao {
    @Query("SELECT * FROM Assignment WHERE projectId = :projectId")
    fun forProject(projectId: Long): Flow<List<Assignment>>

    @Insert
    suspend fun insert(assignment: Assignment): Long
}

@Dao
interface OrderDao {
    @Query("SELECT * FROM `Order` ORDER BY createdAt DESC")
    fun all(): Flow<List<Order>>

    @Insert
    suspend fun insert(order: Order): Long
}

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM Invoice ORDER BY dueDate ASC")
    fun all(): Flow<List<Invoice>>

    @Insert
    suspend fun insert(invoice: Invoice): Long
}
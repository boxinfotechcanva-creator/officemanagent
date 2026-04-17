package com.boxinfotech.manager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [TeamMember::class, Project::class, Assignment::class, Order::class, Invoice::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun teamMemberDao(): TeamMemberDao
    abstract fun projectDao(): ProjectDao
    abstract fun assignmentDao(): AssignmentDao
    abstract fun orderDao(): OrderDao
    abstract fun invoiceDao(): InvoiceDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "box_infotech.db"
                ).addCallback(object: Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed sample data
                        CoroutineScope(Dispatchers.IO).launch {
                            val daoT = get(context).teamMemberDao()
                            val daoP = get(context).projectDao()
                            val daoA = get(context).assignmentDao()
                            val daoO = get(context).orderDao()
                            val daoI = get(context).invoiceDao()

                            // Seed with reserved example.com addresses (RFC 2606) to avoid
                            // shipping real-looking contact data in the APK.
                            val aliceId = daoT.insert(TeamMember(name="Alice", email="alice@example.com", role="PM"))
                            val bobId = daoT.insert(TeamMember(name="Bob", email="bob@example.com", role="Engineer"))
                            val carolId = daoT.insert(TeamMember(name="Carol", email="carol@example.com", role="Designer"))

                            val now = System.currentTimeMillis()
                            val p1Id = daoP.insert(Project(name="Website Revamp", description="New marketing site", status="InProgress", startDate=now-86400000L*10, dueDate=now+86400000L*20))
                            val p2Id = daoP.insert(Project(name="Mobile App", description="Customer app v1", status="Planned", startDate=now, dueDate=now+86400000L*60))

                            daoA.insert(Assignment(projectId=p1Id, memberId=bobId, assignedAt=now))
                            daoA.insert(Assignment(projectId=p1Id, memberId=carolId, assignedAt=now))

                            val o1Id = daoO.insert(Order(customerName="Acme Corp", amount=14999.0, status="Processing", createdAt=now-86400000L*3))
                            daoI.insert(Invoice(orderId=o1Id, invoiceNumber="INV-1001", amount=14999.0, dueDate=now+86400000L*7, paid=false))
                        }
                    }
                }).build()
                INSTANCE = inst
                inst
            }
        }
    }
}
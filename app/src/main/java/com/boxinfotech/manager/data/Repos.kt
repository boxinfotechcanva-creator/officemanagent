package com.boxinfotech.manager.data

import android.content.Context

class Repos private constructor(context: Context) {
    private val db = AppDatabase.get(context)
    val team = db.teamMemberDao()
    val projects = db.projectDao()
    val assign = db.assignmentDao()
    val orders = db.orderDao()
    val invoices = db.invoiceDao()

    companion object {
        @Volatile private var INSTANCE: Repos? = null
        fun of(context: Context): Repos {
            return INSTANCE ?: synchronized(this) {
                val inst = Repos(context.applicationContext)
                INSTANCE = inst
                inst
            }
        }
    }
}
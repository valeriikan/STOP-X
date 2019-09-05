package com.aware.app.testx.model

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.aware.syncadapters.AwareSyncAdapter

class TestX_Sync : Service() {
    private var sSyncAdapter: AwareSyncAdapter? = null
    companion object {
        private val sSyncAdapterLock = Any()
    }

    override fun onCreate() {
        super.onCreate()
        synchronized(sSyncAdapterLock) {

            if (sSyncAdapter == null) {
                sSyncAdapter = AwareSyncAdapter(applicationContext, true, true)
                sSyncAdapter!!.init(
                        Provider.DATABASE_TABLES, Provider.TABLES_FIELDS,
                        arrayOf(Provider.Users_Table.CONTENT_URI)
                )
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return sSyncAdapter!!.syncAdapterBinder
    }
}

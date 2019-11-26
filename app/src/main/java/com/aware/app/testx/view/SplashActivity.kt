package com.aware.app.testx.view

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import com.aware.Aware
import com.aware.ui.PermissionsHandler
import java.util.ArrayList

class SplashActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        // List of required permission
        val REQUIRED_PERMISSIONS = ArrayList<String>()
        REQUIRED_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        REQUIRED_PERMISSIONS.add(Manifest.permission.WRITE_SYNC_SETTINGS)
        REQUIRED_PERMISSIONS.add(Manifest.permission.READ_SYNC_SETTINGS)
        REQUIRED_PERMISSIONS.add(Manifest.permission.INTERNET)
        REQUIRED_PERMISSIONS.add(Manifest.permission.GET_ACCOUNTS)

        // flag to check permissions
        var permissions_ok = true
        for (p in REQUIRED_PERMISSIONS) {
            if (PermissionChecker.checkSelfPermission(this, p) != PermissionChecker.PERMISSION_GRANTED) {
                permissions_ok = false
                break
            }
        }

        // 1st: Check for permissions
        if (permissions_ok) {
            startService(Intent(applicationContext, Aware::class.java))
            startActivity(Intent(this, ConsentActivity::class.java))
            finish()

            // TODO:

            //            if (Aware.isStudy(this)) {
            //                // Open MainActivity when all conditions are ok
            //                Intent main = new Intent(this, MainActivityPresenter.class);
            //                startActivity(main);
            //                finish();
            //
            //            } else {
            //                Aware.joinStudy(this, STUDY_URL);
            //                IntentFilter joinFilter = new IntentFilter(Aware.ACTION_JOINED_STUDY);
            //                registerReceiver(joinObserver, joinFilter);
            //            }

        } else {

            val permissions = Intent(this, PermissionsHandler::class.java)
            permissions.putExtra(PermissionsHandler.EXTRA_REQUIRED_PERMISSIONS, REQUIRED_PERMISSIONS)
            permissions.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(permissions)
        }
    }
}

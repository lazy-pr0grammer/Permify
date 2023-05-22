package com.aylax.library.api

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.aylax.library.model.Application
import com.aylax.library.model.Permission
import com.aylax.library.model.Tracker
import com.aylax.library.util.Level
import java.io.BufferedReader
import java.io.InputStreamReader

class AppManager(private var context: Context) {

    @SuppressLint("QueryPermissionsNeeded", "NewApi")
    fun getApplications(system: Boolean): List<Application> {
        return try {
            val applicationsList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getInstalledApplications(
                    PackageManager.ApplicationInfoFlags.of(
                        PackageManager.GET_META_DATA.toLong()
                    )
                )
            } else {
                @Suppress("DEPRECATION") context.packageManager.getInstalledApplications(
                    PackageManager.GET_META_DATA
                )
            }
            val applications = mutableListOf<Application>()
            for (info in applicationsList) {
                val app = Application()
                app.pkg_name = info.packageName
                app.app_icon = info.loadIcon(context.packageManager)
                app.app_name = info.loadLabel(context.packageManager).toString()
                app.permissions = getPermissions(info.packageName)
                app.trackers = getTrackers(info)
                if (system) {
                    if (info.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                        app.is_system = true
                        applications.add(app)
                    }
                } else {
                    if (info.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                        app.is_system = false
                        applications.add(app)
                    }
                }
            }
            applications
        } catch (e: Exception) {
            emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getPermissions(pkg: String): List<Permission> {
        return try {
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    pkg, PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong())
                )
            } else {
                @Suppress("DEPRECATION") context.packageManager.getPackageInfo(
                    pkg, PackageManager.GET_PERMISSIONS
                )
            }
            val requestedPermissions = packageInfo.requestedPermissions
            val permissions = mutableListOf<Permission>()

            requestedPermissions?.let {
                for (permission in requestedPermissions) {
                    if (context.packageManager.checkPermission(
                            permission, pkg
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        permissions.add(
                            Permission(
                                permission, is_granted = true, is_dangerous = true
                            )
                        )
                    } else {
                        permissions.add(
                            Permission(
                                permission, is_granted = false, is_dangerous = true
                            )
                        )
                    }
                }
            }

            permissions
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getApplicationInfo(pkg: String): Application {
        val appInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getApplicationInfo(
                pkg, PackageManager.ApplicationInfoFlags.of(0)
            )
        } else {
            @Suppress("DEPRECATION") context.packageManager.getApplicationInfo(pkg, 0)
        }
        return Application(
            appInfo.loadLabel(context.packageManager).toString(),
            pkg,
            appInfo.loadIcon(context.packageManager),
            appInfo.flags != 0,
            emptyList(),
        )
    }

    private fun getTrackers(info: ApplicationInfo): List<Tracker> {
        val metadata = info.metaData
        val trackers = mutableListOf<Tracker>()
        context.assets.open("trackers.txt").use { it ->
            BufferedReader(InputStreamReader(it)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    line = reader.readLine()
                    if (metadata != null && metadata.containsKey(line)) {
                        val fields = line.split("-").map { it.trim() }
                        if (fields.size == 2) {
                            val security = when (fields[1]) {
                                "NORMAL" -> Level.NORMAL
                                "MEDIUM" -> Level.MEDIUM
                                else -> Level.DANGEROUS
                            }
                            Log.d(AppManager::class.simpleName, fields[0])
                            trackers.add(Tracker(fields[0], security))
                        }
                    }
                }
            }
        }
        return trackers
    }

    private fun isDangerousPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            context.packageManager.getPermissionInfo(
                permission, PackageManager.GET_META_DATA
            ).protection == PermissionInfo.PROTECTION_DANGEROUS
        } else {
            @Suppress("DEPRECATION")
            context.packageManager.getPermissionInfo(
                permission, PackageManager.GET_META_DATA
            ).protectionLevel == PermissionInfo.PROTECTION_DANGEROUS
        }
    }

}
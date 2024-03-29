package com.aylax.library.api

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import com.aylax.library.model.Application
import com.aylax.library.model.Permission

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
                context.packageManager.getInstalledApplications(
                    PackageManager.GET_META_DATA
                )
            }
            val applications = mutableListOf<Application>()
            for (info in applicationsList) {
                val app = Application()
                app.pkgName = info.packageName
                app.appIcon = info.loadIcon(context.packageManager)
                app.appName = info.loadLabel(context.packageManager).toString()
                app.permissions = getPermissions(info.packageName)
                if (system) {
                    if (info.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                        app.isSystem = true
                        applications.add(app)
                    }
                } else {
                    if (info.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                        app.isSystem = false
                        applications.add(app)
                    }
                }
            }
            applications
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun getPermissions(pkg: String): List<Permission> {
        return try {
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    pkg, PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong())
                )
            } else {
                context.packageManager.getPackageInfo(
                    pkg, PackageManager.GET_PERMISSIONS
                )
            }
            val requestedPermissions = packageInfo.requestedPermissions
            val grantedPermissions = mutableListOf<Permission>()

            requestedPermissions?.let {
                for (permission in requestedPermissions) {
                    if (context.packageManager.checkPermission(
                            permission, pkg
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        grantedPermissions.add(Permission(permission, pkg, true))
                    } else {

                        grantedPermissions.add(Permission(permission, pkg, false))
                    }
                }
            }

            grantedPermissions
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
            context.packageManager.getApplicationInfo(pkg, 0)
        }
        return Application(
            appInfo.loadLabel(context.packageManager).toString(),
            pkg,
            appInfo.loadIcon(context.packageManager),
            appInfo.flags != 0,
            emptyList()
        )
    }

}
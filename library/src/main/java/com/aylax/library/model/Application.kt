package com.aylax.library.model

import android.graphics.drawable.Drawable

data class Application(
    var appName: String? = "",
    var pkgName: String? = "",
    var appIcon: Drawable? = null,
    var isSystem: Boolean? = false,
    var permissions: List<Permission> = emptyList()
)

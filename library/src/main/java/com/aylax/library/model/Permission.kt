package com.aylax.library.model

data class Permission(
    var name: String? = "",
    var pkgName: String? = "",
    var isGranted: Boolean? = false,
    var isDangerous: Boolean? = false
)

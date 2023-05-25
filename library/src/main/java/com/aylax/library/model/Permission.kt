package com.aylax.library.model

data class Permission(
    var name: String? = "",
    var pkg_name: String? = "",
    var is_granted: Boolean? = false,
    var is_dangerous: Boolean? = false
)

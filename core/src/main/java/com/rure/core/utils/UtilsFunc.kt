package com.rure.core.utils

fun String.toPhoneFormat(): String {
    if(this.length != 11) return ""
    return String.format("%s-%s-%s",
        this.substring(0, 3),
        this.substring(3, 7),
        this.substring(7, 11))
}
package com.thailam.weatherwhen.utils

fun StringBuilder.appendUnit(value: String, unit: String): String =
    this@appendUnit.apply {
        append(value).append(" ").append(unit)
    }.toString()

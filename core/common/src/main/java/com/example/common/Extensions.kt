package com.example.common

import android.content.Context
import java.io.InputStream
import java.io.InputStreamReader

fun readAssetFile(context: Context, fileName: String): String {
    val assetManager = context.assets
    val inputStream: InputStream = assetManager.open(fileName)
    val reader = InputStreamReader(inputStream)
    val stringBuilder = StringBuilder()

    reader.forEachLine {
        stringBuilder.append(it)
    }
    return stringBuilder.toString()
}
package com.example.common

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun readAssetFile(context: Context, fileName: String): String {
    return try {
        val assetManager = context.assets
        val inputStream: InputStream = assetManager.open(fileName)
        val reader = InputStreamReader(inputStream)
        val stringBuilder = StringBuilder()

        reader.forEachLine {
            stringBuilder.append(it)
        }
        stringBuilder.toString()
    } catch (e: IOException) {
        throw RuntimeException("Asset dosyası okuma hatası: $fileName", e)
    }
}

fun formatDate(inputDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        outputFormat.format(date)
    } catch (e: Exception) {
        "Geçersiz tarih formatı"
    }
}

fun formatNumber(number: Long): String {
    val locale = Locale("tr", "TR") // Türkçe format için
    val numberFormat = NumberFormat.getInstance(locale)
    return numberFormat.format(number)
}

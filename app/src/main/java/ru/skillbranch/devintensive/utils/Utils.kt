package ru.skillbranch.devintensive.utils

import android.content.Context

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")

        if (fullName.isNullOrBlank())
            return Pair(null, null)

        return Pair(parts?.getOrNull(0), parts?.getOrNull(1))
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName.isNullOrBlank() && lastName.isNullOrBlank())
            return null

        val fNameInitial = firstName?.first()
        val lNameInitial = lastName?.first()

        return (when {
            fNameInitial == null -> "$lNameInitial"
            lNameInitial == null -> "$fNameInitial"
            else -> "$fNameInitial$lNameInitial"
        }).toUpperCase()
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var result = String()

        for (symbol in payload.trim()){
            if(symbol == ' ') {
                result += divider
                continue
            }
            if(translitMap.containsKey(symbol))
                result += translitMap[symbol]
            else
                result += symbol
        }

        return result
    }

    private val translitMap = mutableMapOf(
        'а' to "a",
        'б' to "b",
        'в' to "v",
        'г' to "g",
        'д' to "d",
        'е' to "e",
        'ё' to "e",
        'ж' to "zh",
        'з' to "z",
        'и' to "i",
        'й' to "i",
        'к' to "k",
        'л' to "l",
        'м' to "m",
        'н' to "n",
        'о' to "o",
        'п' to "p",
        'р' to "r",
        'с' to "s",
        'т' to "t",
        'у' to "u",
        'ф' to "f",
        'х' to "h",
        'ц' to "c",
        'ч' to "ch",
        'ш' to "sh",
        'щ' to "sh",
        'ъ' to "",
        'ь' to "",
        'э' to "e",
        'ю' to "yu",
        'я' to "ya",

        'А' to "A",
        'Б' to "B",
        'В' to "V",
        'Г' to "G",
        'Д' to "D",
        'Е' to "E",
        'Ё' to "E",
        'Ж' to "Zh",
        'З' to "Z",
        'И' to "I",
        'Й' to "I",
        'К' to "K",
        'Л' to "L",
        'М' to "M",
        'Н' to "N",
        'О' to "O",
        'П' to "P",
        'Р' to "R",
        'С' to "S",
        'Т' to "T",
        'У' to "U",
        'Ф' to "F",
        'Х' to "H",
        'Ц' to "C",
        'Ч' to "Ch",
        'Ш' to "Sh",
        'Щ' to "Sh",
        'Ъ' to "",
        'Ь' to "",
        'Э' to "E",
        'Ю' to "Yu",
        'Я' to "Ya"
        )

    fun convertPxToDp(context: Context, px: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun convertDpToPx(context: Context, dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun convertSpToPx(context: Context, sp: Int): Int {
        return sp * context.resources.displayMetrics.scaledDensity.toInt()
    }
}
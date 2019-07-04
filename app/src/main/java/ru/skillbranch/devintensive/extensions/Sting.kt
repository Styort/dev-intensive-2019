package ru.skillbranch.devintensive.extensions

fun String.truncate(amount: Int = 16): String {
    val str = this.trim()
    return if (amount >= str.length) str else "${str.substring(0, amount).trim()}..."
}

fun String.stripHtml() : String{
    var removedHtml = "<(.*?)>".toRegex().replace(this, "")
    var removedHtmlEscape = """(&amp;|&lt;|&gt;|&#39;|&quot;)""".toRegex().replace(removedHtml, "") //&amp;|&lt;|&gt;|&#39;|&quot;
    return  "\\s\\s+".toRegex().replace(removedHtmlEscape, " ")
}
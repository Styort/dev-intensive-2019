package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?, String?>{
        val parts : List<String>? = fullName?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?) : String?{
        val fNameInitial = firstName?.first()
        val lNameInitial = lastName?.first()

        return "$fNameInitial$lNameInitial"
    }

    fun transliteration(payload: String, divider: String = " ") : String{

        return "transliteration not implemented"
        TODO("not implemented")
    }
}
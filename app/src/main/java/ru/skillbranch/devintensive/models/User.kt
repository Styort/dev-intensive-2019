package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User private constructor(val builder: Builder)
{
    val id : String
    var firstName : String?
    var lastName : String? = String()
    var avatar : String?
    var rating : Int = 0
    var respect : Int = 0
    var lastVisit : Date? = Date()
    var isOnline : Boolean = false

    init {
        id = builder.id
        firstName = builder.firstName
        lastName = builder.lastName
        avatar = builder.avatar
        println("User created. His name is $firstName $lastName")
    }

    companion object Factory{
        private var lastId : Int = -1

        fun makeUser(fullName:String?) : User{
            lastId ++

            val (firstName, lastName) = Utils.parseFullName(fullName)

            return User.Builder().id("$lastId").firstName(firstName).lastName(lastName).build()
        }
    }

    class Builder {
        var id : String = String()
        var firstName : String?= String()
        var lastName : String?= String()
        var avatar : String?= String()
        var rating : Int = 0
        var respect : Int = 0
        var lastVisit : Date? = Date()
        var isOnline : Boolean = false


        fun id(id: String) = apply { this.id = id }
        fun firstName(firstName: String?) = apply { this.firstName = firstName }
        fun lastName(lastName: String?) = apply { this.lastName = lastName }
        fun avatar(avatar: String?) = apply { this.avatar = avatar }
        fun rating(rating: Int) = apply { this.rating = rating }
        fun respect(respect: Int) = apply { this.respect = respect }
        fun lastVisit(lastVisit: Date?) = apply { this.lastVisit = lastVisit }
        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }
        fun build() = User(this)
    }
}
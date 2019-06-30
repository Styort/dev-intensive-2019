package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

fun User.toUserView(): UserView {

    val nickName = Utils.transliteration("$firstName $lastName");
    val initials = Utils.toInitials(firstName, lastName)
    val status = if(lastVisit == null) "Ещё не был в сети" else if (isOnline) "Online" else "Последний раз был ${lastVisit.humanizeDiff()}"

    return UserView(
        id,
        fullName = "$firstName $lastName",
        avatar = avatar,
        initials = initials,
        nickName = nickName,
        status = status
        )
}

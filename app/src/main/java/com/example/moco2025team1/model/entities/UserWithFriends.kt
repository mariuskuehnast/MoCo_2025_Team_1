package com.example.moco2025team1.model.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "user_friends",
    primaryKeys = ["userId","friendId"],
    foreignKeys = [
        ForeignKey(User::class, parentColumns = ["id"], childColumns = ["userId"]),
        ForeignKey(User::class, parentColumns = ["id"], childColumns = ["friendId"])
    ],
    indices = [ Index("userId"), Index("friendId") ]
)

data class UserFriendCrossRef(
    val userId:  Long,
    val friendId: Long
)

data class UserWithFriends(
    @Embedded val user: User,

    @Relation(
        parentColumn  = "id",
        entityColumn  = "id",
        associateBy   = Junction(
            value         = UserFriendCrossRef::class,
            parentColumn  = "userId",
            entityColumn  = "friendId"
        )
    )
    val friends: List<User>
)

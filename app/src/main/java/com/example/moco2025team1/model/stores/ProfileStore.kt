package com.example.moco2025team1.model.stores

import com.example.moco2025team1.model.daos.UserDao
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.model.entities.UserFriendCrossRef
import com.example.moco2025team1.model.entities.UserWithFriends
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ProfileStore(
    private val userDao: UserDao
) {
    private val defaultNames: List<String> = listOf(
        "Alice", "Bob", "Carol", "David", "Eve"
    )

    suspend fun init() {
        if (userDao.getUserCount() == 0) {
            defaultNames.forEach { name ->
                userDao.insertUser(User(userName = name))
            }
        }
        if (userDao.getUserWithFriendsFlow(1).count() == 0) {
            userDao.addFriendCrossRef(UserFriendCrossRef(1, 2))
            userDao.addFriendCrossRef(UserFriendCrossRef(1, 3))
            userDao.addFriendCrossRef(UserFriendCrossRef(1, 4))
            userDao.addFriendCrossRef(UserFriendCrossRef(1, 5))
        }
    }

    fun getUserWithFriendsFlow(userId: Long): Flow<UserWithFriends> =
        userDao.getUserWithFriendsFlow(userId)

    fun getFriendsFlow(userId: Long): Flow<List<User>> =
        getUserWithFriendsFlow(userId).map { it.friends }

    suspend fun getFriendsOnce(userId: Long): List<User> =
        getUserWithFriendsFlow(userId).first().friends

    suspend fun updateUserName(userId: Long, newName: String) =
        userDao.updateUserName(userId, newName)

    suspend fun addFriend(userId: Long, friendId: Long) =
        userDao.addFriendCrossRef(UserFriendCrossRef(userId, friendId))

    suspend fun removeFriend(userId: Long, friendId: Long) =
        userDao.removeFriend(userId, friendId)
}

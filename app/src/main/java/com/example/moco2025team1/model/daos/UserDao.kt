package com.example.moco2025team1.model.daos

import androidx.room.*
import com.example.moco2025team1.model.entities.User
import com.example.moco2025team1.model.entities.UserFriendCrossRef
import com.example.moco2025team1.model.entities.UserWithFriends
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Query("DELETE FROM users")
    suspend fun clearUsers()

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users")
    fun getAllUsersFlow(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Long): User?

    @Query("UPDATE users SET userName = :newName WHERE id = :userId")
    suspend fun updateUserName(userId: Long, newName: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFriendCrossRef(ref: UserFriendCrossRef)

    @Query("DELETE FROM user_friends WHERE userId = :userId AND friendId = :friendId")
    suspend fun removeFriend(userId: Long, friendId: Long)

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserWithFriendsFlow(userId: Long): Flow<UserWithFriends>
}

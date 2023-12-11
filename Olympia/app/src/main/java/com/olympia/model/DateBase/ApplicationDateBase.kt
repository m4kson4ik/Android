package com.olympia.model.DateBase

import androidx.room.Database
import androidx.room.RoomDatabase
import javax.inject.Singleton


@Database(entities = [Users::class], version = 1)
abstract class UsersDateBase() : RoomDatabase() {
    abstract fun userDAO() : UserDAO
}

//@Database(entities = [Posts::class], version = 1)
//abstract class PostsDateBase() : RoomDatabase() {
//    abstract fun postsDB() : IPostsDB
//}
//
//@Database(entities = [Comments::class], version = 1)
//abstract class CommentDateBase() : RoomDatabase() {
//    abstract fun commentsDB() : ICommentsDB
//}
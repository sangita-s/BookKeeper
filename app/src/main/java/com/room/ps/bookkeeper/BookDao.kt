package com.room.ps.bookkeeper

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface BookDao {
    @Insert
    fun insert(book:Book)

//    @Query("SELECT * FROM books")
//    fun getAllBooks(): LiveData<List<Book>>

    @get:Query("SELECT * FROM books")
    val allBooks:LiveData<List<Book>>

    @Query("SELECT * FROM books WHERE book LIKE :searchString OR author like :searchString")
    fun getBooksByBookOrAuthor(searchString: String): LiveData<List<Book>>

    @Update
    fun update(book:Book)

    @Delete
    fun delete(book: Book)
}
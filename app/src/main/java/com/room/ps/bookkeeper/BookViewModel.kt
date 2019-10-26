package com.room.ps.bookkeeper

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class BookViewModel(application: Application) : AndroidViewModel(application) {

    val allBooks: LiveData<List<Book>>
    private val bookDao: BookDao

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
        allBooks = bookDao.allBooks
    }

    fun insertt(book: Book) {
        InsertAsyncTask(bookDao).execute(book)
    }

    fun updatee(book: Book) {
        UpdateAsyncTask(bookDao).execute(book)
    }

    fun deletee(book: Book) {
        DeleteAsyncTask(bookDao).execute(book)
    }
    companion object {
        private class InsertAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg bookss: Book): Void? {
                bookDao.insert(bookss[0])
                return null
            }
        }

        private class UpdateAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg booksss: Book): Void? {
                bookDao.update(booksss[0])
                return null
            }
        }
        private class DeleteAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg bookssss: Book): Void? {
                bookDao.delete(bookssss[0])
                return null
            }
        }
    }
}
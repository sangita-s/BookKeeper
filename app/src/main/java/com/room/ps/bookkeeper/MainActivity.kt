package com.room.ps.bookkeeper

import android.app.Activity
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BookListAdapter.OnDeleteClickListener {

    private lateinit var bookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val bookListAdapter = BookListAdapter(this, this)
        recyclerview.adapter = bookListAdapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener { view ->
            val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_BOOK_ACTIVITY_RESULT_CODE)
        }

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)

        bookViewModel.allBooks.observe(this, Observer { books ->
            books?.let {
                bookListAdapter.setBooks(books)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_BOOK_ACTIVITY_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            //Store data in view model - Write ViewModel class
            val id = UUID.randomUUID().toString()
            val authorName = data!!.getStringExtra(NewBookActivity.NEW_AUTHOR)
            val bookName = data!!.getStringExtra(NewBookActivity.NEW_BOOK)

            val book = Book(id, authorName!!, bookName!!)

            bookViewModel.insertt(book)

            Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_LONG).show()

        } else if (requestCode == UPDATE_BOOK_ACTIVITY_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            val id = data!!.getStringExtra(EditBookActivity.ID)
            val authorName = data!!.getStringExtra(EditBookActivity.UPDATED_AUTHOR)
            val bookName = data!!.getStringExtra(EditBookActivity.UPDATED_BOOK)

            val book = Book(id, authorName, bookName)

            bookViewModel.updatee(book)

            Toast.makeText(applicationContext, R.string.updated, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, R.string.not_saved, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDeleteClickistener(myBook: Book) {
        bookViewModel.deletee(myBook)
        Toast.makeText(applicationContext, R.string.deleted, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val NEW_BOOK_ACTIVITY_RESULT_CODE = 1
        const val UPDATE_BOOK_ACTIVITY_RESULT_CODE = 2
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        menuInflater.inflate(R.menu.menu_main, menu)

        //Get Searchview and set searchable config
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        //Setting SearchResultActivity to show result
        val componentName = ComponentName(this, SearchResultActivity::class.java)
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)

        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}

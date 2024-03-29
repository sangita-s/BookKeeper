package com.room.ps.bookkeeper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class 	BookListAdapter(private val context: Context,
                        private val onDeleteClickListener: OnDeleteClickListener) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    interface OnDeleteClickListener{
        fun onDeleteClickistener(myBook : Book)
    }
	private var bookList: List<Book> = mutableListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
		val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
		return BookViewHolder(itemView)
	}

	override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
		val book = bookList[position]
		holder.setData(book.author, book.book, position)

		holder.setListeners()
	}

	override fun getItemCount(): Int = bookList.size

	fun setBooks(books: List<Book>) {
		bookList = books
		notifyDataSetChanged()
	}

	inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		private var pos: Int = 0

		fun setData(author: String, book: String, position: Int) {
			itemView.tvAuthor.text = author
			itemView.tvBook.text = book
			this.pos = position
		}

		fun setListeners(){
			itemView.ivRowEdit.setOnClickListener{
				val intent = Intent(context, EditBookActivity::class.java)
				intent.putExtra("id", bookList[pos].id)
				intent.putExtra("author", bookList[pos].author)
				intent.putExtra("book", bookList[pos].book)
				(context as Activity).startActivityForResult(intent, MainActivity.UPDATE_BOOK_ACTIVITY_RESULT_CODE)
			}
			itemView.ivRowDelete.setOnClickListener{
				onDeleteClickListener.onDeleteClickistener(bookList[pos])
			}
		}
	}
}

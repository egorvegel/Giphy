//package com.example.giphy.adapter
//
//import android.R
//import android.content.Context
//import android.graphics.Movie
//import android.graphics.drawable.Drawable
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.ProgressBar
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.RequestOptions
//import com.bumptech.glide.request.target.Target
//
//
//class PaginationAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    private val context: Context
//    private var movieList: MutableList<Movie>?
//    private var isLoadingAdded = false
//    fun setMovieList(movieList: MutableList<Movie>?) {
//        this.movieList = movieList
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        var viewHolder: RecyclerView.ViewHolder? = null
//        val inflater = LayoutInflater.from(parent.context)
//        when (viewType) {
//            ITEM -> {
//                val viewItem: View = inflater.inflate(R.layout.item_list, parent, false)
//                viewHolder = MovieViewHolder(viewItem)
//            }
//            LOADING -> {
//                val viewLoading: View = inflater.inflate(R.layout.item_progress, parent, false)
//                viewHolder = LoadingViewHolder(viewLoading)
//            }
//        }
//        return viewHolder!!
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val movie = movieList!![position]
//        when (getItemViewType(position)) {
//            ITEM -> {
//                val movieViewHolder = holder as MovieViewHolder
//                movieViewHolder.movieTitle.setText(movie.getTitle())
//                Glide.with(context).load(movie.getImageUrl())
//                    .apply(RequestOptions.centerCropTransform())
//                    .into<Target<Drawable>>(movieViewHolder.movieImage)
//            }
//            LOADING -> {
//                val loadingViewHolder = holder as LoadingViewHolder
//                loadingViewHolder.progressBar.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return if (movieList == null) 0 else movieList!!.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (position == movieList!!.size - 1 && isLoadingAdded) LOADING else ITEM
//    }
//
//    fun addLoadingFooter() {
//        isLoadingAdded = true
//        add(Movie())
//    }
//
//    fun removeLoadingFooter() {
//        isLoadingAdded = false
//        val position = movieList!!.size - 1
//        val result = getItem(position)
//        if (result != null) {
//            movieList!!.removeAt(position)
//            notifyItemRemoved(position)
//        }
//    }
//
//    fun add(movie: Movie) {
//        movieList!!.add(movie)
//        notifyItemInserted(movieList!!.size - 1)
//    }
//
//    fun addAll(moveResults: List<Movie>) {
//        for (result in moveResults) {
//            add(result)
//        }
//    }
//
//    fun getItem(position: Int): Movie {
//        return movieList!![position]
//    }
//
//    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val movieTitle: TextView
//        private val movieImage: ImageView
//
//        init {
//            movieTitle = itemView.findViewById(R.id.movie_title)
//            movieImage = itemView.findViewById(R.id.movie_poster) as ImageView
//        }
//    }
//
//    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val progressBar: ProgressBar
//
//        init {
//            progressBar = itemView.findViewById(R.id.loadmore_progress)
//        }
//    }
//
//    companion object {
//        private const val LOADING = 0
//        private const val ITEM = 1
//    }
//
//    init {
//        this.context = context
//        movieList = LinkedList()
//    }
//}
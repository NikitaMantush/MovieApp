
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mantushnikita.movieapp.databinding.ItemMovieBinding
import com.mantushnikita.movieapp.model.Doc
import com.mantushnikita.movieapp.ui.home.adapter.MovieListViewHolder

class MovieListAdapter(
    private val onClick: (id: Int) -> Unit
) : ListAdapter<Doc, MovieListViewHolder>(object : DiffUtil.ItemCallback<Doc>() {
    override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return false
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}
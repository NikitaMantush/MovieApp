
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mantushnikita.movieapp.databinding.ItemSearchBinding
import com.mantushnikita.movieapp.model.Doc
import com.mantushnikita.movieapp.ui.search.adapter.SearchListViewHolder

class SearchListAdapter(
    private val onClick: (id: Int) -> Unit
) : ListAdapter<Doc, SearchListViewHolder>(object : DiffUtil.ItemCallback<Doc>() {
    override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return false
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        return SearchListViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}
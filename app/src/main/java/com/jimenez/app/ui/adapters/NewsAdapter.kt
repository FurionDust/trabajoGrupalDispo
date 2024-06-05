package com.jimenez.app.ui.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jimenez.app.R
import com.jimenez.app.databinding.ItemTopNewsBinding
import com.jimenez.app.ui.entities.NewsDataUI
import com.jimenez.app.ui.fragment.NewsDiffCallback

class NewsAdapter(
    private val onClickItem: (NewsDataUI) -> Unit,
    private val onDeleteItem: (Int) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val differ = AsyncListDiffer(this, NewsDiffCallback())

    var itemList: List<NewsDataUI>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTopNewsBinding.bind(view)

        fun render(
            data: NewsDataUI,
            onClickItem: (NewsDataUI) -> Unit,
            onDeleteItem: (Int) -> Unit
        ) {
            binding.txtTitleNews.text = data.name
            binding.txtUrlNews.text = data.url
            binding.txtDescNews.text = data.description
            binding.imgNews.load(data.image) {
                placeholder(R.drawable.img)
            }
            itemView.setOnClickListener {
                onClickItem(data)
            }
            binding.btnBorrar.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteItem(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(inflater.inflate(R.layout.item_top_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = itemList[position]
        holder.render(item, onClickItem, onDeleteItem)
    }

    override fun getItemCount(): Int = itemList.size
}

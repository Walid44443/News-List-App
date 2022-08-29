package com.linkdevelopment.walid44443.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linkdevelopment.walid44443.R
import com.linkdevelopment.walid44443.databinding.ItemDataLayoutBinding
import com.linkdevelopment.walid44443.models.response.Article
import com.linkdevelopment.walid44443.util.callback.OnItemAdapterClicked


class ArticlesListAdapter(
    private val onItemAdapterClicked: OnItemAdapterClicked<Article>
) : ListAdapter<Article, ArticlesListAdapter.BillViewHolder>(
    object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.title.equals(newItem.title)

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean =
            oldItem == newItem
    }
) {

    inner class BillViewHolder(private val item: ItemDataLayoutBinding) :
        RecyclerView.ViewHolder(item.root) {
        internal fun bindNewsItem(article: Article) {
            item.article = article
            item.executePendingBindings()
            Glide.with(item.root.context)
                .load(article.urlToImage)
                .fitCenter()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(item.articleImage)

            item.root.setOnClickListener {
                onItemAdapterClicked.onClickListener(article)
                item.root.isClickable = false
                item.root.isEnabled = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder =
        BillViewHolder(
            ItemDataLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.bindNewsItem(getItem(position))
    }
}

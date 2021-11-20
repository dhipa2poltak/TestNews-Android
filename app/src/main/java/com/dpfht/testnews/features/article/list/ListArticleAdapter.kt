package com.dpfht.testnews.features.article.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.testnews.databinding.RowArticleBinding
import com.dpfht.testnews.features.article.list.ListArticleAdapter.ListArticleViewHolder
import com.dpfht.testnews.model.Article
import com.squareup.picasso.Picasso

class ListArticleAdapter: PagingDataAdapter<Article, ListArticleViewHolder>(listArticleDiffCallback) {

  companion object {
    val listArticleDiffCallback = object : DiffUtil.ItemCallback<Article>() {
      override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title
      }

      override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
      }
    }
  }

  inner class ListArticleViewHolder(private val binding: RowArticleBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int, article: Article) {
      binding.tvArticleName.text = "${position + 1} - ${article.title}"
      Picasso.get().load(article.image).into(binding.ivArticleBanner)

      binding.root.setOnClickListener {
        onClickArticleItem?.invoke(article)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListArticleViewHolder {
    val binding = RowArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ListArticleViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ListArticleViewHolder, position: Int) {
    holder.bind(position, getItem(position)!!)
  }

  var onClickArticleItem: ((Article) -> (Unit))? = null
}

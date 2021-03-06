package com.dpfht.testnews.ui.source.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.testnews.databinding.RowSourceBinding
import com.dpfht.testnews.data.model.remote.Source
import com.dpfht.testnews.ui.source.adapter.SourceAdapter.SourceHolder

class SourceAdapter(private val sources: ArrayList<Source>): RecyclerView.Adapter<SourceHolder>() {

  private lateinit var binding: RowSourceBinding

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceHolder {
    binding = RowSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return SourceHolder(binding)
  }

  override fun getItemCount(): Int {
    return sources.size
  }

  override fun onBindViewHolder(holder: SourceHolder, position: Int) {
    holder.bindData(sources[position])
  }

  fun addData(datas: List<Source>) {
    for (data in datas) {
      sources.add(data)
      notifyItemInserted(sources.size - 1)
    }
  }

  fun clearData() {
    sources.clear()
    notifyDataSetChanged()
  }

  inner class SourceHolder(private val binding: RowSourceBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(source: Source) {
      binding.tvSource.text = source.name
      binding.root.setOnClickListener {
        onClickSource?.invoke(source)
      }
    }
  }

  var onClickSource: ((Source) -> (Unit))? = null
}

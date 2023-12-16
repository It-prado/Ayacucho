package com.example.charlaayacucho.feature.homeScreen.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.charlaayacucho.R
import com.example.charlaayacucho.databinding.ItemRecyclerviewBinding
import com.example.charlaayacucho.feature.homeScreen.domain.MoviesDomain
import com.example.charlaayacucho.utils.basicDiffUtil
import com.example.charlaayacucho.utils.inflate


class HomeAdapter(private val onclick: (MoviesDomain) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var list: List<MoviesDomain> by basicDiffUtil(
        emptyList(), { old, new -> old.title == new.title }
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(parent.inflate(R.layout.item_recyclerview, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onclick.invoke(item)
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(item: MoviesDomain) {
            ItemRecyclerviewBinding.bind(itemView).run {
                imageView.load(item.image) {

                }
            }
        }

    }


}
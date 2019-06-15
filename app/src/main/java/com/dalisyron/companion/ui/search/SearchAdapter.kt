package com.dalisyron.companion.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dalisyron.companion.R
import com.dalisyron.data.model.PlaceEntity

class SearchAdapter(private val onSearchPlaceItemClickListener: OnSearchPlaceItemClickListener) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var items : ArrayList<PlaceEntity> = ArrayList<PlaceEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        return holder.bind(items[position], onSearchPlaceItemClickListener)
    }

    class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val description : TextView = itemView.findViewById(R.id.place_name)

        fun bind(placeEntity: PlaceEntity, onSearchPlaceItemClickListener: OnSearchPlaceItemClickListener) {
            description.text = placeEntity.description
            itemView.setOnClickListener {
                onSearchPlaceItemClickListener.onItemClicked(placeEntity)
            }
        }
    }
}
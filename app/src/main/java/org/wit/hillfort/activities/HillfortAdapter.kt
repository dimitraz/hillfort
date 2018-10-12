package org.wit.hillfort.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_hillfort.view.hillfortName
import kotlinx.android.synthetic.main.card_hillfort.view.hillfortDescription
import org.wit.hillfort.R
import org.wit.hillfort.models.HillfortModel

class HillfortAdapter constructor(private var hillforts: List<HillfortModel>) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val hillfort = hillforts[holder.adapterPosition]
    holder.bind(hillfort)
  }

  override fun getItemCount(): Int = hillforts.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(hillfort: HillfortModel) {
      itemView.hillfortName.text = hillfort.name
      itemView.hillfortDescription.text = hillfort.description
    }
  }
}
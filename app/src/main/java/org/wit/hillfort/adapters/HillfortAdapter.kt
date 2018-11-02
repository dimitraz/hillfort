package org.wit.hillfort.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.wit.hillfort.R
import org.wit.hillfort.models.hillfort.HillfortModel


class HillfortAdapter constructor(private var hillforts: List<HillfortModel>,
                                  private val listener: HillfortListener) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val hillfort = hillforts[holder.adapterPosition]
    holder.bind(hillfort, listener)
  }

  override fun getItemCount(): Int = hillforts.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(hillfort: HillfortModel, listener: HillfortListener) {
      itemView.hillfortName.text = hillfort.name
      itemView.hillfortDescription.text = hillfort.description
      itemView.setOnClickListener { listener.onHillfortClick(hillfort) }

      // Load the image if one exists
      if (hillfort.images.isNotEmpty()) {
        itemView.hillfortIcon.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
          override fun onGlobalLayout() {
            // Only call once
            itemView.hillfortIcon.viewTreeObserver
                .removeOnGlobalLayoutListener(this)

            // Load image into image view
            Picasso.get()
                .load(hillfort.images.get(0))
                .resize(itemView.hillfortIcon.measuredWidth, itemView.hillfortIcon.measuredHeight)
                .centerCrop().into(itemView.hillfortIcon)
          }
        })
      }
    }
  }
}

interface HillfortListener {
  fun onHillfortClick(hillfort: HillfortModel)
}
package org.wit.hillfort.adapters

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_button.view.*
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.views.hillfort.HillfortView
import org.wit.hillfort.models.hillfort.HillfortModel


class HillfortAdapter constructor(private val context: Context,
                                  private var hillforts: List<HillfortModel>,
                                  private val listener: HillfortListener) : RecyclerView.Adapter<HillfortAdapter.MainHolder>(), AnkoLogger {

  private var FOOTER = 1
  private var CARD = 2

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return if (viewType == CARD) {
      MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
    } else {
      MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_button, parent, false))
    }
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    if (position === hillforts.size) {
      holder.itemView.btnCreate.setOnClickListener {
        val context = context as Activity
        context.startActivityForResult(context.intentFor<HillfortView>(), 0)
      }
    } else {
      val hillfort = hillforts[holder.adapterPosition]
      holder.bind(hillfort, listener)
    }
  }

  override fun getItemCount(): Int = hillforts.size + 1

  override fun getItemViewType(position: Int): Int {
    return if (position === hillforts.size) FOOTER else CARD
  }

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
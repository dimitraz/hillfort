package org.wit.hillfort.adapters

import android.support.v4.view.PagerAdapter
import android.view.View
import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.slider_item.view.*
import org.wit.hillfort.R


class SliderAdapter(private val context: Context, private val images: MutableList<String>): PagerAdapter() {
  override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object` as View

  override fun getCount(): Int = images.size

  override fun instantiateItem(parent: ViewGroup, position: Int): Any {
    // Get the view from slider item layout
    val view = LayoutInflater.from(parent?.context)
        .inflate(R.layout.slider_item, parent, false)

    // Load image into the image view with picasso
    Picasso.get().load(images[position]).into(view.sliderImage)

    // Add the view to the parent
    parent?.addView(view)

    // Return the view
    return view
  }

  override fun destroyItem(parent: ViewGroup, position: Int, `object`: Any) {
    // Remove the view from view group specified position
    parent.removeView(`object` as View)
  }
}
package org.wit.hillfort.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.drawer_appbar.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import org.jetbrains.anko.intentFor
import org.wit.hillfort.R
import org.wit.hillfort.helpers.CircleTransform
import org.wit.hillfort.main.MainApp

open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_base)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    app = application as MainApp

    // Toggle nav drawer
    val toggle = ActionBarDrawerToggle(
        this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()

    // Prefill user profile info
    if (app.currentUser != null) {
      val nav = nav_view.getHeaderView(0)
      nav.textView.text = "${app.currentUser?.name} ${app.currentUser?.surname}"

      if (app.currentUser?.profileImage?.isNotEmpty()!!) {
        Picasso.get().load(app.currentUser?.profileImage)
            .transform(CircleTransform())
            .resize(200, 200).centerCrop().into(nav.imageView)
      }
    }

    nav_view.setNavigationItemSelectedListener(this)
  }

  override fun onBackPressed() {
    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      drawer_layout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_base, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.action_settings -> {
        startActivityForResult(intentFor<SettingsActivity>(), 0)
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.nav_hillforts -> {
        startActivityForResult(intentFor<HillfortListActivity>(), 0)
      }
      R.id.nav_profile -> {
        startActivityForResult(intentFor<ProfileActivity>(), 0)
      }
      R.id.nav_settings -> {
        startActivityForResult(intentFor<SettingsActivity>(), 0)
      }
      R.id.nav_logout -> {
        app.currentUser = null
        startActivityForResult(intentFor<LandingActivity>(), 0)
      }
    }

    drawer_layout.closeDrawer(GravityCompat.START)
    return true
  }
}

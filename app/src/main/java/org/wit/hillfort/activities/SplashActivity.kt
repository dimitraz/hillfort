package org.wit.hillfort.activities

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.intentFor
import org.wit.hillfort.R

class SplashActivity : AppCompatActivity() {
  private lateinit var delayHandler: Handler
  private val SPLASH_DELAY: Long = 1000 //3 seconds

  internal val mRunnable: Runnable = Runnable {
    if (!isFinishing) {
      startActivity(intentFor<LoginActivity>())
      finish()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    // Initialize the Handler
    delayHandler = Handler()

    // Navigate with delay
    delayHandler.postDelayed(mRunnable, SPLASH_DELAY)
  }

  public override fun onDestroy() {
    if (delayHandler != null) {
      delayHandler.removeCallbacks(mRunnable)
    }
    super.onDestroy()
  }
}
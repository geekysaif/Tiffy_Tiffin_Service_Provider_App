package logic.mania.tiffy.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import logic.mania.tiffy.util.ActivityBase

import com.google.android.material.navigation.NavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.wessam.library.LayoutImage
import com.wessam.library.NoInternetLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import logic.mania.tiffy.R
import logic.mania.tiffy.frag.*

class MainActivity : ActivityBase(), NavigationView.OnNavigationItemSelectedListener {

    private val container by lazy { findViewById<View>(R.id.container) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu) }
    private var lastColor: Int = 0


    private var doubleBackToExitPressedOnce = false
    var navigationView: NavigationView? = null
    var main_progressbar: ProgressBar? = null
    var header: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        header = findViewById<TextView>(R.id.header)

        val fragment: Fragment = HomeFragment()
        loadFragment(fragment)

        val toggle = ActionBarDrawerToggle(            this, drawer_layout, toolbar, R.string.navigation_drawer_open,            R.string.navigation_drawer_close

        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        main_progressbar = findViewById<ProgressBar>(R.id.main_progressbar)
        navigationView = findViewById<NavigationView>(R.id.nav_view)

        nav_view.setNavigationItemSelectedListener(this)

        /*click listner of bottom navigation*/
        menu.setOnItemSelectedListener { id ->
            val option = when (id) {
                R.id.home -> {
                    if (applicationContext.isConnectedToNetwork()) {
                        R.color.colorPrimary to "Home"
                        header!!.text = getString(R.string.home)
                        val fragment: Fragment =
                            HomeFragment()
                        loadFragment(fragment)
                        drawer_layout.closeDrawer(GravityCompat.START)
                    } else {
                        (this as ActivityBase).no_internet_full_screen()
                        (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                    }
                }

                R.id.history -> {
                    if (applicationContext.isConnectedToNetwork()) {
                        R.color.colorPrimaryDark to "History"
                        header!!.text = getString(R.string.history)
                        val fragment: Fragment =
                            HistoryFragment()
                        loadFragment(fragment)
                        drawer_layout.closeDrawer(GravityCompat.START)
                    } else {
                        (this as ActivityBase).no_internet_full_screen()
                        (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                    }
                }

                R.id.favorites -> {
                    if (applicationContext.isConnectedToNetwork()) {
                        R.color.colorAccent to "Favorites"
                        header!!.text = getString(R.string.favourates)
                        val fragment: Fragment =
                            FavourateFragment()
                        loadFragment(fragment)
                        drawer_layout.closeDrawer(GravityCompat.START)
                    } else {
                        (this as ActivityBase).no_internet_full_screen()
                        (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                    }
                }

                R.id.profile -> {
                    if (applicationContext.isConnectedToNetwork()) {
                        R.color.colorPrimary to "Profile"
                        header!!.text = getString(R.string.profile)
                        val fragment: Fragment =
                            ProfileFragment()
                        loadFragment(fragment)
                        drawer_layout.closeDrawer(GravityCompat.START)
                    } else {
                        (this as ActivityBase).no_internet_full_screen()
                        (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                    }
                }
                else -> {
                    if (applicationContext.isConnectedToNetwork()) {
                        R.color.colorPrimary to "Home"
                        header!!.text = getString(R.string.home)
                        val fragment: Fragment =
                            HomeFragment()
                        loadFragment(fragment)
                        drawer_layout.closeDrawer(GravityCompat.START)
                    } else {
                        (this as ActivityBase).no_internet_full_screen()
                        (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation_drawer_menu, menu)
        return true
    }

    /*common method for load Fragment*/
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        transaction.replace(R.id.container, fragment)
        transaction.commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle bottom_navigation_menu view item clicks here.
        displayScreen(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    /* click listener of navigation drawer menu*/
    fun displayScreen(id: Int) {
        when (id) {
            R.id.home -> {
                if (applicationContext.isConnectedToNetwork()) {
                    R.color.colorPrimary to "Home"
                    header!!.text = getString(R.string.home)
                    val fragment: Fragment =
                        HomeFragment()
                    loadFragment(fragment)
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    let {
                        NoInternetLayout.Builder(it, R.layout.fragment_home).setImage(
                                LayoutImage.CLOUD
                            )
                            .animate()
                    }
                    (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                }
            }

            R.id.login_signup -> {
                if (applicationContext.isConnectedToNetwork()) {

                    val i = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(i)
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    (this as ActivityBase).no_internet_full_screen()
                    (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                }
            }

            R.id.cart -> {
                if (applicationContext.isConnectedToNetwork()) {
                    R.color.colorPrimaryDark to "Cart"
                    header!!.text = getString(R.string.cart)
                    val fragment: Fragment =
                        CartFragment()
                    loadFragment(fragment)
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    (this as ActivityBase).no_internet_full_screen()
                    (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                }
            }

            R.id.history -> {
                if (applicationContext.isConnectedToNetwork()) {
                    R.color.colorPrimaryDark to "History"
                    header!!.text = getString(R.string.history)
                    val fragment: Fragment =
                        HistoryFragment()
                    loadFragment(fragment)
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    (this as ActivityBase).no_internet_full_screen()
                    (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                }

            }
            R.id.profile -> {
                if (applicationContext.isConnectedToNetwork()) {
                    R.color.colorPrimary to "Profile"
                    header!!.text = getString(R.string.profile)
                    val fragment: Fragment =
                        ProfileFragment()
                    loadFragment(fragment)
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    (this as ActivityBase).no_internet_full_screen()
                    (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                }
            }
            R.id.favorites -> {
                if (applicationContext.isConnectedToNetwork()) {
                    R.color.colorAccent to "Favorites"
                    header!!.text = getString(R.string.favourates)
                    val fragment: Fragment =
                        FavourateFragment()
                    loadFragment(fragment)
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    (this as ActivityBase).no_internet_full_screen()
                    (this as ActivityBase).toasty_error(getString(R.string.internet_connection))
                }
            }
            R.id.share -> {
                if (applicationContext.isConnectedToNetwork()) {
                    share_app()
                } else {
                    toasty_error(getString(R.string.internet_connection))
                }
            }
            R.id.rate -> {
                if (applicationContext.isConnectedToNetwork()) {
                    rate_app()
                } else {
                    toasty_error(getString(R.string.internet_connection))
                }
            }
            R.id.about -> {
                if (applicationContext.isConnectedToNetwork()) {
                    (this as ActivityBase).toasty_error(getString(R.string.app_name))
                } else {
                    toasty_error(getString(R.string.internet_connection))
                }
            }
            R.id.notification -> {
                if (applicationContext.isConnectedToNetwork()) {
                    (this as ActivityBase).toasty_error(getString(R.string.app_name))
                } else {
                    toasty_error(getString(R.string.internet_connection))
                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true

        toasty_success("Please click BACK again to exit")
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}


package com.example.storezaapdemo.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.storezaapdemo.R
import com.example.storezaapdemo.SharedPrefManager
import com.example.storezaapdemo.databinding.ActivityMainBinding
import com.example.storezaapdemo.ui.home.HomeFragment
import com.example.storezaapdemo.ui.news.NewsFragment
import com.example.storezaapdemo.ui.profile.ProfileFragment
import com.example.storezaapdemo.ui.services.ServicesFragment
import com.example.storezaapdemo.ui.store.StoreFragment
import com.example.storezaapdemo.ui.user.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var etname: TextView
    private lateinit var etemail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val bottomNavigationView: BottomNavigationView = binding.appBarMain.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        sharedPrefManager = SharedPrefManager(applicationContext)

        navView.bringToFront()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START)
        /*if (ECONSTANT.logedUser != null) {
            navView.inflateMenu(R.menu.activity_home_drawer_login)
        } else {
            navView.inflateMenu(R.menu.activity_home_drawer_without_login)
        }
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()*/

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_store,
                R.id.nav_service,
                R.id.nav_news,
                R.id.nav_user,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        NavigationUI.setupWithNavController(binding.appBarMain.bottomNavigationView,navController)

        etname = findViewById(R.id.etname)
        etemail = findViewById(R.id.etemail)




        findViewById<LinearLayout>(R.id.nav_home_2).setOnClickListener{
            getSupportActionBar()?.setTitle("Home")
            bottomNavigationView.getMenu().getItem(0).setChecked(true)
            val fragmentB = HomeFragment()
            loadFragment(fragmentB)
            drawerLayout.closeDrawers()
        }
        findViewById<LinearLayout>(R.id.nav_store).setOnClickListener{
            getSupportActionBar()?.setTitle("Store")
            val fragmentB = StoreFragment()
            loadFragment(fragmentB)
            drawerLayout.closeDrawers()
        }
        findViewById<LinearLayout>(R.id.nav_service).setOnClickListener{
            getSupportActionBar()?.setTitle("Service")
            val fragmentB = ServicesFragment()
            loadFragment(fragmentB)
            drawerLayout.closeDrawers()
        }
        findViewById<LinearLayout>(R.id.nav_news).setOnClickListener{
            getSupportActionBar()?.setTitle("News")
            bottomNavigationView.getMenu().getItem(1).setChecked(true)
            val fragmentB = NewsFragment()
            loadFragment(fragmentB)
            drawerLayout.closeDrawers()
        }
        findViewById<LinearLayout>(R.id.nav_user).setOnClickListener{
            getSupportActionBar()?.setTitle("User")
            bottomNavigationView.getMenu().getItem(2).setChecked(true)
            val fragmentB = ProfileFragment()
            loadFragment(fragmentB)
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.nav_logout).setOnClickListener{
            if (sharedPrefManager.isLoggedIn()) {


                android.app.AlertDialog.Builder(this@MainActivity)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to Logout?") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            clearUserSession()
                        }) // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()


            }else{
                getSupportActionBar()?.setTitle("User")
                bottomNavigationView.getMenu().getItem(2).setChecked(true)
                val fragmentB = UserFragment()
                loadFragment(fragmentB)
                drawerLayout.closeDrawers()
            }
        }

        var textView7=findViewById<TextView>(R.id.textView7);
        if (sharedPrefManager.isLoggedIn()) {
            val userName = "Hey! " + sharedPrefManager.getUser().username
            etname.text = userName
            etemail.text = sharedPrefManager.getUser().email

            textView7.setText("Logout")
        }else{
            textView7.setText("Login")
        }

    }
    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_content_main, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
    private fun clearUserSession() {

        sharedPrefManager.logout()
        // Redirect user to Home screen
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
package com.example.kingofnara.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.kingofnara.R

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // retrieve your nav host fragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.activity_main_navhost) as NavHostFragment
        // extract nav controller from nav host because
        // findNavController() function not available yet in onCreate()
        val navController = navHostFragment.navController
        // get a configuration object based upon your nav graph
        appBarConfiguration = AppBarConfiguration(navController.graph)
        // link the actionbar and the nav graph using this configuration
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Use the Fragment manager to create a Fragment transaction
        /*if (supportFragmentManager.findFragmentById(R.id.main_container) == null) {
            Log.d("XXX", "MainActivity: onCreate: no fragment yet")
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, PlateFragment())
                .commit()
        }*/

        /*WindowCompat.setDecorFitsSystemWindows(window, false)
               super.onCreate(savedInstanceState)

               binding = ActivityMainBinding.inflate(layoutInflater)
               setContentView(binding.root)

               setSupportActionBar(binding.toolbar)

               val navController = findNavController(R.id.nav_host_fragment_content_main)
               appBarConfiguration = AppBarConfiguration(navController.graph)
               setupActionBarWithNavController(navController, appBarConfiguration)

               binding.fab.setOnClickListener { view ->
                   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       .setAnchorView(R.id.fab)
                       .setAction("Action", null).show()
               }
         */
   }

    /* override fun onSupportNavigateUp(): Boolean {
          super.onSupportNavigateUp()
          val navController = findNavController(R.id.nav_host_fragment_content_main)
          return navController.navigateUp(appBarConfiguration)
                  || super.onSupportNavigateUp()
      }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.activity_main_navhost)
        return navController.navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }
}
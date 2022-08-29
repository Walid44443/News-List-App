package com.linkdevelopment.walid44443.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.linkdevelopment.walid44443.R
import com.linkdevelopment.walid44443.base.BaseActivity
import com.linkdevelopment.walid44443.databinding.ActivityMainBinding
import com.weightwatchers.ww_exercise_01.base.createViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModelClass: Class<MainActivityViewModel>
        get() = MainActivityViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory =
        createViewModelFactory { MainActivityViewModel() }


    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setSupportActionBar(findViewById(R.id.toolbar))

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_chat, R.id.nav_gallery, R.id.nav_whishlist, R.id.nav_magazine
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        viewModel.viewState.onEach { observeState(it) }.launchIn(lifecycleScope)

        binding.navView.setNavigationItemSelectedListener { _item ->
            lifecycleScope.launch {
                viewModel.intents.send(MainActivityIntents.ClickOnDrawerItem(_item))
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true;
        }
    }

    private fun observeState(viewState: MainActivityState) {
        when (viewState) {
            is MainActivityState.Idle -> {

            }
            is MainActivityState.DrawerItemClicked -> {
                Toast.makeText(this@MainActivity, viewState.menu?.title ?: "", Toast.LENGTH_LONG)
                    .show()
                lifecycleScope.launch {
                    delay(500)
                    viewModel.intents.send(MainActivityIntents.GoIdle)
                }
            }
        }
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
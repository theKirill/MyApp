package com.yanyushkin.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val ID_HOME = 1
    private val ID_MESSAGES = 2
    private val ID_NOTIFICATIONS = 3
    private val ID_SETTINGS = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNavigation()
    }

    private fun initBottomNavigation(){
        bottomNavigation.add(MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_MESSAGES, R.drawable.ic_messages))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_NOTIFICATIONS, R.drawable.ic_notifications))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_SETTINGS, R.drawable.ic_settings))
        bottomNavigation.setCount(ID_NOTIFICATIONS, "10")
        bottomNavigation.show(ID_HOME)

        initClickListenerForBottomNavigation()
    }

    private fun initClickListenerForBottomNavigation(){
        bottomNavigation.setOnClickMenuListener {
            when (it.id){
                ID_NOTIFICATIONS->bottomNavigation.setCount(ID_NOTIFICATIONS, "")
            }
        }
    }
}

package com.yanyushkin.myapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Главная активити приложения
 */
class MainActivity : AppCompatActivity() {

    private val SELECTED_TAB_KEY = "selected_tab"
    private var selectedTab = 4

    /**
     * ID вкладок нижней навигации
     */
    private val ID_HOME = 1
    private val ID_MESSAGES = 2
    private val ID_NOTIFICATIONS = 3
    private val ID_SETTINGS = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doAfterRotate(savedInstanceState)
        initBottomNavigation()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.let {
            outState.apply {
                clear()
                putInt(SELECTED_TAB_KEY, selectedTab)
            }
        }
    }

    private fun doAfterRotate(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            savedInstanceState.apply {
                if (containsKey(SELECTED_TAB_KEY)) {
                    selectedTab = getInt(SELECTED_TAB_KEY)

                    bottomNavigation.show(selectedTab)

                    clear()
                }
            }
        }
    }

    private fun initBottomNavigation() {
        bottomNavigation.add(MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_MESSAGES, R.drawable.ic_messages))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_NOTIFICATIONS, R.drawable.ic_notifications))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_SETTINGS, R.drawable.ic_settings))
        bottomNavigation.setCount(ID_NOTIFICATIONS, "10")
        bottomNavigation.show(selectedTab)

        initClickListenerForBottomNavigation()
    }

    private fun initClickListenerForBottomNavigation() {
        bottomNavigation.setOnClickMenuListener {
            showTab(it.id)

            selectedTab = it.id
        }
    }

    private fun showTab(id: Int){
        when (id) {
            ID_NOTIFICATIONS -> bottomNavigation.setCount(ID_NOTIFICATIONS, "")
           /* ID_SETTINGS -> supportFragmentManager.beginTransaction()
                .replace(R.id.content_layout, LoginFragment.instance).commit()*/
        }
    }
}

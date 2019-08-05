package com.yanyushkin.myapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.yanyushkin.myapp.App
import com.yanyushkin.myapp.R
import com.yanyushkin.myapp.arch.MainActivityContract
import com.yanyushkin.myapp.presenters.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Главная активити приложения
 */
class MainActivity : AppCompatActivity(), MainActivityContract.View {

    companion object {
        private val SELECTED_TAB_KEY = "selected_tab"

        /**
         * ID вкладок нижней навигации
         */
        private const val ID_HOME = 1
        private const val ID_MESSAGES = 2
        private const val ID_NOTIFICATIONS = 3
        private const val ID_SETTINGS = 4
    }

    @Inject
    lateinit var mainActivityPresenter: MainActivityPresenter
    private var selectedTab = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.injectsMainActivity(this)
        mainActivityPresenter.attach(this)

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

    override fun showTab(id: Int) {
        selectedTab = id

        when (id) {
            ID_NOTIFICATIONS -> bottomNavigation.setCount(ID_NOTIFICATIONS, "")
            /* ID_SETTINGS -> supportFragmentManager.beginTransaction()
                 .replace(R.id.content_layout, LoginFragment.instance).commit()*/
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
        bottomNavigation.add(
            MeowBottomNavigation.Model(
                ID_HOME,
                R.drawable.ic_home
            )
        )
        bottomNavigation.add(
            MeowBottomNavigation.Model(
                ID_MESSAGES,
                R.drawable.ic_messages
            )
        )
        bottomNavigation.add(
            MeowBottomNavigation.Model(
                ID_NOTIFICATIONS,
                R.drawable.ic_notifications
            )
        )
        bottomNavigation.add(
            MeowBottomNavigation.Model(
                ID_SETTINGS,
                R.drawable.ic_settings
            )
        )
        bottomNavigation.setCount(ID_NOTIFICATIONS, "10")
        bottomNavigation.show(selectedTab)

        initClickListenerForBottomNavigation()
    }

    private fun initClickListenerForBottomNavigation() {
        bottomNavigation.setOnClickMenuListener {
            mainActivityPresenter.showTab(it.id)
        }
    }
}

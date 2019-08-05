package com.yanyushkin.myapp.ui.activities

import android.os.Bundle
import com.yanyushkin.myapp.R
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

/**
 * Активити регистрации
 */
class UserInfoActivity : SwipeBackActivity() {

    private lateinit var mSwipeBackLayout: SwipeBackLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_userinfo)

        setSwipeBack()
    }

    private fun setSwipeBack() {
        mSwipeBackLayout = swipeBackLayout
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
    }
}

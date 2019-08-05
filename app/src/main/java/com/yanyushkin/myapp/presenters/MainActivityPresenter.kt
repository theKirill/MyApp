package com.yanyushkin.myapp.presenters

import com.yanyushkin.myapp.arch.MainActivityContract

class MainActivityPresenter : MainActivityContract.Presenter {

    private lateinit var mainActivityView: MainActivityContract.View

    /**
     * binding to view
     */
    override fun attach(view: MainActivityContract.View) {
        this.mainActivityView = view
    }

    override fun showTab(id: Int) {
        mainActivityView.showTab(id)
    }
}
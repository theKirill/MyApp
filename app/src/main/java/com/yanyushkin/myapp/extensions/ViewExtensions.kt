package com.yanyushkin.myapp.extensions

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.yanyushkin.myapp.R

fun View.animate(context: Context, startOffset: Long) {
    val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.alpha_in)
    val fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.alpha_out)

    fadeInAnimation.startOffset = startOffset
    fadeOutAnimation.startOffset = startOffset

    this.startAnimation(fadeOutAnimation)
    this.startAnimation(fadeInAnimation)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}
package com.yanyushkin.myapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast

fun getBitmap(context: Context, id: Int): Bitmap = BitmapFactory.decodeResource(context.resources, id)

fun toast(context: Context, message: String) : Unit = Toast.makeText(context, message, Toast.LENGTH_LONG).show()
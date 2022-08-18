package com.amirez.pexels.utils

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.amirez.pexels.R
import com.amirez.pexels.model.dataclass.Collection
import com.amirez.pexels.ui.search.SearchFragment

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun SearchFragment.isSearchKeyValid(): Boolean {
    val et = view?.findViewById<EditText>(R.id.et_search)
    return when {
        et?.text.toString().trim{ it <= ' ' }.isEmpty() -> false
        et?.text.toString().isBlank() -> false
        else -> true
    }
}

fun List<Collection.Media>.filterPhotos(): List<Collection.Media> {
    return filter {
        it.type == "Photo"
    }
}
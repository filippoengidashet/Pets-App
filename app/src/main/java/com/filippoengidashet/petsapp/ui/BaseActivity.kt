package com.filippoengidashet.petsapp.ui

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Filippo 15/07/2021
 */
abstract class BaseActivity : AppCompatActivity() {

    protected fun showToast(message: String?) =
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

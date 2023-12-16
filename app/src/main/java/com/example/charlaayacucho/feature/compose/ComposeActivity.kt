package com.example.charlaayacucho.feature.compose

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.charlaayacucho.R
import com.example.charlaayacucho.databinding.ActivityComposeBinding
import com.example.charlaayacucho.databinding.ActivityMainBinding
import com.example.charlaayacucho.feature.favorites.ui.FavoritesFragment


class ComposeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityComposeBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val argumentsBefore = intent.extras?.getString("id")
        val fragment = FavoritesFragment()
        fragment.arguments = bundleOf("id" to argumentsBefore)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentCompose, fragment)
            .commit()

    }

}

package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
//        setContentView(R.layout.activity_main)

    }

}
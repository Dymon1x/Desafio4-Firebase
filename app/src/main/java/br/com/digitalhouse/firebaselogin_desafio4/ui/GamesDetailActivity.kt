package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.digitalhouse.firebaselogin_desafio4.R
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityGamesDetailBinding
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityMainBinding
import br.com.digitalhouse.firebaselogin_desafio4.firestore.cr
import br.com.digitalhouse.firebaselogin_desafio4.model.GameViewModel

class GamesDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityGamesDetailBinding

    private val viewModel by viewModels<GameViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GameViewModel(cr) as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_games_detail)

        bind = ActivityGamesDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.imgBack.setOnClickListener {
            backHome()
        }

        bind.fbEditGame.setOnClickListener {
            goRegister()
        }

        viewModel.getGamesFirestore()

    }

    private fun backHome(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun goRegister(){
        startActivity(Intent(this, GameCadastroActivity::class.java))
    }

}
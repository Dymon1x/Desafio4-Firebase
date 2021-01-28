package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import br.com.digitalhouse.firebaselogin_desafio4.adapter.GamesInfoAdapter
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityMainBinding
import br.com.digitalhouse.firebaselogin_desafio4.firestore.cr
import br.com.digitalhouse.firebaselogin_desafio4.model.GameViewModel
import br.com.digitalhouse.firebaselogin_desafio4.model.GamesInfo


class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    private lateinit var gameAdapter: GamesInfoAdapter

    private val viewModel by viewModels<GameViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GameViewModel(cr) as T
            }
        }
    }

//    private lateinit var gameCadastro: GameCadastroActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
//        setContentView(R.layout.activity_main)

        bind.rcGamesHome.layoutManager = GridLayoutManager(this, 2)
        bind.rcGamesHome.setHasFixedSize(true)

//        viewModel.getGames()

//        gameCadastro.getRec()
        viewModel.sendGamesFirestore(
            GamesInfo(
                "God of War III",
                2010,
                "Um Deus matando outros Deuses",
                "https://upload.wikimedia.org/wikipedia/pt/6/6c/God_of_War_3_capa.png"
            )
        )

        viewModel.sendGamesFirestore(
            GamesInfo(
                "Undertale",
                2015,
                "Jogo indie baseado em karma",
                "https://pm1.narvii.com/6354/f48758de5149885e862a07703d0e143a730f537c_00.jpg"
            )
        )
        viewModel.sendGamesFirestore(
            GamesInfo(
                "Ori and the Blind Forest",
                2015,
                "Não jogue este jogo, se você for uma pessoa emotiva",
                "https://literaturaempauta.com.br/wp-content/uploads/2016/07/Ori-and-the-Blind-Forest_Capa.jpg"
            )
        )
        viewModel.sendGamesFirestore(
            GamesInfo(
                "Mineirinho Ultra Adventures",
                2017,
                "Game of the year de são nunca",
                "https://www.fabricadejogos.net/wp/wp-content/uploads/2019/06/mineirinho.jpeg"
            )
        )

        viewModel.getGamesFirestore()

        viewModel.listGamesInfo.observe(this) {
            initAdapter(it)

        }

        bind.flButtonRegisterGame.setOnClickListener {
            callRegisterGame()

        }

    }

    fun initAdapter(list: ArrayList<GamesInfo>) {
        gameAdapter = GamesInfoAdapter(list, View.OnClickListener {
            startActivity(Intent(this, GamesDetailActivity::class.java))
        })
        bind.rcGamesHome.adapter = gameAdapter
    }

    fun callRegisterGame() {
        startActivity(Intent(this, GameCadastroActivity::class.java))
    }

}
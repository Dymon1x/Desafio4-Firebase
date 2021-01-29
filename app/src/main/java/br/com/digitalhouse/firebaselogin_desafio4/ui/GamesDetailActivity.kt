package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityGamesDetailBinding
import br.com.digitalhouse.firebaselogin_desafio4.model.GamesInfo
import com.squareup.picasso.Picasso

class GamesDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityGamesDetailBinding

    private var gameInfo: GamesInfo = GamesInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_games_detail)

        bind = ActivityGamesDetailBinding.inflate(layoutInflater)

//        checkInfo()

        val games = intent.getSerializableExtra("game") as? GamesInfo

        if(games != null){
            gameInfo = games
            updateGamesInfo(gameInfo)
        }else{
            Toast.makeText(this, "Não foi possível carregar suas as informações! :(", Toast.LENGTH_SHORT).show()
            finish()
        }
        bind.imgBack.setOnClickListener {
            finish()
        }

        bind.fbEditGame.setOnClickListener {
//            editGame()
            val intent = Intent(this, EditaGameCadastroActivity::class.java)
            intent.putExtra("game", gameInfo)
            startActivity(intent)
            finish()
        }

        setContentView(bind.root)
    }


    private fun updateGamesInfo(gamesInfo: GamesInfo){

        bind.nameFundo.text = gamesInfo.titulo
        bind.tvNameGame.text = gamesInfo.titulo
        bind.tvDataGame.text = gamesInfo.data_lancamento
        bind.tvDescription.text = gamesInfo.descricao

        Picasso.get().load(gamesInfo.imgURL).into(bind.imgFundo)


    }

}
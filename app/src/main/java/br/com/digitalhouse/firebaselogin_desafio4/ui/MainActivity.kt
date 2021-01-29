package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.content.Intent
import android.os.Bundle
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.digitalhouse.firebaselogin_desafio4.adapter.GamesInfoAdapter
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityMainBinding
import br.com.digitalhouse.firebaselogin_desafio4.model.GamesInfo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MainActivity : AppCompatActivity(), GamesInfoAdapter.OnGameClick {

    lateinit var bind: ActivityMainBinding

    lateinit var gamesAdapter: GamesInfoAdapter
    lateinit var layoutManager: LinearLayoutManager
    private var listGamesInfo = MutableLiveData<ArrayList<GamesInfo>>()

    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_main)


        setContentView(bind.root)

        layoutManager = GridLayoutManager(this, 2)
        bind.rcGamesHome.layoutManager = layoutManager
        bind.rcGamesHome.setHasFixedSize(true)

        listGamesInfo.observe(this, {

            gamesAdapter = GamesInfoAdapter(it, this)
            bind.rcGamesHome.adapter = gamesAdapter
        })

        bind.flButtonRegisterGame.setOnClickListener {
            callCadastroGame()
        }

    }


    override fun onClick(position: Int) {
        val intent = Intent(this, GamesDetailActivity::class.java)
        val game = listGamesInfo.value?.get(position)

        intent.putExtra("game", game)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        getGamesInfo()
    }


    private fun getGamesInfo() {
        val db = Firebase.firestore.collection("Games")
        val listGames = ArrayList<GamesInfo>()
        scope.launch {
            val list = db.get().await()
            list.forEach { games ->
                listGames.add(games.toObject())
            }
            listGamesInfo.postValue(listGames)
        }
    }

    private fun callCadastroGame() {
        startActivity(Intent(this, GameCadastroActivity::class.java))
    }
}
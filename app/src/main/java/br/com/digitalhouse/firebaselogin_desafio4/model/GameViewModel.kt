package br.com.digitalhouse.firebaselogin_desafio4.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.firebaselogin_desafio4.firestore.cr
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.launch

class GameViewModel(cr: CollectionReference) : ViewModel() {

    var listGamesInfo = MutableLiveData<ArrayList<GamesInfo>>()
    var listSend = MutableLiveData<Boolean>()


    fun sendGamesFirestore(gamesInfo: GamesInfo) {
        cr.document().set(gamesInfo).addOnSuccessListener {
            listSend.value = true
        }.addOnCanceledListener {
            listSend.value = false
        }
    }

    fun getGamesFirestore() {
        cr.get().addOnSuccessListener { games ->

            val listGame = arrayListOf<GamesInfo>()

            for (g in games) {
                val gamesInfo: GamesInfo = g.toObject(GamesInfo::class.java)

                listGame.add(gamesInfo)
            }
            listGamesInfo.value = listGame
        }

    }

    fun delGameFirestore(nome: String) {
        cr.document(nome).delete().addOnSuccessListener {
            listSend.value = true
        }.addOnCanceledListener {
            listSend.value = false
        }
    }

    fun updateGameFirestore(game: GamesInfo) {
        cr.document(game.toString()).set(game).addOnSuccessListener {
            listSend.value = true
        }.addOnCanceledListener {
            listSend.value = false
        }

    }
}



    /*fun getGames() {
        viewModelScope.launch {
            listGamesInfo.value = arrayListOf(
                GamesInfo(
                "God of War III",
                2010,
                "Um Deus matando outros Deuses",
                "https://upload.wikimedia.org/wikipedia/pt/6/6c/God_of_War_3_capa.png"
            ),
            GamesInfo(
                "Undertale",
                2015,
                "Jogo indie baseado em karma",
                "https://pm1.narvii.com/6354/f48758de5149885e862a07703d0e143a730f537c_00.jpg"
            ),
            GamesInfo(
                "Ori and the Blind Forest",
                2015,
                "Não jogue este jogo, se você for uma pessoa emotiva",
                "https://literaturaempauta.com.br/wp-content/uploads/2016/07/Ori-and-the-Blind-Forest_Capa.jpg"
            ),
            GamesInfo(
                "Mineirinho Ultra Adventures",
                2017,
                "Game of the year de são nunca",
                "https://www.fabricadejogos.net/wp/wp-content/uploads/2019/06/mineirinho.jpeg"
            )
            )
        }
    }*/

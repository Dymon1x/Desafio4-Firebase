package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityGameCadastroBinding
import br.com.digitalhouse.firebaselogin_desafio4.model.GamesInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class GameCadastroActivity : AppCompatActivity() {

    private lateinit var bind: ActivityGameCadastroBinding

    lateinit var storage: StorageReference
    private val CODE_IMG = 100
    private var gameinfo: GamesInfo = GamesInfo()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameCadastroBinding.inflate(layoutInflater)

/*        setContentView(R.layout.activity_game_cadastro)*/


        bind.imgGame.setOnClickListener {
            getPicture()
        }

        bind.includeCadastroGames.btnSaveGame.setOnClickListener {
            gameinfo.titulo = bind.includeCadastroGames.nameGame.text.toString()
            gameinfo.data_lancamento = bind.includeCadastroGames.edDate.text.toString()
            gameinfo.descricao = bind.includeCadastroGames.edDescription.text.toString()

            if (gameinfo.imgURL == "") {
                showMsg("Adicione imagem")
            } else {

                saveInfo()
                finish()
            }
        }

        setContentView(bind.root)


    }

    private fun getPicture() {
        storage = FirebaseStorage.getInstance().getReference(getUniqueId())
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Captura Imagem"), CODE_IMG)
    }

    private fun saveInfo() {
        val db = FirebaseFirestore.getInstance().collection("Games")
        val id = getUniqueId()
        gameinfo.id = id
        db.document(id).set(gameinfo)
    }

    private fun getUniqueId() = FirebaseFirestore.getInstance().collection("Chave").document().id

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG) {

            val upFile = storage.putFile(data!!.data!!)
            upFile.continueWithTask { task ->
                if (task.isSuccessful) {
                    showMsg("Imagem Carregada !!!")
                }
                storage!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val url = downloadUri!!.toString()
                        .substring(0, downloadUri.toString().indexOf("&token"))
                    Log.i("URL da Imagem", url)
                    gameinfo.imgURL = url
                    Picasso.get().load(url).into(bind.imgGame)
                }
            }
        }
    }


    private fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

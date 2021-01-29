package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityEditaGameCadastroBinding
import br.com.digitalhouse.firebaselogin_desafio4.model.GamesInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class EditaGameCadastroActivity : AppCompatActivity() {

    private lateinit var bind: ActivityEditaGameCadastroBinding

    lateinit var storage: StorageReference
    private val CODE_IMG = 100
    private var gameinfo: GamesInfo = GamesInfo()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edita_game_cadastro)

        bind = ActivityEditaGameCadastroBinding.inflate(layoutInflater)

        checkInfo()


        bind.includeEditaCadastroGames.btnSaveGame.setOnClickListener {
            if (gameinfo != null) {
                saveInfo(newGamesInfo(gameinfo))
                showMsg("Informações atualizadas :)")
                finish()
            }else{
                showMsg("Não foi possivel atualizar :(")
                finish()
            }
        }

        bind.imgGameEdita.setOnClickListener {
            getPicture()
        }


        setContentView(bind.root)
    }

    private fun checkInfo(){
        val g = intent.getSerializableExtra("game") as? GamesInfo

        if (g != null){
            gameinfo = g

            setInfo(gameinfo)
        }else{
            showMsg("Não deu para carregar suas informações !")
            finish()
        }
    }

    private fun setInfo(gamesInfo: GamesInfo){
        bind.includeEditaCadastroGames.nameGame.setText(gamesInfo.titulo)
        bind.includeEditaCadastroGames.edDate.setText(gamesInfo.data_lancamento)
        bind.includeEditaCadastroGames.edDescription.setText(gamesInfo.descricao)

        Picasso.get().load(gamesInfo.imgURL).into(bind.imgGameEdita)
    }

    private fun saveInfo(gamesInfo: GamesInfo){
        val db = FirebaseFirestore.getInstance().collection("Games")
        db.document(gamesInfo.id).set(gamesInfo)
    }

    private fun newGamesInfo(gamesInfo: GamesInfo): GamesInfo{
        val nome = bind.includeEditaCadastroGames.nameGame.text.toString()
        val data = bind.includeEditaCadastroGames.edDate.text.toString()
        val desc = bind.includeEditaCadastroGames.edDescription.text.toString()

        return GamesInfo(nome,data, desc,gamesInfo.imgURL, gamesInfo.id)
    }

    private fun getPicture() {
        storage = FirebaseStorage.getInstance().getReference(getUniqueId())
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Captura Imagem"), CODE_IMG)
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
                    Picasso.get().load(url).into(bind.imgGameEdita)
                }
            }
        }
    }

    private fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
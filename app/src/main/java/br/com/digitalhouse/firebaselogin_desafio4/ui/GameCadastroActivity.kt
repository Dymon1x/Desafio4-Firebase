package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.digitalhouse.firebaselogin_desafio4.adapter.GamesInfoAdapter
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityGameCadastroBinding
import br.com.digitalhouse.firebaselogin_desafio4.firestore.cr
import br.com.digitalhouse.firebaselogin_desafio4.model.GameViewModel
import br.com.digitalhouse.firebaselogin_desafio4.model.GamesInfo
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog

class GameCadastroActivity : AppCompatActivity() {

    private lateinit var bind: ActivityGameCadastroBinding

    lateinit var alertDialog: AlertDialog
    lateinit var storage: StorageReference
    private val CODE_IMG = 1000

    private lateinit var gameAdapter: GamesInfoAdapter


    private val viewModel by viewModels<GameViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GameViewModel(cr) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameCadastroBinding.inflate(layoutInflater)
        setContentView(bind.root)

/*        setContentView(R.layout.activity_game_cadastro)*/

        configuration()
        bind.imgGame.setOnClickListener {
            getRec()
        }


        bind.includeCadastroGames.btnSaveGame.setOnClickListener {
            viewModel.updateGameFirestore(GamesInfo().apply {
                img
                titulo
                data_lancamento
                descricao
            })
            callMain()
        }


    }

    fun callMain(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun configuration(){
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        storage = FirebaseStorage.getInstance().getReference("img")

    }

    fun getRec(){
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Captura Imagem"), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG) {
            alertDialog.show()
            val uploadFile = storage.putFile(data!!.data!!)
            val task = uploadFile.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Imagem Carrregada com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                }
                storage!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val url = downloadUri!!.toString()
                        .substring(0, downloadUri.toString().indexOf("&token"))
                    Log.i("URL da Imagem", url)
                    alertDialog.dismiss()
                    Picasso.get().load(url).into(bind.imgGame)
                }
            }
        }
    }
    
}
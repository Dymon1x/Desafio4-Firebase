package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class CadastroActivity : AppCompatActivity() {

    private lateinit var bind: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_cadastro)

        bind = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(bind.root)


        bind.cadastroInclude.btnCreateAccount.setOnClickListener {
            getDataFields()
        }
    }

    private fun getDataFields() { // password no firebase por default é 6 digitos
        val nome = bind.cadastroInclude.edNome.text.toString()
        val email = bind.cadastroInclude.edEmailRegister.text.toString()
        val password = bind.cadastroInclude.edPasswordRegister.text.toString()

        val nomeEpt = nome.isNotEmpty()
        val emailEpt = email.isNotEmpty()
        val passwordEpt = password.isNotEmpty()

        if (emailEpt && passwordEpt) {
            sendDataFirebase(email, password)
            callMain(email, password)
        }
        else
            showMsg("Preencha todas as informações ")
    }

    //Envia dados para o firebase

    fun sendDataFirebase(email: String, password: String) {
//        showMsg("Enviando dados para o firebase")
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result?.user!!
                    showMsg("usuário cadastrado com sucesso")
                    val idUser = firebaseUser.uid
                    val emailUser = firebaseUser.email.toString()

/*                    callMain(idUser, emailUser)*/

                } else {
                    showMsg(task.exception?.message.toString())
                }
            }
    }

    //Chama Activity main
    fun callMain(idUser: String, emailUser: String) {
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("id", idUser)
        intent.putExtra("email", emailUser)

        startActivity(intent)
    }

    //Exibe informação
    fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
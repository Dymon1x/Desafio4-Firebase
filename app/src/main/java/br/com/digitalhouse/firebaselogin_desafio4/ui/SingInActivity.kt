package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivitySingInBinding
import br.com.digitalhouse.firebaselogin_desafio4.model.Usuario
import com.google.firebase.auth.FirebaseAuth

class SingInActivity : AppCompatActivity() {

    lateinit var bind: ActivitySingInBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sing_in)

        bind = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.loginInclude.btnLogin.setOnClickListener {
            login()
            callMain()
        }

        bind.loginInclude.btnCadastreSe.setOnClickListener {
            callRegister()

        }

    }


    private fun getDataFields(): Usuario? { // password no firebase por default é 6 digitos

        val email = bind.loginInclude.edEmail.text.toString()
        val password = bind.loginInclude.edPassword.text.toString()

        return if (email.isNotBlank() and password.isNotBlank()) {
            Usuario("", email, password)
        } else {
            null
        }
    }

    private fun sendDataFirebase(user: Usuario) {
//        showMsg("Enviando dados para o firebase")
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(
                user.email,
                user.senha
            ) // só ira muda o . depois da instance
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val firebaseUser = it.result?.user!!
                    Usuario(firebaseUser.email.toString(), "", firebaseUser.uid)

                    showMsg("Login Realizado")

                } else if (it.isCanceled) {
                    showMsg("Falha no Login, tente mais uma vez !")
                }
            }
    }


    private fun login() {
        val user = getDataFields()

        if (user != null) {
            sendDataFirebase(user)
        } else {
            showMsg("Preencha os campos corretamente")
        }
    }

    //Exibe informação
    private fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    //Chama Activity main
    private fun callMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Chama a activity de cadastro
    private fun callRegister() {
        startActivity(Intent(this, CadastroActivity::class.java))
    }

}
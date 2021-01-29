package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivityCadastroBinding
import br.com.digitalhouse.firebaselogin_desafio4.model.Usuario
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
            registerUser()
            callMain()
        }
    }

    //Envia dados para o firebase

    private fun sendDataFirebase(user: Usuario){
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(user.email,user.senha)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val firebaseUser = it.result?.user!!
                    
                    Usuario(firebaseUser.email.toString(), "", firebaseUser.uid)
                } else {
                    showMsg(it.exception?.message.toString())
                }
            }
    }

    private  fun  getUserFields(): Usuario?{
        val name = bind.cadastroInclude.edNome.text.toString()
        val email = bind.cadastroInclude.edEmailRegister.text.toString()
        val pass = bind.cadastroInclude.edPasswordRegister.text.toString()
        
        return if(email.isNotBlank() && pass.isNotBlank()){
            Usuario(name, email, pass)
        }else{
            null
        }
    }
    private fun registerUser(){
        val user = getUserFields()
        if(user != null){
            sendDataFirebase(user)
        }else{
            showMsg("Preencha os campos corretamente")
        }
    }
    //Chama Activity main
    fun callMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Exibe informação
    fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
package br.com.digitalhouse.firebaselogin_desafio4.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.firebaselogin_desafio4.R
import br.com.digitalhouse.firebaselogin_desafio4.databinding.ActivitySingInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class SingInActivity : AppCompatActivity() {

    lateinit var bind: ActivitySingInBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    lateinit var googleSignInOptions: GoogleSignInOptions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sing_in)

        bind = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(bind.root)


        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            callMain(user.uid, user.email.toString())
        }

        bind.loginInclude.btnLogin.setOnClickListener {
            getDataFields()
        }

        bind.loginInclude.btnCadastreSe.setOnClickListener {
            callRegister()

        }

//        settingsGoogle()
        bind.loginInclude.googleButton.setOnClickListener {
            configureGoogleSignIn()
            signIn()
        }

    }

    //Google


    private fun configureGoogleSignIn() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

/*    private fun setupUI() {
        bind.googleButton.setOnClickListener {
            signIn()
        }
    }*/


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
// nao entra neste bloco de comando
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    fun getDataFields() { // password no firebase por default é 6 digitos

        val email = bind.loginInclude.edEmail.text.toString()
        val password = bind.loginInclude.edPassword.text.toString()
        val emailEpt = email.isNotEmpty()
        val passwordEpt = password.isNotEmpty()

        if (emailEpt && passwordEpt)
            sendDataFirebase(email, password)
        else
            showMsg("Preencha todas as informações ")
    }

    fun sendDataFirebase(email: String, password: String) {
//        showMsg("Enviando dados para o firebase")
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password) // só ira muda o . depois da instance
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result?.user!!
                    val idUser = firebaseUser.uid
                    val emailUser = firebaseUser.email.toString()

                    callMain(idUser, emailUser)

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

    //Chama a activity de cadastro
    fun callRegister() {
        startActivity(Intent(this, CadastroActivity::class.java))
    }


    //GOOGLE LOGIN

    /*   fun settingsGoogle(){
           val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestEmail()
               .build()

           //constroi o singInOptions
           googleSignInClient = GoogleSignIn.getClient(this, gso)

           //Captura o ultimo id
           val account = GoogleSignIn.getLastSignedInAccount(this)
       }


       //Cria a funçao do signIn
       fun loginGoogle(){
           val signInIntent: Intent = googleSignInClient.getSignInIntent()
           startActivityForResult(signInIntent, RC_SIGN_IN)

       }

       override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
           super.onActivityResult(requestCode, resultCode, data)

           if (requestCode == RC_SIGN_IN) {

               val task = GoogleSignIn.getSignedInAccountFromIntent(data)
               handleSignInResult(task)
           }
       }

       private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
           try {
               val account = completedTask.getResult(ApiException::class.java)
               val acct = GoogleSignIn.getLastSignedInAccount(this)

               if(acct != null){

                   val personName = acct.displayName
                   val personGivenName = acct.givenName
                   val personFamilyName = acct.familyName
                   val personEmail = acct.email
                   val personId = acct.id
                   val personPhoto: Uri? = acct.photoUrl

                   Toast.makeText(this, "user: $personName", Toast.LENGTH_SHORT).show()
                   personName?.let { openHome(it) }
               }
           } catch (e: ApiException) {
               Log.i("signInResult", "failed code=" + e.statusCode)
           }
       }

       private fun openHome(msg: String) {
           // recebe como parametro apenas o nome
           val intent = Intent(this, MainActivity::class.java).apply {
               putExtra("nome", msg)
           }
           startActivity(intent)

       }*/
}
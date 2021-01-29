package br.com.digitalhouse.firebaselogin_desafio4.model

import java.io.Serializable

data class Usuario(
    val nome: String,
    val email: String,
    val senha: String,
    val id: String = ""
) : Serializable

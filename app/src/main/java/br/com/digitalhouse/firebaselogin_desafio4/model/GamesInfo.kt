package br.com.digitalhouse.firebaselogin_desafio4.model

import java.io.Serializable


data class GamesInfo(
    var titulo: String = "",
    var data_lancamento: String = "",
    var descricao: String = "",
    var imgURL: String = "",
    var id: String = "",
): Serializable

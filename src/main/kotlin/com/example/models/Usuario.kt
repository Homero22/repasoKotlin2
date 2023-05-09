package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Usuario (val id: Int, val nombre: String, val anios: Int, val correo: String) {

}
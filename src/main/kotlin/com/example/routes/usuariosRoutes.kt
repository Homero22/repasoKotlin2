package com.example.routes
import com.example.models.Usuario
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


private val usuarios = mutableListOf(
    Usuario(1,"Homero Ojeda", 24, "homero@gmail.com"),
    Usuario(2,"Pablo Bolaños", 35, "pablo@gmail.com"),
    Usuario(3,"Diego Logroño", 35, "diego@gmail.com"),
    Usuario(4,"Nayeli Secaira", 35, "nayeli@gmail.com"),
)

fun Route.usuariosRouting(){
    route("/usuarios"){
        get {
            if (usuarios.isNotEmpty()){
                call.respond(usuarios)
            }else{
                call.respondText ( "No hay datos xd", status = HttpStatusCode.OK )
            }
        }
        get("{id?}"){
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Id no encontrado",
                status = HttpStatusCode.BadRequest
            )
            val usuario = usuarios.find { it.id == id.toInt() } ?: return@get call.respondText(
                "Usuario con $id no encontrado",
                status = HttpStatusCode.NotFound
            )
            call.respond(usuario)
        }
        post {
            val usuario = call.receive<Usuario>()
            usuarios.add(usuario)
            call.respondText("Usuario creado correctamente", status = HttpStatusCode.Created)
        }
        delete ("{id}"){
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Id no encontrado",
                status = HttpStatusCode.BadRequest
            )
            if(usuarios.removeIf{it.id == id.toInt()}){
                call.respondText("Usuario eliminado correctamete", status = HttpStatusCode.Accepted)
            }else{
                call.respondText("No encontrado", status = HttpStatusCode.NotFound)
            }
        }
    }

}
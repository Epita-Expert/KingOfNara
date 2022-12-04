package fr.epita.kingofnara.model

import com.example.kingofnara.service.GameService
import java.lang.reflect.Method
import java.util.function.Consumer

class EnergyCard (
    val price : Int,
    val name : String,
    val type : CardType,
    val imgResource: Int,
    val effect : String,
    val method : Consumer<GameService>
        ) {

}
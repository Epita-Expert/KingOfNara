package fr.epita.kingofnara.model

import fr.epita.kingofnara.game.GameProperties

class Player (

    val monster:Monster,
    /**
     * number of vicory point. 0 to GameProperties.NB_MAX_VICTORY_POINT
     */
    var victoryPoint:Int = 0,
    /**
     * number of life point. 0 to GameProperties.NB_MAX_LIFE_POINT
     */
    var lifePoints:Int = GameProperties.NB_MAX_LIFE_POINT,

    var nbEnergy:Int = 0,
) {

}
package com.example.kingofnara.model

import com.example.kingofnara.NB_MAX_LIFE_POINT


class Player (

    val playerType : PlayerType,

    val monster:Monster,
    /**
     * number of vicory point. 0 to GameProperties.NB_MAX_VICTORY_POINT
     */
    var victoryPoint:Int = 0,
    /**
     * number of life point. 0 to GameProperties.NB_MAX_LIFE_POINT
     */
    var lifePoints:Int = NB_MAX_LIFE_POINT,

    var nbEnergy:Int = 0,

    var inTokyo: Boolean = false,
) {
    enum class PlayerType
    {
        REAL,
        BOT
    }

    fun isAlive(): Boolean
    {
        return lifePoints > 0;
    }
}
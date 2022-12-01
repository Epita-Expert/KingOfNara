package com.example.kingofnara.model.dice

import java.io.Serializable

class DiceResult : Serializable
{
    fun add(other: DiceResult)
    {
        healPoint += other.healPoint
        hitPoint += other.hitPoint
        energyPoint += other.energyPoint
        victoryPoint += other.victoryPoint
    }

    var healPoint: Int = 0;
    var hitPoint: Int = 0;
    var energyPoint: Int = 0;
    var victoryPoint: Int = 0;
}

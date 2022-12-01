package com.example.kingofnara.service

import com.example.kingofnara.model.dice.DiceResult
import com.example.kingofnara.model.dice.Dice
import com.example.kingofnara.model.dice.DiceValue

private const val MAX_SHUFFLE = 3;
private const val NB_SAME_DICE_VICTORY_VALUE = 3;

class DiceService()
{

    private var nbShuffle = 0;
    val dices: List<Dice>;

    init
    {
        val dices = ArrayList<Dice>();
        for (i in 1..6)
            dices.add(Dice())
        this.dices = dices;
    }

    fun shuffleDices(): List<Dice> {
        if (isMoreShuffle())
        {
            dices.forEach { shuffleDice(it) };
        }
        nbShuffle ++;
        return dices
    }

    fun isMoreShuffle(): Boolean {
        return MAX_SHUFFLE > nbShuffle && dices.any { !it.validated }
    }

    fun resolveDices(): DiceResult
    {

        val diceResult = DiceResult();

        diceResult.victoryPoint = resolveVictoryPoint(dices);
        diceResult.energyPoint = resovleEnergyPoint(dices);
        diceResult.hitPoint = resolveHitPoint(dices);
        diceResult.healPoint = resolveHealPoint(dices);

        return diceResult;
    }

    private fun resolveHealPoint(dices: List<Dice>): Int {
        return dices.filter { DiceValue.LIFE_POINT == it.value }.size;
    }

    private fun resolveHitPoint(dices: List<Dice>): Int {
        return dices.filter { DiceValue.HIT == it.value }.size;
    }

    private fun resovleEnergyPoint(dices: List<Dice>): Int {
        return dices.filter { DiceValue.ENERGY == it.value }.size;
    }

    private fun resolveVictoryPoint(dices: List<Dice>): Int {
        var victoryPoint = 0;

        victoryPoint += resolveVictoryPointFor(DiceValue.ONE, dices)
        victoryPoint += resolveVictoryPointFor(DiceValue.TWO, dices)
        victoryPoint += resolveVictoryPointFor(DiceValue.THREE, dices)

        return victoryPoint;
    }

    private fun resolveVictoryPointFor(diceValue: DiceValue, dices: List<Dice>)
    : Int
    {
        return resolveVictoryPointFor(diceValue,  dices.filter { diceValue == it.value }.size)
    }

    private fun resolveVictoryPointFor(diceValue: DiceValue, nbValue : Int )
    : Int
    {
        val value = when(diceValue)
        {
            DiceValue.ONE -> 1
            DiceValue.TWO -> 2
            DiceValue.THREE -> 3
            else -> 0
        }

        return if (nbValue > NB_SAME_DICE_VICTORY_VALUE) (nbValue - NB_SAME_DICE_VICTORY_VALUE + value) else 0
    }

    private fun shuffleDice(dice : Dice)
    {
        if (!dice.validated)
            dice.value = DiceValue.values().toList().shuffled().first();
    }
}
package fr.epita.kingofnara.game

import fr.epita.kingofnara.model.Dice
import fr.epita.kingofnara.model.DiceValue

class DiceService
    (val gameRun: GameRun)
{

    private val MAX_SHUFFLE = 3;
    private val NB_SAME_DICE_VICTORY_VALUE = 3;

    fun getDiceResult(): DiceResult {
        val dices : List<Dice> = ArrayList();

        var nbSuffle = 0;
        var finish:Boolean;
        var finishShuffle:Boolean;

        do {
            dices.forEach(this::shuffleDice);
            nbSuffle++;
            finishShuffle = MAX_SHUFFLE == nbSuffle;
            if (!finishShuffle)
            {
                validateDices(dices);
                finish = dices.filter { !it.validated }.isEmpty();
            }
            else
            {
                onNewDicesValue(dices)
                break;
            }
        } while (finish)

        return resolveDices(dices);

    }


    private fun resolveDices(dices: List<Dice>): DiceResult
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

        victoryPoint += dices.filter { DiceValue.ONE == it.value }.size.div(NB_SAME_DICE_VICTORY_VALUE)
        victoryPoint += dices.filter { DiceValue.TWO == it.value }.size.div(NB_SAME_DICE_VICTORY_VALUE)
        victoryPoint += dices.filter { DiceValue.THREE == it.value }.size.div(NB_SAME_DICE_VICTORY_VALUE)

        return victoryPoint;
    }

    private fun validateDices(dice: List<Dice>) {
        gameRun.getValidateDices();
    }

    private fun onNewDicesValue(dices: List<Dice>) {
        gameRun.onNewDicesValue();
    }

    private fun shuffleDice(dice : Dice)
    {
        if (!dice.validated)
            dice.value = DiceValue.values().toList().shuffled().first();
    }
}
package com.example.kingofnara.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kingofnara.model.dice.DiceResult
import com.example.kingofnara.service.DiceService
import com.example.kingofnara.model.dice.Dice
import com.example.kingofnara.service.GameService

class DiceVM : ViewModel()
{
    val endDices = MutableLiveData(false)
    lateinit var gameService : GameService
    private var diceService = DiceService();
    var dices : MutableLiveData<List<Dice>> = MutableLiveData(emptyList());

    fun nextStep() {
        if (diceService.isMoreShuffle())
        {
            shuffle()
        }
        else
        {
            gameService.setDiceResult(diceService.resolveDices());
            endDices.value = true
        }
    }

    fun changeDiceValidatedState(dice : Dice): Boolean
    {
        dice.validated = !dice.validated
        return dice.validated
    }

    fun getDices(): List<Dice>
    {
        return diceService.dices;
    }

    private fun shuffle()
    {
        dices.value = diceService.shuffleDices();
    }
}
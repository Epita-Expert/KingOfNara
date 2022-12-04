package com.example.kingofnara.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kingofnara.model.dice.DiceResult
import com.example.kingofnara.service.DiceService
import com.example.kingofnara.model.dice.Dice
import com.example.kingofnara.service.GameService

class DiceVM : ViewModel()
{
    lateinit var gameService : GameService
    private var diceService = DiceService();
    var dices : MutableLiveData<List<Dice>> = MutableLiveData(emptyList());
    val moreShuffle = MutableLiveData(true)

    fun nextStep() {
        if (diceService.isMoreShuffle())
        {
            shuffle()
        }
        moreShuffle.value = diceService.isMoreShuffle()
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

    fun validate() {
        gameService.setDiceResult(diceService.resolveDices());
    }
}
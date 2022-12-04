package com.example.kingofnara.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kingofnara.model.GameStep
import com.example.kingofnara.model.Monster
import com.example.kingofnara.model.Player
import com.example.kingofnara.model.dice.DiceResult
import com.example.kingofnara.service.DiceService
import com.example.kingofnara.service.GameService

class PlateVM : ViewModel()
{
    lateinit var gameService : GameService

    var currentPlayerName = MutableLiveData("");
    var currentScore = MutableLiveData(DiceResult())

    var outsideTokyoMonsters : MutableLiveData<List<Monster>> = MutableLiveData();
    var insideTokyoMonsters : MutableLiveData<List<Monster>> = MutableLiveData();

    var newLeaveAsking : MutableLiveData<String> = MutableLiveData();

    fun setMonstersList()
    {
        outsideTokyoMonsters.value = gameService.getOutsideTokyoMonster();
        insideTokyoMonsters.value = gameService.getInsideTokyoMonster();
    }

    fun getNewRound()
    {
        gameService.startRound()
        currentPlayerName.value = gameService.getCurrentPlayer().monster.name
        currentScore.value = gameService.getCurrentScore()

        //if a bot set automatic dices result
        if (Player.PlayerType.BOT == gameService.getCurrentPlayer().playerType)
        {
            val diceService = DiceService();
            diceService.shuffleDices();
            val resolveDices = diceService.resolveDices();
            gameService.setDiceResult(resolveDices);
            updateScore();
        }
    }

    fun updateScore()
    {
        gameService.applyCurrentScore();
        currentScore.value = gameService.getCurrentScore();
    }

    fun askForTokyoLeaver()
    {
        val player = gameService.leavingPlayer[0]
        if (Player.PlayerType.BOT == player.playerType)
        {
            //random decision
            setTokyoLeaver((1 == Math.random().toInt()%2));
        }
        else
        {
            newLeaveAsking.value = player.monster.name
        }
    }

    fun setTokyoLeaver(leave : Boolean)
    {
        gameService.playerWantToLeave(gameService.leavingPlayer[0], leave)
        if (0 == gameService.leavingPlayer.size)
        {
            moveMonster()
        }
    }

    fun moveMonster()
    {
        gameService.moveMonster();
        setMonstersList();
    }

    fun getNextStep(): GameStep {
        return gameService.nextStep
    }

}
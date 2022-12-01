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
    var winner = MutableLiveData<Player>();

    var outsideTokyoMonsters : MutableLiveData<List<Monster>> = MutableLiveData();
    var insideTokyoMonsters : MutableLiveData<List<Monster>> = MutableLiveData();

    var newLeaveAsking : MutableLiveData<String> = MutableLiveData();

    var gameFinished = MutableLiveData(false)

    fun doNext()
    {
        Log.i(javaClass.name, "doNext() : " + gameService.nextStep.name)
       when (gameService.nextStep)
       {
           GameStep.INIT_ROUND -> getNewRound()
           //TODO GameStep.THROW_DICE ->
           GameStep.RESOLVE_SCORE -> updateScore()
           GameStep.ASK_FOR_TOKYO_LEAVER -> askForTokyoLeaver()
           GameStep.MOVE_MONSTER -> moveMonster()
           GameStep.STOP_GAME -> {
               gameFinished.value = true
           }
           else -> {}
       }
    }

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
        if (gameService.isWinner())
        {
            winner.value = gameService.getWinner()
        }
    }

    fun askForTokyoLeaver()
    {
        val player = gameService.leavingPlayer[0]
        if (Player.PlayerType.BOT == player.playerType)
        {
            setTokyoLeaver(true);
        }
        else
        {
            newLeaveAsking.value = player.monster.name
        }
    }

    fun setTokyoLeaver(leave : Boolean)
    {
        gameService.playerWantToLeave(gameService.leavingPlayer[0], leave)
    }

    fun moveMonster()
    {
        gameService.moveMonster();
        setMonstersList();
    }

}
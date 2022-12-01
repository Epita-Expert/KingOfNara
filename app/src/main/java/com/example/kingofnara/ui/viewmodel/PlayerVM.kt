package com.example.kingofnara.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kingofnara.model.Monster
import com.example.kingofnara.model.Player
import com.example.kingofnara.service.GameService

class PlayerVM : ViewModel()
{
    lateinit var gameService: GameService

    var player : MutableLiveData<Player> = MutableLiveData()

    fun setPlayer(monster: Monster) {
        player.value = gameService.getPlayer(monster)
    }

    fun getPlayerImgResource(): Int
    {
        return player.value!!.monster.resourceDraw
    }

    fun getMonsterNameString(): String {
        return player.value!!.monster.name
    }

    fun getVPString(): String {
        return player.value!!.victoryPoint.toString()
    }

    fun getLPString(): String {
        return player.value!!.lifePoints.toString()
    }

    fun getEPString(): String {
        return  player.value!!.nbEnergy.toString()
    }

}

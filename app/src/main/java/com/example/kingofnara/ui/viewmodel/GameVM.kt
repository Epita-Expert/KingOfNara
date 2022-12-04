package com.example.kingofnara.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kingofnara.model.Monster
import com.example.kingofnara.service.GameService

class GameVM()
    : ViewModel()
{
    lateinit var gameService : GameService

    var nbPlayer: Int = 1
    var nbPlayerWithMonster = 0
    var totalPlayer: Int = 3
    var playerChosenMonsters = ArrayList<Monster>()

    var ready = false

    fun validateInit()
    {
        gameService = GameService(totalPlayer, playerChosenMonsters)
        ready = true
    }

    fun getNbBotsTxt(): Int
    {
        return totalPlayer - nbPlayer
    }

    fun getChooseMonsterPlayerName(): String
    {
        return "Player " + (nbPlayerWithMonster + 1)
    }

    fun getAvailableMonsterToChoose(): List<Monster>
    {
        return Monster.values().filter { !playerChosenMonsters.contains(it) }
    }

    fun isMoreMonsterToChoose(): Boolean
    {
        return nbPlayerWithMonster < nbPlayer
    }

    fun chooseMonsters(monster: Monster) {
        nbPlayerWithMonster +=1
        playerChosenMonsters.add(monster)
        if (!isMoreMonsterToChoose())
        {
            validateInit()
        }
    }

}
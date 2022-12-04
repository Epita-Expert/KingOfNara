package com.example.kingofnara.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kingofnara.DRAW_PRICE
import com.example.kingofnara.ENERGY_CARDS
import com.example.kingofnara.NB_VISIBLE_CARD
import com.example.kingofnara.service.GameService
import fr.epita.kingofnara.model.EnergyCard

class CardsVM : ViewModel()
{
    lateinit var gameService: GameService

    fun getCards() : List<EnergyCard>
    {
        if (0 == gameService.availableCard.size)
        {
            gameService.availableCard.addAll(initCard())
        }
        return gameService.availableCard
    }

    fun isDrawAvailable(): Boolean
    {
        return gameService.getCurrentPlayer().nbEnergy >= DRAW_PRICE
    }

    fun draw()
    {
        if (isDrawAvailable())
        {
            gameService.getCurrentPlayer().nbEnergy -= DRAW_PRICE
            for (i in 0 until gameService.availableCard.size) {
                selectCard(gameService.availableCard[i])
            }
        }
    }

    fun isCardAvailable(card: EnergyCard): Boolean
    {
        if (gameService.getCurrentPlayer().nbEnergy >= card.price)
        {
            return true
        }
        return false
    }

    fun selectCard(card: EnergyCard): EnergyCard
    {
        if (isCardAvailable(card)) {
            gameService.getCurrentPlayer().nbEnergy -= card.price
            gameService.getCurrentPlayer().cards.add(card)

            gameService.availableCard.remove(card)
            val newCard = getNewCard()
            gameService.availableCard.add(newCard)

            return newCard
        }

        return card
    }

    fun validate() {
        gameService.finishCard()
    }

    companion object{

        fun initCard(): ArrayList<EnergyCard> {
            val availableCard = ArrayList<EnergyCard>()
            for (i in 1..NB_VISIBLE_CARD)
            {
                availableCard.add(getNewCard())
            }
            return availableCard
        }

        fun getNewCard(): EnergyCard
        {
            return ENERGY_CARDS.random()
        }
    }
}

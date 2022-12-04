package com.example.kingofnara

import fr.epita.kingofnara.model.CardType
import fr.epita.kingofnara.model.EnergyCard
import java.util.function.Consumer


const val NB_BLACK_DICES = 6;

    //Cards properties
    val ENERGY_CARDS = listOf(
        EnergyCard(3, "Garde nationale", CardType.ACTION, R.drawable.national_guard,"+2 étoiles et -1 coeur"
        ) { gameService ->
            val currentPlayer = gameService.getCurrentPlayer()
            currentPlayer.victoryPoint += 2
            currentPlayer.lifePoints -= 1
        },

        EnergyCard(3, "Herbivore", CardType.POWER, R.drawable.goat,"Gagner 1 étoile si vous n'avez pas fait de main."
        ) { gameService ->
            if (0 == gameService.getCurrentScore().hitPoint)
            {
                gameService.getCurrentPlayer().victoryPoint += 1
            }
        },
        EnergyCard(8, "Souffle de feu", CardType.POWER, R.drawable.dragon,"Infligez 1 blessure à vos voisins de table lorsque vous détenez au moins une main"
        ) {gameService ->
            if (0 < gameService.getCurrentScore().hitPoint)
            {
                val indexOfCurrentPlayer = gameService.leavingPlayer.indexOf(gameService.getCurrentPlayer())

                    if (0 != indexOfCurrentPlayer)
                    {
                        gameService.leavingPlayer[indexOfCurrentPlayer - 1].lifePoints -= 1
                    }
                    else
                    {
                        gameService.leavingPlayer.last().lifePoints -= 1
                    }
                if (3 <= gameService.leavingPlayer.size)
                {
                    if (gameService.leavingPlayer.lastIndex != indexOfCurrentPlayer)
                    {
                        gameService.leavingPlayer[indexOfCurrentPlayer + 1].lifePoints -= 1
                    }
                    else
                    {
                        gameService.leavingPlayer.first().lifePoints -= 1
                    }
                }
            }
        },
        EnergyCard(4, "Gagner des coeur", CardType.ACTION, R.drawable.heart,"Vous donne 2 coeur"
        ) {gameService ->
            gameService.getCurrentPlayer().lifePoints += 2
        }
    );

    const val NB_VISIBLE_CARD = 3;
    const val DRAW_PRICE = 2;

    //Player properties
    const val NB_MAX_LIFE_POINT = 10;
    const val NB_MAX_VICTORY_POINT = 20;
    const val NB_MAX_PLAYER = 6;
    const val NB_PLAYERS = 3;
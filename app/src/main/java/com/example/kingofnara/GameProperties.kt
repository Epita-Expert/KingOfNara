package com.example.kingofnara

import fr.epita.kingofnara.model.CardType
import fr.epita.kingofnara.model.EnergyCard


const val NB_BLACK_DICES = 6;

    //Cards properties
    val ENERGY_CARDS = listOf(
        EnergyCard(4, "Card 1", CardType.ACTION, R.drawable.heart,"Give you 2 life point")
    );
    const val NB_VISIBLE_CARD = 3;
    const val DRAW_PRICE = 2;

    //Player properties
    const val NB_MAX_LIFE_POINT = 10;
    const val NB_MAX_VICTORY_POINT = 20;
    const val NB_MAX_PLAYER = 6;
    const val NB_PLAYERS = 3;
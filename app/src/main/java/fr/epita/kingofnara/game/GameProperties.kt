package fr.epita.kingofnara.game

import fr.epita.kingofnara.model.CardType
import fr.epita.kingofnara.model.Dice
import fr.epita.kingofnara.model.EnergyCard
import fr.epita.kingofnara.model.Monster

object GameProperties {

    val MONSTER_NAMES = listOf(
        "Cyber Bunny",
        "Alienoid",
        "Mexa Dragon",
        "The King",
        "Zair",
        "Kraken",
    );

    const val NB_BLACK_DICES = 6;

    val ENERGY_CARDS = listOf(
        EnergyCard(4, "Card 1", CardType.ACTION, "Give you 2 life point")
    );

    //Player properties
    const val NB_MAX_LIFE_POINT = 10;
    const val NB_MAX_VICTORY_POINT = 20;
}
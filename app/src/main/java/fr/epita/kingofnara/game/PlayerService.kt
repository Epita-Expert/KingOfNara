package fr.epita.kingofnara.game

import fr.epita.kingofnara.model.Player

class PlayerService(val players : List<Player>)
{
    var currentPlayerId = -1;

    fun getNextRoundPlayer(): Player
    {
        currentPlayerId = (currentPlayerId + 1) % players.size;
        return players[currentPlayerId];
    }
}
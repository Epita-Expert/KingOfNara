package com.example.kingofnara.service

import com.example.kingofnara.NB_MAX_VICTORY_POINT
import com.example.kingofnara.NB_PLAYERS
import com.example.kingofnara.model.GameStep
import com.example.kingofnara.model.Monster
import com.example.kingofnara.model.Player
import com.example.kingofnara.model.dice.DiceResult

class GameService(nbPlayer: Int = NB_PLAYERS)
{
    private var tokyoSize : Int;

    private val totalPlayers: List<Player>
    private var playersAlive : ArrayList<Player>

    private lateinit var currentPlayer : Player;
    private var iterator: Iterator<Player>? = null;
    private var currentScore = DiceResult();

    var nextStep = GameStep.INIT_ROUND

    var leavingPlayer : ArrayList<Player> = ArrayList();

    init
    {
        val players = ArrayList<Player>();
        Monster.values().copyOf(nbPlayer).forEach { if (null != it)
            players.add(Player(
                if (players.isEmpty())
                    Player.PlayerType.REAL
                else
                    Player.PlayerType.BOT
                , it)) };

        this.totalPlayers= players.filter { true }
        this.playersAlive = players;

        this.tokyoSize = if (nbPlayer <= 4) 1 else 2;
    }

    fun startRound()
    {
        //reset round values
        val player = getNextRoundPlayer();
        currentScore = DiceResult();

        if (player.inTokyo)
        {
            currentScore.victoryPoint += 2;
        }

        nextStep = GameStep.THROW_DICE
    }

    fun setDiceResult(diceResult : DiceResult)
    {
        currentScore.add(diceResult);

        nextStep = GameStep.RESOLVE_SCORE
    }

    fun applyCurrentScore()
    {
        val playerInTokyo = currentPlayer.inTokyo;

        //Monster in tokyo cannot use health point result of dice
        if (!playerInTokyo)
        {
            currentPlayer.lifePoints =
                 if (currentPlayer.lifePoints + currentScore.healPoint >= 10) 10
                 else (currentPlayer.lifePoints + currentScore.healPoint);
        }

        //TODO check card that add lifePoint

        //hit
        if (0 < currentScore.hitPoint)
        {
                playersAlive.filter {
                    if (playerInTokyo) !it.inTokyo else it.inTokyo
                }.forEach { hitPlayer(it) }
        }

        //victory point
        currentPlayer.victoryPoint += currentScore.victoryPoint;

        currentPlayer.nbEnergy += currentScore.energyPoint;

        nextStep = if (isWinner())
        {
            GameStep.STOP_GAME
        }
        else if (playerHitTokyo())
        {
            if (tokyoIsEmpty())
            {
                GameStep.MOVE_MONSTER
            }
            else
            {
                leavingPlayer.addAll(getInsideTokyoPlayer())
                GameStep.ASK_FOR_TOKYO_LEAVER
            }
        }
        else
        {
            GameStep.INIT_ROUND
        }
    }

    private fun tokyoIsEmpty(): Boolean {
        return playersAlive.none { it.inTokyo }
    }

    private fun playerHitTokyo(): Boolean {
        return 0 < currentScore.hitPoint && !currentPlayer.inTokyo;
    }

    private fun hitPlayer(player: Player)
    {
        player.lifePoints -=  currentScore.hitPoint
        if (0 >= player.lifePoints)
        {
            playersAlive.remove(player)
            if (4 == playersAlive.size)
            {
                resizeTokyo();
            }
        }
    }

    private fun resizeTokyo() {
        val playersInTokyo = playersAlive.filter { it.inTokyo }
        if (playersInTokyo.size > 1)
        {
            playersInTokyo[1].inTokyo = false
        }
        tokyoSize = 1
    }

    fun playerWantToLeave(player: Player, leave: Boolean)
    {
        if (leavingPlayer.remove(player))
        {
            player.inTokyo = !leave
        }
        nextStep = if (leavingPlayer.size > 0) GameStep.ASK_FOR_TOKYO_LEAVER else GameStep.MOVE_MONSTER;
    }

    fun moveMonster()
    {
        val hitTokyo = playerHitTokyo();

        if (hitTokyo)
        {
            if (!isTokyoFull())
            {
                currentPlayer.inTokyo = true
                currentPlayer.victoryPoint += 1;
            }
        }

        nextStep = GameStep.INIT_ROUND;
    }

    fun isWinner(): Boolean
    {
        return playersAlive.size <=1 || playersAlive.any { it.victoryPoint >= NB_MAX_VICTORY_POINT }
    }

    fun getWinner(): Player
    {
        return if (1 == playersAlive.size) playersAlive[0] else playersAlive.maxBy { it.victoryPoint }

    }

    private fun isTokyoFull(): Boolean
    {
        return playersAlive.filter { it.inTokyo }.size >= tokyoSize;
    }

    private fun getNextRoundPlayer(): Player
    {
        if (null ==  iterator || !iterator!!.hasNext())
        {
            iterator = totalPlayers.iterator()
        }

        currentPlayer = iterator!!.next()

        return if (currentPlayer.isAlive()) currentPlayer else getNextRoundPlayer();
    }

    fun getCurrentPlayer(): Player
    {
        return currentPlayer;
    }

    fun getCurrentScore(): DiceResult
    {
        return currentScore;
    }

    fun getOutsideTokyoMonster(): List<Monster>
    {
        return playersAlive.filter { !it.inTokyo }.map { it.monster }
    }

    fun getInsideTokyoMonster(): List<Monster>
    {
        return playersAlive.filter { it.inTokyo }.map { it.monster }
    }

    fun getPlayer(monster: Monster): Player?
    {
        return totalPlayers.firstOrNull { monster == it.monster }
    }

    private fun getInsideTokyoPlayer(): List<Player> {
        return playersAlive.filter { it.inTokyo }
    }

}
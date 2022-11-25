package fr.epita.kingofnara.game

import fr.epita.kingofnara.model.Monster
import fr.epita.kingofnara.model.Player

class GameRun(selectedMonsters: ArrayList<Monster>)
{
    val diceService = DiceService(this);
    val plateService:PlateService;
    val playerService: PlayerService;

    init
    {
        plateService = PlateService(selectedMonsters);
        val players = selectedMonsters.map { Player(it) }
        playerService = PlayerService(players);
    }

    fun start()
    {
        while (!isGameFinished()) {

            startRound(playerService.getNextRoundPlayer());
        }

    }

    private fun isGameFinished(): Boolean {
        return false;//TODO
    }

    private fun startRound(player: Player) {

        var totalEnergy = 0;
        var totalVictory = 0;
        var totalLife = 0;
        var totalHit = 0;

        val playerInTokyo = plateService.isInTokyo(player.monster);

        if (playerInTokyo)
        {
            totalVictory += 2;
        }

        //Throw dices
        val diceResult: DiceResult = diceService.getDiceResult();

        //setResult
        //  energy
        totalEnergy += diceResult.energyPoint;
        //  health (max 10)
        //TODO check card that add lifePoint
        //Monster in tokyo cannot use health point result of dice
        if (!playerInTokyo)
        {
            totalLife += diceResult.healPoint;
           /*TODO
            player.lifePoints =
                if (player.lifePoints + diceResult.healPoint > 10)
                    10
                    else (player.lifePoints + diceResult.healPoint);
            */
        }
        // hit
        totalHit = diceResult.hitPoint;
        if (0 < totalHit)
        {
            if (playerInTokyo)
            {
                hitMonsterOutsideTokyo(totalHit);
            }
            else
            {
                hitTokyoMonster(totalHit);

                if (!plateService.isTokyoFull())
                {
                    plateService.moveMonsterToTokyo(player.monster);
                    totalVictory += 1;
                }
            }
        }
    }

    private fun hitMonsterOutsideTokyo(totalHit: Int)
    {
        players.filter { !plateService.isInTokyo(it.monster) }
            .forEach { it.hit(totalHit) }
    }

    private fun hitTokyoMonster(totalHit: Int)
    {
        if (!plateService.isTokyoEmpty())
        {
            players.filter { plateService.isInTokyo(it.monster) }
                .forEach {
                //TODO ask if he want to leave here
                it.hit(totalHit)
            }
        }
    }


    fun getValidateDices() {
        TODO("Not yet implemented")
    }

    fun onNewDicesValue() {
        TODO("Not yet implemented")
    }


}
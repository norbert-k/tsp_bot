package lv.tsp.discordbot

import lv.tsp.discordbot.commands.AtzimeCommand
import lv.tsp.discordbot.commands.KreditsCommand
import lv.tsp.discordbot.commands.PlayCommand
import lv.tsp.discordbot.commands.RadioCommand

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        val configuration = Configuration()
        com.beust.jcommander.JCommander.newBuilder()
            .addObject(configuration)
            .build()
            .parse(*args)

        val commands = listOf(
            PlayCommand(),
            AtzimeCommand(),
            KreditsCommand(),
            RadioCommand()
        )

        val bot = Bot(configuration, commands)
        bot.registerCommands()
        bot.start()
    }
}
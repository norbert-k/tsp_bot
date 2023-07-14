package lv.tsp.discordbot.commands

import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import lv.tsp.discordbot.audio.LavaPlayerAudioProvider
import lv.tsp.discordbot.audio.TrackScheduler
import kotlin.jvm.optionals.getOrNull

class KreditsCommand : DiscordCommand {
    override val commandRequest: ApplicationCommandRequest
        get() = ApplicationCommandRequest.builder()
            .name("kredits")
            .description("Izdot atro kreditu")
            .addOption(
                ApplicationCommandOptionData.builder()
                    .name("persona")
                    .description("Persona")
                    .required(true)
                    .type(ApplicationCommandOption.Type.STRING.value)
                    .build()
            )
            .addOption(
                ApplicationCommandOptionData.builder()
                    .name("iestade")
                    .description("Kredita iestade")
                    .required(true)
                    .type(ApplicationCommandOption.Type.STRING.value)
                    .build()
            )
            .addOption(
                ApplicationCommandOptionData.builder()
                    .name("apjoms")
                    .description("Kredita apjoms")
                    .required(true)
                    .type(ApplicationCommandOption.Type.INTEGER.value)
                    .build()
            )
            .build()

    fun random(): Boolean {
        val min = 1
        val max = 10
        val random = (Math.random() * (max - min + 1) + min).toInt()
        return random > 5
    }


    // function that generates random currency icon
    fun randomCurrency(): String {
        val min = 1
        val max = 3
        val random = (Math.random() * (max - min + 1) + min).toInt()
        return when (random) {
            1 -> "💵"
            2 -> "💶"
            3 -> "💴"
            else -> "💵"
        }
    }

    override fun handle(
        playerManager: DefaultAudioPlayerManager,
        provider: LavaPlayerAudioProvider,
        scheduler: TrackScheduler,
        event: ChatInputInteractionEvent
    ): InteractionApplicationCommandCallbackReplyMono {
        val person = event.getOption("persona").getOrNull()?.value?.getOrNull()?.asString()
        val iestade = event.getOption("iestade").getOrNull()?.value?.getOrNull()?.asString()
        val apjoms = event.getOption("apjoms").getOrNull()?.value?.getOrNull()?.asLong()

        return if (!random()) {
            event.reply("Kredits nav izdots [${person}] no [${iestade}] apjoms [${apjoms}${randomCurrency()}]")
        } else {
            event.reply("Kredits izdots [${person}] no [${iestade}] apjoms [${apjoms}${randomCurrency()}]")
        }
    }
}
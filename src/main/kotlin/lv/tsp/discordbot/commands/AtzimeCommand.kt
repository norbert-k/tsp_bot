package lv.tsp.discordbot.commands

import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.spec.EmbedCreateSpec
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.rest.util.Color
import lv.tsp.discordbot.audio.LavaPlayerAudioProvider
import lv.tsp.discordbot.audio.TrackScheduler
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

class AtzimeCommand() : DiscordCommand {
    override val commandRequest: ApplicationCommandRequest
        get() = ApplicationCommandRequest.builder()
            .name("atzime")
            .description("Izprintet atzimi no e-klases")
            .addOption(
                ApplicationCommandOptionData.builder()
                    .name("prieksmets")
                    .description("Prieksmets")
                    .required(true)
                    .type(ApplicationCommandOption.Type.STRING.value)
                    .build()
            )
            .addOption(
                ApplicationCommandOptionData.builder()
                    .name("persona")
                    .description("Persona")
                    .required(true)
                    .type(ApplicationCommandOption.Type.STRING.value)
                    .build()
            )
            .build()

    fun random(): Int {
        val min = 1
        val max = 10
        return (Math.random() * (max - min + 1) + min).toInt()
    }

    override fun handle(
        playerManager: DefaultAudioPlayerManager,
        provider: LavaPlayerAudioProvider,
        scheduler: TrackScheduler,
        event: ChatInputInteractionEvent
    ): InteractionApplicationCommandCallbackReplyMono {
        val prieksmets = event.getOption("prieksmets").getOrNull()?.value?.getOrNull()?.asString()
        val person = event.getOption("persona").getOrNull()?.value?.getOrNull()?.asString()
        val atzime = random()

        val embed: EmbedCreateSpec = EmbedCreateSpec.builder()
            .color(Color.BLUE)
            .title("E-KLASE")
            .url("https://e-klase.lv")
            .author(
                person,
                "https://e-klase.lv",
                "https://muzikasskola.jurmala.lv/wp-content/uploads/E-klase_logo-1-532x300.png"
            )
            .description("${person} atzime prieksmeta ${prieksmets} ir ${atzime}")
            .thumbnail("https://muzikasskola.jurmala.lv/wp-content/uploads/E-klase_logo-1-532x300.png")
            .addField("prieksmets", prieksmets, true)
            .addField("persona", person, true)
            .image("https://muzikasskola.jurmala.lv/wp-content/uploads/E-klase_logo-1-532x300.png")
            .timestamp(Instant.now())
            .footer("E-KLASE TM", "https://muzikasskola.jurmala.lv/wp-content/uploads/E-klase_logo-1-532x300.png")
            .build()

        return event.reply().withEmbeds(embed)
    }
}
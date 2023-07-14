package lv.tsp.discordbot.commands

import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.core.`object`.entity.Member
import discord4j.core.spec.EmbedCreateSpec
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import discord4j.rest.util.Color
import lv.tsp.discordbot.audio.LavaPlayerAudioProvider
import lv.tsp.discordbot.audio.TrackScheduler
import java.time.Instant
import kotlin.jvm.optionals.getOrNull


class PlayCommand : DiscordCommand {
    override val commandRequest: ApplicationCommandRequest
        get() = ApplicationCommandRequest.builder()
            .name("play")
            .description("Play a song")
            .addOption(
                ApplicationCommandOptionData.builder()
                    .name("URL")
                    .description("Song URL to play")
                    .required(true)
                    .type(ApplicationCommandOption.Type.STRING.value)
                    .build()
            )
            .build()

    override fun handle(
        playerManager: DefaultAudioPlayerManager,
        provider: LavaPlayerAudioProvider,
        scheduler: TrackScheduler,
        event: ChatInputInteractionEvent
    ): InteractionApplicationCommandCallbackReplyMono {
        val member: Member? = event.interaction.member.orElse(null)
        if (member != null) {
            val voiceState = member.voiceState.block()
            if (voiceState != null) {
                val channel = voiceState.channel.block()
                if (channel != null) {
                    channel.join { joinSpec ->
                        joinSpec.setProvider(provider)
                    }.block()
                    val url = event.getOption("song").getOrNull()?.value?.getOrNull()
                    playerManager.loadItem(url?.asString(), scheduler)
                }
            }
        }

        val embed: EmbedCreateSpec = EmbedCreateSpec.builder()
            .color(Color.BLUE)
            .title("E-KLASE")
            .url("https://discord4j.com")
            .author("Some Name", "https://discord4j.com", "https://muzikasskola.jurmala.lv/wp-content/uploads/E-klase_logo-1-532x300.png")
            .description("a description")
            .thumbnail("https://muzikasskola.jurmala.lv/wp-content/uploads/E-klase_logo-1-532x300.png")
            .addField("field title", "value", false)
            .addField("\u200B", "\u200B", false)
            .addField("inline field", "value", true)
            .addField("inline field", "value", true)
            .addField("inline field", "value", true)
            .image("https://muzikasskola.jurmala.lv/wp-content/uploads/E-klase_logo-1-532x300.png")
            .timestamp(Instant.now())
            .footer("footer", "https://muzikasskola.jurmala.lv/wp-content/uploads/E-klase_logo-1-532x300.png")
            .build()


        return event.reply().withEmbeds(listOf(embed)).withEphemeral(true)
    }
}
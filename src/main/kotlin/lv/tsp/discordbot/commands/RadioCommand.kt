package lv.tsp.discordbot.commands

import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.component.ActionRow
import discord4j.core.`object`.component.Button
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono
import discord4j.discordjson.json.ApplicationCommandRequest
import lv.tsp.discordbot.audio.LavaPlayerAudioProvider
import lv.tsp.discordbot.audio.TrackScheduler

class RadioCommand : DiscordCommand {

    private val radioLinks = mapOf(
        "Latgales radio" to "http://195.13.253.51:8000/128_mp3",
        "Latvijas radio 1" to "http://lr1mp1.latvijasradio.lv:8012/;",
        "Latvijas radio 2" to "http://lr2mp1.latvijasradio.lv:8002/;",
        "Latvijas radio 3" to "http://lr3mp0.latvijasradio.lv:8004/;",
        "Latvijas radio 4" to "http://lr4mp1.latvijasradio.lv:8020/;",
        "Radio NABA" to "http://nabamp0.latvijasradio.lv:8016/;",
        "Krievijas Hitu radio" to "http://stream.hitirossii.com:8000/khr.mp3"
    )
    override val commandRequest: ApplicationCommandRequest
        get() = ApplicationCommandRequest.builder()
            .name("radio")
            .description("Radio")
            .build()

    override fun handle(
        playerManager: DefaultAudioPlayerManager,
        provider: LavaPlayerAudioProvider,
        scheduler: TrackScheduler,
        event: ChatInputInteractionEvent
    ): InteractionApplicationCommandCallbackReplyMono {
        val lr1 = Button.success("lr-1", "Latvijas radio 1")
        val lr2 = Button.success("lr-2", "Latvijas radio 2")
        val lr3 = Button.success("lr-3", "Latvijas radio 3")
        val lr4 = Button.success("lr-4", "Latvijas radio 4")
        val lr5 = Button.success("lr-5", "Latvijas radio 5")
        val naba = Button.success("lr-6", "Radio NABA")
        return event.reply("rajdio").withEphemeral(true)
            .withComponents(ActionRow.of(lr1, lr2, lr3, lr4, lr5), ActionRow.of(naba))
    }
}
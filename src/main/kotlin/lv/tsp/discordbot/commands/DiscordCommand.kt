package lv.tsp.discordbot.commands

import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono
import discord4j.discordjson.json.ApplicationCommandRequest
import lv.tsp.discordbot.audio.LavaPlayerAudioProvider
import lv.tsp.discordbot.audio.TrackScheduler

interface DiscordCommand {
    val commandRequest: ApplicationCommandRequest
    fun handle(
        playerManager: DefaultAudioPlayerManager,
        provider: LavaPlayerAudioProvider,
        scheduler: TrackScheduler,
        event: ChatInputInteractionEvent
    ): InteractionApplicationCommandCallbackReplyMono
}
package lv.tsp.discordbot

import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.interaction.ButtonInteractionEvent
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.`object`.entity.Member
import lv.tsp.discordbot.audio.LavaPlayerAudioProvider
import lv.tsp.discordbot.audio.TrackScheduler
import lv.tsp.discordbot.commands.DiscordCommand
import kotlin.jvm.optionals.getOrNull

class Bot(private val configuration: Configuration, private val commands: List<DiscordCommand>) {
    val BASS_BOOST = floatArrayOf(
        0.2f,
        0.15f,
        0.1f,
        0.05f,
        0.0f,
        -0.1f,
        -0.2f,
        -0.2f,
        -0.2f,
        -0.2f,
        -0.2f,
        -0.2f,
        -0.2f,
        -0.2f,
        -0.2f
    )

    private var client: DiscordClient? = configuration.discordToken?.let { DiscordClient.create(it) }
    private val playerManager: DefaultAudioPlayerManager = DefaultAudioPlayerManager();
    private val player = playerManager.createPlayer();
    private val provider: LavaPlayerAudioProvider = LavaPlayerAudioProvider(player);
    private val scheduler: TrackScheduler = TrackScheduler(player);
    private val equalizer = EqualizerFactory();
    fun bassBoost(percentage: Float) {
        val multiplier = percentage / 100.00f
        for (i in BASS_BOOST.indices) {
            equalizer.setGain(i, BASS_BOOST[i] * multiplier)
        }
    }

    init {
        AudioSourceManagers.registerRemoteSources(playerManager);
        player.setFrameBufferDuration(500);
        player.setFilterFactory(equalizer);
        playerManager.configuration.isFilterHotSwapEnabled = true;
    }

    fun registerCommands() {
        val applicationId = client?.applicationId?.block();
        commands.forEach {
            client?.applicationService?.createGuildApplicationCommand(
                applicationId!!,
                configuration.guildId!!,
                it.commandRequest
            )?.subscribe();
        }
    }

    fun start() {
        val login: GatewayDiscordClient? = client?.login()?.block();
        val commands = commands.associate { Pair(it.commandRequest.name(), it::handle) }
        login?.on(ChatInputInteractionEvent::class.java) { event ->
            commands.get(event.commandName)?.invoke(playerManager, provider, scheduler, event)
                ?: event.reply("Unsupported command!")
        }?.subscribe()

        login?.on(ButtonInteractionEvent::class.java) { event ->
            println(event.customId)
            if (event.customId.equals("lr-6")) {
                val member: Member? = event.interaction.member.orElse(null)
                if (member != null) {
                    val voiceState = member.voiceState.block()
                    if (voiceState != null) {
                        val channel = voiceState.channel.block()
                        if (channel != null) {
                            channel.join { joinSpec ->
                                joinSpec.setProvider(provider)
                            }.block()
                            playerManager.loadItem("http://nabamp0.latvijasradio.lv:8016/;", scheduler)
                        }
                    }
                }
                event.reply().withEphemeral(true)
            } else {
                event.reply().withEphemeral(true)
            }
        }?.subscribe()

        login?.onDisconnect()?.block()
    }
}
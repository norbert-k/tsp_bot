package lv.tsp.discordbot.audio

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame
import discord4j.voice.AudioProvider
import io.netty.buffer.ByteBuf
import java.nio.ByteBuffer

class LavaPlayerAudioProvider(private val player: AudioPlayer) :
    AudioProvider(ByteBuffer.allocate(StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize())) {

    private val frame = MutableAudioFrame();

    init {
        frame.setBuffer(buffer)
    }

    override fun provide(): Boolean {
        val didProvide = player.provide(frame);
        if (didProvide) {
            (buffer as ByteBuffer).flip();
        }
        return didProvide;
    }

}
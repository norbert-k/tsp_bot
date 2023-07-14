package lv.tsp.discordbot.audio

import com.sedmelluq.discord.lavaplayer.filter.AudioFilter
import com.sedmelluq.discord.lavaplayer.filter.PcmFilterFactory
import com.sedmelluq.discord.lavaplayer.filter.equalizer.Equalizer
import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerConfiguration
import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack

class TrackScheduler(private val player: AudioPlayer) : AudioLoadResultHandler {
    override fun trackLoaded(track: AudioTrack?) {
        player.playTrack(track)
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {
        // TODO: Implement playlist loading
    }

    override fun noMatches() {
        // TODO: Implement no matches
    }

    override fun loadFailed(exception: FriendlyException?) {
        // TODO: Implement load failed
    }
}
package lv.tsp.discordbot
import com.beust.jcommander.Parameter;

open class Configuration(
    @Parameter(names = ["--discord-token"], description = "Discord bot token", required = true)
    open var discordToken: String? = null,

    @Parameter(names = ["--guild-id"], description = "Discord guild id", required = true)
    open var guildId: Long? = null,
)

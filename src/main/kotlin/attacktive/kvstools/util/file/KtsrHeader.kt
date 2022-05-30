package attacktive.kvstools.util.file

import attacktive.kvstools.extension.formatHex
import attacktive.kvstools.extension.toUByteArray
import attacktive.kvstools.hexFormatter
import attacktive.kvstools.ubyteArrayOfNulls
import java.text.DecimalFormat

/**
 * The metadata is taken from http://wiki.xentax.com/index.php/Koei_Tecmo_Audio_SRSA_SRST
 *
 * However, the first null seems to be of 2 bytes not 1.
 */
@OptIn(ExperimentalUnsignedTypes::class)
data class KtsrHeader(
	val signature: String = DEFAULT_SIGNATURE,
	val chunkType: UByteArray = DEFAULT_CHUNK_TYPE,
	val version: UByte = 1U,
	/**
	 * 0x01 - PC
	 * 0x03 - PS Vita
	 * 0x04 - Switch
	 */
	val platform: UByte,
	val game: Game,
	val fileSize: UInt
) {
	companion object {
		const val DEFAULT_SIGNATURE: String = "KTSR"
		const val NUMBER_OF_BYTES: Int = 96
		val DEFAULT_CHUNK_TYPE: UByteArray = ubyteArrayOf(0x02U, 0x94U, 0xddU, 0xfcU)
	}

	override fun toString(): String {
		return """
			signature: $signature
			chunkType: ${hexFormatter().formatHex(chunkType)}
			version: $version
			platform: $platform
			fileSize: ${DecimalFormat("#,###").format(fileSize.toLong())} bytes
			gameId: ${hexFormatter().formatHex(game.id)}
			gameEntries: ${hexFormatter().formatHex(game.entries)}
		""".trimIndent()
	}

	fun toUBytes(): UByteArray {
		return (
			signature.toByteArray().toUByteArray() +
			chunkType +
			version +
			ubyteArrayOfNulls(2) +
			platform +
			game.id +
			ubyteArrayOfNulls(8) +
			fileSize.toUByteArray() +
			fileSize.toUByteArray() +
			ubyteArrayOfNulls(32) +
			game.entries
		)
	}

	enum class Game(val id: UByteArray, val entries: UByteArray) {
		ATTACK_ON_TITAN2(
			ubyteArrayOf(0x36U, 0x0eU, 0xf4U, 0x05U),
			ubyteArrayOf(
				0x09U, 0xd4U, 0xf4U, 0x15U,
				0x20U, 0xe9U, 0x88U, 0x00U,
				0xcaU, 0xabU, 0xa8U, 0xa9U,
				0x20U, 0x00U, 0x00U, 0x00U,
				0xf7U, 0xe8U, 0x88U, 0x00U
			) + ubyteArrayOfNulls(12)
		),
		ATELIER_RYZA1(
			ubyteArrayOf(0x4eU, 0xc5U, 0xe8U, 0xc5U),
			ubyteArrayOf(
				0x09U, 0xd4U, 0xf4U, 0x15U,
				0xf0U, 0x2cU, 0x1bU, 0x00U,
				0x00U, 0x70U, 0x4cU, 0x41U,
				0x20U, 0x00U, 0x00U, 0x00U,
				0xc3U, 0x2cU, 0x1bU, 0x00U
			) + ubyteArrayOfNulls(12)
		);

		companion object {
			fun byId(id: UByteArray): Game = Game.values().first { it.id.contentEquals(id) }
		}
	}
}

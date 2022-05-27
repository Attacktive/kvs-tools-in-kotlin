package attacktive.kvstools.util.file

import attacktive.kvstools.extension.toByteArray
import attacktive.kvstools.util.byteArrayOfNulls

/**
 * The metadata is taken from http://wiki.xentax.com/index.php/Koei_Tecmo_Audio_SRSA_SRST
 *
 * However, the first null seems to be of 2 bytes not 1.
 */
data class KtsrHeader(
	val magic: String = "KTSR",
	val chunkType: ByteArray = byteArrayOf(0x02, 0x94.toByte(), 0xdd.toByte(), 0xfc.toByte()),
	val version: Byte = 1,
	/**
	 * 0x01 - PC
	 * 0x03 - PS Vita
	 * 0x04 - Switch
	 */
	val platform: Byte,
	val game: Game,
	val fileSize: UInt
) {
	fun toBytes(): ByteArray {
		return (
			magic.toByteArray() +
			chunkType +
			version +
			byteArrayOfNulls(2) +
			platform +
			game.id +
			byteArrayOfNulls(8) +
			fileSize.toByteArray() +
			fileSize.toByteArray() +
			byteArrayOfNulls(32) +
			game.entries
		)
	}

	enum class Game(val id: ByteArray, val entries: ByteArray) {
		ATTACK_ON_TITAN2(
			byteArrayOf(0x36, 0x0e, 0xf4.toByte(), 0x05),
			byteArrayOf(
				0x09, 0xd4.toByte(), 0xf4.toByte(), 0x15,
				0x20, 0xe9.toByte(), 0x88.toByte(), 0x00,
				0xca.toByte(), 0xab.toByte(), 0xa8.toByte(), 0xa9.toByte(),
				0x20, 0x00, 0x00, 0x00,
				0xf7.toByte(), 0xe8.toByte(), 0x88.toByte(), 0x00
			) + byteArrayOfNulls(12)
		),
		ATELIER_RYZA1(
			byteArrayOf(0x4e, 0xc5.toByte(), 0xe8.toByte(), 0xc5.toByte()),
			byteArrayOf(
				0x09, 0xd4.toByte(), 0xf4.toByte(), 0x15,
				0xf0.toByte(), 0x2c, 0x1b, 0x00,
				0x00, 0x70, 0x4c, 0x41,
				0x20, 0x00, 0x00, 0x00,
				0xc3.toByte(), 0x2c, 0x1b, 0x00
			) + byteArrayOfNulls(12)
		);
	}
}
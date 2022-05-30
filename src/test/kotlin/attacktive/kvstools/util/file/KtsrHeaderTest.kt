package attacktive.kvstools.util.file

import attacktive.kvstools.extension.toByteArray
import attacktive.kvstools.byteArrayOfNulls
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class KtsrHeaderTest {
	@Test
	fun testKtsrHeader() {
		val signature = "KTSR"
		val chunkType: ByteArray = byteArrayOf(0x02, 0x94.toByte(), 0xdd.toByte(), 0xfc.toByte())
		val version = 2.toByte()
		val platform = 1.toByte()
		val game = KtsrHeader.Game.ATTACK_ON_TITAN2
		val fileSize = 65535U

		val ktsrHeader = KtsrHeader(
			version = version,
			platform = platform,
			game = game,
			fileSize = fileSize
		)

		val headerBytes = ktsrHeader.toUBytes()
		val actualSignature = headerBytes.copyOfRange(0, 4)
		val actualChunkType = headerBytes.copyOfRange(4, 8)
		val actualVersion = headerBytes[8]
		val actualNulls1 = headerBytes.copyOfRange(9, 11)
		val actualPlatform = headerBytes[11]
		val actualGameId = headerBytes.copyOfRange(12, 16)
		val actualNulls2 = headerBytes.copyOfRange(16, 24)
		val actualFileSize1 = headerBytes.copyOfRange(24, 28)
		val actualFileSize2 = headerBytes.copyOfRange(28, 32)
		val actualNulls3 = headerBytes.copyOfRange(32, 64)
		val actualGameEntries = headerBytes.copyOfRange(64, 96)

		assertContentEquals(signature.toByteArray(), actualSignature)
		assertContentEquals(chunkType, actualChunkType)
		assertEquals(version, actualVersion)
		assertContentEquals(byteArrayOfNulls(2), actualNulls1)
		assertEquals(platform, actualPlatform)
		assertContentEquals(game.id, actualGameId)
		assertContentEquals(byteArrayOfNulls(8), actualNulls2)
		assertContentEquals(fileSize.toByteArray(), actualFileSize1)
		assertContentEquals(fileSize.toByteArray(), actualFileSize2)
		assertContentEquals(byteArrayOfNulls(32), actualNulls3)
		assertContentEquals(game.entries, actualGameEntries)
	}
}

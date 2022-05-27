package attacktive.kvstools.util.file

import attacktive.kvstools.hexFormatter

object KtsrHeaderValidator {
	private val expectedPlatforms = byteArrayOf(1, 3, 4)

	fun validate(
		magic: String,
		chunkType: ByteArray,
		platform: Byte,
		gameId: ByteArray,
		fileSize1: UInt,
		fileSize2: UInt,
		gameEntries: ByteArray
	) {
		if (magic != KtsrHeader.DEFAULT_MAGIC) {
			throw InvalidMagicException(magic)
		}
		if (!chunkType.contentEquals(KtsrHeader.DEFAULT_CHUNK_TYPE)) {
			throw InvalidChunkTypeException(chunkType)
		}
		if (!expectedPlatforms.contains(platform)) {
			throw InvalidPlatformException(platform)
		}
		if (fileSize1 != fileSize2) {
			throw InconsistentFileSizeException(fileSize1, fileSize2)
		}

		try {
			val game = KtsrHeader.Game.byId(gameId)
			if (!gameEntries.contentEquals(game.entries)) {
				throw InvalidGameEntriesException()
			}
		} catch (noSuchElementException: NoSuchElementException) {
			throw InvalidGameIdException(gameId, noSuchElementException)
		}
	}

	open class KtsrHeaderParseException(message: String, cause: Throwable? = null): RuntimeException(message, cause)

	class InvalidMagicException(magic: String, cause: Throwable? = null):
		KtsrHeaderParseException(String.format("Unexpected file signature: %s", magic), cause)

	class InvalidChunkTypeException(chunkType: ByteArray, cause: Throwable? = null):
		KtsrHeaderParseException(String.format("Unexpected chunk type: %s", hexFormatter().formatHex(chunkType)), cause)

	class InvalidPlatformException(platform: Byte, cause: Throwable? = null):
		KtsrHeaderParseException(String.format("Unexpected platform: %d", platform), cause)

	class InconsistentFileSizeException(fileSize1: UInt, fileSize2: UInt, cause: Throwable? = null):
		KtsrHeaderParseException(String.format("The file size data is inconsistent: %d vs %d", fileSize1, fileSize2), cause)

	class InvalidGameIdException(gameId: ByteArray, cause: Throwable? = null):
		KtsrHeaderParseException(String.format("Unexpected game ID: %s", hexFormatter().formatHex(gameId)), cause)

	class InvalidGameEntriesException(cause: Throwable? = null):
		KtsrHeaderParseException("The game entries data does not agree with the id", cause)
}

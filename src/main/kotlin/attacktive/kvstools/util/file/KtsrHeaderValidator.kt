package attacktive.kvstools.util.file

import attacktive.kvstools.extension.formatHex
import attacktive.kvstools.hexFormatter

@OptIn(ExperimentalUnsignedTypes::class)
object KtsrHeaderValidator {
	private val expectedPlatforms = ubyteArrayOf(1U, 3U, 4U)

	fun validate(
		signature: String,
		chunkType: UByteArray,
		platform: UByte,
		gameId: UByteArray,
		fileSize1: UInt,
		fileSize2: UInt,
		gameEntries: UByteArray
	) {
		if (signature != KtsrHeader.DEFAULT_SIGNATURE) {
			throw InvalidSignatureException(signature)
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

	class InvalidSignatureException(signature: String, cause: Throwable? = null):
		KtsrHeaderParseException(String.format("Unexpected file signature: %s", signature), cause)

	class InvalidChunkTypeException(chunkType: UByteArray, cause: Throwable? = null):
		KtsrHeaderParseException(String.format("Unexpected chunk type: %s", hexFormatter().formatHex(chunkType)), cause)

	class InvalidPlatformException(platform: UByte, cause: Throwable? = null):
		KtsrHeaderParseException(String.format("Unexpected platform: %d", platform), cause)

	class InconsistentFileSizeException(fileSize1: UInt, fileSize2: UInt, cause: Throwable? = null):
		KtsrHeaderParseException(String.format("The file size data is inconsistent: %d vs %d", fileSize1, fileSize2), cause)

	class InvalidGameIdException(gameId: UByteArray, cause: Throwable? = null):
		KtsrHeaderParseException(String.format("Unexpected game ID: %s", hexFormatter().formatHex(gameId)), cause)

	class InvalidGameEntriesException(cause: Throwable? = null):
		KtsrHeaderParseException("The game entries data does not agree with the id", cause)
}

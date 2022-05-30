package attacktive.kvstools.util.file

import attacktive.kvstools.extension.toUInt
import java.io.File

@OptIn(ExperimentalUnsignedTypes::class)
object KtsrHeaderReader {
	fun readHeader(pathToFile: String): KtsrHeader {
		println("Trying to open $pathToFile")
		File(pathToFile).inputStream().use {
			val bytes = it.readNBytes(KtsrHeader.NUMBER_OF_BYTES).toUByteArray()
			val ktsrHeader = parseKtsrHeader(bytes)

			println("ktsrHeader: $ktsrHeader")
			return ktsrHeader
		}
	}

	private fun parseKtsrHeader(bytes: UByteArray, toValidate: Boolean = true): KtsrHeader {
		val signatureBytes = bytes.copyOfRange(0, 4)
		val chunkTypeBytes = bytes.copyOfRange(4, 8)
		val versionBytes = bytes[8]
		val platformBytes = bytes[11]
		val gameIdBytes = bytes.copyOfRange(12, 16)
		val fileSize1Bytes = bytes.copyOfRange(24, 28)
		val fileSize2Bytes = bytes.copyOfRange(28, 32)
		val gameEntriesBytes = bytes.copyOfRange(64, 96)

		val signature = String(signatureBytes.toByteArray())
		val fileSize1 = fileSize1Bytes.toUInt()
		val fileSize2 = fileSize2Bytes.toUInt()

		if (toValidate) {
			KtsrHeaderValidator.validate(signature, chunkTypeBytes, platformBytes, gameIdBytes, fileSize1, fileSize2, gameEntriesBytes)
		}

		return KtsrHeader(signature, chunkTypeBytes, versionBytes, platformBytes, KtsrHeader.Game.byId(gameIdBytes), fileSize1)
	}
}

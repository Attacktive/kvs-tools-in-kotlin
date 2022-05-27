package attacktive.kvstools.util.file

import attacktive.kvstools.extension.toUInt
import java.io.File

object KtsrHeaderReader {
	fun readHeader(pathToFile: String): KtsrHeader {
		println("Trying to open $pathToFile")
		File(pathToFile).inputStream().use {
			val bytes = it.readNBytes(96)
			val ktsrHeader = parseKtsrHeader(bytes)

			println(ktsrHeader)
			return ktsrHeader
		}
	}

	private fun parseKtsrHeader(bytes: ByteArray, toValidate: Boolean = true): KtsrHeader {
		val magicBytes = bytes.copyOfRange(0, 4)
		val chunkTypeBytes = bytes.copyOfRange(4, 8)
		val versionBytes = bytes[8]
		val platformBytes = bytes[11]
		val gameIdBytes = bytes.copyOfRange(12, 16)
		val fileSize1Bytes = bytes.copyOfRange(24, 28)
		val fileSize2Bytes = bytes.copyOfRange(28, 32)
		val gameEntriesBytes = bytes.copyOfRange(64, 96)

		val magic = String(magicBytes)
		val fileSize1 = fileSize1Bytes.toUInt()
		val fileSize2 = fileSize2Bytes.toUInt()

		if (toValidate) {
			KtsrHeaderValidator.validate(magic, chunkTypeBytes, platformBytes, gameIdBytes, fileSize1, fileSize2, gameEntriesBytes)
		}

		return KtsrHeader(magic, chunkTypeBytes, versionBytes, platformBytes, KtsrHeader.Game.byId(gameIdBytes), fileSize1)
	}
}

package attacktive.kvstools.util.file

import attacktive.kvstools.extension.toUInt
import java.io.File

@OptIn(ExperimentalUnsignedTypes::class)
object SourceHeaderReader {
	fun readHeader(pathToFile: String): MutableList<ByteArray> {
		println("Trying to open $pathToFile")

		val archiveFile = File(pathToFile)
		val archiveFileSize = archiveFile.length()
		val targetFiles: MutableList<ByteArray> = mutableListOf()
		archiveFile.inputStream().use {
			var index = KtsrHeader.NUMBER_OF_BYTES
			do {
				it.channel.position(index.toLong())
				val bytesForSourceHeader = it.readNBytes(SourceHeader.MAXIMUM_SIZE_OF_HEADER)
				index += SourceHeader.MAXIMUM_SIZE_OF_HEADER

				val sourceHeader = parseSourceHeader(bytesForSourceHeader.toUByteArray())

				it.channel.position(index.toLong())
				val bytesForContent = it.readNBytes(sourceHeader.fileSize.toInt())

				targetFiles.add(bytesForContent)
			} while (index <= archiveFileSize)

			return targetFiles
		}
	}

	private fun parseSourceHeader(bytes: UByteArray): SourceHeader {
		val signatureBytes = bytes.copyOfRange(0, 4)
		val fileSizeBytes = bytes.copyOfRange(4, 8)
		val unknownBytes = bytes.copyOfRange(8, 12)

		val signature = try {
			SourceHeader.Signature.bySignatureBytes(signatureBytes)
		} catch (noSuchElementException: NoSuchElementException) {
			throw InvalidSignatureException(String(bytes.toByteArray()), noSuchElementException)
		}

		val fileSize = fileSizeBytes.toUInt()

		return SourceHeader(signature, fileSize, unknownBytes)
	}

	open class SourceHeaderParseException(message: String, cause: Throwable? = null): RuntimeException(message, cause)

	class InvalidSignatureException(signature: String, cause: Throwable? = null):
		SourceHeaderParseException(String.format("Unexpected file signature: %s", signature), cause)
}

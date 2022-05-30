package attacktive.kvstools.util.file

import attacktive.kvstools.extension.formatHex
import attacktive.kvstools.extension.toUByteArray
import attacktive.kvstools.hexFormatter
import attacktive.kvstools.ubyteArrayOfNulls

/**
 * @param unknown In Atelier Ryza 1, the very first one is:
 *
 * `3155 0400 0300 0000`,
 *
 * and the next one:
 *
 * `ff93 0b00 0300 0000`
 */
@OptIn(ExperimentalUnsignedTypes::class)
data class SourceHeader(val signature: Signature, val fileSize: UInt, val unknown: UByteArray) {
	companion object {
		const val MAXIMUM_SIZE_OF_HEADER = 32
	}

	override fun toString(): String {
		return """
			signature: $signature
			fileSize: $fileSize
			unknown: ${hexFormatter().formatHex(unknown)}
		""".trimIndent()
	}

	fun toBytes(): UByteArray {
		return signature.toByteArray().toUByteArray() +
			fileSize.toUByteArray() +
			unknown +
			ubyteArrayOfNulls(16)
	}

	enum class Signature(val value: String, val extension: String) {
		KOVS("KOVS", "kvs"),
		KTSS("KTSS", "kns");

		companion object {
			fun bySignatureBytes(bytes: UByteArray): Signature {
				return Signature.values().first { signature -> signature.toByteArray().toUByteArray().contentEquals(bytes) }
			}
		}

		fun toByteArray() = value.toByteArray()
	}
}

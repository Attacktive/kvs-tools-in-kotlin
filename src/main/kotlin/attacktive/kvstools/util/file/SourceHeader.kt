package attacktive.kvstools.util.file

import attacktive.kvstools.extension.toByteArray
import attacktive.kvstools.byteArrayOfNulls

/**
 * The metadata is taken from http://wiki.xentax.com/index.php/Koei_Tecmo_Audio_SRSA_SRST
 */
data class SourceHeader(
	val magic: Magic,
	val fileSize: UInt
) {
	fun toBytes(): ByteArray {
		return (
			magic.toByteArray() +
			byteArrayOfNulls(4) +
			fileSize.toByteArray() +
			byteArrayOfNulls(4)
		)
	}

	enum class Magic(val value: String, val extension: String) {
		KOVS("KOVS", "kvs"),
		KTSS("KTSS", "kns");

		fun toByteArray() = value.toByteArray()
	}
}

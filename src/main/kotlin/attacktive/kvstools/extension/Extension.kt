@file:OptIn(ExperimentalUnsignedTypes::class)

package attacktive.kvstools.extension

import java.nio.ByteBuffer
import java.util.HexFormat

fun UInt.toUByteArray(): UByteArray {
	val buffer = ByteBuffer.allocate(UInt.SIZE_BYTES)
	buffer.putInt(this.toInt())

	return buffer.array().toUByteArray()
}

fun UByteArray.toUInt(): UInt {
	return ((this[0].toUInt() and 0xffU) shl 24) or
		((this[1].toUInt() and 0xffU) shl 16) or
		((this[2].toUInt() and 0xffU) shl 8) or
		(this[3].toUInt() and 0xffU)
}

fun HexFormat.formatHex(bytes: UByteArray): String {
	return formatHex(bytes.toByteArray())
}

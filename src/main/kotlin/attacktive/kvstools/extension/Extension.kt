package attacktive.kvstools.extension

import java.nio.ByteBuffer

fun UInt.toByteArray(): ByteArray {
	val buffer = ByteBuffer.allocate(UInt.SIZE_BYTES)
	buffer.putInt(this.toInt())

	return buffer.array()
}

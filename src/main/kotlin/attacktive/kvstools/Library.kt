package attacktive.kvstools.util

fun byteArrayOfNulls(numberOfNulls: Int): ByteArray {
	var byteArray = byteArrayOf()
	repeat(numberOfNulls) {
		byteArray += 0x00
	}

	return byteArray
}

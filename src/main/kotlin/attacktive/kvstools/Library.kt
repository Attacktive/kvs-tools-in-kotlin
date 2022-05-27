package attacktive.kvstools

import java.util.HexFormat

fun hexFormatter(): HexFormat {
	return HexFormat.ofDelimiter(" ").withPrefix("0x")
}

fun byteArrayOfNulls(numberOfNulls: Int): ByteArray {
	var byteArray = byteArrayOf()
	repeat(numberOfNulls) {
		byteArray += 0x00
	}

	return byteArray
}

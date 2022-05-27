package attacktive.kvstools.util.file

import attacktive.kvstools.extension.toByteArray
import attacktive.kvstools.byteArrayOfNulls
import kotlin.test.Test
import kotlin.test.assertContentEquals

class SourceHeaderTest {
	@Test
	fun testHeader() {
		val magic = SourceHeader.Magic.KOVS
		val fileSize = 65535U
		val nulls = byteArrayOfNulls(4)

		val sourceHeader = SourceHeader(magic, fileSize)
		val headerBytes = sourceHeader.toBytes()
		val actualMagic = headerBytes.copyOfRange(0, 4)
		val actualFirstNulls = headerBytes.copyOfRange(4, 8)
		val actualSize = headerBytes.copyOfRange(8, 12)
		val actualLastNulls = headerBytes.copyOfRange(12, 16)

		assertContentEquals(magic.toByteArray(), actualMagic)
		assertContentEquals(nulls, actualFirstNulls)
		assertContentEquals(fileSize.toByteArray(), actualSize)
		assertContentEquals(nulls, actualLastNulls)
	}
}

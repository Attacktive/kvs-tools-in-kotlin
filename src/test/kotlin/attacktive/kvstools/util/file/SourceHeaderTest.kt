package attacktive.kvstools.util.file

import attacktive.kvstools.extension.toByteArray
import attacktive.kvstools.byteArrayOfNulls
import kotlin.test.Test
import kotlin.test.assertContentEquals

class SourceHeaderTest {
	@Test
	fun testHeader() {
		val signature = SourceHeader.Signature.KOVS
		val fileSize = 65535U
		val nulls = byteArrayOfNulls(4)

		val sourceHeader = SourceHeader(signature, fileSize)
		val headerBytes = sourceHeader.toBytes()
		val actualSignature = headerBytes.copyOfRange(0, 4)
		val actualFirstNulls = headerBytes.copyOfRange(4, 8)
		val actualSize = headerBytes.copyOfRange(8, 12)
		val actualLastNulls = headerBytes.copyOfRange(12, 16)

		assertContentEquals(signature.toByteArray(), actualSignature)
		assertContentEquals(nulls, actualFirstNulls)
		assertContentEquals(fileSize.toByteArray(), actualSize)
		assertContentEquals(nulls, actualLastNulls)
	}
}

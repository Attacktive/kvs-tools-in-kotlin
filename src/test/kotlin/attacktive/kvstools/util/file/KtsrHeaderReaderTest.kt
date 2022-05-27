package attacktive.kvstools.util.file

import kotlin.test.Test

class KtsrHeaderReaderTest {
	@Test
	fun testReadHeader() {
		val url = KtsrHeaderReaderTest::class.java.classLoader.getResource("sample/header-only.ktsl2stbin")!!
		val ktsrHeader = KtsrHeaderReader.readHeader(url.path)

		println(ktsrHeader)
	}
}

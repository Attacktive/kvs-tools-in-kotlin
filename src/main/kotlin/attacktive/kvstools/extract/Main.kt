package attacktive.kvstools.extract

import attacktive.kvstools.util.ArgumentHandler
import attacktive.kvstools.util.file.KtsrHeaderReader

fun main(vararg args: String) {
	val argument = ArgumentHandler.handle(args = args, "Needs a .ktsl2stbin file as an argument.")

	KtsrHeaderReader.readHeader(argument)
}

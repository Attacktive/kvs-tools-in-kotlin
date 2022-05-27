package attacktive.kvstools.archive

import attacktive.kvstools.util.ArgumentHandler

fun main(vararg args: String) {
	val argument = ArgumentHandler.handle(args = args, "Needs a directory with .kvs files as an argument.")

	println(argument)
}

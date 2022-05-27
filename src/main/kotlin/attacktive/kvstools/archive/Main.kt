package attacktive.kvstools.archive

import attacktive.kvstools.util.InputHandler

fun main(vararg args: String) {
	val argument = InputHandler.handle(args = args, "Needs a directory with .kvs files as an argument.")

	println(argument)
}

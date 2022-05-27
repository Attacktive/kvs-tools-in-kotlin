package attacktive.kvstools.extract

import attacktive.kvstools.util.InputHandler

fun main(vararg args: String) {
	val argument = InputHandler.handle(args = args, "Needs a .ktsl2stbin file as an argument.")

	println(argument)
}

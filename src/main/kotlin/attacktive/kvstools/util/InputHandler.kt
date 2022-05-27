package attacktive.kvstools.util

import kotlin.system.exitProcess

object InputHandler {
	fun handle(vararg args: String, errorMessage: String): String {
		if (args.isEmpty()) {
			System.err.println(errorMessage)
			exitProcess(1)
		}

		val argument = args[0]
		if (args.size > 1) {
			println("You provided more than one argument; other than \"$argument\" will be ignored.")
		}

		return argument
	}
}

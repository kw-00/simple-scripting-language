import java.util.*;
import java.util.function.*;
import java.io.*;

public class Data {
    public static final boolean doLog = true;
	public static void log(String message) {
		if (doLog) {
			System.out.println("LOG: " + message);
		}
	}

    /* Call Result */
    public static class Result {
		/* 
			Code of 0 means success. In case of success, value
			can means return value.

			Positive codes mean an error occurred. In that case, value is
			the error's name.
		*/
		public final String value;
		public final int code;

		private Result(String value, int code) {
			this.value = value;
			this.code = code;
            Data.lastExitCode = code;
		}

		public static Result success(String value) {
			return new Result(value, 0);
		}

		public static Result error(int code, String error) {
			return new Result(error, code);
		}
	}

    public static int lastExitCode = 0;

	/* Errors */
	public static class Errors {
		public static final Supplier<Result> NoArgs = () -> Result.error(1, "no_args");
        public static final Supplier<Result> VarUndefined = () -> Result.error(2, "var_undefined");
		public static final Supplier<Result> IOException = () -> Result.error(3, "io_exception");
	}

	/* Variables */
    public static Map<String, String> variables = new HashMap<>();

	/* Commands */
	public static class Commands {
		/* = (assign) */
		public static Result assign(String name, List<String> args) {
			var result = new StringBuilder();
			for (var arg : args) {
				result.append(arg).append(" ");
			}
			var finalResult = result.toString().strip();
			Data.variables.put(name, finalResult);
			return Data.Result.success(finalResult);
		}

		/* echo */
		public static Result echo(List<String> args) {
			var result = new StringBuilder();
			for (var token : args) {
				result.append(token).append(" ");
			}
			result.append("\n");
			return Data.Result.success(result.toString());			
		}

		public static Result echo() {
			return Data.Result.success("\n");
		}

		/* cat */
		public static Result cat(List<String> args) {
			boolean errorOccurred = false;
			var result = new StringBuilder();
			for (var token : args) {
				var file = new File(token);
				try (java.util.Scanner scanner = new java.util.Scanner(file)){
					while (scanner.hasNextLine()) {
						result.append(scanner.nextLine()).append("\n");
					}
					result.append("\n");
				} catch (IOException e) {
					errorOccurred = true;
					break;
				}
			}
			if (!errorOccurred) {
				return Data.Result.success(result.toString());
			} else {
				return Data.Errors.IOException.get();
			}
		}

		public static Result cat() {
			return Data.Errors.NoArgs.get();
		}

		/* wc */
		public static Result wc(List<String> args) {
			boolean errorOccurred = false;
			var result = new StringBuilder();
			for (var token : args) {
				var count = 0;
				var file = new File(token);
				try (java.util.Scanner scanner = new java.util.Scanner(file)) {
					while (scanner.hasNextLine()) {
						scanner.nextLine();
						count++;
					}
					result.append(count).append(":");
				} catch (IOException e) {
					errorOccurred = true;
					break;
				}
			}
			if (!errorOccurred) {
				return Data.Result.success(result.delete(result.length() - 1, result.length()).append("\n").toString());
			} else {
				return Data.Errors.IOException.get();
			}
		}

		public static Result wc() {
			return Data.Errors.NoArgs.get();
		}
	}

	public static class Redirection {
		public static Result redirectWrite(Result commandResult, List<String> filePaths) {
			boolean errorOccurred = false;
			for (var path : filePaths) {
				try (PrintWriter out = new PrintWriter(path)) {
					out.print(commandResult.value);
					out.flush();
				} catch (IOException e) {
					errorOccurred = true;
				}
			}
			if (!errorOccurred) {
				return Data.Result.success("");
			} else {
				return Data.Errors.IOException.get();
			}
		}

		public static Result redirectAppend(Result commandResult, List<String> filePaths) {
			boolean errorOccurred = false;
			for (var path : filePaths) {
				try (PrintWriter out = new PrintWriter(new FileWriter(path, true))) {
					out.print(commandResult.value);
					out.flush();
				} catch (IOException e) {
					errorOccurred = true;
				}
			}
			if (!errorOccurred) {
				return Data.Result.success("");
			} else {
				return Data.Errors.IOException.get();
			}
		}
	}
}
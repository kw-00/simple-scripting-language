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
            Data.variables.put("?", "" + code);
		}

		public static Result success(String value) {
			return new Result(value, 0);
		}

		public static Result error(int code, String error) {
			Data.variables.put("ERROR_MESSAGE", error);
			return new Result("", code);
		}
	}

	/* Errors */
	public static class Errors {
		public static final Supplier<Result> NoArgs = () -> Result.error(1, "no_args\n");
        public static final Supplier<Result> VarUndefined = () -> Result.error(2, "var_undefined\n");
		public static final Supplier<Result> IOException = () -> Result.error(3, "io_exception\n");
	}

	/* Variables */
    public static Map<String, String> variables = new HashMap<>();

	/* Operators */
	public static class Operators {
		/* = (assign) */
		public static Result assign(String name, String value) {
			Data.variables.put(name, value);
			return Data.Result.success("");
		}

		/* $variable */
		public static String getVariable(String name) {
			var result = Data.variables.get(name);
			if (result == null) {
				return "";
			}
			return result;
		}
	}
	/* Commands */
	public static Map<String, BiFunction<Result, List<String>, Result>> commands = new HashMap<>();
	/* echo */
	static {
		Data.commands.put("echo", (in, args) -> {
			if (in == null && args == null) {
				return Data.Result.success("\n");
			}
			var result = new StringBuilder();
			if (in != null) {
				result.append(in.value.strip()).append(" ");
			}

			if (args != null) {
				for (var token : args) {
					result.append(token.strip()).append(" ");
				}
			}
			result.append("\n");
			return Data.Result.success(result.toString());	
		});

		/* cat */
		Data.commands.put("cat", (in, args) -> {
			if (in == null && args == null) {
				return Data.Errors.NoArgs.get();
			}
			var input = new ArrayList<String>();

			if (in != null) input.add(in.value);
			if (args != null) input.addAll(args);

			boolean errorOccurred = false;
			var result = new StringBuilder();
			for (var token : input) {
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
		});

		/* wc */
		Data.commands.put("wc", (in, args) -> {
			if (in == null && args == null) {
				return Data.Errors.NoArgs.get();
			}
			var input = new ArrayList<String>();

			if (in != null) input.add(in.value);
			if (args != null) input.addAll(args);

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
		});
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
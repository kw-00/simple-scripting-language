import java.util.*;
import java.util.function.*;


public class State {
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
            State.lastExitCode = code;
		}

		public static Result success(String value) {
			return new Result(value, 0);
		}

		public static Result error(int code, String error) {
			return new Result(error, code);
		}
	}

    public static int lastExitCode = 0;

	public static class Errors {
		public static final Supplier<Result> NoArgs = () -> Result.error(1, "no_args");
	}
}
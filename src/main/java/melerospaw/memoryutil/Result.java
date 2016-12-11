package melerospaw.memoryutil;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import melerospaw.memoryutil.validation.ValidationInfoInterface;
import melerospaw.memoryutil.validation.ValidationUtils;

/**
 * <p>Contains the result of  calling a method from {@link MemoryUtils}.</p>
 * <p><b>Fields:</b></p>
 * <ul>
 * <li>{@code successful}: tells whether the method call has been successful or not. Can be obtained
 * by calling {@link #isSuccessful()}.</li>
 * <li>{@code result}: the object resulting from the method call. Can be obtained by calling
 * {@link #getResult()}. If you save something, you'll get the {@code File} where that something has
 * been saved. If you retrieve something, you'll get that something.</li>
 * <li>{@code message}: a {@code String} containing the reason why the method called was not
 * successful. Can be obtained by calling {@link #getMessage()}.</li>
 * </ul>
 *
 * @param <T> The type of the object that will result from the method call. If the call results
 *            in nothing, no {@code T} parameter needs to be specified. For example, from method
 *            {@link MemoryUtils#deleteFile(Path, boolean)} nothing is returned.
 */
public class Result<T> {

    public static final String TAG = MemoryUtils.class.getSimpleName();
    private boolean successful;
    private T result;
    private String message;


    /**
     * Tells whether the method call has been successuful or not. This is the most
     * straightforward way to know, rather than calling {@code if (result.getResult() != null},
     * since sometimes nothing is returned from the latter method.
     *
     * @return {@code true} if the method call has been successful.
     */
    public boolean isSuccessful() {
        return successful;
    }

    void setSuccessful(boolean successful) {
        this.successful = successful;
    }


    /**
     * Returns the result of the call to a {@link MemoryUtils}' method.
     *
     * @return <ul>
     * <li>When you save something, this method returns a {@code File} object pointing to
     * the file where that something was called.</li>
     * <li>When you want to retrieve a text or an object, this method returns that text or
     * the object casted to its class type.</li>
     * <li>When the method you call results in nothing, there is no need to instantiate
     * {@code Result} using the diamond operator and calls to this method returns
     * {@code null}.</li>
     * <li>When the method didn't execute successfully, this method returns {@code null}.</li>
     * </ul>
     */
    public T getResult() {
        return result;
    }

    void setResult(T result) {
        this.result = result;
    }


    /**
     * When an error occurs during the execution of the {@link MemoryUtils} method, this method
     * returns a{@code String} with the error outcome. Errors and their stack traces are also
     * displayed in the logcat.
     *
     * @return A String containing the error.
     */
    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    /**
     * Creates a {@link Result} object with the object resulting from a call to a
     * {@code MemoryUtils}' method specified. It will format the message and log it.
     *
     * @param object           The object resulting from the call to the {@code MemoryUtils}'
     *                         method.
     * @param whatYouWantedToDo          The error/success message resulting from the call to the
     *                         {@code MemoryUtils}' method.
     * @param success          Pass {@code true} if the call to the {@code MemoryUtils}' method was
     *                         successful or else {@code false}.
     * @param exception        The exception resulting from the call to the {@code MemoryUtils}'
     *                         method Pass {@code null} if no exception was thrown.
     * @param formatParameters The parameters necessary to format the message. Pass empty in place
     *                         of the parameters that won't be specified.
     * @param <T>              The type of object resulting from the call to the
     *                         {@code MemoryUtils}' method.
     * @return The {@code Result<T>} object created.
     */
    static <T> Result<T> createResult(@Nullable T object, @NonNull String whatYouWantedToDo,
                                      boolean success, @Nullable Exception exception,
                                      String... formatParameters) {

        String formattedMessage = StringUtil.format(whatYouWantedToDo, formatParameters);

        Result<T> result = new Result<>();
        result.setResult(object);
        result.setSuccessful(success);
        result.setMessage(formattedMessage);

        log(TAG, formattedMessage, success);

        if (exception != null) {
            exception.printStackTrace();
        }

        return result;
    }


    /**
     * Creates a {@link Result} object for an unsuccessful method call without logging no exception.
     * It will format the reason and log it.
     *
     * @param reason          The error/success reason resulting from the call to the
     *                         {@code MemoryUtils}' method.
     *                         method Pass {@code null} if no exception was thrown.
     * @param formatParameters The parameters necessary to format the reason. Pass empty in place
     *                         of the parameters that won't be specified.
     * @param <T>              The type of object resulting from the call to the
     *                         {@code MemoryUtils}' method.
     * @return The {@code Result<T>} object created.
     */
    static <T> Result<T> createNoExceptionResult(@NonNull String reason,
                                                 String... formatParameters) {

        return createResult(null, reason, false, null, formatParameters);
    }

    /**
     * Creates a {@link Result} object for an unsuccessful method call without logging no exception.
     * It will format the message and log it.
     *
     * @param whatYouWantedToDo          The error/success message resulting from the call to the
     *                         {@code MemoryUtils}' method.
     *                         method Pass {@code null} if no exception was thrown.
     * @param formatParameters The parameters necessary to format the message. Pass empty in place
     *                         of the parameters that won't be specified.
     * @param <T>              The type of object resulting from the call to the
     *                         {@code MemoryUtils}' method.
     * @return The {@code Result<T>} object created.
     */
    static <T> Result<T> createSuccessfulResult(T result, @NonNull String whatYouWantedToDo,
                                                 String... formatParameters) {

        return createResult(result, whatYouWantedToDo, true, null, formatParameters);
    }

    /**
     * Creates a {@link Result} object for an unsuccessful method call without logging no exception.
     * It will format the reason and log it.
     *
     * @param whatYouWantedToDo           The error/success message resulting from the call to the
     *                         {@code MemoryUtils}' method.
     * @param exception        The exception resulting from the call to the {@code MemoryUtils}'
     *                         method Pass {@code null} if no exception was thrown.
     * @param formatParameters The parameters necessary to format the reason. Pass empty in place
     *                         of the parameters that won't be specified.
     * @param <T>              The type of object resulting from the call to the
     *                         {@code MemoryUtils}' method.
     * @return The {@code Result<T>} object created.
     */
    static <T> Result<T> createUnsuccessfulResult(@NonNull String whatYouWantedToDo,
                                                  Exception exception, String... formatParameters) {

        return createResult(null, whatYouWantedToDo, false, exception, formatParameters);
    }


    /**
     * Creates a {@link Result} object for an unsuccessful method call when trying to save something
     * with an invalid path.
     * It will format the reason and log it.
     *
     * @param formatParameters The parameters necessary to format the reason. Pass empty in place
     *                         of the parameters that won't be specified.
     * @param <T>              The type of object resulting from the call to the
     *                         {@code MemoryUtils}' method.
     * @return The {@code Result<T>} object created.
     */
    static <T> Result<T> createInvalidFileResult(ValidForSavingInfoInterface validityInfo,
                                                 String formatParameters) {

        String reason;

        switch (validityInfo.getReason()) {
            case IS_A_DIRECTORY:
                reason = MemoryUtils.DESTINATION_FILE_IS_A_FOLDER;
                break;
            case NOT_A_DIRECTORY:
                reason = MemoryUtils.DESTINATION_FILE_IS_NOT_A_FOLDER;
                break;
            case CONTAINER_FOLDER_DOESNT_EXIST:
                reason = MemoryUtils.CONTAINER_FOLDER_DOESNT_EXIST;
                break;
            default:
                reason = MemoryUtils.FAILED;
        }

        return createResult(null, reason, false, null, formatParameters);
    }


    static <T> Result<T> createInfoResult(ValidationInfoInterface validationInfo){
        String problem = ValidationUtils.createErrorMessage(validationInfo);
        return createResult(null, problem, false, null);
    }

    /**
     * This method uses {@link Logger#log} to log messages. Logs using this method will not be
     * shown if you disabled logging by calling {@link MemoryUtils#setLoggingEnabled(boolean)}
     * passing {@code false.}
     *
     * @param message   The message to be displayed.
     * @param isSuccess If {@code true}, {@code Log.i()} will be used. Else, {@code Log.e()} will.
     */
    private static void log(String tag, String message, boolean isSuccess) {
        Logger.log(tag, message, isSuccess);
    }
}
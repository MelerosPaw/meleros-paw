package melerospaw.memoryutil;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Exception indicating that a parameter that you passed to a method was null/empty/useless but
 * it was essential for the method to work and should be corrected right away. Use this instead
 * of returning null or a boolean, since this way you'll avoid MemoryUtils' method's misuse. If
 * you returned a boolean, you wouldn't know whether the method returned false because it failed
 * or because you're not calling the method properly.
 */
class InvalidParameterException extends RuntimeException {

    public static final String PARAM_CONTEXT = "context";
    public static final String PARAM_FILE_OBJECT = "File object";
    public static final String PARAM_OBJECT = "object";
    public static final String PARAM_FILE = "file";
    public static final String PARAM_FILE_NAME = "file parameter";
    public static final String PARAM_INPUTSTREAM = "InputStream";
    public static final String PARAM_OUTPUTSTREAM = "OutputStream";
    public static final String PARAM_FOLDER = "folder";
    public static final String PARAM_PATH = "path";
    public static final String PARAM_PATH_TO_FILE = "path to file";
    public static final String PARAM_PATH_TO_FOLDER = "path to folder";
    public static final String PARAM_PATH_OBJECT = "Path object";
    public static final String PARAM_DESTINATION_PATH = "destination path";
    public static final String PARAM_DESTINATION_PATH_OBJECT = "destination Path object";
    public static final String PARAM_DATABASE_NAME = "database parameter";
    public static final String PARAM_TEXT = "text";
    public static final String PARAM_ORIGIN_PATH = "origin path";
    public static final String PARAM_ORIGIN_PATH_OBJECT = "origin Path object";
    public static final String PARAM_CLASS = "class type";
    public static final String PARAM_BYTE_ARRAY = "byte[]";
    public static final String PARAM_SHARED_PREFERENCES = "SharedPreferences";

    public static final String TYPE_NULL = "is null";
    public static final String TYPE_EMPTY = "is empty";
    public static final String TYPE_INVALID = "is invalid";
    public static final String TYPE_NOT_DIRECTORY = "is not a directory";
    public static final String TYPE_IS_DIRECTORY = "is a directory";
    public static final String TYPE_NOT_SERIALIZABLE = "is not serializable";
    public static final String TYPE_NOT_AN_IMAGE = "is not an image";
    public static final String TYPE_CONTAINER_FOLDER_DOESNT_EXIST = "would be contained in a folder that doesn't exist yet. You must create it first.";

    public static final int EXCEPTION_NORMAL = 1;
    public static final int EXCEPTION_VALIDATION_INFO = 2;
    public static final int EXCEPTION_MESSAGE = 3;

    private String whatYouWantedToDo;
    @InvalidParameter   private String theThingThatWasInvalid;
    @InvalidityType     private String invalidityType;
    private ValidationInfoInterface validationInfo;
    private String message;
    private final int exceptionType;



    /**
     * Defines constants beginning with {@code "TYPE"} in this class set the type of invalidity of
     * the invalid parameter.
     */
    @StringDef({TYPE_NULL, TYPE_EMPTY, TYPE_NOT_DIRECTORY, TYPE_INVALID, TYPE_IS_DIRECTORY,
            TYPE_NOT_SERIALIZABLE, TYPE_CONTAINER_FOLDER_DOESNT_EXIST, TYPE_NOT_AN_IMAGE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface InvalidityType {
    }


    /**
     * Defines constants beginning with {@code "PARAM"} in this class set parameter that was
     * invalid.
     */
    @StringDef({PARAM_DESTINATION_PATH_OBJECT, PARAM_FILE_OBJECT, PARAM_FILE_NAME, PARAM_FOLDER,
            PARAM_INPUTSTREAM, PARAM_OUTPUTSTREAM, PARAM_PATH_TO_FILE, PARAM_PATH_OBJECT,
            PARAM_DESTINATION_PATH, PARAM_CONTEXT, PARAM_DATABASE_NAME, PARAM_TEXT, PARAM_FILE,
            PARAM_ORIGIN_PATH_OBJECT, PARAM_ORIGIN_PATH, PARAM_CLASS, PARAM_BYTE_ARRAY,
            PARAM_SHARED_PREFERENCES, PARAM_PATH_TO_FOLDER, PARAM_PATH, PARAM_OBJECT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface InvalidParameter {
    }


    /**
     * Creates a {@code InvalidParameterException}.
     *
     * @param whatYouWantedToDo      Text indicating the action that could not be performed.
     * @param theThingThatWasInvalid Type of object that was null.
     * @param invalidityType         The sort of invalidity.
     */
    public InvalidParameterException(String whatYouWantedToDo,
                                     @InvalidParameter String theThingThatWasInvalid,
                                     @InvalidityType String invalidityType) {
        this.whatYouWantedToDo = whatYouWantedToDo;
        this.theThingThatWasInvalid = theThingThatWasInvalid;
        this.invalidityType = invalidityType;
        this.exceptionType = EXCEPTION_NORMAL;
    }


    /** Creates a {@code InvalidParameterException} based on the validation information passed.
     *
     * @param validationInfo The validation information to create the exception.
     */
    public InvalidParameterException(ValidationInfoInterface validationInfo){
        this.validationInfo = validationInfo;
        this.exceptionType = EXCEPTION_VALIDATION_INFO;
    }

    /** Creates a {@code InvalidParameterException} based on the validation information passed.
     *
     * @param message The validation information to create the exception.
     */
    public InvalidParameterException(String message){
        this.message = message;
        this.exceptionType = EXCEPTION_MESSAGE;
    }


    @Override
    public String getMessage() {

        String message;

        switch(exceptionType){
            case EXCEPTION_NORMAL:
                message = "You're trying to " + whatYouWantedToDo + ", but the " +
                    theThingThatWasInvalid + " that you're passing " + invalidityType + ".";
                break;
            case EXCEPTION_VALIDATION_INFO:
                message = ValidationUtils.createErrorMessage(validationInfo);
                break;
            case EXCEPTION_MESSAGE: message = this.message;
                break;
            default: message = "";
        }

        return StringUtil.format(message);
    }
}

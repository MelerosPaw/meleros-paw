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
    public static final String PARAM_FILE_NAME = "file name";
    public static final String PARAM_INPUTSTREAM = "InputStream";
    public static final String PARAM_OUTPUTSTREAM = "OutputStream";
    public static final String PARAM_FOLDER = "folder";
    public static final String PARAM_PATH = "path";
    public static final String PARAM_PATH_TO_FILE = "path to file";
    public static final String PARAM_PATH_TO_FOLDER = "path to folder";
    public static final String PARAM_PATH_OBJECT = "Path object";
    public static final String PARAM_DESTINATION_PATH = "destination path";
    public static final String PARAM_DESTINATION_PATH_OBJECT = "destination Path object";
    public static final String PARAM_DATABASE_NAME = "database name";
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
    public static final String TYPE_CONTAINER_FOLDER_DOESNT_EXIST = "would be contained in a folder that doesn't exist yet. You must create it first.";

    private String whatYouWantedToDo;
    @InvalidParameter   private String theThingThatWasInvalid;
    @InvalidityType     private String invalidityType;


    /**
     * Defines constants beginning with {@code "TYPE"} in this class set the type of invalidity of
     * the invalid parameter.
     */
    @StringDef({TYPE_NULL, TYPE_EMPTY, TYPE_NOT_DIRECTORY, TYPE_INVALID, TYPE_IS_DIRECTORY,
            TYPE_NOT_SERIALIZABLE, TYPE_CONTAINER_FOLDER_DOESNT_EXIST})
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
    }

//    /**
//     * Creates a {@code InvalidParameterException}. Use this constructor when the invalidity type
//     * is
//     *
//     * @param whatYouWantedToDo      Text indicating the action that could not be performed.
//     * @param invalidityType         The sort of invalidity.
//     */
//    public InvalidParameterException(String whatYouWantedToDo,
//                                     @InvalidityType String invalidityType) {
//        this.whatYouWantedToDo = whatYouWantedToDo;
//        this.invalidityType = invalidityType;
//    }


    @Override
    public String getMessage() {

        String formattedMessage = "You're trying to " + whatYouWantedToDo + ", but the " +
                theThingThatWasInvalid + " that you're passing " + invalidityType + ".";

//        if (TextUtils.isEmpty(theThingThatWasInvalid)){
//            formattedMessage += invalidityType + ".";
//        } else {
//            formattedMessage += "you're passing " + invalidityType + " "
//                    + theThingThatWasInvalid + ".";
//        }

        return StringUtil.format(formattedMessage);
    }
}

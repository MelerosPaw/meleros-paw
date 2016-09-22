package melerospaw.memoryutil;

/**
 * Created by Juan José Melero on 26/08/2016.
 */
class ExceptionManager {

    private static boolean isThrowingExceptions = true;


    public static void throwNullContextException(String whatYouWantedToDo,
                                                 String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_CONTEXT,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwDestinationPathObjectException(String whatYouWantedToDo,
                                                           String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_DESTINATION_PATH_OBJECT,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }


    public static void throwDestinationPathException(String whatYouWantedToDo,
                                                     String destinationPath,
                                                     String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        boolean isNull = destinationPath == null;
        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_DESTINATION_PATH,
                isNull ? InvalidParameterException.TYPE_NULL : InvalidParameterException.TYPE_EMPTY,
                whatYouWantedToDoFormatParameters);
    }


    public static void throwNullInputStreamException(String whatYouWantedToDo,
                                                     String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_INPUTSTREAM,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }


    public static void throwFileNameException(String whatYouWantedToDo, String fileName,
                                              String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        boolean isNull = fileName == null;
        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_FILE_NAME,
                isNull ? InvalidParameterException.TYPE_NULL : InvalidParameterException.TYPE_EMPTY,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwDatabaseNameException(String whatYouWantedToDo, String databaseName,
                                                  String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        boolean isNull = databaseName == null;
        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_DATABASE_NAME,
                isNull ? InvalidParameterException.TYPE_NULL : InvalidParameterException.TYPE_EMPTY,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNullTextException(String whatYouWantedToDo,
                                              String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_TEXT,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwOriginPathObjectException(String whatYouWantedToDo,
                                                      String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_ORIGIN_PATH_OBJECT,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwOriginPathException(String whatYouWantedToDo, String originPath,
                                                String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        boolean isNull = originPath == null;
        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_ORIGIN_PATH,
                isNull ? InvalidParameterException.TYPE_NULL : InvalidParameterException.TYPE_EMPTY,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNullObjectException(String whatYouWantedToDo,
                                                String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_ORIGIN_PATH,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNullClassException(String whatYouWantedToDo,
                                               String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_CLASS,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNullByteArrayException(String whatYouWantedToDo,
                                                   String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_BYTE_ARRAY,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNullSharedPreferencesException(String whatYouWantedToDo,
                                                           String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_SHARED_PREFERENCES,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNullPathToFileException(String whatYouWantedToDo,
                                                    String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_PATH_OBJECT,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNullFileException(String whatYouWantedToDo,
                                              String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_FILE_OBJECT,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNullPathObjectException(String whatYouWantedToDo,
                                                    String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_PATH_OBJECT,
                InvalidParameterException.TYPE_NULL,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwPathToFileException(String whatYouWantedToDo, String pathToFile,
                                                String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        boolean isNull = pathToFile == null;
        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_PATH_TO_FILE,
                isNull ? InvalidParameterException.TYPE_NULL : InvalidParameterException.TYPE_EMPTY,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwPathToFolderException(String whatYouWantedToDo, String pathToFolder,
                                                String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        boolean isNull = pathToFolder == null;
        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_PATH_TO_FOLDER,
                isNull ? InvalidParameterException.TYPE_NULL : InvalidParameterException.TYPE_EMPTY,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNotADirectoryException(String whatYouWantedToDo,
                                                   String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_FILE_OBJECT,
                InvalidParameterException.TYPE_NOT_DIRECTORY,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNotAFileException(String whatYouWantedToDo,
                                                   String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_FILE_OBJECT,
                InvalidParameterException.TYPE_IS_DIRECTORY,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwPathException(String whatYouWantedToDo, String path,
                                                  String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        boolean isNull = path == null;
        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_PATH,
                isNull ? InvalidParameterException.TYPE_NULL : InvalidParameterException.TYPE_EMPTY,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwNotSerializableException(String whatYouWantedToDo,
                                                     String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_OBJECT,
                InvalidParameterException.TYPE_NOT_SERIALIZABLE,
                whatYouWantedToDoFormatParameters);
    }

    public static void throwInvalidFileForSaving(String whatYouWantedToDo,
                                                 ValidForSavingInfoInterface validityInfo,
                                                 String... formatParameters){

        whatYouWantedToDo = StringUtil.format(whatYouWantedToDo, formatParameters);
        @InvalidParameterException.InvalidityType String invalidity;

        switch (validityInfo.getReason()) {
            case IS_A_DIRECTORY:
                invalidity = InvalidParameterException.TYPE_IS_DIRECTORY;
                break;
            case NOT_A_DIRECTORY:
                invalidity = InvalidParameterException.TYPE_NOT_DIRECTORY;
                break;
            case CONTAINER_FOLDER_DOESNT_EXIST:
                invalidity = InvalidParameterException.TYPE_CONTAINER_FOLDER_DOESNT_EXIST;
                break;
            default:
                invalidity = InvalidParameterException.TYPE_INVALID;
        }

        throwException(whatYouWantedToDo, validityInfo.getInvalidParameter(), invalidity);

    }

    /**
     * Throws an exception if any of the essential parameters in a method call is invalid.
     *
     * @param whatYouWantedToDo          The method's objective.
     * @param theParameterThatWasInvalid The parameter of the method that was invalid.
     * @param invalidityType             The type of invalidity.
     * @throws InvalidParameterException
     */
    private static void throwException(String whatYouWantedToDo,
                                       @InvalidParameterException.InvalidParameter String theParameterThatWasInvalid,
                                       @InvalidParameterException.InvalidityType String invalidityType,
                                       String... formatParameters)
            throws InvalidParameterException {

        whatYouWantedToDo = StringUtil.format(whatYouWantedToDo, formatParameters);

        InvalidParameterException exception = new InvalidParameterException(whatYouWantedToDo,
                theParameterThatWasInvalid, invalidityType);

        if (isThrowingExceptions) {
            throw exception;
        } else {
            exception.printStackTrace();
        }
    }

//    /**
//     * Throws an exception if any of the essential parameters in a method call is invalid.
//     *
//     * @param whatYouWantedToDo          The method's objective.
//     * @param invalidityType             The type of invalidity.
//     * @throws InvalidParameterException
//     */
//    private static void throwException(String whatYouWantedToDo,
//                                       @InvalidParameterException.InvalidityType String invalidityType,
//                                       String... formatParameters)
//            throws InvalidParameterException {
//
//        whatYouWantedToDo = StringUtil.format(whatYouWantedToDo, formatParameters);
//
//        InvalidParameterException exception =
//                new InvalidParameterException(whatYouWantedToDo, invalidityType);
//
//        if (isThrowingExceptions) {
//            throw exception;
//        } else {
//            exception.printStackTrace();
//        }
//    }

    public static void throwNonExistingFileException(String whatYouWantedToDo,
                                                     String nonExistingPath,
                                                     String... formatParameters)
            throws NonExistingFileException {

        whatYouWantedToDo = StringUtil.format(whatYouWantedToDo, formatParameters);
        NonExistingFileException exception = new NonExistingFileException(whatYouWantedToDo,
                nonExistingPath);
        if (isThrowingExceptions) {
            throw exception;
        } else {
            exception.printStackTrace();
        }
    }


    public static void setThrowingExceptions(boolean enabled) {
        isThrowingExceptions = enabled;
    }
}

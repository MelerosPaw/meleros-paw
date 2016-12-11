package melerospaw.memoryutil;

import melerospaw.memoryutil.validation.ValidationInfoInterface;

class ExceptionManager {

    public enum ExceptionType{FILE_NOT_FOUND, IO_EXCEPTION}

    private static boolean isThrowingExceptions = true;


    public static void throwNotAnImageException(String whatYouWantedToDo,
                                                String... whatYouWantedToDoFormatParameters)
            throws InvalidParameterException {

        throwException(whatYouWantedToDo,
                InvalidParameterException.PARAM_FILE,
                InvalidParameterException.TYPE_NOT_AN_IMAGE,
                whatYouWantedToDoFormatParameters);
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


    public static void throwCaughtException(String message, ExceptionType type){

        RuntimeException exception;

            switch(type){
                case FILE_NOT_FOUND:
                case IO_EXCEPTION:
                    exception = new InvalidParameterException(message);
                    break;
                default: exception = new RuntimeException(message);

            }

        if (isThrowingExceptions){
            throw exception;
        } else {
            exception.printStackTrace();
        }
    }


    public static void throwValidationInfoException(ValidationInfoInterface validationInfo) {
        throwInvalidParameterException(validationInfo);
    }


    private static void throwInvalidParameterException(ValidationInfoInterface validationInfo)
            throws RuntimeException {

        InvalidParameterException exception = new InvalidParameterException(validationInfo);

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
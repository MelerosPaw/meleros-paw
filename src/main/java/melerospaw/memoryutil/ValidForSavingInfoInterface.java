package melerospaw.memoryutil;

interface ValidForSavingInfoInterface {

    Invalidity getReason();
    boolean isValid();
    @InvalidParameterException.InvalidParameter String getInvalidParameter();

    enum Invalidity {
        NONE, NOT_A_DIRECTORY, IS_A_DIRECTORY, CONTAINER_FOLDER_DOESNT_EXIST;
    }
}

package melerospaw.memoryutil;

class ValidationEnums {

    private static final String DESTINATION_FOLDER = "destination folder";
    private static final String DESTINATION_FOLDER_DESCRIPTION = "the destination folder";
    private static final String ORIGIN_FOLDER = "origin folder";
    private static final String ORIGIN_FOLDER_DESCRIPTION = "the origin folder";

    enum Parameter {
        BITMAP("Bitmap", "the bitmap"),
        BYTE_ARRAY("byte[]", "the byte[]"),
        CLASS("Class", "the class"),
        CONTEXT("Context", "the context"),
        DATABASE_NAME("data base name", "the data base name"),
        DESTINATION_FOLDER_FILE(DESTINATION_FOLDER, DESTINATION_FOLDER_DESCRIPTION),
        DESTINATION_FOLDER_PATH(DESTINATION_FOLDER, DESTINATION_FOLDER_DESCRIPTION),
        DESTINATION_FOLDER_PATH_OBJECT(DESTINATION_FOLDER, DESTINATION_FOLDER_DESCRIPTION),
        DESTINATION_PATH("destination path", "the destination path"),
        DESTINATION_PATH_OBJECT("destination Path object", "the destination Path object"),
        FILE("File", "the file"),
        FILE_NAME("file name", "the file name"),
        FOLDER("folder", "the folder"),
        INPUTSTREAM("InputStream", "the InputStream"),
        OBJECT("Object", "the object"),
        ORIGIN_FOLDER_FILE(ORIGIN_FOLDER, ORIGIN_FOLDER_DESCRIPTION),
        ORIGIN_FOLDER_PATH(ORIGIN_FOLDER, ORIGIN_FOLDER_DESCRIPTION),
        ORIGIN_FOLDER_PATH_OBJECT(ORIGIN_FOLDER, ORIGIN_FOLDER_DESCRIPTION),
        ORIGIN_PATH("origin path", "the origin path"),
        ORIGIN_PATH_OBJECT("origin Path object", "the origin Path object"),
        ORIGIN_URI("origin uri", "the origin uri"),
        PATH("path to file", "the path to file"),
        PATH_IN_URI("path in Uri", "the file referenced by the Uri"),
        PATH_OBJECT("Path object", "the Path object"),
        PATH_OBJECT_STRING("String path in origin Path object", "the String path contained in the origin Path object"),
        PATH_OBJECT_TO_FILE("Path to file", "the Path object pointing to file"),
        PATH_OBJECT_TO_FOLDER("Path to folder", "the Path object pointing to folder"),
        PATH_TO_FILE("path to file", "the path to file"),
        PATH_TO_FOLDER("path to folder", "the path to folder"),
        SHARED_PREFERENCES("SharedPreferences", "the SharedPreferences object"),
        TEXT("text", "the text"),
        UNEXPECTED_PARAMETER("unexpected parameter", "some of the parameters");

        public final String parameterName;
        public final String description;

        Parameter(String parameterName, String description) {
            this.parameterName = parameterName;
            this.description = description;
        }

        @Override
        public String toString(){
            return parameterName;
        }
    }


    enum Invalidity {
        NONE("none"),
        ASSET_DOESNT_EXIST("refers to an asset file that doesn't exist"),
        CONTAINER_FOLDER_DOESNT_EXIST("would be contained in a folder that still doesn't exist. You must create it first"),
        EXISTS_AS_NOT_DIRECTORY("already exists and is not a directory"),
        FILE_DOESNT_EXIST("doesn't exist"),
        IS_A_DIRECTORY("is a directory when it should be a file"),
        IS_EMPTY("is empty"),
        IS_NULL("is null"),
        NOT_A_DIRECTORY("is not a directory"),
        NOT_SERIALIZABLE("is not serializable"),
        UNEXPECTED("is unexpected"),
        UNPARSEABLE_URI("refers to a file that couldn't be converted into an InputStream");


        public final String invalidityName;

        Invalidity(String invalidityName) {
            this.invalidityName = invalidityName;
        }
    }


    enum Method {
        CLEAR_FOLDER("clearFolder(pathToFolder)"),
        COPY_FROM_INPUTSTREAM("copyFromInputStream(originPath, inputStream, destinationPath)"),
        CREATE_FOLDER("createFolder(pathToFolder)"),
        CREATE_PATH("createPath(path)"),
        DELETE_FILE("deleteFile(pathToFile)"),
        DUPLICATE_FILE("duplicateFile(originPath, destinationPath)"),
        DUPLICATE_FOLDER("duplicateFolder(originFolder, destinationFolder)"),
        EXISTS("exists(pathToFile)"),
        EXISTS_FILE("exists(file)"),
        GET_FILES_IN_DIRECTORY("getFilesInDirectory(folder)"),
        GET_FILE_TREE("getFileTree(folder)"),
        GET_LONGEST_PATH("getLongestPath(path)"),
        IMPORT_DATABASE_FROM_ASSETS("importDatabaseFromAssets(context, dataBaseName)"),
        IMPORT_FROM_ASSETS("importFromAssets(context, fileName, destinationPath)"),
        IS_DIRECTORY("isDirectory(path)"),
        IS_FOLDER_EMPTY("isFolderEmpty(folder)"),
        IS_VALID_FOR_SAVING("isValidForSaving(path)"),
        LOAD_BITMAP("loadBitmap(originPath)"),
        LOAD_BITMAP_FROM_ASSETS("loadBitmapFromAssets(context, fileName)"),
        LOAD_BITMAP_URI("loadBitmap(originUri)"),
        LOAD_OBJECT("loadObject(originPath, class)"),
        LOAD_SHARED_PREFERENCES("loadSharedPreferences(originPath)"),
        LOAD_SHARED_PREFERENCES_2("loadSharedPreferences(originPath, sharedPreferences)"),
        LOAD_TEXT_FILE("loadTextFile(originPath)"),
        SAVE_BITMAP("saveBitmap(bitmap, destinationPath)"),
        SAVE_BYTE_ARRAY("saveByteArray(byteArray, destinationPath)"),
        SAVE_OBJECT("saveObject(object, destinationPath)"),
        SAVE_SHARED_PREFERENCES("saveSharedPreferences(sharedPreferences, destinationPath)"),
        SAVE_TEXT_FILE("saveTextFile(text, destinationPath)");


        public final String description;

        Method(String description) {
            this.description = description;
        }
    }
}

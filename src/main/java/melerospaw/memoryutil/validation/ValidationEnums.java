package melerospaw.memoryutil.validation;

public class ValidationEnums {

    public enum Parameter {
        UNEXPECTED_PARAMETER("unexpected parameter", "some of the parameters"),
        BITMAP("Bitmap", "the bitmap"),
        CONTEXT("Context", "the context"),
        DESTINATION_PATH("destination path", "the destination path"),
        DESTINATION_PATH_OBJECT("destination Path object", "the destination Path object"),
        FILE_NAME("file name", "the file name"),
        ORIGIN_PATH("origin path", "the origin path"),
        ORIGIN_PATH_OBJECT("origin Path object", "the origin Path object"),
        PATH_OBJECT_STRING("String path in origin Path object", "the String path contained in the origin Path object"),
        SHARED_PREFERENCES("SharedPreferences", "the SharedPreferences object"),
        PATH_OBJECT_TO_FILE("Path object pointing to file", "the Path object pointing to file"),
        PATH_OBJECT_TO_FOLDER("Path object pointing to folder", "the Path object pointing to folder"),
        PATH_TO_FILE("path to file", "the path to file"),
        PATH_TO_FOLDER("path to folder", "the path to folder"),
        PATH("path to file", "the path to file"),
        PATH_OBJECT("Path object", "the Path object"),
        FILE("File", "the file"),
        FOLDER("folder", "the folder"),
        INPUTSTREAM("InputStream", "the InputStream"),
        DATABASE_NAME("data base name", "the data base name"),
        TEXT("text", "the text"),
        OBJECT("Object", "the object"),
        CLASS("Class", "the class"),
        BYTE_ARRAY("byte[]", "the byte[]");

        public String parameter;
        public String description;

        Parameter(String parameter, String description) {
            this.parameter = parameter;
            this.description = description;
        }

        public String toString(){
            return parameter;
        }
    }


    public enum Invalidity {
        NONE("none"),
        UNEXPECTED("is unexpected"),
        FILE_DOESNT_EXIST("doesn't exist"),
        IS_NULL("is null"),
        IS_EMPTY("is empty"),
        NOT_A_DIRECTORY("is not a directory"),
        CONTAINER_FOLDER_DOESNT_EXIST("would be contained in a folder that still doesn't exist. " +
                "You must create it first."),
        IS_A_DIRECTORY("is a directory when it should be a file"),
        EXISTS_AS_NOT_DIRECTORY("already exists and is not a directory"),
        NOT_SERIALIZABLE("is not serializable"),
        ASSET_DOESNT_EXIST("refers to an asset file that doesn't exist");


        public String invalidity;

        Invalidity(String invalidity) {
            this.invalidity = invalidity;
        }
    }


    public enum Method {
        SAVE_BITMAP("saveBitmap(bitmap, destinationPath)"),
        LOAD_BITMAP("loadBitmap(originPath)"),
        LOAD_BITMAP_FROM_ASSETS("loadBitmapFromAssets(context, fileName)"),
        SAVE_SHARED_PREFERENCES("saveSharedPreferences(sharedPreferences, destinationPath)"),
        LOAD_SHARED_PREFERENCES("loadSharedPreferences(originPath)"),
        LOAD_SHARED_PREFERENCES_2("loadSharedPreferences(originPath, sharedPreferences)"),
        EXISTS("exists(pathToFile)"),
        EXISTS_FILE("exists(file)"),
        IS_VALID_FOR_SAVING("isValidForSaving(path)"),
        DUPLICATE_FILE("duplicateFile(originPath, destinationPath)"),
        DELETE_FILE("deleteFile(pathToFile)"),
        CLEAR_FOLDER("clearFolder(pathToFolder)"),
        IS_FOLDER_EMPTY("isFolderEmpty(folder)"),
        CREATE_FOLDER("createFolder(pathToFolder)"),
        CREATE_PATH("createPath(path)"),
        GET_LONGEST_PATH("getLongestPath(path)"),
        IS_DIRECTORY("isDirectory(path)"),
        GET_FILES_IN_DIRECTORY("getFilesInDirectory(folder)"),
        GET_FILE_TREE("getFileTree(folder)"),
        COPY_FROM_INPUTSTREAM("copyFromInputStream(originPath, inputStream, destinationPath)"),
        IMPORT_FROM_ASSETS("importFromAssets(context, fileName, destinationPath)"),
        IMPORT_DATABASE_FROM_ASSETS("importDatabaseFromAssets(context, dataBaseName)"),
        LOAD_TEXT_FILE("loadTextFile(originPath)"),
        SAVE_TEXT_FILE("saveTextFile(text, destinationPath)"),
        SAVE_OBJECT("saveObject(object, destinationPath)"),
        LOAD_OBJECT("loadObject(originPath, class)"),
        SAVE_BYTE_ARRAY("saveByteArray(byteArray, destinationPath)");


        public String description;

        Method(String description) {
            this.description = description;
        }
    }
}

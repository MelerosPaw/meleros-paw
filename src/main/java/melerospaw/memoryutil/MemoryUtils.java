package melerospaw.memoryutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <p>Class to make it easier to save and load from an Android device's memory.
 * Use {@link Path.Builder} to create a {@code Path} to call {@code MemoryUtils}' methods.
 * This way, folders will be created automatically when saving.
 * Then use {@link Result} class to retrieve objects from method calls, for example, the
 * {@code File} where something was saved to or the object that you are trying to retrieve from
 * memory.</p>
 * <p>
 * <p>Example code:</p>
 */
public class MemoryUtils {

    private static final String TAG = MemoryUtils.class.getSimpleName();

    static final String ALL_PREFERENCES_RESTORED = "Preferences in %1$s were restored.\n%2$s";
    static final String ALREADY_EXISTS = "Folder %1$s already exists.";
    static final String ASSET_NOT_FOUND = "File %1$s was not found in assets directory.";
    static final String A_FILE = "a file";
    static final String BYTE_ARRAY_SAVED = "Byte array was saved to %1$s.";
    static final String CANNOT_COPY = "Couldn't copy file %1$s to %2$s.";
    static final String CANNOT_CREATE = "Couldn't create folder %1$s.";
    static final String CANNOT_CREATE_OBJECT_FILE = "Cannot save the given object to file %1$s because there was an error while writing.";
    static final String CANNOT_CREATE_OBJECT_INPUT_STREAM = "Cannot %1$s because it was not possible to create an ObjectOutputStream.";
    static final String CANNOT_DELETE = "File %1$s couldn't be deleted.";
    static final String CANNOT_DELETE_FOLDER = "Cannot %1$s because inner folder %2$s cannot be deleted.";
    static final String CANNOT_READ_OBJECT = "Cannot %1$s because the object could not be read.";
    static final String CANNOT_RETRIEVE_TEXT_FILE = "Cannot retrieve text from file %1$s.";
    static final String CANNOT_SAVE_TEXT = "Cannot save text to file %1$s.";
    static final String CHECK_FILE_EXISTS = "check if file exists";
    static final String CHECK_FOLDER_EXISTS = "check if folder %1$s to save a file exists";
    static final String CHECK_IS_EMPTY = "check if folder %1$s is empty";
    static final String CHECK_FILE_IS_A_FOLDER = "check if a file is a folder";
    static final String CHECK_FILE_IS_FOLDER = "check if file %1$s is a folder";
    static final String CLASS_NOT_FOUND = "Cannot %1$s because such class was not found.";
    static final String CLEAR_FOLDER = "clear folder %1$s";
    static final String COPY_FROM_INPUT_STREAM = "copy %1$s from an InputStream";
    static final String COPY_FROM_INPUT_STREAM_TO = "copy %1$s from an InputStream to %2$s";
    static final String COULD_NOT_CREATE_PATH = "Could not create path %1$s.";
    static final String CREATED = "Folder %1$s was created.";
    static final String CREATE_A_FOLDER = "created a folder";
    static final String CREATE_PATH = "create path using a Path object";
    static final String DELETED_BUT_STILL_NOT_EMPTY = "All files in folder %1$s were deleted but still it's not empty.";
    static final String DELETE_FILE = "delete file %1$s";
    static final String DESTINATION_FILE_DOESNT_EXIST = "Cannot %1$s because destination file %2$s doesn't exist.";
    static final String DESTINATION_FILE_NOT_FOUND = "Cannot %1$s because destination file %2$s was not found.";
    static final String DESTINATION_FOLDER_NOT_FOUND = "Cannot %1$s because the destination folder was not found.";
    static final String DIRECTORY_HAS_FILES = "Cannot %1$s because it is a directory containing other files. If you want to delete it, pass shouldClearIfDirectory = true as second parameter to method deleteFile().";
    static final String DUPLICATE_FILE = "duplicate file %1$s";
    static final String EMPTY_DATABASE_NAME = "Cannot import database from assets because the database name provided is empty.";
    static final String EMPTY_DESTINATION_PATH = "Cannot %1$s because the destination path provided is empty.";
    static final String EMPTY_ORIGIN_PATH = "Cannot %1$s because the origin path provided is empty.";
    static final String EMPTY_PATH = "Cannot %1$s because the path provided is empty.";
    static final String ERROR_WHILE_READING = "An error occurred while reading text fom file %1$s.";
    static final String ERROR_WRITING_BYTE_ARRAY = "Cannot %1$s because there was an error while writing the byte[].";
    static final String FAILED = "Cannot %1$s. FAILED.";
    static final String FILENAME_EMPTY = "The file name to %1$s is empty.";
    static final String FILENAME_NULL = "The file name to %1$s is null.";
    static final String FILE_COPIED = "File %1$s was copied to %2$s.";
    static final String FILE_DELETED = "File %1$s was deleted.";
    static final String FILE_DOESNT_EXIST = "Cannot %1$s because the file doesn't exist.";
    static final String FILE_EXISTS = "File %1$s exists.";
    static final String FILE_NOT_FOUND = "Cannot %1$s because file was not found.";
    static final String FILE_TREE_GENERATED = "File tree generated.";
    static final String FOLDER_ALREADY_EMPTY = "Folder %1$s was already empty.";
    static final String FOLDER_DOESNT_EXIST = "Cannot %1$s because the folder doesn't exist.";
    static final String FOLDER_EMPTY = "Folder %1$s is empty.";
    static final String FOLDER_IS_NOT_EMPTY = "Folder %1$s is not empty.";
    static final String FOLDER_NOT_CLEARED = "Cannot %1$s because it's a folder but it could not be cleared.";
    static final String DESTINATION_FILE_IS_A_FOLDER = "Cannot %1$s because the destination file is a folder.";
    static final String DESTINATION_FILE_IS_NOT_A_FOLDER = "Cannot %1$s because the destination file is not a folder.";
    static final String CONTAINER_FOLDER_DOESNT_EXIST = "Cannot %1$s because the folder that will contain the file doesn't exist. You must create it first.";
    static final String GENERATE_A_FOLDER_FILE_TREE = "generate a folder's file tree";
    static final String GENERATE_FILE_TREE = "generate file tree from folder %1$s";
    static final String GET_AN_IMAGE_FROM_ASSETS = "get an image from assets";
    static final String GET_FILES_IN_A_DIRECTORY = "get a list of the files in a directory";
    static final String GET_FILES_IN_DIRECTORY = "get a list of the files in directory %1$s";
    static final String GET_IMAGE_FROM_ASSETS = "get image %1$s from assets";
    static final String GET_LONGEST_VALID_PATH = "get the longest valid path";
    static final String IMAGE_LOADED_FROM_ASSETS = "Image %1$s was retrieved from assets.";
    static final String IMAGE_NOT_FOUND_IN_ASSETS = "Image %1$s was not found in assets.";
    static final String IMPORT_DATABASE_FROM_ASSETS = "import database %1$s from assets";
    static final String IMPORT_FROM_ASSETS = "import %1$s from assets";
    static final String IS_A_FOLDER = "File %1$s is a folder.";
    static final String LOAD_BITMAP_FROM_ASSETS = "load Bitmap %1$s from assets";
    static final String LOAD_OBJECT = "load a/an %1$s object from file %2$s";
    static final String LOAD_SHARED_PREFERENCES = "load SharedPreferences from file %1$s";
    static final String LOAD_TEXT_FROM_FILE = "load text from file %1$s";
    static final String NOT_A_DIRECTORY = "Cannot %1$s because the file is not a directory.";
    static final String NOT_A_FOLDER = "File %1$s is not a folder.";
    static final String NOT_SERIALIZABLE = "Cannot save object to %1$s because it's not serializable.";
    static final String NO_PREFERENCES_RESTORED = "No preferences at all were restored from %1$s.";
    static final String NULL_BYTE_ARRAY = "Cannot %1$s because the byte[] is null.";
    static final String NULL_CLASS = "Class to load object from %1$s is null.";
    static final String NULL_CONTEXT = "Cannot %1$s because the context provided is null.";
    static final String NULL_DATABASE_NAME = "Cannot import database from assets because the database name provided is null.";
    static final String NULL_DESTINATION_PATH = "Cannot %1$s because the destination path is null.";
    static final String NULL_FILE = "Cannot %1$s because the File object provided is null.";
    static final String NULL_INPUTSTREAM = "Cannot copy file %1$s into %2$s because the InputStream provided is null.";
    static final String NULL_OBJECT = "Cannot %1$s because the object is null.";
    static final String NULL_ORIGIN_PATH = "Cannot %1$s because the origin path provided is null.";
    static final String NULL_PATH = "Cannot %1$s because the path provided is null.";
    static final String NULL_PATH_OBJECT = "Cannot %1$s because the Path object provided is null.";
    static final String NULL_SHAREDPREFERENCES_OBJECT = "Cannot %1$s because the SharedPreferences you provided is null.";
    static final String NULL_TEXT = "Cannot %1$s because text is null.";
    static final String OBJECT_LOADED = "Object retrieved from %1$s.";
    static final String OBJECT_SAVED = "Object saved to %1$s.";
    static final String PATH_CONTAINED_NO_FOLDERS = "Path object %1$s contains no folders, so no intermediate folders were created to create it.";
    static final String PATH_CREATED = "Folders %1$s were created or already existed. Path created.";
    static final String PREFERENCES_PARTIALLY_RESTORED = "Stored preferences in %1$s could only be partially restored.\n%2$s";
    static final String SAVE_BYTE_ARRAY = "save a byte array to file %1$s";
    static final String SAVE_OBJECT = "save an object to file %1$s";
    static final String SAVE_SHARED_PREFERENCES = "save SharedPreferences to file %1$s";
    static final String SAVE_TEXT_TO_FILE = "save text to file %1$s";
    static final String SHARED_PREFERENCES_NOT_RESTORED = "Cannot load SharedPreferences preferences from %1$s.";
    static final String STATEMENT_FILE_DOESNT_EXISTS = "File %1$s doesn't exists.";
    static final String TEXT_FILE_SAVED = "Text was saved to %1$s.";
    static final String TEXT_LOADED = "Text was loaded from file %1$s.";


    /**
     * Receives an {@code InputStream} whose file will be copied to {@code destinationPath}. The
     * folders necessary will be created for you.
     *
     * @param originPath      The path to the file pointed by the {@code InputStream}, just for
     *                        logging purposes. Pass null if you don't know the path or don't care
     *                        about the log.
     * @param inputStream     Stream to copy from.
     * @param destinationPath Path where the file will be copied.
     * @return A {@code Result<File>} containing the file where the inputStream content was copied
     * or else {@code null}.
     */
    public static Result<File> copyFromInputStream(@Nullable String originPath,
                                                   @NonNull InputStream inputStream,
                                                   @NonNull Path destinationPath) {

        Result<File> result;
        originPath = StringUtil.getStringOrEmpty(originPath);

        if (destinationPath == null) {
            ExceptionManager.throwDestinationPathObjectException(COPY_FROM_INPUT_STREAM, originPath);
            result = Result.createNoExceptionResult(NULL_DESTINATION_PATH,
                    StringUtil.format(COPY_FROM_INPUT_STREAM, originPath));
        } else if (createPath(destinationPath)) {
            result = copyFromInputStream(originPath, inputStream, destinationPath.getPath());
        } else {
            result = Result.createNoExceptionResult(FAILED,
                    StringUtil.format(COPY_FROM_INPUT_STREAM, originPath));
        }

        return result;
    }


    /**
     * Receives an {@code InputStream} whose file will be copied to {@code destinationPath}. The
     * destination folder must exist or else it will fail.
     *
     * @param originPath      The path to the file pointed by the {@code InputStream}, just for
     *                        logging purposes. Pass null if you don't know the path or don't care
     *                        about the log.
     * @param inputStream     Stream to copy from.
     * @param destinationPath Path where the file will be copied.
     * @return A {@code Result<File>} containing the file where the inputStream content was copied
     * or else {@code null}.
     */
    public static Result<File> copyFromInputStream(@Nullable String originPath,
                                                   @NonNull InputStream inputStream,
                                                   @NonNull String destinationPath) {

        //TODO Hay que comprobar si la carpeta de destino existe, pero no sabemos si el input stream
        //TODO apunta a una carpeta o no, ni si puede apuntar a una carpeta
        originPath = StringUtil.getStringOrEmpty(originPath);

        if (TextUtils.isEmpty(destinationPath)) {
            ExceptionManager.throwDestinationPathException(COPY_FROM_INPUT_STREAM,
                    destinationPath, originPath);
            return Result.createNoExceptionResult(destinationPath == null ?
                            NULL_DESTINATION_PATH : EMPTY_DESTINATION_PATH,
                    StringUtil.format(COPY_FROM_INPUT_STREAM, originPath));
        } else if (inputStream == null) {
            ExceptionManager.throwNullInputStreamException(COPY_FROM_INPUT_STREAM_TO,
                    originPath, destinationPath);
            return Result.createNoExceptionResult(NULL_INPUTSTREAM, originPath, destinationPath);
        }

        OutputStream streamToSaveFile;
        try {
            streamToSaveFile = new FileOutputStream(destinationPath);
        } catch (FileNotFoundException e) {
            return Result.createUnsuccessfulResult(DESTINATION_FOLDER_NOT_FOUND, e,
                    destinationPath, StringUtil.format(COPY_FROM_INPUT_STREAM, StringUtil.EMPTY));
        }

        byte[] buffer = new byte[1024];
        int length;

        try {
            while ((length = inputStream.read(buffer)) > 0) {
                streamToSaveFile.write(buffer, 0, length);
            }
            streamToSaveFile.flush();
            streamToSaveFile.close();
            inputStream.close();
            return Result.createSuccessfulResult(new File(destinationPath), FILE_COPIED,
                    originPath, destinationPath);
        } catch (IOException e) {
            return Result.createUnsuccessfulResult(CANNOT_COPY, e, originPath, destinationPath);
        }
    }


    /**
     * Copies file {@code fileName} from assets directory to {@code destinationPath}.
     * It creates the folders in {@code destinationPath} for you.
     *
     * @param context         It will be used to get access to assets.
     * @param fileName        Name of the file in assets folder, as in <i>myFile.mp3</i> or
     *                        <i>subdirectory/myFile.mp3</i> if it is in a folder within the assets
     *                        directory.
     * @param destinationPath Path where the file will be copied to.
     * @return A {@code Result<File>} containing the file created or {@code null} if it couldn't be
     * created.
     */
    public static Result<File> importFromAssets(@NonNull Context context, @NonNull String fileName,
                                                @NonNull Path destinationPath) {

        Result<File> result;
        fileName = StringUtil.getStringOrEmpty(fileName);

        if (destinationPath == null) {
            ExceptionManager.throwDestinationPathObjectException(IMPORT_FROM_ASSETS, fileName);
            result = Result.createNoExceptionResult(NULL_DESTINATION_PATH,
                    StringUtil.format(IMPORT_FROM_ASSETS, fileName));
        } else if (createPath(destinationPath)) {
            result = importFromAssets(context, fileName, destinationPath.getPath());
        } else {
            result = Result.createNoExceptionResult(FAILED,
                    StringUtil.format(COPY_FROM_INPUT_STREAM, fileName));
        }

        return result;
    }


    /**
     * Copies a file {@code fileName} from assets directory to {@code destinationPath}.
     * {@code destinationPath} must exist.
     *
     * @param context         It will be used to get access to assets.
     * @param fileName        Name of the file in assets folder, as in <i>myFile.mp3</i> or
     *                        <i>subdirectory/myFile.mp3</i> if it's in a folder within the assets
     *                        directory.
     * @param destinationPath Path where the file will be copied to.
     * @return A {@code Result<File>} containing the file created or {@code null} if it couldn't be
     * created.
     */
    public static Result<File> importFromAssets(@NonNull Context context, @NonNull String fileName,
                                                @NonNull String destinationPath) {

        Result<File> result;

        if (TextUtils.isEmpty(fileName)) {
            ExceptionManager.throwFileNameException(IMPORT_FROM_ASSETS, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(fileName == null ?
                            FILENAME_NULL : FILENAME_EMPTY,
                    StringUtil.format(IMPORT_FROM_ASSETS, StringUtil.EMPTY));
        } else if (TextUtils.isEmpty(destinationPath)) {
            ExceptionManager.throwDestinationPathException(IMPORT_FROM_ASSETS, fileName);
            result = Result.createNoExceptionResult(destinationPath == null ?
                            NULL_DESTINATION_PATH : EMPTY_DESTINATION_PATH,
                    StringUtil.format(IMPORT_FROM_ASSETS, fileName));
        } else if (context == null) {
            ExceptionManager.throwNullContextException(IMPORT_FROM_ASSETS);
            result = Result.createNoExceptionResult(NULL_CONTEXT,
                    StringUtil.format(IMPORT_FROM_ASSETS, StringUtil.EMPTY));
        } else {
            ValidForSavingInfoInterface validityInfo = isValidForSaving(new File(destinationPath),
                    true, InvalidParameterException.PARAM_DESTINATION_PATH);
            if (!validityInfo.isValid() &&
                    validityInfo.getReason() != ValidForSavingInfoInterface.Invalidity.NONE) {
                ExceptionManager.throwInvalidFileForSaving(IMPORT_FROM_ASSETS, validityInfo,
                        destinationPath);
                result = Result.createInvalidFileResult(validityInfo,
                        StringUtil.format(IMPORT_FROM_ASSETS, destinationPath));
            } else {

                InputStream streamToAssets;

                try {
                    streamToAssets = context.getAssets().open(fileName);
                    result = copyFromInputStream(fileName, streamToAssets, destinationPath);
                } catch (IOException e) {
                    result = Result.createUnsuccessfulResult(ASSET_NOT_FOUND, e, fileName);
                }
            }
        }

        return result;
    }


    /**
     * Imports a file in assets folder to the database folder.
     *
     * @param context      Needed to access the database folder.
     * @param databaseName Name of the file in assets. It must be the same as the database name.
     * @return A {@code Result<File>} containing a {@code File} pointing to the file where the
     * database has been copied to.
     */
    public static Result<File> importDatabaseFromAssets(@NonNull Context context,
                                                        @NonNull String databaseName) {

        Result<File> result;

        if (TextUtils.isEmpty(databaseName)) {
            ExceptionManager.throwDatabaseNameException(IMPORT_DATABASE_FROM_ASSETS,
                    StringUtil.EMPTY);
            result = Result.createNoExceptionResult(databaseName == null ?
                    NULL_DATABASE_NAME : EMPTY_DATABASE_NAME);
        } else if (context == null) {
            ExceptionManager.throwNullContextException(IMPORT_DATABASE_FROM_ASSETS, databaseName);
            result = Result.createNoExceptionResult(NULL_CONTEXT,
                    StringUtil.format(IMPORT_DATABASE_FROM_ASSETS, databaseName));
        } else {

            Path pathToDatabase = new Path.Builder(context)
                    .databaseDirectory(databaseName)
                    .build();

            result = importFromAssets(context, databaseName, pathToDatabase);
        }

        return result;
    }


    /**
     * Gets a {@code Bitmap} from the assets folder.
     *
     * @param context       Context to access the assets folder.
     * @param imageFileName Name of the picture file in assets with extension, such as
     *                      <i>image.jpg</i>.
     * @return A{@code Result<Bitmap>} with the picture or {@code null} if the image doesn't exist
     * in the assets directory.
     */
    public static Result<Bitmap> loadBitmapFromAssets(@NonNull Context context,
                                                      @NonNull String imageFileName) {

        Result<Bitmap> result;

        if (TextUtils.isEmpty(imageFileName)) {
            ExceptionManager.throwFileNameException(GET_AN_IMAGE_FROM_ASSETS, imageFileName);
            result = Result.createNoExceptionResult(imageFileName == null ?
                            NULL_ORIGIN_PATH : EMPTY_ORIGIN_PATH,
                    StringUtil.format(LOAD_BITMAP_FROM_ASSETS, StringUtil.EMPTY));
        } else if (context == null) {
            ExceptionManager.throwNullContextException(GET_IMAGE_FROM_ASSETS, imageFileName);
            result = Result.createNoExceptionResult(NULL_CONTEXT,
                    StringUtil.format(LOAD_BITMAP_FROM_ASSETS, imageFileName));
        } else {

            try {
                Bitmap image = BitmapFactory.decodeStream(context.getAssets().open(imageFileName));
                result = Result.createSuccessfulResult(image, IMAGE_LOADED_FROM_ASSETS, imageFileName);
            } catch (IOException e) {
                result = Result.createUnsuccessfulResult(IMAGE_NOT_FOUND_IN_ASSETS, e, imageFileName);
            }
        }

        return result;
    }


    /**
     * Saves a text into a file using {@code FileWriter} and {@code BufferedWriter}.
     * It creates the folders necessary for you.
     *
     * @param text            The text to be saved.
     * @param destinationPath The destinationPath where the file will be stored. Must include the name of the file.
     * @param append          Pass {@code true} if you want content to be appended at the end of the existing
     *                        file, or {@code false} if you want the existing text to be overwritten by the
     *                        new text.
     * @return A {@code Result<File>} containing the file if it was created or else {@code null}.
     */
    public static Result<File> saveTextFile(@NonNull String text, @NonNull Path destinationPath,
                                            boolean append) {

        Result<File> result;

        if (destinationPath == null) {
            ExceptionManager.throwDestinationPathObjectException(SAVE_TEXT_TO_FILE);
            result = Result.createNoExceptionResult(NULL_DESTINATION_PATH,
                    StringUtil.format(SAVE_TEXT_TO_FILE, StringUtil.EMPTY));
        } else if (createPath(destinationPath)) {
            result = saveTextFile(text, destinationPath.getPath(), append);
        } else {
            result = Result.createNoExceptionResult(FAILED,
                    StringUtil.format(SAVE_TEXT_TO_FILE, destinationPath.getPath()));
        }

        return result;
    }


    /**
     * Saves a text into a file using {@code FileWriter} and {@code BufferedWriter}.
     * The destinationPath must exist.
     *
     * @param text            The text to be saved.
     * @param destinationPath The destinationPath where the file will be stored. Must include the name of the file.
     * @param append          Pass {@code true} if you want content to be appended at the end of the existing
     *                        file, or {@code false} if you want the existing text to be overwritten by the
     *                        new text.
     * @return A {@code Result<File>} containing the file if it was created or else {@code null}.
     */
    public static Result<File> saveTextFile(@NonNull String text, @NonNull String destinationPath,
                                            boolean append) {

        if (TextUtils.isEmpty(destinationPath)) {
            ExceptionManager.throwDestinationPathException(SAVE_TEXT_TO_FILE, destinationPath,
                    StringUtil.EMPTY);
            return Result.createNoExceptionResult(destinationPath == null ?
                            NULL_DESTINATION_PATH : EMPTY_DESTINATION_PATH,
                    StringUtil.format(SAVE_TEXT_TO_FILE, StringUtil.EMPTY));
        } else if (text == null) {
            ExceptionManager.throwNullTextException(SAVE_TEXT_TO_FILE, destinationPath);
            return Result.createNoExceptionResult(NULL_TEXT,
                    StringUtil.format(SAVE_TEXT_TO_FILE, destinationPath));
        } else {
            ValidForSavingInfoInterface validityInfo = isValidForSaving(new File(destinationPath),
                    false, InvalidParameterException.PARAM_DESTINATION_PATH);
            if (!validityInfo.isValid() &&
                    validityInfo.getReason() != ValidForSavingInfoInterface.Invalidity.NONE) {
                ExceptionManager.throwInvalidFileForSaving(SAVE_TEXT_TO_FILE, validityInfo,
                        destinationPath);
                return Result.createInvalidFileResult(validityInfo,
                        StringUtil.format(SAVE_TEXT_TO_FILE, destinationPath));
            }
        }

        File destinationFile = new File(destinationPath);
        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(destinationFile, append);
        } catch (IOException e) {
            return Result.createUnsuccessfulResult(DESTINATION_FOLDER_NOT_FOUND, e,
                    destinationPath, StringUtil.format(SAVE_TEXT_TO_FILE, StringUtil.EMPTY));
        }

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            bufferedWriter.write(text.toCharArray(), 0, text.length());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            return Result.createUnsuccessfulResult(CANNOT_SAVE_TEXT, e, destinationPath);
        }

        return Result.createSuccessfulResult(destinationFile, TEXT_FILE_SAVED, destinationPath);

    }


    /**
     * Loads a text file from the given {@code originPath}.
     *
     * @param originPath The path to the text file.
     * @return Null if the file could not be found or read, or else a {@code String} with the
     * content.
     */
    public static Result<String> loadTextFile(@NonNull Path originPath) {

        Result<String> result;

        if (originPath == null) {
            ExceptionManager.throwOriginPathObjectException(LOAD_TEXT_FROM_FILE, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(CANNOT_RETRIEVE_TEXT_FILE, StringUtil.EMPTY);
        } else {
            result = loadTextFile(originPath.getPath());
        }

        return result;
    }

    /**
     * Loads a text file from the given originPath.
     *
     * @param originPath The originPath to the text file.
     * @return Null if the file could not be found or read, or else a {@code String} with the
     * content.
     */
    public static Result<String> loadTextFile(@NonNull String originPath) {

        if (TextUtils.isEmpty(originPath)) {
            ExceptionManager.throwOriginPathException(LOAD_TEXT_FROM_FILE, originPath,
                    StringUtil.EMPTY);
            return Result.createNoExceptionResult(originPath == null ?
                            NULL_ORIGIN_PATH : EMPTY_ORIGIN_PATH,
                    StringUtil.format(LOAD_TEXT_FROM_FILE, StringUtil.EMPTY));
        } else if (!new File(originPath).exists()) {
            ExceptionManager.throwNonExistingFileException(LOAD_TEXT_FROM_FILE, originPath,
                    originPath);
            return Result.createNoExceptionResult(FILE_DOESNT_EXIST,
                    StringUtil.format(LOAD_TEXT_FROM_FILE, originPath));
        }

        FileReader fileReader;

        try {
            fileReader = new FileReader(new File(originPath));
        } catch (FileNotFoundException e) {
            return Result.createUnsuccessfulResult(FILE_NOT_FOUND, e, originPath,
                    StringUtil.format(LOAD_TEXT_FROM_FILE, originPath));

        }

        BufferedReader reader = new BufferedReader(fileReader);
        StringBuilder text = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
        } catch (IOException e) {
            return Result.createUnsuccessfulResult(ERROR_WHILE_READING, e, originPath);
        }

        return Result.createSuccessfulResult(text.toString(), TEXT_LOADED, originPath);
    }

    /**
     * Saves an object to {@code destinationPath}.
     *
     * @param object          Object to save.
     * @param destinationPath Path in the device where the object will be saved.
     * @return A {@code Result<File>} containing the file where the object has been saved or else
     * {@code null}.
     */
    public static Result<File> saveObject(@NonNull Object object, @NonNull Path destinationPath) {

        Result<File> result;

        if (destinationPath == null) {
            ExceptionManager.throwDestinationPathObjectException(SAVE_OBJECT, A_FILE);
            result = Result.createNoExceptionResult(NULL_DESTINATION_PATH,
                    StringUtil.format(SAVE_OBJECT, A_FILE));
        } else if (createPath(destinationPath)) {
            result = saveObject(object, destinationPath.getPath());
        } else {
            result = Result.createNoExceptionResult(FAILED,
                    StringUtil.format(SAVE_OBJECT, destinationPath.getPath()));
        }

        return result;
    }

    /**
     * Saves an object to {@code destinationPath}.
     *
     * @param object          Object to save.
     * @param destinationPath Path in the device where the object will be saved.
     * @return A {@code Result<File>} containing the file where the object has been saved or else
     * {@code null}.
     */
    public static Result<File> saveObject(@NonNull Object object, @NonNull String destinationPath) {

        if (TextUtils.isEmpty(destinationPath)) {
            ExceptionManager.throwDestinationPathException(SAVE_OBJECT, destinationPath, A_FILE);
            return Result.createNoExceptionResult(destinationPath == null ?
                            NULL_DESTINATION_PATH : EMPTY_DESTINATION_PATH,
                    StringUtil.format(SAVE_OBJECT, A_FILE));
        } else if (object == null) {
            ExceptionManager.throwNullObjectException(SAVE_OBJECT, destinationPath);
            return Result.createNoExceptionResult(NULL_OBJECT,
                    StringUtil.format(SAVE_OBJECT, destinationPath));
        } else if (!(object instanceof Serializable)) {
            ExceptionManager.throwNotSerializableException(SAVE_OBJECT, destinationPath);
            return Result.createNoExceptionResult(NOT_SERIALIZABLE, destinationPath);
        } else {
            ValidForSavingInfoInterface validityInfo = isValidForSaving(new File(destinationPath),
                    false, InvalidParameterException.PARAM_DESTINATION_PATH);
            if (!validityInfo.isValid() &&
                    validityInfo.getReason() != ValidForSavingInfoInterface.Invalidity.NONE) {
                ExceptionManager.throwInvalidFileForSaving(SAVE_OBJECT, validityInfo,
                        destinationPath);
                return Result.createInvalidFileResult(validityInfo,
                        StringUtil.format(SAVE_OBJECT, destinationPath));
            }
        }

        File fileToSave = new File(destinationPath);
        FileOutputStream streamToFile;

        try {
            streamToFile = new FileOutputStream(fileToSave, false);
        } catch (FileNotFoundException e) {
            return Result.createUnsuccessfulResult(DESTINATION_FOLDER_NOT_FOUND, e,
                    StringUtil.format(SAVE_OBJECT, destinationPath));
        }

        ObjectOutputStream objectStreamToFile;
        try {
            objectStreamToFile = new ObjectOutputStream(streamToFile);
        } catch (IOException e) {
            return Result.createUnsuccessfulResult(CANNOT_CREATE_OBJECT_INPUT_STREAM, e,
                    StringUtil.format(SAVE_OBJECT, destinationPath));
        }

        try {
            objectStreamToFile.writeObject(object);
            objectStreamToFile.flush();
            objectStreamToFile.close();
        } catch (IOException e) {
            return Result.createUnsuccessfulResult(CANNOT_CREATE_OBJECT_FILE, e, destinationPath);
        }

        return Result.createSuccessfulResult(new File(destinationPath), OBJECT_SAVED, destinationPath);
    }

    /**
     * Loads an object from a file.
     *
     * @param destinationPath Path to the file where the object is stored.
     * @param clazz           Class containing the object's type.
     * @param <T>             Object's type.
     * @return A {@code Result} containing the the object retrieved or null if it couldn't be
     * retrieved.
     */
    public static <T> Result<T> loadObject(@NonNull Path destinationPath, @NonNull Class<T> clazz) {

        Result<T> result;

        if (destinationPath == null) {
            ExceptionManager.throwDestinationPathObjectException(LOAD_OBJECT,
                    clazz.getSimpleName(), StringUtil.EMPTY);
            result = Result.createNoExceptionResult(NULL_DESTINATION_PATH,
                    StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), StringUtil.EMPTY));
        } else {
            result = loadObject(destinationPath.getPath(), clazz);
        }

        return result;
    }

    /**
     * Loads an object from a file.
     *
     * @param originPath Path to the file where the object is stored.
     * @param clazz      Class containing the object's type.
     * @param <T>        Object's type.
     * @return A {@code Result} containing the the object retrieved or null if it couldn't be
     * retrieved.
     */
    public static <T> Result<T> loadObject(@NonNull String originPath, @NonNull Class clazz) {

        if (TextUtils.isEmpty(originPath)) {
            ExceptionManager.throwDestinationPathException(LOAD_OBJECT, clazz.getSimpleName(),
                    StringUtil.EMPTY);
            return Result.createNoExceptionResult(originPath == null ?
                            NULL_ORIGIN_PATH : EMPTY_ORIGIN_PATH,
                    StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), StringUtil.EMPTY));
        } else if (! new File(originPath).exists()){
            ExceptionManager.throwNonExistingFileException(LOAD_OBJECT, originPath,
                    clazz.getSimpleName(), originPath);
            return Result.createNoExceptionResult(StringUtil.format(FILE_DOESNT_EXIST,
                    StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), originPath)));
        } else if (clazz == null) {
            ExceptionManager.throwNullClassException(LOAD_OBJECT, clazz.getSimpleName(), originPath);
            return Result.createNoExceptionResult(NULL_CLASS, originPath);
        }

        FileInputStream streamToFile;

        try {
            streamToFile = new FileInputStream(originPath);
        } catch (FileNotFoundException e) {
            return Result.createUnsuccessfulResult(FILE_NOT_FOUND, e,
                    StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), originPath));
        }

        ObjectInputStream streamToLoadObject;

        try {
            streamToLoadObject = new ObjectInputStream(streamToFile);
        } catch (IOException e) {
            return Result.createUnsuccessfulResult(CANNOT_CREATE_OBJECT_INPUT_STREAM, e,
                    StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), originPath));
        }

        try {
            T object = (T) streamToLoadObject.readObject();
            return Result.createSuccessfulResult(object, OBJECT_LOADED, originPath);
        } catch (ClassNotFoundException e) {
            return Result.createUnsuccessfulResult(CLASS_NOT_FOUND, e,
                    StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), originPath));
        } catch (IOException e) {
            return Result.createUnsuccessfulResult(CANNOT_READ_OBJECT, e,
                    StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), originPath));
        }
    }

    /**
     * Saves a {@code byte[]} in {@code destinationPath}. Creates the folders necessary to create
     * the path for you.
     *
     * @param byteArray       The {@code byte[]} that you want to save.
     * @param destinationPath The path to the file where the {@code byteArray} will be saved.
     * @return A {@code Result<File>} containing the {@code File} where the {@code byteArray} will
     * be saved.
     */
    public static Result<File> saveByteArray(@NonNull byte[] byteArray,
                                             @NonNull Path destinationPath) {

        Result<File> result;

        if (destinationPath == null) {
            ExceptionManager.throwDestinationPathObjectException(SAVE_BYTE_ARRAY, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(NULL_DESTINATION_PATH,
                    StringUtil.format(SAVE_BYTE_ARRAY, StringUtil.EMPTY));
        } else if (createPath(destinationPath)) {
            result = saveByteArray(byteArray, destinationPath.getPath());
        } else {
            result = Result.createNoExceptionResult(FAILED,
                    StringUtil.format(SAVE_BYTE_ARRAY, destinationPath.getPath()));
        }

        return result;
    }

    /**
     * Saves a {@code byte[]} in {@code destinationPath}. The folders to the path must exist or else
     * it will fail.
     *
     * @param byteArray       The {@code byte[]} that you want to save.
     * @param destinationPath The path to the file where the {@code byteArray} will be saved.
     * @return A {@code Result<File>} containing the {@code File} where the {@code byteArray} will
     * be saved.
     */
    public static Result<File> saveByteArray(@NonNull byte[] byteArray,
                                             @NonNull String destinationPath) {

        FileOutputStream streamToDestinationFile;

        if (TextUtils.isEmpty(destinationPath)) {
            ExceptionManager.throwDestinationPathException(SAVE_BYTE_ARRAY,
                    destinationPath, StringUtil.EMPTY);
            return Result.createNoExceptionResult(destinationPath == null ?
                            NULL_DESTINATION_PATH : EMPTY_DESTINATION_PATH,
                    StringUtil.format(SAVE_BYTE_ARRAY, StringUtil.EMPTY));
        } else if (byteArray == null) {
            ExceptionManager.throwNullByteArrayException(SAVE_BYTE_ARRAY, destinationPath);
            return Result.createNoExceptionResult(NULL_BYTE_ARRAY,
                    StringUtil.format(SAVE_BYTE_ARRAY, destinationPath));
        } else {
            ValidForSavingInfoInterface validityInfo = isValidForSaving(new File(destinationPath),
                    true, InvalidParameterException.PARAM_DESTINATION_PATH);
            if (!validityInfo.isValid() &&
                    validityInfo.getReason() != ValidForSavingInfoInterface.Invalidity.NONE) {
                ExceptionManager.throwInvalidFileForSaving(SAVE_BYTE_ARRAY, validityInfo,
                        destinationPath);
                return Result.createInvalidFileResult(validityInfo,
                        StringUtil.format(SAVE_BYTE_ARRAY, destinationPath));
            }
        }


        try {
            streamToDestinationFile = new FileOutputStream(new File(destinationPath), false);
        } catch (FileNotFoundException e) {
            return Result.createUnsuccessfulResult(DESTINATION_FOLDER_NOT_FOUND, e,
                    StringUtil.format(SAVE_BYTE_ARRAY, destinationPath));
        }

        try {
            streamToDestinationFile.write(byteArray, 0, byteArray.length);
            streamToDestinationFile.flush();
            streamToDestinationFile.close();
            return Result.createSuccessfulResult(new File(destinationPath), BYTE_ARRAY_SAVED,
                    destinationPath);
        } catch (IOException e) {
            return Result.createUnsuccessfulResult(ERROR_WRITING_BYTE_ARRAY, e,
                    StringUtil.format(SAVE_BYTE_ARRAY, destinationPath));
        }
    }

    /**
     * Saves every preference in the {@code SharedPreferences} object received to a file as a
     * {@code Map<String, ?}. It will create the intermediate folders for you.
     *
     * @param sharedPreferences Object containing the preferences to be saved.
     * @param destinationPath   Path to the file were the preferences will be saved. Don't assign an
     *                          extension to the file name.
     * @return A{@code Result<File>} containing the {@code File} where the mapped preferences are
     * stored.
     */
    public static Result<File> saveSharedPreferences(@NonNull SharedPreferences sharedPreferences,
                                                     @NonNull Path destinationPath) {

        Result<File> result;

        if (destinationPath == null) {
            ExceptionManager.throwDestinationPathObjectException(SAVE_SHARED_PREFERENCES, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(NULL_DESTINATION_PATH,
                    StringUtil.format(SAVE_SHARED_PREFERENCES, StringUtil.EMPTY));
        } else if (createPath(destinationPath)) {
            result = MemoryUtils.saveSharedPreferences(sharedPreferences, destinationPath.getPath());
        } else {
            result = Result.createNoExceptionResult(FAILED,
                    StringUtil.format(SAVE_SHARED_PREFERENCES, destinationPath.getPath()));
        }

        return result;
    }


    /**
     * Saves every preference in the {@code SharedPreferences} object received to a file as a
     * {@code Map<String, ?}. You must make sure that you have created every intermediate folder in
     * the path or else the saving will fail.
     *
     * @param sharedPreferences Object containing the preferences to be saved.
     * @param destinationPath   Path to the file were the preferences will be saved. Don't assign an
     *                          extension to the file name.
     * @return A{@code Result<File>} containing the {@code File} where the mapped preferences are
     * stored.
     */
    public static Result<File> saveSharedPreferences(@NonNull SharedPreferences sharedPreferences,
                                                     @NonNull String destinationPath) {

        Result<File> result;

        if (TextUtils.isEmpty(destinationPath)) {
            ExceptionManager.throwDestinationPathException(SAVE_SHARED_PREFERENCES, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(destinationPath == null ?
                            NULL_DESTINATION_PATH : EMPTY_DESTINATION_PATH,
                    StringUtil.format(SAVE_SHARED_PREFERENCES, StringUtil.EMPTY));
        } else if (sharedPreferences == null) {
            ExceptionManager.throwNullSharedPreferencesException(SAVE_SHARED_PREFERENCES, destinationPath);
            result = Result.createNoExceptionResult(NULL_SHAREDPREFERENCES_OBJECT,
                    StringUtil.format(SAVE_SHARED_PREFERENCES, destinationPath));
        } else {
            result = MemoryUtils.saveObject(sharedPreferences.getAll(), destinationPath);
        }

        return result;
    }


    /**
     * Retrieves a {@code Map<String, Object>} object with mapped shared preferences stored
     * previously in a file. Use this method if you want to retrieve the preferences and store them
     * your own way.
     *
     * @param originPath Path to the file were the preferences were saved.
     * @return A {@code Result<Map<String, Object>>} containing the mapped shared preferences.
     */
    public static Result<Map<String, Object>> loadSharedPreferences(@NonNull Path originPath) {

        Result<Map<String, Object>> result;

        if (originPath == null) {
            ExceptionManager.throwOriginPathObjectException(LOAD_SHARED_PREFERENCES, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(NULL_ORIGIN_PATH,
                    StringUtil.format(LOAD_SHARED_PREFERENCES, StringUtil.EMPTY));
        } else {
            result = loadSharedPreferences(originPath.getPath());
        }

        return result;
    }


    /**
     * Retrieves a {@code Map<String, Object>} object with mapped shared preferences stored
     * previously in a file. Use this method if you want to retrieve the preferences and store them
     * your own way.
     *
     * @param originPath Path to the file were the preferences were saved.
     * @return A {@code Result<Map<String, Object>>} containing the mapped shared preferences.
     */
    @SuppressWarnings("unchecked")
    public static Result<Map<String, Object>> loadSharedPreferences(@NonNull String originPath) {

        return MemoryUtils.loadObject(originPath, Map.class);
    }


    /**
     * Retrieves a {@code Map<String, Object>} object with mapped shared preferences stored
     * previously in a file and loads them in {@code sharedPreferences} .
     *
     * @param originPath        {@code Path} object pointing to the file were the preferences were saved.
     * @param sharedPreferences The {@code SharedPreferences} object where the preferences will be
     *                          restored to.
     * @return A {@code Result<SharedPreferences>} containing preferences retrieved.
     */
    public static Result<SharedPreferences> loadSharedPreferences(@NonNull Path originPath,
                                                                  @NonNull SharedPreferences sharedPreferences) {

        Result<SharedPreferences> result;

        if (originPath == null) {
            ExceptionManager.throwOriginPathObjectException(LOAD_SHARED_PREFERENCES, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(NULL_ORIGIN_PATH,
                    StringUtil.format(LOAD_SHARED_PREFERENCES, StringUtil.EMPTY));
        } else {
            result = loadSharedPreferences(originPath.getPath(), sharedPreferences);
        }

        return result;
    }


    /**
     * Retrieves a {@code Map<String, Object>} object with mapped shared preferences stored
     * previously in a file and loads them in the given {@code sharedPreferences}.
     *
     * @param originPath        Path to the file were the preferences were saved.
     * @param sharedPreferences The {@code SharedPreferences} object where the preferences will be
     *                          restored to.
     * @return A {@code Result<SharedPreferences>} containing the preferences retrieved or else
     * null.
     */
    public static Result<SharedPreferences> loadSharedPreferences(@NonNull String originPath,
                                                                  @NonNull SharedPreferences sharedPreferences) {

        if (TextUtils.isEmpty(originPath)) {
            ExceptionManager.throwOriginPathException(LOAD_SHARED_PREFERENCES, originPath, StringUtil.EMPTY);
            return Result.createNoExceptionResult(originPath == null ?
                            NULL_ORIGIN_PATH : EMPTY_ORIGIN_PATH,
                    StringUtil.format(LOAD_SHARED_PREFERENCES, StringUtil.EMPTY));
        } else if (sharedPreferences == null) {
            ExceptionManager.throwNullSharedPreferencesException(LOAD_SHARED_PREFERENCES, originPath);
            return Result.createNoExceptionResult(NULL_SHAREDPREFERENCES_OBJECT,
                    StringUtil.format(LOAD_SHARED_PREFERENCES, originPath));
        }

        Result<Map<String, Object>> result = loadSharedPreferences(originPath);
        String preferencesSummary;

        if (!result.isSuccessful()) {
            return Result.createNoExceptionResult(SHARED_PREFERENCES_NOT_RESTORED, originPath);
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Map<String, ?> mappedPreferences = result.getResult();
            String loadedPreferences = "Retrieved preferences:\n",
                    unparseablePreferences = "Unparseable preferences:\n";
            int unparsedPreferences = 0;

            for (Map.Entry<String, ?> singlePreference : mappedPreferences.entrySet()) {

                String preferenceKey = singlePreference.getKey();
                Object preferenceValue = singlePreference.getValue();
                boolean parseable = true;

                if (preferenceValue instanceof Boolean) {
                    editor.putBoolean(preferenceKey, ((Boolean) preferenceValue));
                } else if (preferenceValue instanceof Float) {
                    editor.putFloat(preferenceKey, ((Float) preferenceValue));
                } else if (preferenceValue instanceof Integer) {
                    editor.putInt(preferenceKey, ((Integer) preferenceValue));
                } else if (preferenceValue instanceof Long) {
                    editor.putLong(preferenceKey, ((Long) preferenceValue));
                } else if (preferenceValue instanceof String) {
                    editor.putString(preferenceKey, ((String) preferenceValue));
                } else if (preferenceValue instanceof Set) {
                    editor.putStringSet(preferenceKey, ((Set<String>) preferenceValue));
                } else {
                    parseable = false;
                    unparsedPreferences++;
                }

                // Adds a line indicating if the preference could be restored or not
                if (!parseable) {
                    unparseablePreferences += preferenceKey + "\n";
                } else {
                    loadedPreferences += preferenceKey + ": " + String.valueOf(preferenceValue) + "\n";
                }
            }

            preferencesSummary = loadedPreferences;
            if (unparsedPreferences > 0){
                preferencesSummary += unparseablePreferences;
            }

            // Creates the result to be returned
            if (unparsedPreferences == mappedPreferences.size()) {
                return Result.createNoExceptionResult(NO_PREFERENCES_RESTORED, originPath);
            } else {
                editor.apply();
                return Result.createSuccessfulResult(sharedPreferences,
                        unparsedPreferences == 0 ?
                                ALL_PREFERENCES_RESTORED : PREFERENCES_PARTIALLY_RESTORED,
                        originPath, preferencesSummary);
            }
        }
    }


    /**
     * Checks whether a file exists or not. Throws a {@code RuntimeException} if {@code pathToFile} is
     * {@code null}.
     *
     * @param pathToFile {@code Path} to the file to be checked.
     * @return {@code true} if the file exists or else {@code false}. Throws a
     * {@code RuntimeException} if {@code pathToFile} is {@code null}.
     */
    public static boolean exists(@NonNull Path pathToFile) {

        boolean exists;

        if (pathToFile == null) {
            ExceptionManager.throwNullPathToFileException(CHECK_FILE_EXISTS);
            log(StringUtil.format(NULL_PATH_OBJECT, CHECK_FILE_EXISTS), false);
            exists = false;
        } else {
            exists = exists(new File(pathToFile.getPath()));
        }

        return exists;
    }


    /**
     * Checks whether a file exists or not. Throws an exception if {@code pathToFile} is null or empty.
     *
     * @param pathToFile Path to the file to be checked.
     * @return {@code true} if the file exists or else {@code false}.
     */
    public static boolean exists(@NonNull String pathToFile) {

        boolean exists;

        if (TextUtils.isEmpty(pathToFile)) {
            ExceptionManager.throwNullPathToFileException(CHECK_FILE_EXISTS, pathToFile, StringUtil.EMPTY);
            log(StringUtil.format(pathToFile == null ? NULL_PATH : EMPTY_PATH,
                    CHECK_FILE_EXISTS), false);
            exists = false;
        } else {
            exists = exists(new File(pathToFile));
        }

        return exists;
    }


    /**
     * Checks whether {@code file} can be created. This is doesn't exists and the folder that
     * will contain it does or if it does exist and it's not a folder. This method is used when you
     * want to check if something can be saved to that place.
     *
     * @param file Path to the file to be checked.
     * @return {@code true} if the file exists or else {@code false}.
     */
    public static ValidForSavingInfoInterface isValidForSaving(
            @NonNull File file, boolean isFolder,
            @InvalidParameterException.InvalidParameter String parameter) {

        ValidForSavingInfoInterface.Invalidity invalidity;
        boolean isValid;

        // If the file exists
        if (file.exists()) {
            // It if should be a folder, verifies it's a folder
            if (isFolder){
                if (file.isDirectory()) {
                    invalidity = ValidForSavingInfoInterface.Invalidity.NONE;
                    isValid = true;
                } else {
                    invalidity = ValidForSavingInfoInterface.Invalidity.NOT_A_DIRECTORY;
                    isValid = false;
                }
            // If it should be a file, verifies it's a file
            } else {
                if (!file.isDirectory()) {
                    invalidity = ValidForSavingInfoInterface.Invalidity.NONE;
                    isValid = true;
                } else {
                    invalidity = ValidForSavingInfoInterface.Invalidity.IS_A_DIRECTORY;
                    isValid = false;
                }
            }
        // If the file doesn't exists, verifies that the folder that's going to contain it exists
        } else {
            File containerFolder = new File(getContainerFolder(file.getPath()));
            if (containerFolder.exists()){
                invalidity = ValidForSavingInfoInterface.Invalidity.NONE;
                isValid = true;
            } else {
                invalidity = ValidForSavingInfoInterface.Invalidity.CONTAINER_FOLDER_DOESNT_EXIST;
                isValid = false;
            }
        }

        return new CreateFileValidForSavingInfo(parameter, isValid, invalidity);
    }


    /**
     * Checks whether a file exists or not. Throws an exception if {@code file} is null or empty.
     *
     * @param file File to be checked.
     * @return {@code true} if {@code file} exists or else {@code false}.
     */
    public static boolean exists(@NonNull File file) {

        boolean exists;

        if (file == null) {
            ExceptionManager.throwNullFileException(CHECK_FILE_EXISTS);
            log(StringUtil.format(NULL_FILE, CHECK_FILE_EXISTS), false);
            exists = false;
        } else {
            exists = file.exists();
            if (exists) {
                log(StringUtil.format(FILE_EXISTS, file.getPath()), true);
            } else {
                log(StringUtil.format(STATEMENT_FILE_DOESNT_EXISTS, file.getPath()), false);
            }
        }

        return exists;
    }


    /**
     * Duplicates a file. Creates the destination folders for you.
     *
     * @param originPath      Path to file to be copied.
     * @param destinationPath Path where the file will be copied to.
     * @return A {@code Result} containing the duplicated {@code File} or null if duplication was
     * not possible.
     */
    public static Result<File> duplicateFile(@NonNull Path originPath,
                                             @NonNull Path destinationPath) {

        Result<File> result;

        if (originPath == null) {
            ExceptionManager.throwOriginPathObjectException(DUPLICATE_FILE, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(NULL_ORIGIN_PATH,
                    StringUtil.format(DUPLICATE_FILE, StringUtil.EMPTY));
        } else if (destinationPath == null) {
            ExceptionManager.throwDestinationPathException(DUPLICATE_FILE, originPath.getPath());
            result = Result.createNoExceptionResult(NULL_DESTINATION_PATH,
                    StringUtil.format(DUPLICATE_FILE, originPath.getPath()));
        } else if (createPath(destinationPath)) {
            result = duplicateFile(originPath.getPath(), destinationPath.getPath());
        } else {
            result = Result.createNoExceptionResult(FAILED,
                    StringUtil.format(DUPLICATE_FILE, originPath.getPath()));
        }

        return result;
    }


    /**
     * Duplicates a file. The destination folders must exist or else copy will fail.
     *
     * @param originPath      Path to file to be copied.
     * @param destinationPath Path where the file will be copied to.
     * @return A {@code Result} containing the duplicated {@code File} or null if duplication was
     * not possible.
     */
    public static Result<File> duplicateFile(@NonNull String originPath,
                                             @NonNull String destinationPath) {

        Result<File> result;

        if (TextUtils.isEmpty(originPath)) {
            ExceptionManager.throwOriginPathException(DUPLICATE_FILE, originPath, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(originPath == null ?
                            NULL_ORIGIN_PATH : EMPTY_ORIGIN_PATH,
                    StringUtil.format(DUPLICATE_FILE, StringUtil.EMPTY));
        } else if (TextUtils.isEmpty(destinationPath)) {
            ExceptionManager.throwDestinationPathException(DUPLICATE_FILE, destinationPath,
                    originPath);
            result = Result.createNoExceptionResult(originPath == null ?
                            NULL_DESTINATION_PATH : EMPTY_DESTINATION_PATH,
                    StringUtil.format(DUPLICATE_FILE, originPath));
        } else if (!new File(originPath).exists()) {
            ExceptionManager.throwNonExistingFileException(DUPLICATE_FILE, originPath, originPath);
            result = Result.createNoExceptionResult(FILE_DOESNT_EXIST,
                    StringUtil.format(DUPLICATE_FILE, originPath));
        } else if (!new File(destinationPath).exists()) {
            ExceptionManager.throwNonExistingFileException(DUPLICATE_FILE, destinationPath,
                    originPath);
            result = Result.createNoExceptionResult(DESTINATION_FILE_DOESNT_EXIST,
                    StringUtil.format(DUPLICATE_FILE, originPath), destinationPath);
        } else {

            try {
                FileInputStream streamToOriginFile = new FileInputStream(new File(originPath));
                result = copyFromInputStream(originPath, streamToOriginFile, destinationPath);
            } catch (FileNotFoundException e) {
                result = Result.createUnsuccessfulResult(DESTINATION_FILE_NOT_FOUND, e,
                        StringUtil.format(DUPLICATE_FILE, originPath), destinationPath);
            }
        }

        return result;
    }


    /**
     * Deletes the file in {@code pathToFile}.
     *
     * @param pathToFile              Path to the file.
     * @param clearContentIfDirectory If {@code pathToFile} refers to a folder, {@code true} will
     *                                delete every file in the folder in order to delete the folder.
     *                                If {@code false} the folder and its content will be kept.
     * @return A {@code Result} returning {@code isSuccessful() == true} if the file could be
     * deleted.
     */
    public static Result deleteFile(@NonNull Path pathToFile, boolean clearContentIfDirectory) {

        Result result;

        if (pathToFile == null) {
            ExceptionManager.throwNullPathObjectException(DELETE_FILE, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(NULL_PATH_OBJECT,
                    StringUtil.format(DELETE_FILE, StringUtil.EMPTY));
        } else {
            result = deleteFile(new File(pathToFile.getPath()), clearContentIfDirectory);
        }

        return result;
    }


    /**
     * Deletes the file in {@code pathToFile}. If it's a folder, deletes its content only if
     * {@code shouldClearIfDirectory} is {@code true}.
     *
     * @param pathToFile             Path to the file.
     * @param shouldClearIfDirectory If {@code pathToFile} refers to a folder, {@code true} will delete
     *                               every file in the folder in order to delete the folder. If
     *                               {@code false} the folder and its content will be kept.
     * @return A {@code Result} containing nothing but {@code true} or {@code false} in you call
     * method {@link Result#isSuccessful()} on it depending on whether the file was deleted or not.
     */
    public static Result deleteFile(@NonNull String pathToFile, boolean shouldClearIfDirectory) {

        return deleteFile(new File(pathToFile), shouldClearIfDirectory);

    }


    /**
     * Deletes {@code file}. If it's a folder, deletes its content only if
     * {@code shouldClearIfDirectory} is {@code true}.
     *
     * @param file                   File to be deleted.
     * @param shouldClearIfDirectory If {@code file} refers to a folder, {@code true} will delete
     *                               every file in the folder in order to delete the folder. If
     *                               {@code false} the folder and its content will be kept.
     * @return A {@code Result} containing nothing but {@code true} or {@code false} in you call
     * method {@link Result#isSuccessful()} on it depending on whether the file was deleted or not.
     */

    public static Result deleteFile(@NonNull File file, boolean shouldClearIfDirectory) {

        Result result;

        if (file == null) {
            ExceptionManager.throwNullFileException(DELETE_FILE, StringUtil.EMPTY);
            result = Result.createNoExceptionResult(NULL_FILE,
                    StringUtil.format(DELETE_FILE, StringUtil.EMPTY));
        } else if (!file.exists()) {
            ExceptionManager.throwNonExistingFileException(DELETE_FILE, file.getPath(),
                    file.getPath());
            result = Result.createNoExceptionResult(FILE_DOESNT_EXIST,
                    StringUtil.format(DELETE_FILE, file.getPath()));
            // If the file exists, tries to delete it
        } else {

            boolean canBeDeleted;

            // If it's a file, it can be deleted
            if (!file.isDirectory()) {
                canBeDeleted = true;

                // If it's a directory, empties it first
            } else {

                boolean isEmpty;

                // If the folder is empty, deletes nothing
                if (file.list() == null || file.list().length == 0) {
                    isEmpty = true;
                    // If the folder is not empty, only clears its content if shouldClearIfDirectory
                    // is true
                } else if (shouldClearIfDirectory) {
                    isEmpty = clearFolder(file.getPath());
                    // If eventually folder is not empty, returns failure
                    if (!isEmpty) {
                        result = Result.createNoExceptionResult(FOLDER_NOT_CLEARED,
                                StringUtil.format(DELETE_FILE, file.getPath()));
                    }
                    // If shouldClearIfDirectory is false, it won't be able to delete the directory
                } else {
                    isEmpty = false;
                    result = Result.createNoExceptionResult(DIRECTORY_HAS_FILES,
                            StringUtil.format(DELETE_FILE, file.getPath()));
                }

                canBeDeleted = isEmpty;
            }

            if (canBeDeleted) {
                boolean deleted = file.delete();
                if (deleted) {
                    result = Result.createSuccessfulResult(file, FILE_DELETED, file.getPath());
                } else {
                    result = Result.createNoExceptionResult(CANNOT_DELETE, file.getPath());
                }
            } else {
                result = Result.createNoExceptionResult(FAILED, StringUtil.format(DELETE_FILE, file.getPath()));
            }
        }

        return result;
    }


    /**
     * Deletes the content of a folder. If one of the files cannot be deleted, stops deleting.
     *
     * @param pathToFolder Folder to be cleared.
     * @return {@code true} if the content was cleared, or {@code false} if one of the files could
     * not be deleted or after deleting every file the folder is still not empty.
     */
    public static boolean clearFolder(@NonNull String pathToFolder) {

        boolean folderCleared;

        if (TextUtils.isEmpty(pathToFolder)) {
            ExceptionManager.throwPathToFileException(CLEAR_FOLDER, StringUtil.EMPTY);
            log(StringUtil.format(pathToFolder == null ? NULL_PATH : EMPTY_PATH,
                    StringUtil.format(CLEAR_FOLDER, StringUtil.EMPTY)), false);
            folderCleared = false;
        } else if (!new File(pathToFolder).exists()) {
            ExceptionManager.throwNonExistingFileException(CLEAR_FOLDER, pathToFolder, pathToFolder);
            log(StringUtil.format(FOLDER_DOESNT_EXIST,
                    StringUtil.format(CLEAR_FOLDER, pathToFolder)), false);
            folderCleared = false;
        } else {

            File folder = new File(pathToFolder);

            // If the folder is empty, just logs it
            if (isFolderEmpty(folder, false)) {
                folderCleared = true;
                log(StringUtil.format(FOLDER_ALREADY_EMPTY, folder.getPath()), true);

                // If the folder is not empty, deletes every file in the folder
            } else {
                boolean contentDeleted = true;

                for (File file : folder.listFiles()) {

                    // If some of the files are directories, makes a recursive call to clear them
                    if (file.isDirectory()) {

                        boolean cleared = clearFolder(file.getPath());

                        // If the content couldn't be deleted, returns false
                        if (!cleared) {
                            log(String.format(CANNOT_DELETE_FOLDER,
                                    StringUtil.format(CLEAR_FOLDER, folder.getPath()),
                                    file.getName()), false);
                            contentDeleted = false;
                            break;
                        }
                    }

                    // Deletes de folder
                    boolean fileDeleted = deleteFile(file, true).isSuccessful();

                    // If the file could not be deleted, returns false and stops deleting
                    if (!fileDeleted) {
                        log(String.format(CANNOT_DELETE, file.getPath()), false);
                        contentDeleted = false;
                        break;
                        // If the file was deleted, logs it
                    } else {
                        log(String.format(FILE_DELETED, file.getPath()), true);
                    }
                }

                folderCleared = contentDeleted;
            }
        }

        return folderCleared && isFolderEmpty(new File(pathToFolder), true);
    }


    /**
     * Returns {@code true} if the folder is empty.
     *
     * @param folder        The folder to be checked.
     * @param shouldBeEmpty Pass false if you're calling this method on your own. This method only
     *                      receives {@code true} when called from {@link #clearFolder(String)}.
     * @return {@code true} if the folder is empty or {@code false} if it's not empty or it's not
     * a directory.
     */
    public static boolean isFolderEmpty(@NonNull File folder, boolean shouldBeEmpty) {

        boolean isFolderEmpty;

        if (folder == null) {
            ExceptionManager.throwNullFileException(CHECK_IS_EMPTY, StringUtil.EMPTY);
            log(StringUtil.format(NULL_FILE,
                    StringUtil.format(CHECK_FILE_EXISTS, StringUtil.EMPTY)),
                    false);
            isFolderEmpty = false;
        } else if (!folder.exists()) {
            ExceptionManager.throwNonExistingFileException(CHECK_IS_EMPTY, folder.getPath(),
                    folder.getPath());
            log(StringUtil.format(FOLDER_DOESNT_EXIST,
                    StringUtil.format(CHECK_FILE_EXISTS, StringUtil.EMPTY)),
                    false);
            isFolderEmpty = false;
        } else if (!folder.isDirectory()) {
            ExceptionManager.throwNotADirectoryException(CHECK_IS_EMPTY, folder.getPath());
            log(StringUtil.format(NOT_A_DIRECTORY,
                    StringUtil.format(CHECK_IS_EMPTY, folder.getPath())),
                    false);
            isFolderEmpty = false;
        } else {
            isFolderEmpty = folder.list() == null || folder.list().length == 0;
            if (!isFolderEmpty) {
                if (shouldBeEmpty) {
                    log(StringUtil.format(DELETED_BUT_STILL_NOT_EMPTY, folder.getPath()), true);
                } else {
                    log(StringUtil.format(FOLDER_IS_NOT_EMPTY, folder.getPath()), true);
                }
            } else {
                log(String.format(FOLDER_EMPTY, folder.getPath()), true);
            }
        }
        return isFolderEmpty;
    }


    /**
     * Creates folder {@code pathToFolder}.
     *
     * @param pathToFolder Path to the folder including the folder name.
     * @return A {@code Result<File>} whose method {@link Result#isSuccessful()} returns
     * {@code true} if the folder was created or already existed, or {@code false} if the folder
     * could not be created.
     */
    public static Result<File> createFolder(@NonNull String pathToFolder) {

        Result<File> result;

        if (TextUtils.isEmpty(pathToFolder)) {
            ExceptionManager.throwPathToFolderException(CREATE_A_FOLDER, pathToFolder);
            result = Result.createNoExceptionResult(NULL_PATH, CREATE_A_FOLDER);
        } else {
            File folderFile = new File(pathToFolder);
            if (folderFile.exists() && folderFile.isDirectory()) {
                result = Result.createSuccessfulResult(folderFile, ALREADY_EXISTS, pathToFolder);
            } else {
                boolean created = folderFile.mkdir();
                if (created) {
                    result = Result.createSuccessfulResult(folderFile, CREATED, pathToFolder);
                } else {
                    result = Result.createNoExceptionResult(CANNOT_CREATE, pathToFolder);
                }
            }
        }

        return result;
    }


    /**
     * Creates the folders necessary to generate the path.
     *
     * @param path Contains the path to create.
     * @return The path to the file if it could be created or {@code null} if it couldn't.
     */
    public static boolean createPath(@NonNull Path path) {

        boolean pathCreated = false;

        if (path == null) {
            ExceptionManager.throwNullPathObjectException(CREATE_PATH);
            log(StringUtil.format(NULL_PATH_OBJECT, CREATE_PATH), false);
            pathCreated = false;
        } else if (path.getFolders().isEmpty()) {
            pathCreated = true;
            log(PATH_CONTAINED_NO_FOLDERS, true);
        } else {
            String basePath = path.getBasePath();

            for (String folderName : path.getFolders()) {
                String folderPath = basePath + "/" + folderName;
                Result<File> folderCreatedResult = createFolder(folderPath);

                if (folderCreatedResult.isSuccessful()) {
                    basePath = folderPath;
                    pathCreated = true;
                } else {
                    pathCreated = false;
                    break;
                }
            }

            if (pathCreated) {
                log(StringUtil.format(PATH_CREATED, path.getFolders().toString()), true);
            } else {
                log(StringUtil.format(COULD_NOT_CREATE_PATH, path.getPath()), false);
            }
        }

        return pathCreated;
    }


    /**
     * <p>Checks files in the device to obtain the path representing the deepest existing file
     * in {@code path}.</p>
     * <p>For example, you pass
     * {@code file:///storage/sdcard/myFiles/mySpecialFiles/myFile.png}. This method begins
     * by checking whether folder {@code storage} exists. If so, it will check if
     * {@code storage/sdcard} folder exists, and so on until finding deepest existing file in the
     * path.</p>
     * <p>Suppose, in this example, that {@code mySpecialFiles} doesn't exist. The result of the
     * method would be {@code file:///storage/sdcard/myFiles}, which is the deepes existing
     * file in the path.</p>
     *
     * @param path The path to be checked.
     * @return The longest path possible representing the deepest existing file in the device, or
     * empty if not even the file root exists.
     */
    public static String getLongestValidPath(@NonNull String path) {

        String longestValidPath;

        if (TextUtils.isEmpty(path)) {
            ExceptionManager.throwPathException(GET_LONGEST_VALID_PATH, path);
            longestValidPath = StringUtil.EMPTY;
        } else {
            File file = new File(path);
            if (file.exists()) {
                longestValidPath = path;
            } else {
                String aux = "";
                StringTokenizer tokens = new StringTokenizer(path, "/");
                while (tokens.hasMoreTokens()) {
                    String pathToVerify = aux + "/" + tokens.nextToken();
                    file = new File(pathToVerify);
                    if (file.exists()) {
                        aux = pathToVerify;
                    } else {
                        break;
                    }
                }

                longestValidPath = aux;
            }
        }

        return longestValidPath;
    }


    /**
     * Returns true if the path {@code path} references a folder. Throws an exception if
     * {@code path} is {@code null} or empty.
     *
     * @param path Path to be checked.
     * @return {@code true} if {@code path} is a folder. Else, returns {@code false}.
     */
    public static boolean isDirectory(@NonNull String path) {

        boolean isDirectory;

        if (TextUtils.isEmpty(path)) {
            ExceptionManager.throwFileNameException(CHECK_FILE_IS_A_FOLDER, path);
            log(StringUtil.format(path == null ?
                    NULL_PATH_OBJECT : EMPTY_PATH, CHECK_FILE_IS_A_FOLDER), false);
            isDirectory = false;
        } else {
            isDirectory = isDirectory(new File(path));
        }

        return isDirectory;
    }


    /**
     * Returns true if {@code file} references a folder. Throws an exception if
     * {@code file} doesn't exist.
     *
     * @param file File to be checked.
     * @return {@code true} if {@code path} is a folder. Else, returns {@code false}.
     */
    public static boolean isDirectory(@NonNull File file) {

        boolean isDirectory;

        if (file == null) {
            ExceptionManager.throwNullFileException(CHECK_FILE_IS_A_FOLDER, StringUtil.EMPTY);
            log(StringUtil.format(NULL_FILE,
                    StringUtil.format(CHECK_FILE_IS_A_FOLDER, StringUtil.EMPTY)), false);
            isDirectory = false;
        } else if (!file.exists()) {
            ExceptionManager.throwNonExistingFileException(CHECK_FILE_IS_FOLDER, file.getPath(),
                    file.getPath());
            isDirectory = false;
        } else {
            if (file.isDirectory()) {
                log(StringUtil.format(IS_A_FOLDER, file.getPath()), true);
                isDirectory = true;
            } else {
                log(StringUtil.format(NOT_A_FOLDER, file.getPath()), true);
                isDirectory = false;
            }
        }

        return isDirectory;
    }


    /**
     * Returns the names of the files in {@code pathToFolder}. Throws an exception if
     * {@code pathToFolder} is null or empty.
     *
     * @param folder The path to the directory.
     * @return A {@code String} containing a list of the names of the files in the
     * {@code pathToFolder} separated by line breaks. If {@code pathToFolder} contained no files,
     * return an empty {@code String}.
     */
    public static String getFilesInDirectory(@NonNull File folder) {

        StringBuilder filesInDirectory = new StringBuilder();

        if (!folder.isDirectory()) {
            ExceptionManager.throwNotADirectoryException(GET_FILES_IN_DIRECTORY, folder.getPath());
            log(String.format(NOT_A_DIRECTORY,
                    StringUtil.format(GET_FILES_IN_A_DIRECTORY, folder.getPath())),
                    false);
            filesInDirectory.append(StringUtil.EMPTY);
        } else {
            if (folder.list().length > 0) {
                int filesAmount = folder.listFiles().length;
                for (int i = 0; i < filesAmount; i++) {
                    File fileContained = folder.listFiles()[i];
                    filesInDirectory.append(fileContained.getName());
                    if (i == filesAmount - 1) {
                        filesInDirectory.append(".");
                    } else {
                        filesInDirectory.append(", ");
                    }
                }
            } else {
                filesInDirectory.append(StringUtil.EMPTY);
            }
        }

        return filesInDirectory.toString();
    }


    /**
     * Generates a file tree of the folder provided.
     *
     * @param folder Folder to create the file tree from.
     * @return A {@code Result<String>} with the generated file tree.
     */
    public Result<String> getFileTree(@NonNull File folder) {

        Result<String> result;

        if (folder == null) {
            ExceptionManager.throwNullFileException(GENERATE_A_FOLDER_FILE_TREE);
            result = Result.createNoExceptionResult(StringUtil.format(NULL_FILE,
                    StringUtil.format(CHECK_FILE_IS_A_FOLDER, StringUtil.EMPTY)));
        } else if (!folder.exists()) {
            ExceptionManager.throwNonExistingFileException(GENERATE_FILE_TREE, folder.getPath(),
                    folder.getPath());
            result = Result.createNoExceptionResult(String.format(FOLDER_DOESNT_EXIST,
                    StringUtil.format(GENERATE_FILE_TREE, folder.getPath())));
        } else if (!folder.isDirectory()) {
            ExceptionManager.throwNotADirectoryException(GENERATE_FILE_TREE, folder.getPath());
            result = Result.createNoExceptionResult(String.format(NOT_A_DIRECTORY,
                    StringUtil.format(GENERATE_FILE_TREE, folder.getPath())));
        } else {
            String report = "Files in " + folder.getPath() + ":\n";

            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    report += printContent(file, 1);
                } else {
                    report += "\t\\" + file.getName() + "\n";
                }
            }

            result = Result.createSuccessfulResult(report, FILE_TREE_GENERATED);
        }

        return result;
    }

    /**
     * Generates a tab formatted report with the contents of a folder.
     *
     * @param folder    The folder whose content has to be printed.
     * @param tabAmount The amount of tabAmount that must be appended at the beggining of every line,
     *                  depending on the depth level.
     * @return A {@code String} containing the list of files in {@code folder}.
     */
    private static String printContent(@NonNull File folder, int tabAmount) {

        String tabs = StringUtil.getTabs(tabAmount);
        String folderReport = tabs + "\\" + folder.getName() + "\n";

        for (File file : folder.listFiles()) {

            if (isDirectory(folder)) {
                tabAmount++;
                folderReport += printContent(file, tabAmount);
            } else {
                folderReport += tabs + "\t\\" + file.getName() + "\n";
            }
        }

        return folderReport;
    }

    /**Returns the path to the folder containing the file in {@code paht}.
     *
     * @param path  The path to the file whose container folder we want to get.
     */
    private static String getContainerFolder(@NonNull String path){
        return path.substring(0, path.lastIndexOf("/"));
    }

    /**
     * Tells whether there is any external memory inserted in the device.
     *
     * @return {@code true} if there external memory is available or else {@code false}.
     */
    public static boolean isExternalMemoryAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            log("External memory is available.", false);
            return true;
        }
        log("External memory is not available.", false);
        return false;
    }


    /**
     * Enables or disables displaying information in the logging. The tag used is "MemoryUtils".
     * Logging is enabled by default.
     *
     * @param enable Sets the debugging to {@code true} or {@code false} depending on the value of
     *               {@code enable}.
     */
    public static void setLoggingEnabled(boolean enable) {
        Logger.setLoggingEnabled(enable);
    }

    /**
     * Enables or disables throwing an exception when passing an invalid parameter. "Invalid"
     * parameters are those that will cause the method to never execute correctly and depend only on
     * the developer. This value is {@code true} by default and you're discouraged from turning it
     * off, since you should correct these faults right away during development.
     *
     * @param enable If {@code true} it will throw exceptions. Else, it won't.
     */
    public static void setThrowingExceptions(boolean enable) {
        ExceptionManager.setThrowingExceptions(enable);
    }

    /**
     * This method uses {@link Logger#log} to log messages. Logs using this method will not be
     * shown if you disabled logging by calling {@link #setLoggingEnabled(boolean)} passing
     * {@code false.}
     *
     * @param message The message to be displayed.
     * @param isInfo  If {@code true}, {@code Log.i()} will be used. Else, {@code Log.e()} will.
     */
    private static void log(String message, boolean isInfo) {
        Logger.log(TAG, message, isInfo);
    }
}
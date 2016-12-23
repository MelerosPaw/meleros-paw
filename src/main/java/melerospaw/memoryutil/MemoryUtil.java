package melerospaw.memoryutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

import melerospaw.memoryutil.ExceptionManager.ExceptionType;
import melerospaw.memoryutil.validation.ValidationInfoInterface;
import melerospaw.memoryutil.validation.Validator;

public class MemoryUtil {

    private static final String TAG = MemoryUtil.class.getSimpleName();

    private static final String ALL_PREFERENCES_RESTORED = "Preferences in %1$s were restored.\n%2$s";
    private static final String ALREADY_EXISTS = "Folder %1$s already exists.";
    private static final String ASSET_NOT_FOUND = "File %1$s was not found in assets directory.";
    private static final String BYTE_ARRAY_SAVED = "Byte array was saved to %1$s.";
    private static final String CANNOT_COPY = "Couldn't copy file %1$s to %2$s.";
    private static final String CANNOT_CREATE = "Couldn't create folder %1$s.";
    private static final String CANNOT_CREATE_OBJECT_FILE = "Cannot save the given object to file %1$s because there was an error while writing.";
    private static final String CANNOT_CREATE_OBJECT_INPUT_STREAM = "Cannot %1$s because it was not possible to create an ObjectOutputStream.";
    private static final String CANNOT_DELETE = "File %1$s couldn't be deleted.";
    private static final String CANNOT_DELETE_FOLDER = "Cannot %1$s because inner folder %2$s cannot be deleted.";
    private static final String CANNOT_READ_OBJECT = "Cannot %1$s because the object could not be read.";
    private static final String CANNOT_SAVE_TEXT = "Cannot save text to file %1$s.";
    private static final String CLASS_NOT_FOUND = "Cannot %1$s because such class was not found.";
    private static final String CLEAR_FOLDER = "clear folder %1$s";
    private static final String COPY_FROM_INPUT_STREAM = "copy %1$s from an InputStream";
    private static final String COULD_NOT_CREATE_PATH = "Could not create path %1$s.";
    private static final String CREATED = "Folder %1$s was created.";
    private static final String DELETED_BUT_STILL_NOT_EMPTY = "All files in folder %1$s were deleted but still it's not empty.";
    private static final String DELETE_FILE = "delete file %1$s";
    private static final String DESTINATION_FILE_NOT_FOUND = "Cannot %1$s because destination file %2$s was not found.";
    private static final String DESTINATION_FOLDER_NOT_FOUND = "Cannot %1$s because the destination folder was not found.";
    private static final String DIRECTORY_HAS_FILES = "Cannot %1$s because it is a directory containing other files. If you want to delete it, pass shouldClearIfDirectory = true as second parameter to method deleteFile().";
    private static final String DUPLICATE_FILE = "duplicate file %1$s";
    private static final String ERROR_WHILE_READING = "An error occurred while reading text fom file %1$s.";
    private static final String ERROR_WRITING_BYTE_ARRAY = "Cannot %1$s because there was an error while writing the byte[].";
    private static final String FAILED = "Cannot %1$s. FAILED.";
    private static final String FILE_COPIED = "File %1$s was copied to %2$s.";
    private static final String FILE_DELETED = "File %1$s was deleted.";
    private static final String FILE_DOESNT_EXIST = "Cannot %1$s because the file doesn't exist.";
    private static final String FILE_EXISTS = "File %1$s exists.";
    private static final String FILE_NOT_FOUND = "Cannot %1$s because the file was not found.";
    private static final String FILE_TREE_GENERATED = "File tree generated.";
    private static final String FOLDER_ALREADY_EMPTY = "Folder %1$s was already empty.";
    private static final String FOLDER_EMPTY = "Folder %1$s is empty.";
    private static final String FOLDER_IS_NOT_EMPTY = "Folder %1$s is not empty.";
    private static final String FOLDER_NOT_CLEARED = "Cannot %1$s because it's a folder but it could not be cleared.";
    private static final String IMAGE_LOADED_FROM_ASSETS = "Image %1$s was retrieved from assets.";
    private static final String IMAGE_NOT_FOUND_IN_ASSETS = "Image %1$s was not found in assets.";
    private static final String IS_A_FOLDER = "File %1$s is a folder.";
    private static final String LOAD_OBJECT = "load a/an %1$s object from file %2$s";
    private static final String LOAD_TEXT_FROM_FILE = "load text from file %1$s";
    private static final String NOT_A_FOLDER = "File %1$s is not a folder.";
    private static final String NO_PREFERENCES_RESTORED = "No preferences at all were restored from %1$s.";
    private static final String OBJECT_LOADED = "Object retrieved from %1$s.";
    private static final String OBJECT_SAVED = "Object saved to %1$s.";
    private static final String PATH_CONTAINED_NO_FOLDERS = "Path object %1$s contains no folders, so no intermediate folders were created to create it.";
    private static final String PATH_CREATED = "Folders %1$s were created or already existed. Path created.";
    private static final String PREFERENCES_PARTIALLY_RESTORED = "Stored preferences in %1$s could only be partially restored.\n%2$s";
    private static final String SAVE_BYTE_ARRAY = "save a byte array to file %1$s";
    private static final String SAVE_OBJECT = "save an object to file %1$s";
    private static final String SAVE_SHARED_PREFERENCES = "save SharedPreferences to file %1$s";
    private static final String SAVE_TEXT_TO_FILE = "save text to file %1$s";
    private static final String SHARED_PREFERENCES_NOT_RESTORED = "Cannot load SharedPreferences preferences from %1$s.";
    private static final String STATEMENT_FILE_DOESNT_EXISTS = "File %1$s doesn't exists.";
    private static final String TEXT_FILE_SAVED = "Text was saved to %1$s.";
    private static final String TEXT_LOADED = "Text was loaded from file %1$s.";
    private static final String SAVE_BITMAP = "save a bitmap to %1$s";
    private static final String UNEXPECTED_IO_ERROR = "Cannot %1$s because there was an unexpected IO error.";
    private static final String BITMAP_SAVED = "Bitmap was saved to %1$s.";
    private static final String LOAD_BITMAP = "load image %1$s";
    private static final String BITMAP_LOADED = "Bitmap was loaded from %1$s.";
    private static final String CANNOT_CONVERT_TO_BITMAP = "Cannot %1$s because the file cannot be converted to a Bitmap object.";
    private static final String IS_VALID_FOR_SAVING = "Path %1$s is valid for saving to it.";
    private static final String FOLDER_CLEARED = "Folder %1$s was cleared.";
    private static final String NULL_FILE_FROM_URI = "Cannot %1$s because the bitmap obtained from %2$s happens to be null. The uri may not be referencing an image.";
    private static final String LOAD_BITMAP_FROM_URI = "load bitmap from uri %1$s";
    private static final String BITMAP_LOADED_FROM_URI = "Bitmap was loaded from uri %1$s.";


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

        ValidationInfoInterface info = Validator.validateCopyFromInputStream(inputStream, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else if (createPath(destinationPath)) {
            result = copyFromInputStream(originPath, inputStream, destinationPath.getPath());
        } else {
            result = Result.createNoExceptionResult(FAILED,
                    StringUtil.format(COPY_FROM_INPUT_STREAM, originPath));
        }

        return result;
    }


    // INCLUDED

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
        Result<File> result;

        ValidationInfoInterface info = Validator.validateCopyFromInputStream(inputStream,
                destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            originPath = StringUtil.getStringOrEmpty(originPath);
            try {
                OutputStream streamToSaveFile = new FileOutputStream(destinationPath);
                byte[] buffer = new byte[1024];
                int length;

                try {
                    while ((length = inputStream.read(buffer)) > 0) {
                        streamToSaveFile.write(buffer, 0, length);
                    }
                    streamToSaveFile.flush();
                    streamToSaveFile.close();
                    inputStream.close();
                    result = Result.createSuccessfulResult(new File(destinationPath), FILE_COPIED,
                            originPath, destinationPath);
                } catch (IOException e) {
                    result = Result.createUnsuccessfulResult(CANNOT_COPY, e, originPath, destinationPath);
                }

            } catch (FileNotFoundException e) {
                result = Result.createUnsuccessfulResult(DESTINATION_FOLDER_NOT_FOUND, e,
                        destinationPath, StringUtil.format(COPY_FROM_INPUT_STREAM, StringUtil.EMPTY));
            }
        }
        return result;
    }

    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateImportFromAssets(context, fileName,
                destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            if (createPath(destinationPath)) {
                result = importFromAssets(context, fileName, destinationPath.getPath());
            } else {
                result = Result.createNoExceptionResult(FAILED,
                        StringUtil.format(COPY_FROM_INPUT_STREAM, fileName));
            }
        }

        return result;
    }


    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateImportFromAssets(context, fileName,
                destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {

            InputStream streamToAssets;

            try {
                streamToAssets = context.getAssets().open(fileName);
                result = copyFromInputStream(fileName, streamToAssets, destinationPath);
            } catch (IOException e) {
                ExceptionManager.throwCaughtException(ASSET_NOT_FOUND, ExceptionType.FILE_NOT_FOUND);
                result = Result.createUnsuccessfulResult(ASSET_NOT_FOUND, e, fileName);
            }
        }
        return result;
    }


    /**
     * Imports a file in assets folder to the database folder.
     *
     * @param context      Needed to access the database folder.
     * @param databaseName Name of the file in assets. It must be the same as the database parameter.
     * @return A {@code Result<File>} containing a {@code File} pointing to the file where the
     * database has been copied to.
     */
    public static Result<File> importDatabaseFromAssets(@NonNull Context context,
                                                        @NonNull String databaseName) {

        Result<File> result;

        ValidationInfoInterface info = Validator.validateImportDatabaseFromAssets(context, databaseName);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            Path pathToDatabase = new Path.Builder(context)
                    .databaseDirectory(databaseName)
                    .build();

            result = importFromAssets(context, databaseName, pathToDatabase);
        }

        return result;
    }


    // INCLUDED

    /**
     * Gets a {@code Bitmap} from the assets folder.
     *
     * @param context  Context to access the assets folder.
     * @param fileName Name of the picture file in assets with extension, such as
     *                 <i>image.jpg</i>.
     * @return A{@code Result<Bitmap>} with the picture or {@code null} if the image doesn't exist
     * in the assets directory.
     */
    public static Result<Bitmap> loadBitmapFromAssets(@NonNull Context context,
                                                      @NonNull String fileName) {

        Result<Bitmap> result;

        ValidationInfoInterface validationInfo = Validator.validateLoadBitmapFromAssets(context, fileName);
        if (!validationInfo.isValid()) {
            result = reportInvalidParameter(validationInfo);
        } else {
            try {
                Bitmap image = BitmapFactory.decodeStream(context.getAssets().open(fileName));
                result = Result.createSuccessfulResult(image, IMAGE_LOADED_FROM_ASSETS, fileName);
            } catch (IOException e) {
                ExceptionManager.throwCaughtException(
                        StringUtil.format(IMAGE_NOT_FOUND_IN_ASSETS, fileName),
                        ExceptionType.FILE_NOT_FOUND);
                result = Result.createUnsuccessfulResult(IMAGE_NOT_FOUND_IN_ASSETS, e, fileName);
            }
        }

        return result;
    }


    // INCLUDED

    /**
     * Saves a text into a file using {@code FileWriter} and {@code BufferedWriter}.
     * It creates the folders necessary for you.
     *
     * @param text            The text to be saved.
     * @param destinationPath The destinationPath where the file will be stored. Must include the parameter of the file.
     * @param append          Pass {@code true} if you want content to be appended at the end of the existing
     *                        file, or {@code false} if you want the existing text to be overwritten by the
     *                        new text.
     * @return A {@code Result<File>} containing the file if it was created or else {@code null}.
     */
    public static Result<File> saveTextFile(@NonNull String text, @NonNull Path destinationPath,
                                            boolean append) {

        Result<File> result;

        ValidationInfoInterface info = Validator.validateSaveTextFile(text, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            if (createPath(destinationPath)) {
                result = saveTextFile(text, destinationPath.getPath(), append);
            } else {
                result = Result.createNoExceptionResult(FAILED,
                        StringUtil.format(SAVE_TEXT_TO_FILE, destinationPath.getPath()));
            }
        }

        return result;
    }

    // INCLUDED

    /**
     * Saves a text into a file using {@code FileWriter} and {@code BufferedWriter}.
     * The destinationPath must exist.
     *
     * @param text            The text to be saved.
     * @param destinationPath The destinationPath where the file will be stored. Must include the parameter of the file.
     * @param append          Pass {@code true} if you want content to be appended at the end of the existing
     *                        file, or {@code false} if you want the existing text to be overwritten by the
     *                        new text.
     * @return A {@code Result<File>} containing the file if it was created or else {@code null}.
     */
    public static Result<File> saveTextFile(@NonNull String text, @NonNull String destinationPath,
                                            boolean append) {

        Result<File> result;

        ValidationInfoInterface info = Validator.validateSaveTextFile(text, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {

            File destinationFile = new File(destinationPath);

            try {
                FileWriter fileWriter = new FileWriter(destinationFile, append);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                try {
                    bufferedWriter.write(text.toCharArray(), 0, text.length());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    fileWriter.close();
                    result = Result.createSuccessfulResult(destinationFile, TEXT_FILE_SAVED,
                            destinationPath);
                } catch (IOException e) {
                    result = Result.createUnsuccessfulResult(CANNOT_SAVE_TEXT, e, destinationPath);
                }
            } catch (IOException e) {
                result = Result.createUnsuccessfulResult(DESTINATION_FOLDER_NOT_FOUND, e,
                        destinationPath, StringUtil.format(SAVE_TEXT_TO_FILE, StringUtil.EMPTY));
            }
        }

        return result;
    }


    // INCLUDED

    /**
     * Loads a text file from the given {@code originPath}.
     *
     * @param originPath The path to the text file.
     * @return Null if the file could not be found or read, or else a {@code String} with the
     * content.
     */
    public static Result<String> loadTextFile(@NonNull Path originPath) {

        Result<String> result;

        ValidationInfoInterface info = Validator.validateLoadTextFile(originPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            result = loadTextFile(originPath.getPath());
        }

        return result;
    }


    // INCLUDED

    /**
     * Loads a text file from the given originPath.
     *
     * @param originPath The originPath to the text file.
     * @return Null if the file could not be found or read, or else a {@code String} with the
     * content.
     */
    public static Result<String> loadTextFile(@NonNull String originPath) {

        Result<String> result;

        ValidationInfoInterface info = Validator.validateLoadTextFile(originPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            try {
                FileReader fileReader = new FileReader(new File(originPath));
                BufferedReader reader = new BufferedReader(fileReader);
                StringBuilder text = new StringBuilder();
                String line;

                try {
                    while ((line = reader.readLine()) != null) {
                        text.append(line).append("\n");
                    }
                    fileReader.close();
                    reader.close();
                    result = Result.createSuccessfulResult(text.toString(), TEXT_LOADED, originPath);
                } catch (IOException e) {
                    result = Result.createUnsuccessfulResult(ERROR_WHILE_READING, e, originPath);
                }
            } catch (FileNotFoundException e) {
                result = Result.createUnsuccessfulResult(FILE_NOT_FOUND, e, originPath,
                        StringUtil.format(LOAD_TEXT_FROM_FILE, originPath));
            }
        }
        return result;
    }


    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateSaveObject(object, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            if (createPath(destinationPath)) {
                result = saveObject(object, destinationPath.getPath());
            } else {
                result = Result.createNoExceptionResult(FAILED,
                        StringUtil.format(SAVE_OBJECT, destinationPath.getPath()));
            }
        }

        return result;
    }


    // INCLUDED

    /**
     * Saves an object to {@code destinationPath}.
     *
     * @param object          Object to save.
     * @param destinationPath Path in the device where the object will be saved.
     * @return A {@code Result<File>} containing the file where the object has been saved or else
     * {@code null}.
     */
    public static Result<File> saveObject(@NonNull Object object, @NonNull String destinationPath) {

        Result<File> result;

        ValidationInfoInterface info = Validator.validateSaveObject(object, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {

            File fileToSave = new File(destinationPath);

            try {
                FileOutputStream streamToFile = new FileOutputStream(fileToSave, false);
                try {
                    ObjectOutputStream objectStreamToFile = new ObjectOutputStream(streamToFile);
                    try {
                        objectStreamToFile.writeObject(object);
                        objectStreamToFile.flush();
                        objectStreamToFile.close();
                        streamToFile.close();
                        result = Result.createSuccessfulResult(new File(destinationPath), OBJECT_SAVED, destinationPath);
                    } catch (IOException e) {
                        return Result.createUnsuccessfulResult(CANNOT_CREATE_OBJECT_FILE, e, destinationPath);
                    }
                } catch (IOException e) {
                    return Result.createUnsuccessfulResult(CANNOT_CREATE_OBJECT_INPUT_STREAM, e,
                            StringUtil.format(SAVE_OBJECT, destinationPath));
                }
            } catch (FileNotFoundException e) {
                return Result.createUnsuccessfulResult(DESTINATION_FOLDER_NOT_FOUND, e,
                        StringUtil.format(SAVE_OBJECT, destinationPath));
            }
        }

        return result;
    }


    // INCLUDED

    /**
     * Loads an object from a file.
     *
     * @param originPath Path to the file where the object is stored.
     * @param clazz      Class containing the object's type.
     * @param <T>        Object's type.
     * @return A {@code Result} containing the the object retrieved or null if it couldn't be
     * retrieved.
     */
    public static <T> Result<T> loadObject(@NonNull Path originPath, @NonNull Class clazz) {

        Result<T> result;

        ValidationInfoInterface info = Validator.validateLoadObject(originPath, clazz);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            result = loadObject(originPath.getPath(), clazz);
        }

        return result;
    }


    // INCLUDED

    /**
     * Loads an object from a file.
     *
     * @param originPath Path to the file where the object is stored.
     * @param clazz      Class containing the object's type.
     * @param <T>        Object's type.
     * @return A {@code Result} containing the the object retrieved or null if it couldn't be
     * retrieved.
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> loadObject(@NonNull String originPath, @NonNull Class clazz) {

        Result<T> result;

        ValidationInfoInterface info = Validator.validateLoadObject(originPath, clazz);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            try {
                FileInputStream streamToFile = new FileInputStream(originPath);
                try {
                    ObjectInputStream streamToLoadObject = new ObjectInputStream(streamToFile);
                    try {
                        T object = (T) streamToLoadObject.readObject();
                        streamToFile.close();
                        result = Result.createSuccessfulResult(object, OBJECT_LOADED, originPath);
                    } catch (ClassNotFoundException e) {
                        result = Result.createUnsuccessfulResult(CLASS_NOT_FOUND, e,
                                StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), originPath));
                    } catch (IOException e) {
                        result = Result.createUnsuccessfulResult(CANNOT_READ_OBJECT, e,
                                StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), originPath));
                    }
                } catch (IOException e) {
                    result = Result.createUnsuccessfulResult(CANNOT_CREATE_OBJECT_INPUT_STREAM, e,
                            StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), originPath));
                }
            } catch (FileNotFoundException e) {
                result = Result.createUnsuccessfulResult(FILE_NOT_FOUND, e,
                        StringUtil.format(LOAD_OBJECT, clazz.getSimpleName(), originPath));
            }
        }
        return result;
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

        ValidationInfoInterface info = Validator.validateSaveByteArray(byteArray, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            if (createPath(destinationPath)) {
                result = saveByteArray(byteArray, destinationPath.getPath());
            } else {
                result = Result.createNoExceptionResult(FAILED,
                        StringUtil.format(SAVE_BYTE_ARRAY, destinationPath.getPath()));
            }
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

        Result<File> result;

        ValidationInfoInterface info = Validator.validateSaveByteArray(byteArray, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            try {
                FileOutputStream streamToDestinationFile =
                        new FileOutputStream(new File(destinationPath), false);
                try {
                    streamToDestinationFile.write(byteArray, 0, byteArray.length);
                    streamToDestinationFile.flush();
                    streamToDestinationFile.close();
                    result = Result.createSuccessfulResult(new File(destinationPath),
                            BYTE_ARRAY_SAVED, destinationPath);
                } catch (IOException e) {
                    result = Result.createUnsuccessfulResult(ERROR_WRITING_BYTE_ARRAY, e,
                            StringUtil.format(SAVE_BYTE_ARRAY, destinationPath));
                }
            } catch (FileNotFoundException e) {
                result = Result.createUnsuccessfulResult(DESTINATION_FOLDER_NOT_FOUND, e,
                        StringUtil.format(SAVE_BYTE_ARRAY, destinationPath));
            }
        }

        return result;
    }


    // INCLUDED

    /**********************************************************************************************
     * Saves a {@code Bitmap} to the device. This method stores a Bitmap in memory as a file, since
     * it cannot be stored as an object, for it cannot implement {@link Serializable}.
     *
     * @param bitmap          The image to be saved.
     * @param destinationPath The path to the file where it will be saved.
     * @return Returns a {@code Result} with the {@code File} where the {@code Bitmap} has been
     * stored or null if there was an error.
     */
    public static Result<File> saveBitmap(@NonNull Bitmap bitmap, @NonNull Path destinationPath) {

        Result<File> result;

        ValidationInfoInterface info = Validator.validateSaveBitmap(bitmap, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            boolean pathCreated = createPath(destinationPath);
            if (pathCreated) {
                result = saveBitmap(bitmap, destinationPath.getPath());
            } else {
                result = Result.createNoExceptionResult(FAILED,
                        StringUtil.format(SAVE_BITMAP, destinationPath.getPath()));
            }
        }

        return result;
    }


    // INCLUDED

    /**********************************************************************************************
     * Saves a {@code Bitmap} to the device. This method stores a Bitmap in memory as a file, since
     * it cannot be stored as an object, for it cannot implemente {@link Serializable}.
     *
     * @param bitmap          The image to be saved.
     * @param destinationPath The path to the file where it will be saved.
     * @return Returns a {@code Result} with the {@code File} where the {@code Bitmap} has been
     * stored or null if there was an error.
     */
    public static Result<File> saveBitmap(@NonNull Bitmap bitmap, @NonNull String destinationPath) {

        Result<File> result;

        ValidationInfoInterface info = Validator.validateSaveBitmap(bitmap, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            try {
                OutputStream streamToFile = new FileOutputStream(new File(destinationPath), false);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, streamToFile);
                streamToFile.flush();
                streamToFile.close();
                result = Result.createSuccessfulResult(new File(destinationPath), BITMAP_SAVED,
                        destinationPath);
            } catch (IOException e) {
                result = Result.createUnsuccessfulResult(UNEXPECTED_IO_ERROR, e,
                        StringUtil.format(SAVE_BITMAP, destinationPath));
            }
        }

        return result;
    }


    // INCLUDED

    /**
     * Loads an image file from the device.
     *
     * @param originPath The path to the file where the image is stored.
     * @return Returns a {@code Result} with the {@code Bitmap} obtained.
     */
    public static Result<Bitmap> loadBitmap(@NonNull Path originPath) {

        Result<Bitmap> result;

        ValidationInfoInterface info = Validator.validateLoadBitmap(originPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            result = loadBitmap(originPath.getPath());
        }

        return result;
    }


    // INCLUDED

    /**
     * Loads an image file from the device.
     *
     * @param originPath The path to the file where the image is stored.
     * @return Returns a {@code Result} with the {@code Bitmap} obtained.
     */
    public static Result<Bitmap> loadBitmap(@NonNull String originPath) {

        Result<Bitmap> result;

        ValidationInfoInterface info = Validator.validateLoadBitmap(originPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            try {
                InputStream streamFromFile = new FileInputStream(new File(originPath));
                Bitmap bitmap = BitmapFactory.decodeStream(streamFromFile);
                if (bitmap == null) {
                    ExceptionManager.throwNotAnImageException(LOAD_BITMAP, originPath);
                    result = Result.createNoExceptionResult(CANNOT_CONVERT_TO_BITMAP,
                            StringUtil.format(LOAD_BITMAP, originPath));
                } else {
                    result = Result.createSuccessfulResult(bitmap, BITMAP_LOADED, originPath);
                }
            } catch (FileNotFoundException e) {
                result = Result.createUnsuccessfulResult(FILE_NOT_FOUND, e,
                        StringUtil.format(LOAD_BITMAP, originPath));
            }
        }

        return result;
    }


    /**
     * Loads a {@code Bitmap} from a {@code Uri}.
     *
     * @param context   A {@code Context} to call contentResolver.
     * @param originUri The image identifier.
     * @return A {@code Result} containing the {@code Bitmap} referenced by the {@code originUri} or
     * {@code null} if the {@code originUri} references a non-existing file.
     */
    public static Result<Bitmap> loadBitmap(Context context, Uri originUri) {

        Result<Bitmap> result;

        ValidationInfoInterface info = Validator.validateLoadBitmap(context, originUri);
        if (!info.isValid()){
            result = reportInvalidParameter(info);
        } else {
            try {
                InputStream is = context.getContentResolver().openInputStream(originUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if (bitmap == null) {
                    result = Result.createNoExceptionResult(
                            StringUtil.format(NULL_FILE_FROM_URI,
                                    StringUtil.format(LOAD_BITMAP_FROM_URI, originUri.getPath())));
                } else {
                    result = Result.createSuccessfulResult(bitmap, BITMAP_LOADED_FROM_URI,
                            originUri.getPath());
                }
            } catch (FileNotFoundException e) {
                result = Result.createUnsuccessfulResult(FILE_NOT_FOUND, e,
                        StringUtil.format(LOAD_BITMAP_FROM_URI, originUri.getPath()));
            }
        }

        return result;
    }


    // INCLUDED

    /**
     * Saves every preference in the {@code SharedPreferences} object received to a file as a
     * {@code Map<String, ?}. It will create the intermediate folders for you.
     *
     * @param sharedPreferences Object containing the preferences to be saved.
     * @param destinationPath   Path to the file were the preferences will be saved. Don't assign an
     *                          extension to the file parameter.
     * @return A{@code Result<File>} containing the {@code File} where the mapped preferences are
     * stored.
     */
    public static Result<File> saveSharedPreferences(@NonNull SharedPreferences sharedPreferences,
                                                     @NonNull Path destinationPath) {

        Result<File> result;

        ValidationInfoInterface info = Validator.validateSaveSharedPreferences(sharedPreferences, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            if (createPath(destinationPath)) {
                result = MemoryUtil.saveSharedPreferences(sharedPreferences, destinationPath.getPath());
            } else {
                result = Result.createNoExceptionResult(FAILED,
                        StringUtil.format(SAVE_SHARED_PREFERENCES, destinationPath.getPath()));
            }
        }

        return result;
    }


    // INCLUDED

    /**
     * Saves every preference in the {@code SharedPreferences} object received to a file as a
     * {@code Map<String, ?}. You must make sure that you have created every intermediate folder in
     * the path or else the saving will fail.
     *
     * @param sharedPreferences Object containing the preferences to be saved.
     * @param destinationPath   Path to the file were the preferences will be saved. Don't assign an
     *                          extension to the file parameter.
     * @return A{@code Result<File>} containing the {@code File} where the mapped preferences are
     * stored.
     */
    public static Result<File> saveSharedPreferences(@NonNull SharedPreferences sharedPreferences,
                                                     @NonNull String destinationPath) {

        Result<File> result;

        ValidationInfoInterface info = Validator.validateSaveSharedPreferences(sharedPreferences, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            result = MemoryUtil.saveObject(sharedPreferences.getAll(), destinationPath);
        }

        return result;
    }


    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateLoadSharedPreferences(originPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            result = loadSharedPreferences(originPath.getPath());
        }

        return result;
    }


    // INCLUDED

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

        Result<Map<String, Object>> result;

        ValidationInfoInterface info = Validator.validateLoadSharedPreferences(originPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            result = MemoryUtil.loadObject(originPath, Map.class);
        }

        return result;
    }


    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateLoadSharedPreferences(originPath, sharedPreferences);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            result = loadSharedPreferences(originPath.getPath(), sharedPreferences);
        }

        return result;
    }

    // INCLUDED

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
    @SuppressWarnings("unchecked")
    public static Result<SharedPreferences> loadSharedPreferences(@NonNull String originPath,
                                                                  @NonNull SharedPreferences sharedPreferences) {

        Result<SharedPreferences> result;

        ValidationInfoInterface info = Validator.validateLoadSharedPreferences(originPath,
                sharedPreferences);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            // Loads the preferences
            Result<Map<String, Object>> loadPreferencesResult = loadSharedPreferences(originPath);
            String preferencesSummary;

            if (!loadPreferencesResult.isSuccessful()) {
                result = Result.createNoExceptionResult(SHARED_PREFERENCES_NOT_RESTORED, originPath);
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Map<String, ?> mappedPreferences = loadPreferencesResult.getResult();

                String loadedPreferences = "Retrieved preferences:\n";
                String unparseablePreferences = "Unparseable preferences:\n";
                int unparsedPreferences = 0;

                // Loads the preferences retrieved in the SharedPreferences object.
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

                // Creates the preferences report.
                preferencesSummary = loadedPreferences;
                if (unparsedPreferences > 0) {
                    preferencesSummary += unparseablePreferences;
                }

                // Creates the result to be returned
                if (unparsedPreferences == mappedPreferences.size()) {
                    result = Result.createNoExceptionResult(NO_PREFERENCES_RESTORED, originPath);
                } else {
                    editor.apply();
                    result = Result.createSuccessfulResult(sharedPreferences,
                            unparsedPreferences == 0 ?
                                    ALL_PREFERENCES_RESTORED : PREFERENCES_PARTIALLY_RESTORED,
                            originPath, preferencesSummary);
                }
            }
        }

        return result;
    }

    // INCLUDED

    /**
     * Checks whether a file exists or not. Throws a {@code RuntimeException} if {@code pathToFile}
     * is {@code null}.
     *
     * @param pathToFile {@code Path} to the file to be checked.
     * @return {@code true} if the file exists or else {@code false}. Throws a
     * {@code RuntimeException} if {@code pathToFile} is {@code null}.
     */
    public static boolean exists(@NonNull Path pathToFile) {

        boolean exists;
        ValidationInfoInterface info = Validator.validateExists(pathToFile);
        if (!info.isValid()) {
            reportInvalidParameter(info);
            exists = false;
        } else {
            exists = exists(new File(pathToFile.getPath()));
        }

        return exists;
    }


    // INCLUDED

    /**
     * Checks whether a file exists or not. Throws an exception if {@code pathToFile} is null or empty.
     *
     * @param pathToFile Path to the file to be checked.
     * @return {@code true} if the file exists or else {@code false}.
     */
    public static boolean exists(@NonNull String pathToFile) {

        boolean exists;

        ValidationInfoInterface info = Validator.validateExists(pathToFile);
        if (!info.isValid()) {
            reportInvalidParameter(info);
            exists = false;
        } else {
            exists = exists(new File(pathToFile));
        }

        return exists;
    }


    // INCLUDED

    /**
     * Checks whether a file exists or not. Throws an exception if {@code file} is null or empty.
     *
     * @param file File to be checked.
     * @return {@code true} if {@code file} exists or else {@code false}.
     */
    public static boolean exists(@NonNull File file) {

        boolean exists;
        ValidationInfoInterface info = Validator.validateExists(file);
        if (!info.isValid()) {
            reportInvalidParameter(info);
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
     * Checks whether {@code file} can be created. This is doesn't exists and the folder that
     * will contain it does or if it does exist and it's not a folder. This method is used when you
     * want to check if something can be saved to that place.
     *
     * @param path      Path to the file to be checked.
     * @param isFolder  Pass true if the path refers to a folder
     * @param parameter The paramater type that you are passing. Must be one of
     *                  {@link melerospaw.memoryutil.InvalidParameterException.InvalidParameter}.
     * @return {@code true} if the file exists or else {@code false}.
     */
    public static Result<ValidForSavingInfoInterface> isValidForSaving(
            @NonNull String path, boolean isFolder,
            @InvalidParameterException.InvalidParameter String parameter) {

        Result<ValidForSavingInfoInterface> result;

        ValidationInfoInterface info = Validator.validateIsValidForSaving(path);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            ValidForSavingInfoInterface.Invalidity invalidity;
            boolean isValid;
            File file = new File(path);

            // If the file exists
            if (file.exists()) {
                // It if should be a folder, verifies it's a folder
                if (isFolder) {
                    if (file.isDirectory()) {
                        invalidity = ValidForSavingInfoInterface.Invalidity.NONE;
                    } else {
                        invalidity = ValidForSavingInfoInterface.Invalidity.NOT_A_DIRECTORY;
                    }
                    // If it should be a file, verifies it's a file
                } else {
                    if (!file.isDirectory()) {
                        invalidity = ValidForSavingInfoInterface.Invalidity.NONE;
                    } else {
                        invalidity = ValidForSavingInfoInterface.Invalidity.IS_A_DIRECTORY;
                    }
                }
                // If the file doesn't exists, verifies that the folder that's going to contain it exists
            } else {
                File containerFolder = new File(getContainerFolder(file.getPath()));
                if (containerFolder.exists()) {
                    invalidity = ValidForSavingInfoInterface.Invalidity.NONE;
                } else {
                    invalidity = ValidForSavingInfoInterface.Invalidity.CONTAINER_FOLDER_DOESNT_EXIST;
                }
            }

            isValid = invalidity == ValidForSavingInfoInterface.Invalidity.NONE;
            ValidForSavingInfoInterface validInfo = new ValidForSavingInfo(parameter, isValid,
                    invalidity);
            result = Result.createSuccessfulResult(validInfo, IS_VALID_FOR_SAVING, path);
        }

        return result;
    }


    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateDuplicateFile(originPath, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else if (createPath(destinationPath)) {
            result = duplicateFile(originPath.getPath(), destinationPath.getPath());
        } else {
            result = Result.createNoExceptionResult(FAILED,
                    StringUtil.format(DUPLICATE_FILE, originPath.getPath()));
        }

        return result;
    }


    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateDuplicateFile(originPath, destinationPath);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
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


    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateDeleteFile(pathToFile);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
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
        Result result;

        ValidationInfoInterface info = Validator.validateDeleteFile(pathToFile);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            result = deleteFile(new File(pathToFile), shouldClearIfDirectory);
        }

        return result;
    }


    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateDeleteFile(file);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            // If the file doesn't exist, return a successful Result
            if (!file.exists()) {
                result = Result.createSuccessfulResult(null, StringUtil.format(FILE_DOESNT_EXIST,
                        StringUtil.format(DELETE_FILE, file.getPath())));

                // If the file exists, tries to delete it
            } else {
                // If it's a file, it can be deleted right away
                if (!file.isDirectory()) {
                    result = deleteIt(file);
                    // If it's a directory, tries to empty it first
                } else {
                    // If the folder is already empty, deletes it
                    if (file.list() == null || file.list().length == 0) {
                        result = deleteIt(file);
                        // If the folder is not empty, only clears its content if shouldClearIfDirectory
                        // is true
                    } else {
                        if (shouldClearIfDirectory) {
                            boolean emptied = clearFolder(file.getPath()).isSuccessful();
                            // If eventually the folder is empty, deletes it
                            if (emptied) {
                                result = deleteIt(file);
                                // It it's not been emptied, returns a failure Result
                            } else {
                                result = Result.createNoExceptionResult(FOLDER_NOT_CLEARED,
                                        StringUtil.format(DELETE_FILE, file.getPath()));
                            }
                            // If shouldClearIfDirectory is false, the directory won't be deleted and
                            // a failure Result will be returned
                        } else {
                            result = Result.createNoExceptionResult(DIRECTORY_HAS_FILES,
                                    StringUtil.format(DELETE_FILE, file.getPath()));
                        }
                    }
                }
            }
        }

        return result;
    }

    // INCLUDED

    /**
     * Simply deletes a file and returns a failure or successful {@link Result}.
     *
     * @param file The file to be deleted.
     * @return Returns a successful result if the file could be deleted or else a failure one.
     */
    private static Result deleteIt(@NonNull File file) {

        Result result;

        boolean deleted = file.delete();

        if (deleted) {
            result = Result.createSuccessfulResult(file, FILE_DELETED, file.getPath());
        } else {
            result = Result.createNoExceptionResult(FAILED, StringUtil.format(DELETE_FILE, file.getPath()));
        }

        return result;
    }


    // INCLUDED

    /**
     * Deletes the content of a folder. If one of the files cannot be deleted, stops deleting.
     *
     * @param pathToFolder Path to the folder to be cleared.
     * @return {@code true} if the content was cleared or there was no content, or else
     * {@code false} if one of the files could not be deleted or after deleting every file the
     * folder is still not empty.
     */
    public static Result clearFolder(Path pathToFolder) {

        Result result;

        ValidationInfoInterface info = Validator.validateClearFolder(pathToFolder);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            result = clearFolder(pathToFolder.getPath());
        }

        return result;
    }


    // INCLUDED

    /**
     * Deletes the content of a folder. If one of the files cannot be deleted, stops deleting.
     *
     * @param pathToFolder Folder to be cleared.
     * @return {@code true} if the content was cleared, or {@code false} if one of the files could
     * not be deleted or after deleting every file the folder is still not empty.
     */
    public static Result clearFolder(@NonNull String pathToFolder) {

        Result result = null;

        ValidationInfoInterface info = Validator.validateClearFolder(pathToFolder);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
        } else {
            File folder = new File(pathToFolder);

            // If the folder is empty, just returns true
            if (isFolderEmpty(folder, false)) {
                result = Result.createSuccessfulResult(null, FOLDER_ALREADY_EMPTY, folder.getPath());

                // If the folder is not empty, deletes everything in the folder
            } else {

                boolean cleared = true;
                for (File file : folder.listFiles()) {

                    // If some of the files are directories, makes a recursive call to clear them
                    if (file.isDirectory()) {

                        Result clearFolderResult = clearFolder(file.getPath());

                        // If the content couldn't be deleted, returns false
                        if (!clearFolderResult.isSuccessful()) {
                            result = Result.createNoExceptionResult(CANNOT_DELETE_FOLDER,
                                    StringUtil.format(CLEAR_FOLDER, folder.getPath()),
                                    file.getName());
                            cleared = false;
                            break;
                        }
                    }

                    // Deletes de folder
                    boolean fileDeleted = deleteFile(file, true).isSuccessful();

                    // If the file could not be deleted, returns false and stops deleting
                    if (!fileDeleted) {
                        result = Result.createNoExceptionResult(String.format(CANNOT_DELETE, file.getPath()));
                        cleared = false;
                        break;
                    }
                }

                if (cleared) {
                    result = Result.createSuccessfulResult(null, FOLDER_CLEARED, pathToFolder);
                }
            }
        }
        return result;
    }


    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateIsFolderEmpty(folder);
        if (!info.isValid()) {
            reportInvalidParameter(info);
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


    // INCLUDED

    /**
     * Creates folder {@code pathToFolder}.
     *
     * @param pathToFolder Path to the folder including the folder parameter.
     * @return A {@code Result<File>} whose method {@link Result#isSuccessful()} returns
     * {@code true} if the folder was created or already existed, or {@code false} if the folder
     * could not be created.
     */
    public static Result<File> createFolder(@NonNull String pathToFolder) {

        Result<File> result;

        ValidationInfoInterface info = Validator.validateCreateFolder(pathToFolder);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
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


    // INCLUDED

    /**
     * Creates the folders necessary to generate the path.
     *
     * @param path Contains the path to create.
     * @return The path to the file if it could be created or {@code null} if it couldn't.
     */
    public static boolean createPath(@NonNull Path path) {

        boolean pathCreated = false;

        ValidationInfoInterface info = Validator.validateCreatePath(path);
        if (!info.isValid()) {
            reportInvalidParameter(info);
        } else {
            if (path.getFolders().isEmpty()) {
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
        }

        return pathCreated;
    }


    // INCLUDED

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

        ValidationInfoInterface info = Validator.validateGetLongestPath(path);
        if (!info.isValid()) {
            reportInvalidParameter(info);
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

        ValidationInfoInterface info = Validator.validateIsDirectory(path);
        if (!info.isValid()) {
            reportInvalidParameter(info);
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

        ValidationInfoInterface info = Validator.validateIsDirectory(file);
        if (!info.isValid()) {
            reportInvalidParameter(info);
            isDirectory = false;
        } else if (!file.isDirectory()) {
            log(StringUtil.format(NOT_A_FOLDER, file.getPath()), true);
            isDirectory = false;
        } else {
            log(StringUtil.format(IS_A_FOLDER, file.getPath()), true);
            isDirectory = true;
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

        ValidationInfoInterface info = Validator.validateGetFilesInDirectory(folder);
        if (!info.isValid()) {
            reportInvalidParameter(info);
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

        ValidationInfoInterface info = Validator.validateGetFileTree(folder);
        if (!info.isValid()) {
            result = reportInvalidParameter(info);
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


    /**
     * Returns the path to the folder containing the file in {@code paht}.
     *
     * @param path The path to the file whose container folder we want to get.
     */
    private static String getContainerFolder(@NonNull String path) {
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
            log("External storage is available.", true);
            return true;
        }
        log("External storage is not available.", false);
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


    /**
     * Reports that a methods as been incorrectly called using invalid parameters. Calls for the
     * {@link ExceptionManager} to throw and exception and calls for the {@link Logger} to log the
     * information. Both the exception and the log report will be isued depending on whether logging
     * and throwing exceptions are enabled.
     *
     * @param validationInfo The information generated about the incorrect parameter.
     * @return A {@link Result} containing a null result, returning {@code false} when asked if it
     * was successful and the reason for the failure as the message.
     */
    private static <T> Result<T> reportInvalidParameter(ValidationInfoInterface validationInfo) {
        ExceptionManager.throwValidationInfoException(validationInfo);
        return Result.createInfoResult(validationInfo);
    }
}
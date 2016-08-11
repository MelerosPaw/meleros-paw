package melerospaw.memoryutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

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

/**
 * <p>Class to make it easier to save and load from an Android device's memory.
 * Use {@link Path.Builder} to create a {@code Path} to call {@code MemoryUtils}' methods.
 * This way, folders will be created automatically when saving.
 * Then use {@link Result} class to retrieve objects from method calls, for example, the
 * {@code File} where something was saved to or the object that you are trying to retrieve from
 * memory.</p>
 *
 * <p>Example code:</p>
 */
public class MemoryUtils {

    private static final String TAG = MemoryUtils.class.getSimpleName();

    public static final String ALREADY_EXISTS = "Folder %1$s already exists.";
    public static final String ASSET_NOT_FOUND = "File %1$s was not found in assets directory.";
    public static final String BYTE_ARRAY_SAVED = "Byte array saved to %1$s.";
    public static final String CANNOT_COPY = "Couldn't copy file %1$s to %2$s.";
    public static final String CANNOT_CREATE = "Couldn't create folder %1$s.";
    public static final String CANNOT_CREATE_OBJECT_FILE = "Error while writing the object to %1$s.";
    public static final String CANNOT_CREATE_OBJECT_INPUT_STREAM = "Could not create ObjectInputStream for %1$s.";
    public static final String CANNOT_CREATE_OBJECT_OUTPUT_STREAM = "Could not create ObjectOutputStream for %1$s.";
    public static final String CANNOT_DELETE = "File %1$s couldn't be deleted.";
    public static final String CANNOT_READ_OBJECT = "Couldn't read object from %1$s.";
    public static final String CANNOT_SAVE = "Couldn't save file %1$s.";
    public static final String CLASS_NOT_FOUND = "Couldn't find the class to cast the object stored in %1$s.";
    public static final String CREATED = "Folder %1$s was created.";
    public static final String DELETED_BUT_STILL_NOT_EMPTY = "All files in folder %1$s were deleted but still it's not empty.";
    public static final String DESTINATION_FOLDER_NOT_FOUND = "Destination folder %1$s was not found.";
    public static final String DIRECTORY_HAS_FILES = "File %1$s is a directory containing other files. It was not deleted. If you want to delete it, pass true as second parameter to method deleteFile().";
    public static final String DOESNT_EXIST = "File %1$s doesn't exist.";
    public static final String ERROR_WHILE_READING = "Error while reading the file %1$s";
    public static final String ERROR_WHILE_WRITING = "Error writing the file %1$s";
    public static final String FILENAME_NULL = "The file name is null.";
    public static final String FILE_COPIED = "File %1$s was copied to %2$s.";
    public static final String FILE_DELETED = "File %1$s was deleted.";
    public static final String FILE_LOADED = "File %1$s was loaded.";
    public static final String FILE_NOT_FOUND = "Destination path %1$s couldn't be found.";
    public static final String FOLDER_CLEARED = "Folder %1$s was cleared.";
    public static final String FOLDER_DELETED = "Folder %1$s was deleted.";
    public static final String FOLDER_NOT_CLEARED = "File %1$s was a folder but could not be cleared.";
    public static final String FOLDER_NOT_DELETED = "Folder %1$s could not be deleted.";
    public static final String NOT_SERIALIZABLE = "Cannot save object because it's not serializable.";
    public static final String NULL_DELETE_PATH = "Path %1$s to delete file is null.";
    public static final String NULL_DESTINATION_PATH = "Destination path to save file is null.";
    public static final String NULL_OBJECT = "Object to be saved is null.";
    public static final String NULL_ORIGIN_PATH = "Origin path is null.";
    public static final String NULL_TEXT = "Text to be saved is null.";
    public static final String OBJECT_LOADED = "Object retrieved from %1$s.";
    public static final String OBJECT_SAVED = "Object saved to %1$s.";
    public static final String ORIGIN_FILE_NOT_FOUND = "Origin file %1$s was not found.";
    public static final String TEXT_FILE_SAVED = "Text was saved to %1$s.";
    public static final String NOT_A_DIRECTORY = "File %1$s is not a directory.";
    public static final String FOLDER_IS_NOT_EMPTY = "Folder %1$s is not empty.";
    public static final String SHARED_PREFERENCES_NOT_RESTORED = "Could not be restore preferences from %1$s.";
    public static final String NO_PREFERENCES_RESTORED = "No preferences at all were restored from %1$s.\n%2$s";
    public static final String PREFERENCES_PARTIALLY_RESTORED = "Stored preferences in %1$s could only be partially restored.\n%2$s";
    public static final String ALL_PREFERENCES_RESTORED = "Preferences in %1$s were restored.\n%2$s";
    public static final String NULL_CLASS = "Class to load object from %1$s is null.";
    public static final String NULL_INPUTSTREAM = "The InputStream provided is null. Cannot copy the file %1$s into %2$s.";
    public static final String NULL_SHAREDPREFERENCES_BACKUP_PATH = "Path to SharedPreferences backup file is null.";
    public static final String NULL_SHAREDPREFERENCES_OBJECT = "The SharedPreferences object received is null. Preferences from %1$ cannot be restored.";


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

        String path = createPath(destinationPath);
        return copyFromInputStream(originPath, inputStream, path);

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
                                                   InputStream inputStream, String destinationPath) {

        if (originPath == null) {
            originPath = "";
        }

        if (TextUtils.isEmpty(destinationPath)) {
            return createResult(null, NULL_DESTINATION_PATH, false, null, " from " + originPath);
        }

        if (inputStream == null) {
            return createResult(null, NULL_INPUTSTREAM, false, null, originPath, destinationPath);
        }

        OutputStream streamToSaveFile;
        try {
            streamToSaveFile = new FileOutputStream(destinationPath);
        } catch (FileNotFoundException e) {
            return createResult(null, DESTINATION_FOLDER_NOT_FOUND, false, e, destinationPath);
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
            return createResult(new File(destinationPath), FILE_COPIED, true, null, originPath,
                    destinationPath);
        } catch (IOException e) {
            return createResult(null, CANNOT_COPY, false, e, originPath, destinationPath);
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
    public static Result<File> copyFromAssets(@NonNull Context context, @NonNull String fileName,
                                              @NonNull Path destinationPath) {

        String path = createPath(destinationPath);
        return copyFromAssets(context, fileName, path);
    }


    /**
     * Copies a file {@code fileName} from assets directory to {@code destinationPath}.
     * {@code destinationPath} must exist.
     *
     * @param context         It will be used to get access to assets.
     * @param fileName        Name of the file in assets folder, as in <i>myFile.mp3</i> or
     *                        <i>subdirectory/myFile.mp3</i> if it is in a folder within the assets
     *                        directory.
     * @param destinationPath Path where the file will be copied to.
     * @return A {@code Result<File>} containing the file created or {@code null} if it couldn't be
     * created.
     */
    public static Result<File> copyFromAssets(@NonNull Context context, @NonNull String fileName,
                                              @NonNull String destinationPath) {
        if (fileName == null) {
            return createResult(null, FILENAME_NULL, false, null);
        }

        if (destinationPath == null) {
            return createResult(null, NULL_DESTINATION_PATH, false, null);
        }

        InputStream streamToAssets;

        try {
            streamToAssets = context.getAssets().open(fileName);
        } catch (IOException e) {
            return createResult(null, ASSET_NOT_FOUND, false, e, fileName);
        }

        return copyFromInputStream(fileName, streamToAssets, destinationPath);
    }


    /**
     * Imports a file in assets folder to the database folder.
     *
     * @param context      Needed to access the database folder.
     * @param databaseName Name of the file in assets. It must be the same as the database name.
     * @return A {@code Result<File>} containing a {@code File} pointing to the file where the
     * database has been copied to.
     */
    public static Result<File> importDatabaseFromAssets(Context context, String databaseName) {
        Path pathToDatabase = new Path.Builder(context)
                .databaseDirectory(databaseName)
                .build();
        return copyFromAssets(context, databaseName, pathToDatabase);
    }


    /**
     * Gets a {@code Bitmap} from the assets folder.
     *
     * @param context       Context to access the assets folder.
     * @param imageFileName Name of the picture file in assets with extension, such as
     *                      <i>imagen.jpg</i>.
     * @return Returns a {@code Bitmap} with the picture or {@code null} if the image doesn't
     * exist in the assets directory.
     */
    public static Bitmap getBitmapFromAssets(Context context, String imageFileName) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(imageFileName));
        } catch (IOException e) {
            Log.e(TAG, "Image was not found in assets.");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Saves a text into a file using {@code FileWriter} and {@code BufferedWriter}.
     * It creates the folders necessary for you.
     *
     * @param text   The text to be saved.
     * @param path   The path where the file will be stored. Must include the name of the file.
     * @param append Pass {@code true} if you want content to be appended at the end of the existing
     *               file, or {@code false} if you want the existing text to be overwritten by the
     *               new text.
     * @return A {@code Result<File>} containing the file if it was created or else {@code null}.
     */
    public static Result<File> saveTextFile(String text, Path path, boolean append) {

        String filePath = createPath(path);
        return saveTextFile(text, filePath, append);

    }


    /**
     * Saves a text into a file using {@code FileWriter} and {@code BufferedWriter}.
     * The path must exist.
     *
     * @param text   The text to be saved.
     * @param path   The path where the file will be stored. Must include the name of the file.
     * @param append Pass {@code true} if you want content to be appended at the end of the existing
     *               file, or {@code false} if you want the existing text to be overwritten by the
     *               new text.
     * @return A {@code Result<File>} containing the file if it was created or else {@code null}.
     */
    public static Result<File> saveTextFile(String text, String path, boolean append) {

        if (path == null) {
            return createResult(null, NULL_DESTINATION_PATH, false, null, "the input text");
        }

        if (text == null) {
            return createResult(null, NULL_TEXT, false, null);
        }

        File destinationFile = new File(path);
        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(destinationFile, append);
        } catch (IOException e) {
            return createResult(null, DESTINATION_FOLDER_NOT_FOUND, false, e, path);
        }

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            bufferedWriter.write(text.toCharArray(), 0, text.length());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            return createResult(null, CANNOT_SAVE, false, e, path);
        }

        return createResult(destinationFile, TEXT_FILE_SAVED, true, null, path);

    }

    /**
     * Loads a text file from the given path.
     *
     * @param path The path to the text file.
     * @return Null if the file could not be found or read, or else a {@code String} with the
     * content.
     */
    public static Result<String> loadTextFile(Path path) {
        return loadTextFile(path.getPath());
    }

    /**
     * Loads a text file from the given path.
     *
     * @param path The path to the text file.
     * @return Null if the file could not be found or read, or else a {@code String} with the
     * content.
     */
    public static Result<String> loadTextFile(String path) {

        FileReader fileReader;

        try {
            fileReader = new FileReader(new File(path));
        } catch (FileNotFoundException e) {
            return createResult(null, ORIGIN_FILE_NOT_FOUND, false, e, path);

        }

        BufferedReader reader = new BufferedReader(fileReader);
        StringBuilder texto = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                texto.append(line).append("\n");
            }
        } catch (IOException e) {
            return createResult(null, ERROR_WHILE_READING, false, e, path);
        }

        return createResult(texto.toString(), FILE_LOADED, true, null, path);
    }

    /**
     * Saves an object to {@code destinationPath}.
     *
     * @param object          Object to save.
     * @param destinationPath Path in the device where the object will be saved.
     * @return A {@code Result<File>} containing the file where the object has been saved or else
     * {@code null}.
     */
    public static Result<File> saveObject(Object object, Path destinationPath) {
        String path = createPath(destinationPath);
        return saveObject(object, path);
    }

    /**
     * Saves an object to {@code destinationPath}.
     *
     * @param object          Object to save.
     * @param destinationPath Path in the device where the object will be saved.
     * @return A {@code Result<File>} containing the file where the object has been saved or else
     * {@code null}.
     */
    public static Result<File> saveObject(Object object, String destinationPath) {

        if (destinationPath == null) {
            return createResult(null, NULL_DESTINATION_PATH, false, null, "the given object");
        }

        if (object == null) {
            return createResult(null, NULL_OBJECT, false, null);
        }

        if (!(object instanceof Serializable)) {
            return createResult(null, NOT_SERIALIZABLE, false, null);
        }

        File fileToSave = new File(destinationPath);
        FileOutputStream streamToFile;

        try {
            streamToFile = new FileOutputStream(fileToSave, false);
        } catch (FileNotFoundException e) {
            return createResult(null, DESTINATION_FOLDER_NOT_FOUND, false, e, destinationPath);
        }


        ObjectOutputStream objectStreamToFile;
        try {
            objectStreamToFile = new ObjectOutputStream(streamToFile);
        } catch (IOException e) {
            return createResult(null, CANNOT_CREATE_OBJECT_OUTPUT_STREAM, false, e, destinationPath);
        }

        try {
            objectStreamToFile.writeObject(object);
            objectStreamToFile.flush();
            objectStreamToFile.close();
        } catch (IOException e) {
            return createResult(null, CANNOT_CREATE_OBJECT_FILE, false, e, destinationPath);
        }

        return createResult(new File(destinationPath), OBJECT_SAVED, true, null, destinationPath);
    }

    /**
     * Loads an object from a file.
     *
     * @param path  Path to the file where the object is stored.
     * @param clazz Class containing the object's type.
     * @param <T>   Object's type.
     * @return A {@code Result} containing the the object retrieved or null if it couldn't be
     * retrieved.
     */
    public static <T> Result<T> loadObject(Path path, Class<T> clazz) {
        return loadObject(path.getPath(), clazz);
    }

    /**
     * Loads an object from a file.
     *
     * @param path  Path to the file where the object is stored.
     * @param clazz Class containing the object's type.
     * @param <T>   Object's type.
     * @return A {@code Result} containing the the object retrieved or null if it couldn't be
     * retrieved.
     */
    public static <T> Result<T> loadObject(String path, Class<T> clazz) {

        if (path == null) {
            return createResult(null, NULL_ORIGIN_PATH, false, null);
        }

        if (clazz == null) {
            return createResult(null, NULL_CLASS, false, null, path);
        }

        FileInputStream streamToFile;

        try {
            streamToFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            return createResult(null, FILE_NOT_FOUND, false, e, path);
        }

        ObjectInputStream streamToLoadObject;

        try {
            streamToLoadObject = new ObjectInputStream(streamToFile);
        } catch (IOException e) {
            return createResult(null, CANNOT_CREATE_OBJECT_INPUT_STREAM, false, e, path);
        }

        try {
            T object = clazz.cast(streamToLoadObject.readObject());
            return createResult(clazz.cast(object), OBJECT_LOADED, true, null, path);
        } catch (ClassNotFoundException e) {
            return createResult(null, CLASS_NOT_FOUND, false, e, path);
        } catch (IOException e) {
            return createResult(null, CANNOT_READ_OBJECT, false, e, path);
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
    public static Result<File> saveByteArray(byte[] byteArray, Path destinationPath) {
        String path = createPath(destinationPath);
        return saveByteArray(byteArray, path);
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
    public static Result<File> saveByteArray(byte[] byteArray, String destinationPath) {

        FileOutputStream streamToDestinationFile;

        try {
            streamToDestinationFile = new FileOutputStream(new File(destinationPath), false);
        } catch (FileNotFoundException e) {
            return createResult(null, DESTINATION_FOLDER_NOT_FOUND, false, e, destinationPath);
        }

        try {
            streamToDestinationFile.write(byteArray, 0, byteArray.length);
            streamToDestinationFile.flush();
            streamToDestinationFile.close();
            return createResult(new File(destinationPath), BYTE_ARRAY_SAVED, true, null,
                    destinationPath);
        } catch (IOException e) {
            return createResult(null, ERROR_WHILE_WRITING, false, e, destinationPath);
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
    public static Result<File> saveSharedPreferences(SharedPreferences sharedPreferences,
                                                     Path destinationPath) {
        String path = createPath(destinationPath);
        return MemoryUtils.saveSharedPreferences(sharedPreferences, path);
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
    public static Result<File> saveSharedPreferences(SharedPreferences sharedPreferences,
                                                     String destinationPath) {
        return MemoryUtils.saveObject(sharedPreferences.getAll(), destinationPath);
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
    public static Result<Map<String, Object>> loadSharedPreferences(Path originPath) {
        return loadSharedPreferences(originPath.getPath());
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
    public static Result<Map<String, Object>> loadSharedPreferences(String originPath) {
        Result<Map> result = MemoryUtils.loadObject(originPath, Map.class);
        Map<String, Object> map = null;

        if (result.isSuccessful()) {
            map = (Map<String, Object>) result.getResult();
        }

        return createResult(map, result.getMessage(), result.isSuccessful(), null);
    }


    /**
     * Retrieves a {@code Map<String, Object>} object with mapped shared preferences stored
     * previously in a file and loads them in the given {@code SharedPreferences} object.
     *
     * @param originPath        {@code Path} object pointing to the file were the preferences were saved.
     * @param sharedPreferences The {@code SharedPreferences} object where the preferences will be
     *                          restored to.
     * @return A {@code Result<Map<String, Object>>} containing the mapped shared preferences.
     */
    public static Result<SharedPreferences> loadSharedReferences(Path originPath,
                                                                 SharedPreferences sharedPreferences) {
        return loadSharedReferences(originPath.getPath(), sharedPreferences);
    }


    /**
     * Retrieves a {@code Map<String, Object>} object with mapped shared preferences stored
     * previously in a file and loads them in the given {@code SharedPreferences} object.
     *
     * @param pathToSharedPreferencesBackUp        Path to the file were the preferences were saved.
     * @param sharedPreferences The {@code SharedPreferences} object where the preferences will be
     *                          restored to.
     * @return A {@code Result<Map<String, Object>>} containing the mapped shared preferences.
     */
    public static Result<SharedPreferences> loadSharedReferences(@NonNull String pathToSharedPreferencesBackUp,
                                                                 @NonNull SharedPreferences sharedPreferences) {

        if (pathToSharedPreferencesBackUp == null) {
            return createResult(null, NULL_SHAREDPREFERENCES_BACKUP_PATH, false, null);
        }

        if (sharedPreferences == null){
            return createResult(null, NULL_SHAREDPREFERENCES_OBJECT, false, null, pathToSharedPreferencesBackUp);
        }

        Result<Map<String, Object>> result = loadSharedPreferences(pathToSharedPreferencesBackUp);

        String message;
        boolean success;
        String preferencesSummary;

        if (!result.isSuccessful()) {
            message = SHARED_PREFERENCES_NOT_RESTORED;
            success = false;
            preferencesSummary = "";
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

            preferencesSummary = loadedPreferences + unparseablePreferences;

            // Creates the result to be returned and logs
            if (unparsedPreferences == mappedPreferences.size()) {
                message = NO_PREFERENCES_RESTORED;
                success = false;
            } else {
                editor.apply();
                success = true;
                if (unparsedPreferences == 0) {
                    message = ALL_PREFERENCES_RESTORED;
                } else {
                    message = PREFERENCES_PARTIALLY_RESTORED;
                }
            }

            Log.e(TAG, preferencesSummary);
        }

        return createResult(sharedPreferences, message, success, null, pathToSharedPreferencesBackUp,
                preferencesSummary);
    }


    /**
     * Checks whether a file exists or not.
     *
     * @param path {@code Path} to the file to be checked.
     * @return {@code true} if the file exists or else {@code false}.
     */
    public static boolean exists(Path path) {
        return exists(path.getPath());
    }


    /**
     * Checks whether a file exists or not.
     *
     * @param path Path to the file to be checked.
     * @return {@code true} if the file exists or else {@code false}.
     */
    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
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
        String path = createPath(destinationPath);
        return duplicateFile(originPath.getPath(), path);
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

        if (originPath == null) {
            return createResult(null, NULL_ORIGIN_PATH, false, null);
        }

        if (destinationPath == null) {
            return createResult(null, NULL_DESTINATION_PATH, false, null);
        }

        FileInputStream streamToOriginFile;

        try {
            streamToOriginFile = new FileInputStream(new File(originPath));
        } catch (FileNotFoundException e) {
            return createResult(null, ORIGIN_FILE_NOT_FOUND, false, e, originPath);
        }

        return copyFromInputStream(originPath, streamToOriginFile, destinationPath);
    }


    /**
     * Deletes the file in {@code path}.
     *
     * @param path                    Path to the file.
     * @param clearContentIfDirectory If {@code path} refers to a folder, {@code true} will delete
     *                                every file in the folder in order to delete the folder. If
     *                                {@code false} the folder and its content will be kept.
     * @return A {@code Result} returning {@code isSuccessful() == true} if the file could be
     * deleted.
     */
    public static Result deleteFile(Path path, boolean clearContentIfDirectory) {
        return deleteFile(path.getPath(), clearContentIfDirectory);
    }


    /**
     * Deletes the file in {@code path}. If it's a folder, deletes its content only if
     * {@code clearContentIfDirectory} is {@code true}.
     *
     * @param path                    Path to the file.
     * @param clearContentIfDirectory If {@code path} refers to a folder, {@code true} will delete
     *                                every file in the folder in order to delete the folder. If
     *                                {@code false} the folder and its content will be kept.
     * @return A {@code Result} containing nothing but {@code true} or {@code false} in you call
     * method {@link Result#isSuccessful()} on it depending on whether the file was deleted or not.
     */
    public static Result deleteFile(String path, boolean clearContentIfDirectory) {

        if (path == null) {
            return createResult(null, NULL_DELETE_PATH, false, null);
        }

        File fileToDelete = new File(path);
        // If the file doesn't exist, returns true
        if (!fileToDelete.exists()) {
            return createResult(null, DOESNT_EXIST, true, null, path);
            // If the file exists, tries to delete it
        } else {
            // If it's a directory, deletes its content first
            if (fileToDelete.isDirectory()) {
                // Only clears the content if clearContentIfDirectory is true
                if (clearContentIfDirectory) {
                    boolean folderCleared = clearFolder(path);
                    // If the folder content wasn't cleared, stops deleting
                    if (!folderCleared) {
                        return createResult(null, FOLDER_NOT_CLEARED, false, null, path);
                        // If it was cleared, tries to delete the directory
                    } else {
                        boolean folderDeleted = fileToDelete.delete();
                        if (folderDeleted) {
                            return createResult(null, FOLDER_DELETED, true, null, path);
                        } else {
                            return createResult(null, FOLDER_NOT_DELETED, false, null, path);
                        }
                    }
                    // If clearContentIfDirectory is false, it won't be able to delete the directory
                } else {
                    return createResult(null, DIRECTORY_HAS_FILES, false, null, path);
                }
                // If it's a file, just tries to delete it
            } else {
                boolean deleted = fileToDelete.delete();
                if (deleted) {
                    return createResult(null, FILE_DELETED, true, null, path);
                } else {
                    return createResult(null, CANNOT_DELETE, false, null, path);
                }
            }
        }
    }


    /**
     * Deletes the content of a folder. If one of the files cannot be deleted, stops deleting.
     *
     * @param pathToFolder Folder to be cleared.
     * @return {@code true} if the content was cleared, or {@code false} if one of the files could
     * not be deleted or after deleting every file the folder is still not empty.
     */
    public static boolean clearFolder(String pathToFolder) {

        File folder = new File(pathToFolder);

        if (!isFolderEmpty(folder, false)) {
            if (folder.listFiles().length > 0) {
                // Deletes every file in the folder
                for (File file : folder.listFiles()) {
                    // If some of the files are directories, makes a recursive call to clear them
                    if (file.isDirectory()) {
                        boolean folderDeleted = clearFolder(file.getPath());
                        // If the content couldn't be deleted, returns false
                        if (!folderDeleted) {
                            Log.e(TAG, String.format(CANNOT_DELETE, file.getPath()));
                            return false;
                            // If the content was deleted, logs it.
                        } else {
                            Log.i(TAG, String.format(FILE_DELETED, file.getPath()));
                        }
                        // If some of the files are just files, deletes them
                    } else {
                        boolean fileDeleted = file.delete();
                        // If the file could not be deleted, returns false and stops deleting
                        if (!fileDeleted) {
                            Log.e(TAG, String.format(CANNOT_DELETE, file.getPath()));
                            return false;
                        } else {
                            // If the file was deleted, logs it
                            Log.i(TAG, String.format(FILE_DELETED, file.getPath()));
                        }
                    }
                }

                return isFolderEmpty(folder, true);
            }
        }

        return isFolderEmpty(folder, false);
    }


    /**
     * Returns {@code true} if the folder was cleared
     *
     * @param folder         The folder to be checked.
     * @param hasBeenCleared Pass false if you're calling this method on your own. This method only
     *                       receives {@code true} when called from {@link #clearFolder(String)}.
     * @return {@code true} if the folder is empty or {@code false} if it's not empty or it's not
     * a directory.
     */
    public static boolean isFolderEmpty(File folder, boolean hasBeenCleared) {

        if (!folder.isDirectory()) {
            Log.e(TAG, String.format(NOT_A_DIRECTORY, folder.getPath()));
            return false;
        }

        boolean isEmpty = folder.list().length == 0;
        if (!isEmpty) {
            if (hasBeenCleared) {
                Log.e(TAG, String.format(DELETED_BUT_STILL_NOT_EMPTY, folder.getPath()));
            } else {
                Log.i(TAG, String.format(FOLDER_IS_NOT_EMPTY, folder.getPath()));
            }
        } else {
            Log.i(TAG, String.format(FOLDER_CLEARED, folder.getPath()));
        }
        return isEmpty;
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
            return true;
        }
        Log.e(TAG, "External memory is not available.");
        return false;
    }


    /**
     * Creates folder {@code pathToFolder}.
     *
     * @param pathToFolder Path to the folder including the folder name.
     * @return A {@code Result<File>} whose method {@link Result#isSuccessful()} returns
     * {@code true} if the folder was created or already existed, or {@code false} if the folder
     * could not be created.
     */
    public static Result<File> createFolder(String pathToFolder) {

        File folderFile = new File(pathToFolder);
        if (folderFile.exists() && folderFile.isDirectory()) {
            return createResult(null, ALREADY_EXISTS, true, null, pathToFolder);
        } else {
            boolean creada = folderFile.mkdir();
            if (creada) {
                return createResult(folderFile, CREATED, true, null, pathToFolder);
            } else {
                return createResult(null, CANNOT_CREATE, false, null, pathToFolder);
            }
        }
    }


    /**
     * Creates the folders necessary to generate the path.
     *
     * @param path Contains the path to create.
     * @return The path to the file if it could be created or {@code null} if it couldn't.
     */
    public static String createPath(Path path) {

        if (!path.getFolders().isEmpty()) {

            String basePath = path.getBasePath();

            for (String folderName : path.getFolders()) {
                String folderPath = basePath + "/" + folderName;
                Result<File> folderCreatedResult = createFolder(folderPath);

                if (folderCreatedResult.isSuccessful()) {
                    basePath = folderPath;
                } else {
                    return null;
                }
            }

            Log.i(TAG, "Folders " + path.getFolders().toString() + " were created or already existed.");
        } else {
            Log.i(TAG, "The path contained no folders. No folders have been created.");
        }

        return path.getPath();
    }


    /**
     * Creates a {@link Result} object with the object resulting from a call to a
     * {@code MemoryUtils}' method specified. It will format the message and log it.
     *
     * @param object           The object resulting from the call to the {@code MemoryUtils}'
     *                         method.
     * @param message          The error/success message resulting from the call to the
     *                         {@code MemoryUtils}' method.
     * @param success          Pass {@code true} if the call to the {@code MemoryUtils}' method was
     *                         successful or else {@code false}.
     * @param e                The exception resulting from the call to the {@code MemoryUtils}'
     *                         method Pass {@code null} if no exception was thrown.
     * @param formatParameters The parameters necessary to format the message. Don't pass anything
     *                         if the message needs not be formatted.
     * @param <T>              The type of object resulting from the call to the
     *                         {@code MemoryUtils}' method.
     * @return The {@code Result<T>} object created.
     */
    private static <T> Result<T> createResult(@Nullable T object, @NonNull String message,
                                              boolean success, @Nullable Exception e,
                                              String... formatParameters) {
        String formattedMessage;
        if (formatParameters.length > 0) {
            formattedMessage = String.format(message, formatParameters);
        } else {
            formattedMessage = message;
        }

        Result<T> result = new Result<>();
        result.setResult(object);
        result.setSuccessful(success);
        result.setMessage(formattedMessage);

        if (success) {
            Log.i(TAG, formattedMessage);
        } else {
            Log.e(TAG, formattedMessage);
        }

        if (e != null) {
            e.printStackTrace();
        }

        return result;
    }
}
package melerospaw.memoryutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;

/**
 * Created by Juan José Melero on 26/05/2015.<br/><br/>
 * Class to make it easier to save and load from Android device's memory.
 * Use {@link Path.Builder} to create a {@code Path} to call {@code MemoryUtils}' methods.
 * This way, folders will be created automatically when saving.
 * Then use {@link Result} class to retrieve objects from method calls, for example, the
 * {@code File} where something was saved to or the object that you are trying to retrieve from
 * memory.
 * <p/>
 * Example code:
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
    public static final String DESTINATION_FOLDER_NOT_FOUND = "Destination folder for %1$s was not found.";
    public static final String DOESNT_EXIST = "File %1$s doesn't exist.";
    public static final String ERROR_WHILE_READING = "Error while reading the file %1$s";
    public static final String ERROR_WHILE_WRITING = "Error writing the file %1$s";
    public static final String FILENAME_NULL = "The file name is null.";
    public static final String FILE_COPIED = "File %1$s was copied to %2$s.";
    public static final String FILE_DELETED = "File %1$s was deleted.";
    public static final String FILE_LOADED = "File %1$s was loaded.";
    public static final String FILE_NOT_FOUND = "Destination path %1$s couldn't be found.";
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


    /**
     * Checks whether a a file exists or not. </i>
     *
     * @param path {@code Path} to the file to be checked.
     * @return {@code true} if the file exists or else {@code false}.
     */
    public static boolean exists(Path path) {
        return exists(path.getPath());
    }

    /**
     * Checks whether a a file exists or not. </i>
     *
     * @param path Path to the file to be checked.
     * @return {@code true} if the file exists or else {@code false}.
     */
    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * Gets a {@code Bitmap} from assets folder.
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
     * Duplicates a file. Creates the destination folders for you.
     *
     * @param originPath      Path to file to be copied.
     * @param destinationPath Path where the file will be copied to.
     * @return A {@code Result} containing the duplicated {@code File} or null if duplication was
     * not possible.
     */
    public static Result<File> duplicateFile(Path originPath, Path destinationPath) {
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
    public static Result<File> duplicateFile(@NonNull String originPath, @NonNull String destinationPath) {

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

    public static Result<File> copyFromInputStream(String originPath, InputStream inputStream, String destinationPath) {

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
     * Deletes the file in {@code path}.
     *
     * @param path Path to the file.
     * @return A {@code Result} returning {@code isSuccessful() == true} if the file could be
     * deleted.
     */
    public static Result deleteFile(Path path) {
        return deleteFile(path.getPath());
    }

    /**
     * Deletes the file in {@code path}.
     *
     * @param path Path to the file.
     * @return A {@code Result} returning {@code isSuccessful() == true} if the file could be
     * deleted.
     */
    public static Result deleteFile(String path) {

        if (path == null) {
            return createResult(null, NULL_DELETE_PATH, false, null);
        }

        File fileToDelete = new File(path);
        if (fileToDelete.exists()) {
            boolean deleted = fileToDelete.delete();
            if (deleted) {
                return createResult(null, FILE_DELETED, true, null, path);
            } else {
                return createResult(null, CANNOT_DELETE, false, null, path);
            }
        } else {
            return createResult(null, DOESNT_EXIST, true, null, path);
        }
    }

    /**
     * Saves a text into a file using {@code FileWriter} and {@code BufferedWriter}.
     * It creates the folders necessary for you.
     *
     * @param text The text to be saved.
     * @param path The path where the file will be stored. Must include the name of the file.
     * @return A {@code Result<File>} containing the file if it was created or else {@code null}.
     */
    public static Result<File> saveTextFile(String text, Path path, boolean overwrite) {

        String filePath = createPath(path);
        return saveTextFile(text, filePath, overwrite);

    }

    /**
     * Saves a text into a file using {@code FileWriter} and {@code BufferedWriter}.
     * The path must exist.
     *
     * @param text The text to be saved.
     * @param path The path where the file will be stored. Must include the name of the file.
     * @return A {@code Result<File>} containing the file if it was created or else {@code null}.
     */
    public static Result<File> saveTextFile(String text, String path, boolean overwrite) {

        if (path == null) {
            return createResult(null, NULL_DESTINATION_PATH, false, null);
        }

        if (text == null) {
            return createResult(null, NULL_TEXT, false, null);
        }

        File destinationFile = new File(path);
        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(destinationFile, overwrite);
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
            return createResult(null, NULL_DESTINATION_PATH, false, null);
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

    public static String createFolder(String pathToFolder) {

        File folderFile = new File(pathToFolder);
        if (folderFile.exists() && folderFile.isDirectory()) {
            return ALREADY_EXISTS;
        } else {
            boolean creada = folderFile.mkdir();
            if (creada) {
                return pathToFolder;
            } else {
                return CANNOT_CREATE;
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

        if (!path.mFolders.isEmpty()) {

            String basePath = path.getBasePath();

            for (String folderName : path.mFolders) {
                String folderPath = basePath + "/" + folderName;
                String created = createFolder(folderPath);

                if (created.equals(CREATED) || created.equals(ALREADY_EXISTS)) {
                    basePath = folderPath;
                } else {
                    return null;
                }
            }

            Log.i(TAG, "Folders " + path.mFolders.toString() + " were created or already existed.");
        } else {
            Log.i(TAG, "The path contained no folders. No folders have been created.");
        }

        return path.getPath();
    }

    /**
     * Object representation of a path. A {@code Path} can be created using {@link Path.Builder}
     * class. and then obtained in its {@code String} form using {@link Path#getPath()}.
     */


    public static class Path {

        public static final int PRIVATE_INTERNAL = 0;
        public static final int PRIVATE_EXTERNAL = 1;
        public static final int PUBLIC_EXTERNAL = 2;
        public static final int PREDEFINED_PUBLIC_EXTERNAL = 3;
        public static final String DOCUMENTS = "documents";
        public static final String DCIM = "dcim";
        public static final String ALARMS = "alarms";
        public static final String DOWNLOADS = "downloads";
        public static final String MOVIES = "movies";
        public static final String MUSIC = "music";
        public static final String NOTIFICATIONS = "notifications";
        public static final String PICTURES = "pictures";
        public static final String PODCASTS = "podcasts";
        public static final String RINGTONES = "ringtones";

        private LinkedList<String> mFolders;
        private String mBasePath;
        private String mFileName;

        private Path(String mBasePath) {
            this.mBasePath = mBasePath;
            mFolders = new LinkedList<>();
        }


        public LinkedList<String> getFolders() {
            return mFolders;
        }

        private void setFolders(LinkedList<String> mFolders) {
            this.mFolders = mFolders;
        }

        public String getBasePath() {
            return mBasePath;
        }

        private void setBasePath(String mBasePath) {
            this.mBasePath = mBasePath;
        }

        public String getFileName() {
            return mFileName;
        }

        private void setFileName(String mFileName) {
            this.mFileName = mFileName;
        }

        public String getPath() {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(mBasePath);

            for (String folder : mFolders) {
                stringBuilder.append("/");
                stringBuilder.append(folder);
            }

            stringBuilder.append("/");

            if (!TextUtils.isEmpty(mFileName)) {
                stringBuilder.append(mFileName);
            }

            return stringBuilder.toString();
        }

        public File getFile() {
            return new File(getPath());
        }

        /**
         * Class to build a {@code Path}. You'll need to call at least {@link Builder#directory}
         * and optionally {@link Builder#folder(String)} and
         * {@link Builder#file(String)}.
         */
        public static class Builder {

            private Context context;

            private LinkedList<String> mFolders;
            private String mBasePath;
            private String mFileName;

            public Builder(Context context) {
                this.context = context;
                this.mFolders = new LinkedList<>();
            }

            public Builder folder(String folderName) {
                mFolders.add(folderName);
                return this;
            }

            public Builder file(String fileName) {
                mFileName = fileName;
                return this;
            }

            public Builder directory(@Directory int directory, @ExternalPublicDirectoryType String type) {
                switch (directory) {
                    case PRIVATE_EXTERNAL:
                        return usePrivateExternalDirectory(context);
                    case PUBLIC_EXTERNAL:
                        return usePublicExternalDirectory();
                    case PREDEFINED_PUBLIC_EXTERNAL:
                        if (type != null) {
                            String directoryType = getEnvironmentDirectoryType(type);
                            return usePublicExternalDirectory(context, directoryType);
                        } else {
                            Log.e(TAG, "To use a predefined external directory, you must specify" +
                                    "which one using Environment constants.");
                        }
                    case PRIVATE_INTERNAL:
                        return usePrivateExternalDirectory(context);
                }

                return this;
            }

            protected Builder usePublicExternalDirectory() {
                mBasePath = Environment.getExternalStorageDirectory().getPath();
                return this;
            }

            protected Builder usePublicExternalDirectory(Context context, String type) {
                mBasePath = Environment.getExternalStoragePublicDirectory(type).getPath();
                return this;
            }

            protected Builder usePrivateInternalDirectory(Context context) {
                mBasePath = context.getFilesDir().getPath();
                return this;
            }

            protected Builder usePrivateExternalDirectory(Context context) {
                if (MemoryUtils.isExternalMemoryAvailable()) {
                    mBasePath = context.getExternalFilesDir(null).getPath();
                } else {
                    Log.e(TAG, "Can't use external memory.");
                }

                return this;
            }

            /**
             * Returns the {@link Environment} constant for the path to the given predefined
             * external public directory expressed with the {@link ExternalPublicDirectoryType}.
             */
            public String getEnvironmentDirectoryType(@ExternalPublicDirectoryType String type) {

                String folderType;

                if (type.equals(ALARMS)) {
                    folderType = Environment.DIRECTORY_ALARMS;
                } else if (type.equals(DCIM)) {
                    folderType = Environment.DIRECTORY_DCIM;
                } else if (type.equals(DOCUMENTS)) {
                    folderType = Environment.DIRECTORY_DOCUMENTS;
                } else if (type.equals(MOVIES)) {
                    folderType = Environment.DIRECTORY_MOVIES;
                } else if (type.equals(MUSIC)) {
                    folderType = Environment.DIRECTORY_MUSIC;
                } else if (type.equals(NOTIFICATIONS)) {
                    folderType = Environment.DIRECTORY_NOTIFICATIONS;
                } else if (type.equals(PICTURES)) {
                    folderType = Environment.DIRECTORY_PICTURES;
                } else if (type.equals(PODCASTS)) {
                    folderType = Environment.DIRECTORY_PODCASTS;
                } else {
                    folderType = Environment.DIRECTORY_RINGTONES;
                }

                return folderType;
            }

            public Path build() {

                if (canCreatePath()) {
                    Path path = new Path(mBasePath);

                    path.setFolders(mFolders);

                    if (!TextUtils.isEmpty(mFileName)) {
                        path.setFileName(mFileName);
                    }

                    return path;

                } else {
                    return null;
                }
            }


            public boolean canCreatePath() {

                if (TextUtils.isEmpty(mBasePath)) {
                    Log.e(TAG, "No base path has been specified. You must choose a base directory.");
                    return false;
                }

                return true;
            }

        }

        @StringDef({ALARMS, DCIM, DOCUMENTS, DOWNLOADS, MOVIES, MUSIC, NOTIFICATIONS, PICTURES,
                PODCASTS, RINGTONES})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ExternalPublicDirectoryType {
        }

        @IntDef({PUBLIC_EXTERNAL, PREDEFINED_PUBLIC_EXTERNAL, PRIVATE_EXTERNAL, PRIVATE_INTERNAL})
        @Retention(RetentionPolicy.SOURCE)
        public @interface Directory {
        }
    }

    /**
     * Contains the result of method call from {@link MemoryUtils} methods.<br /><br />
     * <b>Fields:</b><br/>
     * <ul>
     * <li>{@code successful}: tells whether the method call has been successful or not.</li>
     * <li>{@code result}: the object resulting from the method call. If you save something,
     * you'll get the {@code File} pointing to the file where that something was saved.</li>
     * <li>{@code message}: a {@code String} containing the reason why the method called was not
     * successful.</li>
     * </ul>
     *
     * @param <T> The type of the object that will result from the method call. If the call results
     *            in nothing, no {@code T} parameter needs to be specified. For example, from method
     *            {@link #deleteFile(Path)} nothing is returned.
     */
    public static class Result<T> {

        private boolean successful;
        private T result;
        private String message;

        /**
         * Tells whether the method call has been successuful or not. This is the most
         * straightforward way to know, rather than calling {@code if (result.getResult() != null},
         * since sometimes nothing is returned from the latter method.
         *
         * @return {@code true} if the method call has been successful.
         */
        public boolean isSuccessful() {
            return successful;
        }

        private void setSuccessful(boolean successful) {
            this.successful = successful;
        }

        /**
         * Returns the result of the call to a {@link MemoryUtils}' method.
         *
         * @return <ul>
         * <li>When you save something, this method returns a {@code File} object pointing to
         * the file where that something was called.</li>
         * <li>When you want to retrieve a text or an object, this method returns that text or
         * the object casted to its class type.</li>
         * <li>When the method you call results in nothing, there is no need to instantiate
         * {@code Result} using the diamond operator and calls to this method returns
         * {@code null}.</li>
         * <li>When the method didn't execute successfully, this method returns {@code null}.</li>
         * </ul>
         */
        public T getResult() {
            return result;
        }

        private void setResult(T result) {
            this.result = result;
        }

        /**
         * When an error occurs during the execution of the {@link MemoryUtils} method, this method
         * returns a{@code String} with the error outcome. Errors and their stack traces are also
         * displayed in the logcat.
         *
         * @return A String containing the error.
         */
        public String getMessage() {
            return message;
        }

        private void setMessage(String message) {
            this.message = message;
        }
    }

    private static <T> Result<T> createResult(T object, String message, boolean success,
                                              @Nullable Exception e, String... formatParameters) {
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

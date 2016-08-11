package melerospaw.memoryutil;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;

/**
 * Object representation of a path. A {@code Path} can be created using the {@link Builder}
 * class and then obtained in its {@code String} form using {@link Path#getPath()}. We encourage
 * you to use {@code Path} when calling {@code MemoryUtils}' methods because this way methods
 * will take care of creating intermediate folders in the path built for you.
 */
public class Path {

    public static final String TAG = Path.class.getSimpleName();

    public static final int STORAGE_PRIVATE_INTERNAL = 0;
    public static final int STORAGE_PRIVATE_EXTERNAL = 1;
    public static final int STORAGE_PUBLIC_EXTERNAL = 2;
    public static final int STORAGE_PREDEFINED_PUBLIC_EXTERNAL = 3;
    public static final int STORAGE_PREDEFINED_PRIVATE_EXTERNAL = 4;
    public static final String TYPE_DOCUMENTS = "documents";
    public static final String TYPE_DCIM = "dcim";
    public static final String TYPE_ALARMS = "alarms";
    public static final String TYPE_DOWNLOADS = "downloads";
    public static final String TYPE_MOVIES = "movies";
    public static final String TYPE_MUSIC = "music";
    public static final String TYPE_NOTIFICATIONS = "notifications";
    public static final String TYPE_PICTURES = "pictures";
    public static final String TYPE_PODCASTS = "podcasts";
    public static final String TYPE_RINGTONES = "ringtones";

    private String mBasePath;
    private LinkedList<String> mFolders;
    private String mFileName;

    /**
     * Defines constants beginning with {@code "TYPE"} in this class to reference predefined folders
     * in external public or private storage directories to be used when calling
     * {@link Builder#storageDirectory(int, String)}.
     */
    @StringDef({TYPE_ALARMS, TYPE_DCIM, TYPE_DOCUMENTS, TYPE_DOWNLOADS, TYPE_MOVIES, TYPE_MUSIC,
            TYPE_NOTIFICATIONS, TYPE_PICTURES, TYPE_PODCASTS, TYPE_RINGTONES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExternalDirectoryType {
    }

    /**
     * Defines constants to refer to external and internal directories available for saving/loading
     * files in an Android device, used when calling {@link Builder#storageDirectory(int, String)}.
     * These are:
     * <ul>
     * <li>{@link #STORAGE_PRIVATE_EXTERNAL}</li>
     * <li>{@link #STORAGE_PUBLIC_EXTERNAL}</li>
     * <li>{@link #STORAGE_PRIVATE_INTERNAL}</li>
     * </ul>
     */
    @IntDef({STORAGE_PUBLIC_EXTERNAL, STORAGE_PRIVATE_EXTERNAL, STORAGE_PRIVATE_INTERNAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Directory {
    }

    /**
     * Defines constants to refer to predefined private/public external directories available for
     * saving/loading files in an Android device, to be used when calling
     * {@link Builder#storageDirectory(int, String)}. These are:
     * <ul>
     * <li>{@link #STORAGE_PREDEFINED_PUBLIC_EXTERNAL}</li>
     * <li>{@link #STORAGE_PREDEFINED_PRIVATE_EXTERNAL}.</li>
     * </ul>
     */
    @IntDef({STORAGE_PREDEFINED_PUBLIC_EXTERNAL, STORAGE_PREDEFINED_PRIVATE_EXTERNAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PredefinedDirectory {
    }

    private Path(String mBasePath) {
        this.mBasePath = mBasePath;
        mFolders = new LinkedList<>();
    }

    /**
     * Returns the list of the folders that will be appended to the {@link #mBasePath} to create
     * the full path. Folders will be appended to the path in the same order they were added.
     *
     * @return A {@code LinkedList} of String with as many elements as folders will be appended
     * to the path.
     */
    public LinkedList<String> getFolders() {
        return mFolders;
    }

    private void setFolders(LinkedList<String> mFolders) {
        this.mFolders = mFolders;
    }

    /**
     * Returns the path to the base folder referenced. This value will be the path to the
     * directory in the device specified in the method {@link Builder#storageDirectory(int)},
     * {@link Builder#storageDirectory(int, String)} or
     * {@link Builder#databaseDirectory(String)}.
     *
     * @return A {@code String} containing the path to the base directory on the device.
     */
    public String getBasePath() {
        return mBasePath;
    }

    private void setBasePath(String mBasePath) {
        this.mBasePath = mBasePath;
    }

    /**
     * Returns name of the destination file that will be appended at the end of the path.
     *
     * @return The name of destination file or an empty string if no file name is specified
     * during the building process.
     */
    public String getFileName() {
        return mFileName;
    }

    private void setFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    /**
     * Returns the full path composed by the base storage directory, the folders and the file
     * name specified during the building of the object.
     *
     * @return A {@code String} with the path.
     */
    public String getPath() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(mBasePath);

        for (String folder : mFolders) {
            stringBuilder.append("/").append(folder);
        }


        if (!TextUtils.isEmpty(mFileName)) {
            stringBuilder.append("/").append(mFileName);
        }

        return stringBuilder.toString();
    }

    /**
     * Returns a {@code File} pointing to the path contained in this {@code Path} object
     * specified with {@link Builder#file(String)} during the bulding process.
     *
     * @return A {@code File} object.
     */
    public File getFile() {
        return new File(getPath());
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("BASE PATH: ");

        if (TextUtils.isEmpty(mBasePath)) {
            toString.append("No base path specified.");
        } else {
            toString.append(mBasePath);
        }

        if (!mFolders.isEmpty()){
            toString.append("\nFOLDERS: ");
            for (String folder : mFolders){
                toString.append("/").append(folder);
            }
        }

        if (!TextUtils.isEmpty(mFileName)) {
            toString.append("\nFILE: ").append("/").append(mFileName);
        }

        return toString.toString();
    }


    /**
     * <p>Class to build a {@code Path}. You'll need to call at least
     * {@link Builder#storageDirectory} or {@link Builder#databaseDirectory(String)} and,
     * optionally {@link Builder#folder(String)} and {@link Builder#file(String)}.</p>
     * <p>E.g.: to create a {@code Path} referencing
     * <i>data/data/my.application.package/files/customFolder/myFile.txt</i>, call:
     * </p>
     * <pre>{@code Path} myPath = new {@link Builder}(context)
     * .storageDirectory(Path.{@link #STORAGE_PRIVATE_INTERNAL})
     * .folder("customFolder")
     * .file("myFile.txt")
     * .build();
     * </pre>
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

        /**
         * <p>Sets the name of the folders to be appended to the path after the storage directory
         * path specified with {@link #storageDirectory(int, String)},
         * {@link #storageDirectory(int)} or {@link #databaseDirectory(String)}. If you want to
         * specify a path with several folders, call this method passing the name of the folders
         * in the order that will appear in the path.</p>
         * E.g.: to specify path
         * <i>/myFolderA/myFolderB/myFolderC</i>, call
         * {@code builder.folder("myFolderA").folder("myFolderB").folder("myFolderC");}
         *
         * @param folderName The name of the folder being referenced.
         * @return The same {@code Builder} object making the call.
         */
        public Builder folder(String folderName) {
            mFolders.add(folderName);
            return this;
        }


        /**
         * Sets the name of the file at the very end of the path being created.
         *
         * @param fileName The name of the file being referenced.
         * @return The same {@code Builder} object making the call.
         */
        public Builder file(String fileName) {
            mFileName = fileName;
            return this;
        }


        /**
         * Sets the base path to one of the application's predefined external directories.
         *
         * @param directory The name of the external directory to be referenced.
         * @param type      The type of the predefined folder in the external directory to be
         *                  referenced.
         * @return The same {@code Builder} object making the call.
         */
        public Builder storageDirectory(@PredefinedDirectory int directory,
                                        @ExternalDirectoryType String type) {
            if (type == null) {
                Log.e(TAG, "To use a predefined external directory, you must specify" +
                        "which one using Environment constants.");
                mBasePath = "";

            } else {
                switch (directory) {
                    case STORAGE_PREDEFINED_PUBLIC_EXTERNAL:
                        mBasePath = usePublicExternalDirectory(type);
                        break;
                    case STORAGE_PREDEFINED_PRIVATE_EXTERNAL:
                        mBasePath = usePrivateExternalDirectory(type);
                    default:
                        Log.e(TAG, "You must pass STORAGE_PREDEFINED_PUBLIC_EXTERNAL or " +
                                "STORAGE_PREDEFINED_PRIVATE_EXTERNAL as a directory.");
                }
            }

            return this;
        }


        /**
         * Sets the base path to the application's external or internal directory.
         *
         * @param directory The name of the base directory file to be referenced.
         * @return The same {@code Builder} object making the call.
         */
        public Builder storageDirectory(@Directory int directory) {
            switch (directory) {
                case STORAGE_PRIVATE_EXTERNAL:
                    mBasePath = usePrivateExternalDirectory();
                    break;
                case STORAGE_PUBLIC_EXTERNAL:
                    mBasePath = usePublicExternalDirectory();
                    break;
                case STORAGE_PRIVATE_INTERNAL:
                    mBasePath = usePrivateInternalDirectory();
                    break;
            }

            return this;
        }


        /**
         * Sets the base path to the application's database directory.
         *
         * @param databaseName The name of the database file to be referenced.
         * @return The same {@code Builder} object making the call.
         */
        public Builder databaseDirectory(String databaseName) {
            mBasePath = useDatabaseDirectory(databaseName);
            return this;
        }


        /**
         * Returns the path to the application's public external directory in an Android device.
         *
         * @return Default implementation returns
         * {@code Environment.getExternalStorageDirectory().getPath()}.
         */
        protected String usePublicExternalDirectory() {
            return Environment.getExternalStorageDirectory().getPath();
        }


        /**
         * Returns the path to the application's public external directory in an Android
         * device.
         *
         * @param type The destination directory in the public external. Use any of the
         *             following {@code Path}'s constants: {@link #TYPE_ALARMS},
         *             {@link #TYPE_DCIM}, {@link #TYPE_DOCUMENTS}, {@link #TYPE_DOWNLOADS},
         *             {@link #TYPE_MOVIES}, {@link #TYPE_MUSIC}, {@link #TYPE_NOTIFICATIONS},
         *             {@link #TYPE_PICTURES}, {@link #TYPE_PODCASTS}, {@link #TYPE_RINGTONES}.
         * @return Default implementation returns
         * {@code Environment.getExternalStoragePublicDirectory(type).getPath()}.
         */
        protected String usePublicExternalDirectory(@ExternalDirectoryType String type) {
            if (MemoryUtils.isExternalMemoryAvailable()) {
                return Environment.getExternalStoragePublicDirectory(
                        getEnvironmentDirectoryType(type)).getPath();
            } else {
                Log.e(TAG, "External memory is not available.");
                return "";
            }
        }

        /**
         * Returns the path to the application's private internal directory in an Android
         * device.
         *
         * @return Default implementation returns
         * {@code context.getFilesDir().getPath()}.
         */
        protected String usePrivateInternalDirectory() {
            return context.getFilesDir().getPath();
        }


        /**
         * Returns the path to the application's private external directory in an Android
         * device.
         *
         * @return Default implementation returns
         * {@code context.getExternalFilesDir(null).getPath()}.
         */
        protected String usePrivateExternalDirectory() {
            if (MemoryUtils.isExternalMemoryAvailable()) {
                return context.getExternalFilesDir(null).getPath();
            } else {
                Log.e(TAG, "External memory is not available.");
                return "";
            }
        }


        /**
         * Returns the path to the application's private external directory in an Android
         * device.
         *
         * @param type The destination directory in the private external memory. Use any of the
         *             following {@code Path}'s constants: {@link #TYPE_ALARMS},
         *             {@link #TYPE_DCIM}, {@link #TYPE_DOCUMENTS}, {@link #TYPE_DOWNLOADS},
         *             {@link #TYPE_MOVIES}, {@link #TYPE_MUSIC}, {@link #TYPE_NOTIFICATIONS},
         *             {@link #TYPE_PICTURES}, {@link #TYPE_PODCASTS}, {@link #TYPE_RINGTONES}.
         * @return Default implementation returns
         * {@code context.getExternalFilesDir(type).getPath()}.
         */
        protected String usePrivateExternalDirectory(@ExternalDirectoryType String type) {
            if (MemoryUtils.isExternalMemoryAvailable()) {
                return context.getExternalFilesDir(getEnvironmentDirectoryType(type)).getPath();
            } else {
                Log.e(TAG, "External memory is not available.");
                return "";
            }
        }


        /**
         * Returns the path to the applications's database directory in an Android device.
         *
         * @param databaseName The name of the database file in the device.
         * @return Default implementation returns
         * {@code context.getDatabasePath(databaseName).getPath()}.
         */
        protected String useDatabaseDirectory(String databaseName) {
            return context.getDatabasePath(databaseName).getPath();
        }


        /**
         * Returns the {@link Environment} constant for the path to the given predefined external
         * public/private directory expressed with the {@link ExternalDirectoryType}.
         *
         * @param type The destination directory in the private external memory. Use any of the
         *             following {@code Path}'s constants: {@link #TYPE_ALARMS},
         *             {@link #TYPE_DCIM}, {@link #TYPE_DOCUMENTS}, {@link #TYPE_DOWNLOADS},
         *             {@link #TYPE_MOVIES}, {@link #TYPE_MUSIC}, {@link #TYPE_NOTIFICATIONS},
         *             {@link #TYPE_PICTURES}, {@link #TYPE_PODCASTS}, {@link #TYPE_RINGTONES}.
         * @return The {@link Environment} constant to refer the destination predefined directory.
         */

        public String getEnvironmentDirectoryType(@ExternalDirectoryType String type) {

            String folderType;

            switch (type) {
                case TYPE_ALARMS:
                    folderType = Environment.DIRECTORY_ALARMS;
                    break;
                case TYPE_DCIM:
                    folderType = Environment.DIRECTORY_DCIM;
                    break;
                case TYPE_DOCUMENTS:
                    folderType = Environment.DIRECTORY_DOCUMENTS;
                    break;
                case TYPE_MOVIES:
                    folderType = Environment.DIRECTORY_MOVIES;
                    break;
                case TYPE_MUSIC:
                    folderType = Environment.DIRECTORY_MUSIC;
                    break;
                case TYPE_NOTIFICATIONS:
                    folderType = Environment.DIRECTORY_NOTIFICATIONS;
                    break;
                case TYPE_PICTURES:
                    folderType = Environment.DIRECTORY_PICTURES;
                    break;
                case TYPE_PODCASTS:
                    folderType = Environment.DIRECTORY_PODCASTS;
                    break;
                default:
                    folderType = Environment.DIRECTORY_PICTURES;
                    break;
            }

            return folderType;
        }

        /**
         * Creates the {@code Path} object using the specified base directory, folders and file
         * name. You must at least call {@link #storageDirectory(int)},
         * {@link #storageDirectory(int, String)} or {@link #databaseDirectory(String)} to
         * create a valid {@code Path} object.
         *
         * @return A valid Path object or {@code null} if no base directory was specified.
         */
        public Path build() {

            if (canCreatePath()) {
                Path path = new Path(mBasePath);
                path.setFolders(mFolders);
                path.setFileName(TextUtils.isEmpty(mFileName) ? "" : mFileName);
                Log.i(TAG, String.format("Path object pointing to %1$s was created.", path.getPath()));
                return path;
            } else {
                Log.e(TAG, "No base path has been specified. You must explicitly call" +
                        "Builder.storageDirectory() or Builder.databaseDirectory(). to" +
                        "specify a base directory.");
                return null;
            }
        }


        /**
         * Tells whether the specified path can be created.
         *
         * @return Return {@code true} if a base path has been specified or else {@code false}.
         */
        public boolean canCreatePath() {
            return !TextUtils.isEmpty(mBasePath);
        }

        @Override
        public String toString() {
            StringBuilder toString = new StringBuilder();
            toString.append("BASE PATH: ");

            if (TextUtils.isEmpty(mBasePath)) {
                toString.append("No base path specified.");
            } else {
                toString.append(mBasePath);
            }

            if (!mFolders.isEmpty()){
                toString.append("\nFOLDERS: ");
                for (String folder : mFolders){
                    toString.append("/").append(folder);
                }
            }

            if (!TextUtils.isEmpty(mFileName)) {
                toString.append("\nFILE: ").append("/").append(mFileName);
            }

            return toString.toString();
        }
    }
}
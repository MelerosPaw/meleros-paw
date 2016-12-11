package melerospaw.memoryutil.validation;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import melerospaw.memoryutil.Path;
import melerospaw.memoryutil.validation.ValidationEnums.Invalidity;
import melerospaw.memoryutil.validation.ValidationEnums.Method;
import melerospaw.memoryutil.validation.ValidationEnums.Parameter;

public class Validator implements ValidatorInterface {

    private final HashMap<Parameter, Object> parameters;
    private final Method method;

    public Validator(HashMap<Parameter, Object> parameters, Method method) {
        this.parameters = parameters;
        this.method = method;
    }

    @Override
    public ValidationInfoInterface assertAreParametersValid() {

        ValidationInfoInterface info = new ValidationInfo(method, parameters);

        for (Map.Entry<Parameter, Object> paramEntry : parameters.entrySet()) {
            Parameter parameter = paramEntry.getKey();
            Object value = paramEntry.getValue();

            if (method == Method.IMPORT_FROM_ASSETS && parameter == Parameter.FILE_NAME) {
                info = validateFileName(parameters, info);
            } else {
                switch (parameter) {
                    case DESTINATION_PATH:
                        info = validateDestinationPath((String) value, info);
                        break;
                    case PATH_TO_FOLDER:
                        info = validatePathToFolder((String) value, info);
                        break;
                    case ORIGIN_PATH:
                        info = validateOriginPath((String) value, info);
                        break;
                    case ORIGIN_PATH_OBJECT:
                        info = validateOriginPathObject((Path) value, info);
                        break;
                    case DESTINATION_PATH_OBJECT:
                    case PATH_OBJECT_TO_FOLDER:
                    case BITMAP:
                    case FILE:
                    case CONTEXT:
                    case SHARED_PREFERENCES:
                    case PATH_OBJECT:
                    case INPUTSTREAM:
                    case TEXT:
                    case CLASS:
                    case BYTE_ARRAY:
                        info = validateNull(value, info, parameter);
                        break;
                    case PATH_OBJECT_TO_FILE:
                        info = validatePathToFile((Path) value, info);
                        break;
                    case FILE_NAME:
                    case PATH_TO_FILE:
                    case PATH:
                    case DATABASE_NAME:
                        info = validateString((String) value, info, parameter);
                        break;
                    case FOLDER:
                        info = validateFolder((File) value, info);
                        break;
                    case OBJECT:
                        info = validateObject(value, info);
                        break;
                    default:
                        info.setUnexpectedParamInfo();
                }
            }

            if (!info.isValid()) {
                break;
            }
        }

        return info;
    }


    /*PARAMETER VALIDATION*/
    public ValidationInfoInterface validateDestinationPath(String destinationPath,
                                                           ValidationInfoInterface info) {

        Invalidity invalidity;
        boolean isValid;

        Invalidity invalidityAux = validateNullOrEmpty(destinationPath);
        if (invalidityAux != Invalidity.NONE) {
            invalidity = invalidityAux;
        } else {
            switch (method) {
                case COPY_FROM_INPUTSTREAM:
                    if (new File(destinationPath).isDirectory()) {
                        invalidity = Invalidity.IS_A_DIRECTORY;
                    } else {
                        invalidity = isValidForSaving(destinationPath, info);
                    }
                    break;
                default:
                    invalidity = isValidForSaving(destinationPath, info);
            }
        }

        isValid = invalidity == Invalidity.NONE;
        setValidationValues(info, Parameter.DESTINATION_PATH, invalidity, isValid);
        return info;
    }


    public ValidationInfoInterface validateOriginPath(String originPath,
                                                      ValidationInfoInterface info) {

        Invalidity invalidity;
        boolean isValid;

        Invalidity invalidityAux = validateNullOrEmpty(originPath);
        if (invalidityAux != Invalidity.NONE) {
            invalidity = invalidityAux;
        } else if (!new File(originPath).exists()) {
            invalidity = Invalidity.FILE_DOESNT_EXIST;
        } else {
            invalidity = Invalidity.NONE;
        }

        isValid = invalidity == Invalidity.NONE;
        setValidationValues(info, Parameter.ORIGIN_PATH, invalidity, isValid);
        return info;
    }


    public ValidationInfoInterface validateOriginPathObject(Path originPath,
                                                            ValidationInfoInterface info) {

        Invalidity invalidity;

        Invalidity invalidityAux = validateNull(originPath);
        if (invalidityAux != Invalidity.NONE) {
            invalidity = invalidityAux;
            setValidationValues(info, Parameter.ORIGIN_PATH_OBJECT, invalidity, false);
        } else {
            info = validateOriginPathObjectPath(originPath.getPath(), info);
        }

        return info;
    }


    public ValidationInfoInterface validateNull(Object object, ValidationInfoInterface info,
                                                Parameter parameter) {
        Invalidity invalidity = validateNull(object);
        boolean isValid = invalidity == Invalidity.NONE;
        setValidationValues(info, parameter, invalidity, isValid);
        return info;
    }


    public ValidationInfoInterface validatePathToFile(Path pathToFile,
                                                      ValidationInfoInterface info) {

        Invalidity invalidity = validateNull(pathToFile);
        boolean isValid = invalidity == Invalidity.NONE;
        setValidationValues(info, Parameter.PATH_OBJECT_TO_FILE, invalidity, isValid);
        return info;
    }


    public ValidationInfoInterface validatePathToFolder(String pathToFolder,
                                                        ValidationInfoInterface info) {

        Invalidity invalidity;
        boolean isValid;

        Invalidity invalidityAux = validateNullOrEmpty(pathToFolder);
        if (invalidityAux != Invalidity.NONE) {
            invalidity = invalidityAux;
        } else {

            File folder = new File(pathToFolder);

            if (method == Method.CREATE_FOLDER) {
                if (folder.exists() && !folder.isDirectory()) {
                    invalidity = Invalidity.EXISTS_AS_NOT_DIRECTORY;
                } else {
                    invalidity = isValidForSaving(pathToFolder, info);
                }
            } else {
                if (!folder.exists()) {
                    invalidity = Invalidity.FILE_DOESNT_EXIST;
                } else if (!folder.isDirectory()) {
                    invalidity = Invalidity.NOT_A_DIRECTORY;
                } else {
                    invalidity = Invalidity.NONE;
                }
            }
        }

        isValid = invalidity == Invalidity.NONE;
        setValidationValues(info, Parameter.PATH_TO_FOLDER, invalidity, isValid);
        return info;
    }


    public ValidationInfoInterface validateFolder(File folder, ValidationInfoInterface info) {

        Invalidity invalidity;
        boolean isValid;

        Invalidity invalidityAux = validateNull(folder);
        if (invalidityAux != Invalidity.NONE) {
            invalidity = invalidityAux;
        } else if (!folder.exists()) {
            invalidity = Invalidity.FILE_DOESNT_EXIST;
        } else if (!folder.isDirectory()) {
            invalidity = Invalidity.NOT_A_DIRECTORY;
        } else {
            invalidity = Invalidity.NONE;
        }

        isValid = invalidity == Invalidity.NONE;
        setValidationValues(info, Parameter.FOLDER, invalidity, isValid);
        return info;
    }


    public ValidationInfoInterface validateObject(Object object, ValidationInfoInterface info) {

        Invalidity invalidity;
        boolean isValid;

        Invalidity invalidityAux = validateNull(object);
        if (invalidityAux != Invalidity.NONE) {
            invalidity = invalidityAux;
        } else if (!(object instanceof Serializable)) {
            invalidity = Invalidity.NOT_SERIALIZABLE;
        } else {
            invalidity = Invalidity.NONE;
        }

        isValid = invalidity == Invalidity.NONE;
        setValidationValues(info, Parameter.OBJECT, invalidity, isValid);
        return info;
    }


    public ValidationInfoInterface validateFileName(HashMap<Parameter, Object> parameters,
                                                  ValidationInfoInterface info){

        Invalidity invalidity;
        boolean isValid;
        Context context = (Context) parameters.get(Parameter.CONTEXT);
        String fileName = (String) parameters.get(Parameter.FILE_NAME);


        Invalidity invalidityAux = validateNullOrEmpty(fileName);
        if (invalidityAux != Invalidity.NONE) {
            invalidity = invalidityAux;
        } else if (context != null){
            try {
                context.getAssets().open(fileName);
                invalidity = Invalidity.NONE;
            } catch(IOException e){
                invalidity = Invalidity.ASSET_DOESNT_EXIST;
            }
        } else {
            invalidity = Invalidity.NONE;
        }

        isValid = invalidity == Invalidity.NONE;
        setValidationValues(info, Parameter.FILE_NAME, invalidity, isValid);
        return info;

    }


    /* GENERIC VALIDATION */
    public ValidationInfoInterface validateString(String pathToFile, ValidationInfoInterface info,
                                                  Parameter parameter) {
        Invalidity invalidity = validateNullOrEmpty(pathToFile);
        boolean isValid = invalidity == Invalidity.NONE;
        setValidationValues(info, parameter, invalidity, isValid);
        return info;
    }

    public Invalidity validateNull(Object object) {
        return object == null ? Invalidity.IS_NULL : Invalidity.NONE;
    }

    public Invalidity validateNullOrEmpty(String theString) {

        Invalidity invalidity;

        if (theString == null) {
            invalidity = Invalidity.IS_NULL;
        } else if (theString.isEmpty()) {
            invalidity = Invalidity.IS_EMPTY;
        } else {
            invalidity = Invalidity.NONE;
        }

        return invalidity;
    }

    public ValidationInfoInterface validateOriginPathObjectPath(String stringPath,
                                                                ValidationInfoInterface info) {

        Invalidity invalidity;
        boolean isValid;

        String path = stringPath;
        Invalidity invalidityAux = validateNullOrEmpty(path);
        if (invalidityAux != Invalidity.NONE){
            invalidity = invalidityAux;
        } else {
            if (!new File(stringPath).exists()) {
                invalidity = Invalidity.FILE_DOESNT_EXIST;
            } else {
                invalidity = Invalidity.NONE;
            }
        }

        isValid = invalidity == Invalidity.NONE;
        setValidationValues(info, Parameter.PATH_OBJECT_STRING, invalidity, isValid);
        return info;
    }

    public Invalidity isValidForSaving(String path, ValidationInfoInterface info) {
        ValidationInfoInterface infoAux =
                ValidationUtils.isPathValidForSaving(new File(path), info.getMethod() == Method.CREATE_FOLDER, info);
        return !infoAux.isValid() ? infoAux.getInvalidityType() : Invalidity.NONE;
    }

    private void setValidationValues(ValidationInfoInterface info, Parameter parameter,
                                     Invalidity invalidity, boolean isValid) {
        info.setInvalidParameter(parameter);
        info.setInvalidityType(invalidity);
        info.setIsValid(isValid);
    }



    /*METHOD VALIDATION*/
    public static ValidationInfoInterface validateCopyFromInputStream(InputStream inputStream,
                                                                      Path destinationPath) {
        Method method = Method.COPY_FROM_INPUTSTREAM;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.INPUTSTREAM, inputStream);
        parameters.put(Parameter.DESTINATION_PATH_OBJECT, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateCopyFromInputStream(InputStream inputStream,
                                                                      String destinationPath) {
        Method method = Method.COPY_FROM_INPUTSTREAM;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.INPUTSTREAM, inputStream);
        parameters.put(Parameter.DESTINATION_PATH, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateImportFromAssets(Context context, String fileName,
                                                                   Path destinationPath) {
        Method method = Method.IMPORT_FROM_ASSETS;
        HashMap<Parameter, Object> parameters = new HashMap<>(3);
        parameters.put(Parameter.CONTEXT, context);
        parameters.put(Parameter.FILE_NAME, fileName);
        parameters.put(Parameter.DESTINATION_PATH_OBJECT, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateImportFromAssets(Context context, String fileName,
                                                                   String destinationPath) {
        Method method = Method.IMPORT_FROM_ASSETS;
        HashMap<Parameter, Object> parameters = new HashMap<>(3);
        parameters.put(Parameter.CONTEXT, context);
        parameters.put(Parameter.FILE_NAME, fileName);
        parameters.put(Parameter.DESTINATION_PATH, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateImportDatabaseFromAssets(Context context,
                                                                           String dataBaseName) {
        Method method = Method.IMPORT_DATABASE_FROM_ASSETS;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.CONTEXT, context);
        parameters.put(Parameter.DATABASE_NAME, dataBaseName);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadBitmapFromAssets(Context context,
                                                                       String fileName) {
        Method method = Method.LOAD_BITMAP_FROM_ASSETS;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.CONTEXT, context);
        parameters.put(Parameter.FILE_NAME, fileName);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateSaveTextFile(String text,
                                                               String destinationPath) {
        Method method = Method.SAVE_TEXT_FILE;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.TEXT, text);
        parameters.put(Parameter.DESTINATION_PATH, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateSaveTextFile(String text, Path destinationPath) {
        Method method = Method.SAVE_TEXT_FILE;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.TEXT, text);
        parameters.put(Parameter.DESTINATION_PATH_OBJECT, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadTextFile(Path originPath) {
        Method method = Method.LOAD_TEXT_FILE;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.ORIGIN_PATH_OBJECT, originPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadTextFile(String originPath) {
        Method method = Method.LOAD_TEXT_FILE;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.ORIGIN_PATH, originPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateSaveObject(Object object, Path destinationPath) {
        Method method = Method.SAVE_OBJECT;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.OBJECT, object);
        parameters.put(Parameter.DESTINATION_PATH_OBJECT, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateSaveObject(Object object,
                                                             String destinationPath) {
        Method method = Method.SAVE_OBJECT;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.OBJECT, object);
        parameters.put(Parameter.DESTINATION_PATH, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadObject(Path originPath, Class clazz) {
        Method method = Method.LOAD_OBJECT;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.ORIGIN_PATH_OBJECT, originPath);
        parameters.put(Parameter.CLASS, clazz);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadObject(String originPath, Class clazz) {
        Method method = Method.LOAD_OBJECT;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.ORIGIN_PATH, originPath);
        parameters.put(Parameter.CLASS, clazz);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateSaveByteArray(byte[] byteArray,
                                                                Path destinationPath) {
        Method method = Method.SAVE_BYTE_ARRAY;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.BYTE_ARRAY, byteArray);
        parameters.put(Parameter.DESTINATION_PATH_OBJECT, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateSaveByteArray(byte[] byteArray,
                                                                String destinationPath) {
        Method method = Method.SAVE_BYTE_ARRAY;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.BYTE_ARRAY, byteArray);
        parameters.put(Parameter.DESTINATION_PATH, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateSaveBitmap(Bitmap bitmap,
                                                             String destinationPath) {
        Method method = Method.SAVE_BITMAP;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.BITMAP, bitmap);
        parameters.put(Parameter.DESTINATION_PATH, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateSaveBitmap(Bitmap bitmap, Path destinationPath) {
        Method method = Method.SAVE_BITMAP;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.BITMAP, bitmap);
        parameters.put(Parameter.DESTINATION_PATH_OBJECT, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadBitmap(Path originPath) {
        Method method = Method.LOAD_BITMAP;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.ORIGIN_PATH_OBJECT, originPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadBitmap(String originPath) {
        Method method = Method.LOAD_BITMAP;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.ORIGIN_PATH, originPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateSaveSharedPreferences(SharedPreferences sharedPreferences,
                                                                        Path destinationPath) {
        Method method = Method.SAVE_SHARED_PREFERENCES;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.SHARED_PREFERENCES, sharedPreferences);
        parameters.put(Parameter.DESTINATION_PATH_OBJECT, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateSaveSharedPreferences(SharedPreferences sharedPreferences,
                                                                        String destinationPath) {
        Method method = Method.SAVE_SHARED_PREFERENCES;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.SHARED_PREFERENCES, sharedPreferences);
        parameters.put(Parameter.DESTINATION_PATH, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadSharedPreferences(Path originPath) {
        Method method = Method.LOAD_SHARED_PREFERENCES;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.ORIGIN_PATH_OBJECT, originPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadSharedPreferences(String originPath) {
        Method method = Method.LOAD_SHARED_PREFERENCES;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.ORIGIN_PATH, originPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadSharedPreferences(Path originPath,
                                                                        SharedPreferences sharedPreferences) {
        Method method = Method.LOAD_SHARED_PREFERENCES_2;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.ORIGIN_PATH_OBJECT, originPath);
        parameters.put(Parameter.SHARED_PREFERENCES, sharedPreferences);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateLoadSharedPreferences(String originPath,
                                                                        SharedPreferences sharedPreferences) {
        Method method = Method.LOAD_SHARED_PREFERENCES_2;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.ORIGIN_PATH, originPath);
        parameters.put(Parameter.SHARED_PREFERENCES, sharedPreferences);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateExists(Path pathToFile) {
        Method method = Method.EXISTS;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH_OBJECT_TO_FILE, pathToFile);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateExists(String pathToFile) {
        Method method = Method.EXISTS;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH_TO_FILE, pathToFile);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateExists(File file) {
        Method method = Method.EXISTS_FILE;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.FILE, file);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateIsValidForSaving(String path) {
        Method method = Method.IS_VALID_FOR_SAVING;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH, path);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateDuplicateFile(Path originPath,
                                                                Path destinationPath) {
        Method method = Method.DUPLICATE_FILE;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.ORIGIN_PATH_OBJECT, originPath);
        parameters.put(Parameter.DESTINATION_PATH_OBJECT, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateDuplicateFile(String originPath,
                                                                String destinationPath) {
        Method method = Method.DUPLICATE_FILE;
        HashMap<Parameter, Object> parameters = new HashMap<>(2);
        parameters.put(Parameter.ORIGIN_PATH, originPath);
        parameters.put(Parameter.DESTINATION_PATH, destinationPath);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateDeleteFile(Path pathToFile) {
        Method method = Method.DELETE_FILE;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH_OBJECT_TO_FILE, pathToFile);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateDeleteFile(String pathToFile) {
        Method method = Method.DELETE_FILE;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH_TO_FILE, pathToFile);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateDeleteFile(File file) {
        Method method = Method.DELETE_FILE;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.FILE, file);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateClearFolder(Path pathToFolder) {
        Method method = Method.CLEAR_FOLDER;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH_OBJECT_TO_FOLDER, pathToFolder);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateClearFolder(String pathToFolder) {
        Method method = Method.CLEAR_FOLDER;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH_TO_FOLDER, pathToFolder);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateIsFolderEmpty(File folder) {
        Method method = Method.IS_FOLDER_EMPTY;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.FOLDER, folder);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateCreateFolder(String pathToFolder) {
        Method method = Method.CREATE_FOLDER;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH_TO_FOLDER, pathToFolder);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateCreatePath(Path path) {
        Method method = Method.CREATE_PATH;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH_OBJECT, path);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateGetLongestPath(String path) {
        Method method = Method.GET_LONGEST_PATH;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH, path);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateIsDirectory(String path) {
        Method method = Method.IS_DIRECTORY;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.PATH, path);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateIsDirectory(File file) {
        Method method = Method.IS_DIRECTORY;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.FILE, file);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateGetFilesInDirectory(File folder) {
        Method method = Method.GET_FILES_IN_DIRECTORY;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.FOLDER, folder);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }

    public static ValidationInfoInterface validateGetFileTree(File folder) {
        Method method = Method.GET_FILE_TREE;
        HashMap<Parameter, Object> parameters = new HashMap<>(1);
        parameters.put(Parameter.FOLDER, folder);
        Validator validator = new Validator(parameters, method);
        return validator.assertAreParametersValid();
    }
}
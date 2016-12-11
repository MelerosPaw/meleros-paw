package melerospaw.memoryutil.validation;

import android.support.annotation.NonNull;

import java.io.File;

import melerospaw.memoryutil.StringUtil;
import melerospaw.memoryutil.validation.ValidationEnums.Invalidity;

/**
 * Created by Juan José Melero on 23/09/2016.
 */
public class ValidationUtils {

    public static ValidationInfoInterface isPathValidForSaving(
            @NonNull File file, boolean isFolder, ValidationInfoInterface info) {

        Invalidity invalidity;
        boolean isValid;

        // If the file exists
        if (file.exists()) {
            // It if should be a folder, verifies it's a folder
            if (isFolder){
                if (file.isDirectory()) {
                    invalidity = Invalidity.NONE;
                    isValid = true;
                } else {
                    invalidity = Invalidity.NOT_A_DIRECTORY;
                    isValid = false;
                }
            // If it should be a file, verifies it's a file
            } else {
                if (!file.isDirectory()) {
                    invalidity = Invalidity.NONE;
                    isValid = true;
                } else {
                    invalidity = Invalidity.IS_A_DIRECTORY;
                    isValid = false;
                }
            }
        // If the file doesn't exists, verifies that the folder that's going to contain it exists
        } else {
            File containerFolder = new File(StringUtil.getContainerFolder(file.getPath()));
            if (containerFolder.exists()){
                invalidity = Invalidity.NONE;
                isValid = true;
            } else {
                invalidity = Invalidity.CONTAINER_FOLDER_DOESNT_EXIST;
                isValid = false;
            }
        }

        info.setInvalidityType(invalidity);
        info.setIsValid(isValid);
        return info;
    }

    public static String createErrorMessage(ValidationInfoInterface validationInfo){
        return "You're calling " + validationInfo.getMethod().description
                + ", but " + validationInfo.getInvalidParameter().description
                +  " that you're passing " + validationInfo.getInvalidityType().invalidity
                + ". Parameters for the call:\n" + validationInfo.getParameterListToString();
    }
}
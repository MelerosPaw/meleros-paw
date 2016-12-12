package melerospaw.memoryutil;

import java.io.File;

/**
 * Created by Juan José Melero on 27/08/2016.
 */
public class NonExistingFileException extends RuntimeException {

    private String nonExistingPath;
    private String whatYouWantedToDo;

    public NonExistingFileException(String whatYouWantedToDo, String nonExistingPath){

        this.nonExistingPath = nonExistingPath;
        this.whatYouWantedToDo = whatYouWantedToDo;
    }

    @Override
    public String getMessage() {
        String existingFilesReport = getExistingFilesReport();
        return "You were trying to " + whatYouWantedToDo + " but file " + nonExistingPath
                + " doesn't exist. " + existingFilesReport;
    }

    public String getExistingFilesReport(){

        String existingFilesReport = "";

        String longestExistingPath = MemoryUtil.getLongestValidPath(nonExistingPath);
        if (!longestExistingPath.isEmpty()) {
            existingFilesReport += "Closest existing file is " + longestExistingPath;
            if (new File(longestExistingPath).list().length > 0) {
                String existingFiles = MemoryUtil.getFilesInDirectory(new File(longestExistingPath));
                if (!existingFiles.isEmpty()){
                    existingFilesReport += ", and contains the following files: " + existingFiles;
                } else {
                    existingFilesReport += "and is empty.";
                }
            } else {
                existingFilesReport += ".";
            }
        }

        return existingFilesReport;
    }
}

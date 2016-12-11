package melerospaw.memoryutil;

import android.support.annotation.NonNull;

/**
 * Created by Juan José Melero on 26/08/2016.
 */
public class StringUtil {

    static final String EMPTY = "";
    private static final String formatPlaceholderRegEx = "\\%[0-9]*\\$(s|d)";

    /**
     * Formats {@code text} with {@code formatParameter}.
     *
     * @param text             {@code String} to be formatted.
     * @param formatParameters Parameters used to call {@link String#format(String, Object...)}.
     * @return The formatted {@code String} text. If no formatParameters are provided, format
     * placeholders will be removed.
     */
    public static String format(String text, String... formatParameters) {
        if (formatParameters.length > 0) {
            text = String.format(text, formatParameters);
        } else {
            text = text.replaceAll(formatPlaceholderRegEx, "");
        }

        return text.replaceAll("  ", " ").replaceAll(" \\.", ".");
    }

    /**
     * Returns an empty {@code String} if the String is null or the String itself if it's not.
     *
     * @param text The {@code String} to be checked.
     * @return An empty {@code String} if the String is null or the String itself if it's not.
     */
    public static String getStringOrEmpty(String text) {
        return text == null ? EMPTY : text;
    }


    /**
     * Returns a {@code String} concatenating {@code tabAmount} tabulations.
     *
     * @param tabAmount The amount of tabulations to be added to the resulting {@code String}.
     * @return A {@code String} containing {@code tabAmount} tabulations.
     */
    public static String getTabs(int tabAmount) {
        String tabs = "";

        for (int i = 0; i < tabAmount; i++) {
            tabs += "\t";
        }

        return tabs;
    }

    /**Returns the path to the folder containing the file in {@code paht}.
     *
     * @param path  The path to the file whose container folder we want to get.
     */
    public static String getContainerFolder(@NonNull String path){
        return path.substring(0, path.lastIndexOf("/"));
    }
}

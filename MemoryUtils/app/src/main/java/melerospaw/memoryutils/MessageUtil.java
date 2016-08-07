package melerospaw.memoryutils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Juan José Melero on 19/07/2016.
 */
public class MessageUtil {

    public static void alert(Context context, String text, String buttonText){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Atención");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(text);
        builder.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}

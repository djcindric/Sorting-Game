package com.example.sortinggame;

import android.app.Dialog;
import android.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class DeleteDialogBox extends DialogFragment {
	Context mContext;
	String level;
	CustomizerControl cControl;
	
    public DeleteDialogBox(String level, Context context) {
		this.level = level;
		cControl = new CustomizerControl(context);
		mContext = context;
	}

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_level)
               .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Delete the level
                	   cControl.deleteLevel(level);
                	   Intent intent = new Intent(mContext, CustomizerActivity.class);
                	   startActivity(intent);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the delete
                   }
               });
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
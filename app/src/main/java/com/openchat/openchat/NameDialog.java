package com.openchat.openchat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Dan on 14/03/2018.
 */

public class NameDialog extends AppCompatDialogFragment {

    private EditText editTextName;

    private NameDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialoglayout, null);

        builder.setView(view).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String username = editTextName.getText().toString();

                listener.saveInfo(username);
            }
        });

        editTextName = view.findViewById(R.id.editUsername);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (NameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Missing NameDialogListener");
        }
    }

    public interface NameDialogListener {

        void saveInfo(String username);
    }
}

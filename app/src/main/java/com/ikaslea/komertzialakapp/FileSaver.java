package com.ikaslea.komertzialakapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class FileSaver {

    private final FileSaveCallback callback;
    private final Context context;
    private ActivityResultLauncher<Intent> launcher;

    public interface FileSaveCallback {
        void onFileSaved(Uri uri);
        void onSaveCancelled();
        void onError(String message);
    }

    public FileSaver(Context context, FileSaveCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void registerLauncher(Fragment fragment) {
        this.launcher = fragment.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleResult
        );
    }

    public void saveFile(String defaultFileName) {
        if (launcher == null) {
            throw new IllegalStateException("Debes llamar a registerLauncher() antes de saveFile().");
        }
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/xml"); //  <--  Tipo MIME para XML
        intent.putExtra(Intent.EXTRA_TITLE, defaultFileName); //  <--  Nombre de archivo sugerido
        launcher.launch(intent);
    }

    private void handleResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                callback.onFileSaved(uri); // Notificar que se seleccionó una URI
            } else {
                callback.onError("Datos nulos.");
            }
        } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
            callback.onSaveCancelled();
        } else {
            callback.onError("Error al guardar el archivo.");
        }
    }

}
package com.example.alfasdk.Util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.example.alfasdk.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class Util {


    public static boolean logOut = false;
    public static String logOutString = "";

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static void hideKeyboard(Activity context) {

        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void performNavigation(Activity activity, int fragmentId) {
        Navigation.findNavController(activity, R.id.nav_host_account_opening).navigate(fragmentId);
    }

    public static void setInputError(TextInputEditText textInputEditText) {
        textInputEditText.setError("can not be empty.");
        textInputEditText.requestFocus();
    }

    public static void setInputEditable(TextInputEditText textInputEditText, Boolean isEditable) {
        textInputEditText.setFocusable(isEditable);
        textInputEditText.setCursorVisible(isEditable);
        textInputEditText.setFocusableInTouchMode(isEditable);
    }

    public static void setInputEditable(AutoCompleteTextView autoCompleteTextView, Boolean isEditable) {
        autoCompleteTextView.setFocusable(isEditable);
        autoCompleteTextView.setCursorVisible(isEditable);
        autoCompleteTextView.setFocusableInTouchMode(isEditable);
    }

    public static String convertImageToBase64(Uri uri, FragmentActivity fragmentActivity) {
        final InputStream imageStream;
        try {
            imageStream = fragmentActivity.getContentResolver().openInputStream(uri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            return encodeImage(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT)+"";
    }

    public static FileMetaData getFileMetaData(Context context, Uri uri) {
        FileMetaData fileMetaData = new FileMetaData();

        if ("file".equalsIgnoreCase(uri.getScheme()))
        {
            File file = new File(uri.getPath());
            fileMetaData.displayName = file.getName();
            fileMetaData.size = file.length();
            fileMetaData.path = file.getPath();

            return fileMetaData;
        }
        else
        {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            fileMetaData.mimeType = contentResolver.getType(uri);

            try
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                    fileMetaData.displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    if (!cursor.isNull(sizeIndex))
                        fileMetaData.size = cursor.getLong(sizeIndex);
                    else
                        fileMetaData.size = -1;

                    try
                    {
                        fileMetaData.path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                    }
                    catch (Exception e)
                    {
                        // DO NOTHING, _data does not exist
                    }

                    return fileMetaData;
                }
            }
            catch (Exception e)
            {
                Log.e("FileMetaData", "Exception: "+e.getMessage());
            }
            finally
            {
                if (cursor != null)
                    cursor.close();
            }

            return null;
        }
    }



}

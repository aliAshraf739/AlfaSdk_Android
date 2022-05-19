package com.example.alfasdk.Util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.alfasdk.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Util {

    private static final String TAG = "Util";

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

    public static String convertUriToBase64(Uri uri, FragmentActivity fragmentActivity) {
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

    public static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT)+"";
    }

    public static Bitmap decodeImage(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
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


    public static String getFormattedDate(int day, int month, int year) {
        int monthOfYear = month+1;
        String mt,dy;
        if(monthOfYear<10){
            mt="0"+monthOfYear;
        }
        else{
            mt=String.valueOf(monthOfYear);
        }

        if(day<10){
            dy = "0"+day;
        }else{
            dy = String.valueOf(day);
        }
        return dy+"/"+mt+"/"+year;
    }

    public static boolean isExpiryDateBeforeToday(String expiryDate) {
        Calendar c = Calendar.getInstance();

        // set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        // and get that as a Date
        Date today = c.getTime();


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dateSpecified = null;
        try {
            dateSpecified = dateFormat.parse(expiryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(dateSpecified!=null){
            return dateSpecified.before(today);
        }else{
            return false;
        }
    }

    public static boolean checkAlphabetValidity(TextInputEditText textInputEditText){
        if(textInputEditText.getText().toString().matches("[a-zA-Z]+")){
            return true;
        }else{
            return false;
        }
    }

    public static byte[] recoverImageFromUrl(String urlText) throws Exception {
        URL url = new URL(urlText);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }
        return output.toByteArray();
    }

}

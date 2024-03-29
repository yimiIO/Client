/**    
 * file name：TransforUtils.java    
 * 
 * @author zpy zpy@98ki.com   
 * @date：2014-12-3 
 * Copyright shape100.com Corporation 2014         
 *    
 */
package com._98ki.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

/**    
 *     
 * project：shape100    
 * class：TransforUtils    
 * desc：    
 * author：zpy zpy@98ki.com    
 * create date：2014-12-3 下午2:48:45    
 * modify author: zpy    
 * update date：2014-12-3 下午2:48:45    
 * update remark：    
 * @version     
 *     
 */
public class TransforUtils {
	/**
	 * Gets the corresponding path to a file from the given content:// URI
	 * @param selectedVideoUri The content:// URI to find the file path from
	 * @param contentResolver The content resolver to use to perform the query.
	 * @return the file path as a string
	 */
	public static String getFilePathFromContentUri(Uri selectedVideoUri, ContentResolver contentResolver) {
		String filePath;
		String[] filePathColumn = { MediaColumns.DATA };

		Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
		// 也可用下面的方法拿到cursor
		// Cursor cursor = this.context.managedQuery(selectedVideoUri,
		// filePathColumn, null, null, null);

		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		filePath = cursor.getString(columnIndex);
		cursor.close();
		return filePath;
	}
	
	/**
	 * Gets the content:// URI  from the given corresponding path to a file
	 * @param context
	 * @param imageFile
	 * @return content Uri
	 */
	public static Uri getImageContentUri(Context context, java.io.File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}

//package com.r00ta.telematics.android.persistence;
//
//import android.database.sqlite.SQLiteOpenHelper;
//import android.database.Cursor;
//import android.content.Context;
//import android.content.ContentValues;
//import android.util.Log;
//
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.ArrayList;
//
//import android.database.sqlite.SQLiteDatabase;
///**
// * Created by r00ta on 21/06/2017.
// */
//public class Storage extends SQLiteOpenHelper {
//    private static final String TAG = "LoginActivity";
//    // Database Info
//    private static final String DATABASE_NAME = "perceptoDB";
//    private static final int DATABASE_VERSION = 1;
//
//    // Table Names
//    private static final String TABLE_STOCK = "stockIndex";
//    private static final String TABLE_FAVOURITES = "favourites";
//
//    // Stock Index Table Columns
//    private static final String KEY_STOCK_ID = "id";
//    private static final String KEY_STOCK_NAME = "stockName";
//    private static final String KEY_STOCK_COST = "stockCost";
//
//    // User Table Columns
//    private static final String KEY_FAV_ID = "id";
//    private static final String KEY_FAV_NAME = "stockName";
//    private static final String KEY_FAV_COST = "stockCost";
//
//    private static Storage sInstance;
//
//    // Called when the database connection is being configured.
//    // Configure database settings for things like foreign key support, write-ahead logging, etc.
//    @Override
//    public void onConfigure(SQLiteDatabase db) {
//        super.onConfigure(db);
//        // db.setForeignKeyConstraintsEnabled(true);
//    }
//
//    // Called when the database is created for the FIRST time.
//    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_STOCK +
//                "(" +
//                KEY_STOCK_ID + " INTEGER PRIMARY KEY," + // Define a primary key
//                KEY_STOCK_NAME + " TEXT UNIQUE,"  +
//                KEY_STOCK_COST + " INTEGER" +
//                ")";
//
//        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_FAVOURITES +
//                "(" +
//                KEY_FAV_ID + " INTEGER PRIMARY KEY," +
//                KEY_FAV_NAME + " TEXT UNIQUE," +
//                KEY_FAV_COST + " INTEGER" +
//                ")";
//
//        db.execSQL(CREATE_POSTS_TABLE);
//        db.execSQL(CREATE_USERS_TABLE);
//    }
//
//    // Called when the database needs to be upgraded.
//    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
//    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion != newVersion) {
//            // Simplest implementation is to drop all old tables and recreate them
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK);
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITES);
//            onCreate(db);
//        }
//    }
//
//    public static synchronized PostsDatabaseHelper getInstance(Context context) {
//        // Use the application context, which will ensure that you
//        // don't accidentally leak an Activity's context.
//        // See this article for more information: http://bit.ly/6LRzfx
//        if (sInstance == null) {
//            sInstance = new PostsDatabaseHelper(context.getApplicationContext());
//        }
//        return sInstance;
//    }
//
//    /**
//     * Constructor should be private to prevent direct instantiation.
//     * Make a call to the static method "getInstance()" instead.
//     */
//    private PostsDatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    // Insert a Favourite into the database
//    public void addFav(StockIndex index) {
//        // Create and/or open the database for writing
//        SQLiteDatabase db = getWritableDatabase();
//
//        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
//        // consistency of the database.
//        db.beginTransaction();
//        try {
//            // The user might already exist in the database (i.e. the same user created multiple posts).
//
//            ContentValues values = new ContentValues();
//            values.put(KEY_FAV_NAME, index.nameStock);
//            values.put(KEY_FAV_COST, index.cost);
//
//            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
//            db.insertOrThrow(TABLE_FAVOURITES, null, values);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to add favourite to database");
//        } finally {
//            db.endTransaction();
//        }
//    }
//
//    public void addStockList(StockIndex index) {
//        // Create and/or open the database for writing
//        SQLiteDatabase db = getWritableDatabase();
//
//        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
//        // consistency of the database.
//        db.beginTransaction();
//        try {
//            // The user might already exist in the database (i.e. the same user created multiple posts).
//
//            ContentValues values = new ContentValues();
//            values.put(KEY_FAV_NAME, index.nameStock);
//            values.put(KEY_FAV_COST, index.cost);
//
//            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
//            db.insertOrThrow(TABLE_STOCK, null, values);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to add stock to database");
//        } finally {
//            db.endTransaction();
//        }
//    }
//
//
//    public void deleteAllFavAndStock() {
//        SQLiteDatabase db = getWritableDatabase();
//        db.beginTransaction();
//        try {
//            db.delete(TABLE_FAVOURITES, null, null);
//            db.delete(TABLE_STOCK, null, null);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to delete all favourites and stockList");
//        } finally {
//            db.endTransaction();
//        }
//    }
//
//    public void deleteAllFavourites() {
//        SQLiteDatabase db = getWritableDatabase();
//        db.beginTransaction();
//        try {
//            db.delete(TABLE_FAVOURITES, null, null);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to delete all favourites");
//        } finally {
//            db.endTransaction();
//        }
//    }
//
//
//    public void deleteAllStockList() {
//        SQLiteDatabase db = getWritableDatabase();
//        db.beginTransaction();
//        try {
//            db.delete(TABLE_STOCK, null, null);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to delete all stock data");
//        } finally {
//            db.endTransaction();
//        }
//    }
//
//    public List<StockIndex> getAllFavourites() {
//        List<StockIndex> favourites = new ArrayList<>();
//
//        String FAV_SELECT_QUERY =
//                String.format("SELECT * FROM %s",
//                        TABLE_FAVOURITES
//                );
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(FAV_SELECT_QUERY, null);
//        try {
//            if (cursor.moveToFirst()) {
//                do {
//                    StockIndex si = new StockIndex();
//                    si.nameStock = cursor.getString(cursor.getColumnIndex(KEY_FAV_NAME));
//                    si.cost = cursor.getInt(cursor.getColumnIndex(KEY_FAV_COST));
//
//                    favourites.add(si);
//                } while(cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to get favourites from database");
//        } finally {
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//        if (favourites.size() > 0) {
//            Collections.sort(favourites, new Comparator<StockIndex>() {
//                @Override
//                public int compare(final StockIndex object1, final StockIndex object2) {
//                    return object1.getNameStock().compareTo(object2.getNameStock());
//                }
//            });
//        }
//        return favourites;
//    }
//
//    public List<String> getAllFavouritesName() {
//        List<String> favourites = new ArrayList<>();
//
//        String FAV_SELECT_QUERY =
//                String.format("SELECT %s FROM %s", KEY_FAV_NAME,
//                        TABLE_FAVOURITES
//                );
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(FAV_SELECT_QUERY, null);
//        try {
//            if (cursor.moveToFirst()) {
//                do {
//                    String si;
//                    si = cursor.getString(cursor.getColumnIndex(KEY_FAV_NAME));
//                    favourites.add(si);
//                } while(cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to get favourites from database");
//        } finally {
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//        if (favourites.size() > 0) {
//            Collections.sort(favourites, new Comparator<String>() {
//                @Override
//                public int compare(final String object1, final String object2) {
//                    return object1.compareTo(object2);
//                }
//            });
//        }
//        return favourites;
//    }
//    public List<StockIndex> getAllStockWithoutPref() {
//        List<StockIndex> favourites = new ArrayList<>();
//
//        String FAV_SELECT_QUERY =
//                String.format("SELECT DISTINCT * FROM %s WHERE %s Not IN (SELECT DISTINCT %s FROM %s)",
//                        TABLE_STOCK, KEY_STOCK_NAME, KEY_FAV_NAME, TABLE_FAVOURITES
//                );
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(FAV_SELECT_QUERY, null);
//        try {
//            if (cursor.moveToFirst()) {
//                do {
//                    StockIndex si = new StockIndex();
//                    si.nameStock = cursor.getString(cursor.getColumnIndex(KEY_FAV_NAME));
//                    si.cost = cursor.getInt(cursor.getColumnIndex(KEY_FAV_COST));
//
//                    favourites.add(si);
//                } while(cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to get favourites from database");
//        } finally {
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//        return favourites;
//    }
//
//
//    public List<StockIndex> getAllStockList() {
//        List<StockIndex> allStock = new ArrayList<>();
//
//        String ALLSTOCK_SELECT_QUERY =
//                String.format("SELECT * FROM %s",
//                        TABLE_STOCK
//                );
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(ALLSTOCK_SELECT_QUERY, null);
//        try {
//            if (cursor.moveToFirst()) {
//                do {
//                    StockIndex si = new StockIndex();
//                    si.nameStock = cursor.getString(cursor.getColumnIndex(KEY_FAV_NAME));
//                    si.cost = cursor.getInt(cursor.getColumnIndex(KEY_FAV_COST));
//
//                    allStock.add(si);
//                } while(cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to get favourites from database");
//        } finally {
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//        return allStock;
//    }
//
//    public boolean rmFavourite(StockIndex si){
//        SQLiteDatabase db = getWritableDatabase();
//        db.beginTransaction();
//        try {
//            db.delete(TABLE_FAVOURITES, KEY_FAV_NAME + "=?", new String[] { si.nameStock });
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to delete all stock data");
//            return false;
//        } finally {
//            db.endTransaction();
//        }
//        return true;
//
//    }
//
//}

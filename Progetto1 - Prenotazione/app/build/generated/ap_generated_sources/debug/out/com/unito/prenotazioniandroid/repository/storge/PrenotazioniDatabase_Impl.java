package com.unito.prenotazioniandroid.repository.storge;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PrenotazioniDatabase_Impl extends PrenotazioniDatabase {
  private volatile PrenotazioneDao _prenotazioneDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `prenotazioni` (`id` INTEGER, `nome` TEXT, `congome` TEXT, `titolo` TEXT, `username` TEXT, `ora` TEXT, `giorno` TEXT, `stato` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '75b03fb9490b99191b6f5d49474ae6ce')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `prenotazioni`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPrenotazioni = new HashMap<String, TableInfo.Column>(8);
        _columnsPrenotazioni.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrenotazioni.put("nome", new TableInfo.Column("nome", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrenotazioni.put("congome", new TableInfo.Column("congome", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrenotazioni.put("titolo", new TableInfo.Column("titolo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrenotazioni.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrenotazioni.put("ora", new TableInfo.Column("ora", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrenotazioni.put("giorno", new TableInfo.Column("giorno", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPrenotazioni.put("stato", new TableInfo.Column("stato", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPrenotazioni = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPrenotazioni = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPrenotazioni = new TableInfo("prenotazioni", _columnsPrenotazioni, _foreignKeysPrenotazioni, _indicesPrenotazioni);
        final TableInfo _existingPrenotazioni = TableInfo.read(_db, "prenotazioni");
        if (! _infoPrenotazioni.equals(_existingPrenotazioni)) {
          return new RoomOpenHelper.ValidationResult(false, "prenotazioni(com.unito.prenotazioniandroid.repository.storge.model.Prenotazione).\n"
                  + " Expected:\n" + _infoPrenotazioni + "\n"
                  + " Found:\n" + _existingPrenotazioni);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "75b03fb9490b99191b6f5d49474ae6ce", "2ff114d474181079ebfc3710a4b9a063");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "prenotazioni");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `prenotazioni`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public PrenotazioneDao prenotazioneDao() {
    if (_prenotazioneDao != null) {
      return _prenotazioneDao;
    } else {
      synchronized(this) {
        if(_prenotazioneDao == null) {
          _prenotazioneDao = new PrenotazioneDao_Impl(this);
        }
        return _prenotazioneDao;
      }
    }
  }
}

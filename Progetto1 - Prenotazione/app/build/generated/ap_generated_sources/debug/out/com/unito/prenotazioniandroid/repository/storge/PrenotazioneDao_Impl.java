package com.unito.prenotazioniandroid.repository.storge;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.unito.prenotazioniandroid.repository.storge.model.Prenotazione;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PrenotazioneDao_Impl implements PrenotazioneDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Prenotazione> __insertionAdapterOfPrenotazione;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllPrenotazioni;

  public PrenotazioneDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPrenotazione = new EntityInsertionAdapter<Prenotazione>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `prenotazioni` (`id`,`nome`,`congome`,`titolo`,`username`,`ora`,`giorno`,`stato`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Prenotazione value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getNome() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNome());
        }
        if (value.getCognome() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCognome());
        }
        if (value.getTitolo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTitolo());
        }
        if (value.getUsername() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getUsername());
        }
        if (value.getOra() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getOra());
        }
        if (value.getGiorno() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getGiorno());
        }
        if (value.getStato() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getStato());
        }
      }
    };
    this.__preparedStmtOfDeleteAllPrenotazioni = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM prenotazioni";
        return _query;
      }
    };
  }

  @Override
  public void insertPrenotazione(final Prenotazione prenotazione) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPrenotazione.insert(prenotazione);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllPrenotazioni() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllPrenotazioni.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllPrenotazioni.release(_stmt);
    }
  }

  @Override
  public List<Prenotazione> getPrenotazioni() {
    final String _sql = "SELECT * FROM prenotazioni";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNome = CursorUtil.getColumnIndexOrThrow(_cursor, "nome");
      final int _cursorIndexOfCognome = CursorUtil.getColumnIndexOrThrow(_cursor, "congome");
      final int _cursorIndexOfTitolo = CursorUtil.getColumnIndexOrThrow(_cursor, "titolo");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfOra = CursorUtil.getColumnIndexOrThrow(_cursor, "ora");
      final int _cursorIndexOfGiorno = CursorUtil.getColumnIndexOrThrow(_cursor, "giorno");
      final int _cursorIndexOfStato = CursorUtil.getColumnIndexOrThrow(_cursor, "stato");
      final List<Prenotazione> _result = new ArrayList<Prenotazione>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Prenotazione _item;
        final Integer _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getInt(_cursorIndexOfId);
        }
        final String _tmpNome;
        _tmpNome = _cursor.getString(_cursorIndexOfNome);
        final String _tmpCognome;
        _tmpCognome = _cursor.getString(_cursorIndexOfCognome);
        final String _tmpTitolo;
        _tmpTitolo = _cursor.getString(_cursorIndexOfTitolo);
        final String _tmpUsername;
        _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        final String _tmpOra;
        _tmpOra = _cursor.getString(_cursorIndexOfOra);
        final String _tmpGiorno;
        _tmpGiorno = _cursor.getString(_cursorIndexOfGiorno);
        final String _tmpStato;
        _tmpStato = _cursor.getString(_cursorIndexOfStato);
        _item = new Prenotazione(_tmpId,_tmpNome,_tmpCognome,_tmpTitolo,_tmpUsername,_tmpOra,_tmpGiorno,_tmpStato);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}

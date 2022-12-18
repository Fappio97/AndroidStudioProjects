package com.unito.prenotazioniandroid.repository.storge.model;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;


/**
 * Immutable model class for a Prenotazione and entity in the Room database.
 */


@Entity(tableName = "prenotazioni")
public class Prenotazione extends BaseObservable {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName(value = "id")
    private Integer id;
    @ColumnInfo(name = "nome")
    @SerializedName(value = "nome")
    private String nome;
    @ColumnInfo(name = "congome")
    @SerializedName(value = "cognome")
    private String cognome;
    @ColumnInfo(name = "titolo")
    @SerializedName(value = "titolo")
    private String titolo;
    @ColumnInfo(name = "username")
    @SerializedName(value = "username")
    private String username;
    @ColumnInfo(name = "ora")
    @SerializedName(value = "ora")
    private String ora;
    @ColumnInfo(name = "giorno")
    @SerializedName(value = "giorno")
    private String giorno;
    @ColumnInfo(name = "stato")
    @SerializedName(value = "stato")
    private String stato;

    public Prenotazione(Integer id, String nome, String cognome, String titolo, String username, String ora, String giorno, String stato) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.titolo = titolo;
        this.username = username;
        this.ora = ora;
        this.giorno = giorno;
        this.stato = stato;
    }

    // use for ordering the items in view
    public static DiffUtil.ItemCallback<Prenotazione> DIFF_CALLBACK = new DiffUtil.ItemCallback<Prenotazione>() {
        @Override
        public boolean areItemsTheSame(@NonNull Prenotazione oldItem, @NonNull Prenotazione newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Prenotazione oldItem, @NonNull Prenotazione newItem) {
            return oldItem.equalsWithStatus(newItem);
        }
    };

    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer mId) {
        this.id = mId;
    }

    @Bindable
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Bindable
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String mCognome) {
        this.cognome = mCognome;
    }

    @Bindable
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String mTitolo) {
        this.titolo = mTitolo;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String mUsername) {
        this.username = mUsername;
    }

    @Bindable
    public String getOra() {
        return ora;
    }

    public void setOra(String mOra) {
        this.ora = mOra;
    }

    @Bindable
    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String mGiorno) {
        this.giorno = mGiorno;
    }

    @Bindable
    public String getStato() {
        return stato;
    }

    public void setStato(String mStato) {
        this.stato = mStato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prenotazione that = (Prenotazione) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(titolo, that.titolo) &&
                Objects.equals(ora, that.ora) &&
                Objects.equals(giorno, that.giorno);
    }

    public boolean equalsWithStatus(Object o){
        return this.equals(o) && ((Prenotazione) o).getStato().equalsIgnoreCase(this.getStato());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titolo, ora, giorno);
    }
}

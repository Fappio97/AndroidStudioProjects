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
 * Immutable model class for a Libera and entity in the Room database.
 */


@Entity(tableName = "libere")
public class Libera extends BaseObservable {

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
    @ColumnInfo(name = "ora")
    @SerializedName(value = "ora")
    private String ora;
    @ColumnInfo(name = "giorno")
    @SerializedName(value = "giorno")
    private String giorno;

    public Libera(Integer id, String nome, String cognome, String titolo, String ora, String giorno) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.titolo = titolo;
        this.ora = ora;
        this.giorno = giorno;
    }

    // use for ordering the items in view
    public static DiffUtil.ItemCallback<Libera> DIFF_CALLBACK = new DiffUtil.ItemCallback<Libera>() {
        @Override
        public boolean areItemsTheSame(@NonNull Libera oldItem, @NonNull Libera newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Libera oldItem, @NonNull Libera newItem) {
            return oldItem.getId().equals(newItem.getId());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libera libera = (Libera) o;
        return Objects.equals(id, libera.id) &&
                Objects.equals(nome, libera.nome) &&
                Objects.equals(cognome, libera.cognome) &&
                Objects.equals(titolo, libera.titolo) &&
                Objects.equals(ora, libera.ora) &&
                Objects.equals(giorno, libera.giorno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cognome, titolo, ora, giorno);
    }
}

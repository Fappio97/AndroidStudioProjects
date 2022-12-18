package com.unito.prenotazioniandroid.databinding;
import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class PrenotazioneFragmentDetailsBindingImpl extends PrenotazioneFragmentDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.black_mask, 8);
        sViewsWithIds.put(R.id.main_container, 9);
        sViewsWithIds.put(R.id.overview_container, 10);
        sViewsWithIds.put(R.id.buttonParent, 11);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public PrenotazioneFragmentDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private PrenotazioneFragmentDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.view.View) bindings[8]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.Button) bindings[6]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[4]
            , (android.widget.Button) bindings[7]
            , (android.widget.TextView) bindings[3]
            , (android.widget.ScrollView) bindings[9]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[2]
            );
        this.conferma.setTag(null);
        this.cover.setTag(null);
        this.dateAndHour.setTag(null);
        this.disdici.setTag(null);
        this.docente.setTag(null);
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.stato.setTag(null);
        this.title.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x100L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.prenotazione == variableId) {
            setPrenotazione((com.unito.prenotazioniandroid.repository.storge.model.Prenotazione) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPrenotazione(@Nullable com.unito.prenotazioniandroid.repository.storge.model.Prenotazione Prenotazione) {
        updateRegistration(0, Prenotazione);
        this.mPrenotazione = Prenotazione;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.prenotazione);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangePrenotazione((com.unito.prenotazioniandroid.repository.storge.model.Prenotazione) object, fieldId);
        }
        return false;
    }
    private boolean onChangePrenotazione(com.unito.prenotazioniandroid.repository.storge.model.Prenotazione Prenotazione, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.id) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        else if (fieldId == BR.titolo) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        else if (fieldId == BR.nome) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        else if (fieldId == BR.cognome) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        else if (fieldId == BR.giorno) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        else if (fieldId == BR.ora) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        else if (fieldId == BR.stato) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String prenotazioneGiornoCharPrenotazioneOraCharChar0Char0Char = null;
        java.lang.String prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1 = null;
        java.lang.Integer prenotazioneId = null;
        java.lang.String prenotazioneStato = null;
        int integerParseIntPrenotazioneOra = 0;
        java.lang.String prenotazioneTitolo = null;
        java.lang.String prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1CharChar0 = null;
        java.lang.String prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1CharChar0Char0 = null;
        java.lang.String prenotazioneGiornoCharPrenotazioneOraChar = null;
        java.lang.String prenotazioneCognome = null;
        java.lang.String prenotazioneGiornoCharPrenotazioneOra = null;
        java.lang.String prenotazioneGiorno = null;
        java.lang.String prenotazioneGiornoCharPrenotazioneOraCharChar0Char0 = null;
        int integerParseIntPrenotazioneOraInt1 = 0;
        java.lang.String prenotazioneNome = null;
        java.lang.String prenotazioneNomeCharPrenotazioneCognome = null;
        java.lang.String prenotazioneGiornoChar = null;
        java.lang.String prenotazioneNomeChar = null;
        java.lang.String prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1Char = null;
        com.unito.prenotazioniandroid.repository.storge.model.Prenotazione prenotazione = mPrenotazione;
        java.lang.String prenotazioneGiornoCharPrenotazioneOraCharChar0 = null;
        java.lang.String prenotazioneOra = null;

        if ((dirtyFlags & 0x1ffL) != 0) {


            if ((dirtyFlags & 0x103L) != 0) {

                    if (prenotazione != null) {
                        // read prenotazione.id
                        prenotazioneId = prenotazione.getId();
                    }
            }
            if ((dirtyFlags & 0x181L) != 0) {

                    if (prenotazione != null) {
                        // read prenotazione.stato
                        prenotazioneStato = prenotazione.getStato();
                    }
            }
            if ((dirtyFlags & 0x105L) != 0) {

                    if (prenotazione != null) {
                        // read prenotazione.titolo
                        prenotazioneTitolo = prenotazione.getTitolo();
                    }
            }
            if ((dirtyFlags & 0x119L) != 0) {

                    if (prenotazione != null) {
                        // read prenotazione.cognome
                        prenotazioneCognome = prenotazione.getCognome();
                        // read prenotazione.nome
                        prenotazioneNome = prenotazione.getNome();
                    }


                    // read (prenotazione.nome) + (' ')
                    prenotazioneNomeChar = (prenotazioneNome) + (' ');


                    // read ((prenotazione.nome) + (' ')) + (prenotazione.cognome)
                    prenotazioneNomeCharPrenotazioneCognome = (prenotazioneNomeChar) + (prenotazioneCognome);
            }
            if ((dirtyFlags & 0x161L) != 0) {

                    if (prenotazione != null) {
                        // read prenotazione.giorno
                        prenotazioneGiorno = prenotazione.getGiorno();
                        // read prenotazione.ora
                        prenotazioneOra = prenotazione.getOra();
                    }


                    // read (prenotazione.giorno) + (' ')
                    prenotazioneGiornoChar = (prenotazioneGiorno) + (' ');
                    // read Integer.parseInt(prenotazione.ora)
                    integerParseIntPrenotazioneOra = java.lang.Integer.parseInt(prenotazioneOra);


                    // read ((prenotazione.giorno) + (' ')) + (prenotazione.ora)
                    prenotazioneGiornoCharPrenotazioneOra = (prenotazioneGiornoChar) + (prenotazioneOra);
                    // read (Integer.parseInt(prenotazione.ora)) + (1)
                    integerParseIntPrenotazioneOraInt1 = (integerParseIntPrenotazioneOra) + (1);


                    // read (((prenotazione.giorno) + (' ')) + (prenotazione.ora)) + ('.')
                    prenotazioneGiornoCharPrenotazioneOraChar = (prenotazioneGiornoCharPrenotazioneOra) + ('.');


                    // read ((((prenotazione.giorno) + (' ')) + (prenotazione.ora)) + ('.')) + ('0')
                    prenotazioneGiornoCharPrenotazioneOraCharChar0 = (prenotazioneGiornoCharPrenotazioneOraChar) + ('0');


                    // read (((((prenotazione.giorno) + (' ')) + (prenotazione.ora)) + ('.')) + ('0')) + ('0')
                    prenotazioneGiornoCharPrenotazioneOraCharChar0Char0 = (prenotazioneGiornoCharPrenotazioneOraCharChar0) + ('0');


                    // read ((((((prenotazione.giorno) + (' ')) + (prenotazione.ora)) + ('.')) + ('0')) + ('0')) + ('-')
                    prenotazioneGiornoCharPrenotazioneOraCharChar0Char0Char = (prenotazioneGiornoCharPrenotazioneOraCharChar0Char0) + ('-');


                    // read (((((((prenotazione.giorno) + (' ')) + (prenotazione.ora)) + ('.')) + ('0')) + ('0')) + ('-')) + ((Integer.parseInt(prenotazione.ora)) + (1))
                    prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1 = (prenotazioneGiornoCharPrenotazioneOraCharChar0Char0Char) + (integerParseIntPrenotazioneOraInt1);


                    // read ((((((((prenotazione.giorno) + (' ')) + (prenotazione.ora)) + ('.')) + ('0')) + ('0')) + ('-')) + ((Integer.parseInt(prenotazione.ora)) + (1))) + ('.')
                    prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1Char = (prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1) + ('.');


                    // read (((((((((prenotazione.giorno) + (' ')) + (prenotazione.ora)) + ('.')) + ('0')) + ('0')) + ('-')) + ((Integer.parseInt(prenotazione.ora)) + (1))) + ('.')) + ('0')
                    prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1CharChar0 = (prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1Char) + ('0');


                    // read ((((((((((prenotazione.giorno) + (' ')) + (prenotazione.ora)) + ('.')) + ('0')) + ('0')) + ('-')) + ((Integer.parseInt(prenotazione.ora)) + (1))) + ('.')) + ('0')) + ('0')
                    prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1CharChar0Char0 = (prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1CharChar0) + ('0');
            }
        }
        // batch finished
        if ((dirtyFlags & 0x101L) != 0) {
            // api target 1

            this.conferma.setTag(prenotazione);
            this.disdici.setTag(prenotazione);
        }
        if ((dirtyFlags & 0x103L) != 0) {
            // api target 1

            androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable(this.cover, androidx.databinding.adapters.Converters.convertColorToDrawable(prenotazioneId));
        }
        if ((dirtyFlags & 0x161L) != 0) {
            // api target 1

            this.dateAndHour.setText(prenotazioneGiornoCharPrenotazioneOraCharChar0Char0CharIntegerParseIntPrenotazioneOraInt1CharChar0Char0);
        }
        if ((dirtyFlags & 0x119L) != 0) {
            // api target 1

            this.docente.setText(prenotazioneNomeCharPrenotazioneCognome);
        }
        if ((dirtyFlags & 0x181L) != 0) {
            // api target 1

            this.stato.setText(prenotazioneStato);
        }
        if ((dirtyFlags & 0x105L) != 0) {
            // api target 1

            this.title.setText(prenotazioneTitolo);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): prenotazione
        flag 1 (0x2L): prenotazione.id
        flag 2 (0x3L): prenotazione.titolo
        flag 3 (0x4L): prenotazione.nome
        flag 4 (0x5L): prenotazione.cognome
        flag 5 (0x6L): prenotazione.giorno
        flag 6 (0x7L): prenotazione.ora
        flag 7 (0x8L): prenotazione.stato
        flag 8 (0x9L): null
    flag mapping end*/
    //end
}
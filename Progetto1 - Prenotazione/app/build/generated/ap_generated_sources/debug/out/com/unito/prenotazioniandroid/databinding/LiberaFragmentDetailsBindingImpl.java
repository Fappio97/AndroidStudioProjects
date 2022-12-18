package com.unito.prenotazioniandroid.databinding;
import com.unito.prenotazioniandroid.R;
import com.unito.prenotazioniandroid.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LiberaFragmentDetailsBindingImpl extends LiberaFragmentDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.black_mask, 6);
        sViewsWithIds.put(R.id.main_container, 7);
        sViewsWithIds.put(R.id.overview_container, 8);
        sViewsWithIds.put(R.id.stato, 9);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LiberaFragmentDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private LiberaFragmentDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.view.View) bindings[6]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[3]
            , (android.widget.ScrollView) bindings[7]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.Button) bindings[5]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[2]
            );
        this.cover.setTag(null);
        this.dateAndHour.setTag(null);
        this.docente.setTag(null);
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.prenota.setTag(null);
        this.title.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x80L;
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
        if (BR.libera == variableId) {
            setLibera((com.unito.prenotazioniandroid.repository.storge.model.Libera) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setLibera(@Nullable com.unito.prenotazioniandroid.repository.storge.model.Libera Libera) {
        updateRegistration(0, Libera);
        this.mLibera = Libera;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.libera);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeLibera((com.unito.prenotazioniandroid.repository.storge.model.Libera) object, fieldId);
        }
        return false;
    }
    private boolean onChangeLibera(com.unito.prenotazioniandroid.repository.storge.model.Libera Libera, int fieldId) {
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
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String liberaNome = null;
        java.lang.Integer liberaId = null;
        int integerParseIntLiberaOraInt1 = 0;
        java.lang.String liberaTitolo = null;
        java.lang.String liberaGiornoCharLiberaOra = null;
        java.lang.String liberaGiornoCharLiberaOraChar = null;
        java.lang.String liberaGiornoCharLiberaOraCharChar0Char0Char = null;
        java.lang.String liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1CharChar0 = null;
        java.lang.String liberaGiornoChar = null;
        int integerParseIntLiberaOra = 0;
        java.lang.String liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1CharChar0Char0 = null;
        java.lang.String liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1 = null;
        java.lang.String liberaOra = null;
        java.lang.String liberaGiornoCharLiberaOraCharChar0Char0 = null;
        java.lang.String liberaCognome = null;
        com.unito.prenotazioniandroid.repository.storge.model.Libera libera = mLibera;
        java.lang.String liberaNomeChar = null;
        java.lang.String liberaNomeCharLiberaCognome = null;
        java.lang.String liberaGiornoCharLiberaOraCharChar0 = null;
        java.lang.String liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1Char = null;
        java.lang.String liberaGiorno = null;

        if ((dirtyFlags & 0xffL) != 0) {


            if ((dirtyFlags & 0x99L) != 0) {

                    if (libera != null) {
                        // read libera.nome
                        liberaNome = libera.getNome();
                        // read libera.cognome
                        liberaCognome = libera.getCognome();
                    }


                    // read (libera.nome) + (' ')
                    liberaNomeChar = (liberaNome) + (' ');


                    // read ((libera.nome) + (' ')) + (libera.cognome)
                    liberaNomeCharLiberaCognome = (liberaNomeChar) + (liberaCognome);
            }
            if ((dirtyFlags & 0x83L) != 0) {

                    if (libera != null) {
                        // read libera.id
                        liberaId = libera.getId();
                    }
            }
            if ((dirtyFlags & 0x85L) != 0) {

                    if (libera != null) {
                        // read libera.titolo
                        liberaTitolo = libera.getTitolo();
                    }
            }
            if ((dirtyFlags & 0xe1L) != 0) {

                    if (libera != null) {
                        // read libera.ora
                        liberaOra = libera.getOra();
                        // read libera.giorno
                        liberaGiorno = libera.getGiorno();
                    }


                    // read Integer.parseInt(libera.ora)
                    integerParseIntLiberaOra = java.lang.Integer.parseInt(liberaOra);
                    // read (libera.giorno) + (' ')
                    liberaGiornoChar = (liberaGiorno) + (' ');


                    // read (Integer.parseInt(libera.ora)) + (1)
                    integerParseIntLiberaOraInt1 = (integerParseIntLiberaOra) + (1);
                    // read ((libera.giorno) + (' ')) + (libera.ora)
                    liberaGiornoCharLiberaOra = (liberaGiornoChar) + (liberaOra);


                    // read (((libera.giorno) + (' ')) + (libera.ora)) + ('.')
                    liberaGiornoCharLiberaOraChar = (liberaGiornoCharLiberaOra) + ('.');


                    // read ((((libera.giorno) + (' ')) + (libera.ora)) + ('.')) + ('0')
                    liberaGiornoCharLiberaOraCharChar0 = (liberaGiornoCharLiberaOraChar) + ('0');


                    // read (((((libera.giorno) + (' ')) + (libera.ora)) + ('.')) + ('0')) + ('0')
                    liberaGiornoCharLiberaOraCharChar0Char0 = (liberaGiornoCharLiberaOraCharChar0) + ('0');


                    // read ((((((libera.giorno) + (' ')) + (libera.ora)) + ('.')) + ('0')) + ('0')) + ('-')
                    liberaGiornoCharLiberaOraCharChar0Char0Char = (liberaGiornoCharLiberaOraCharChar0Char0) + ('-');


                    // read (((((((libera.giorno) + (' ')) + (libera.ora)) + ('.')) + ('0')) + ('0')) + ('-')) + ((Integer.parseInt(libera.ora)) + (1))
                    liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1 = (liberaGiornoCharLiberaOraCharChar0Char0Char) + (integerParseIntLiberaOraInt1);


                    // read ((((((((libera.giorno) + (' ')) + (libera.ora)) + ('.')) + ('0')) + ('0')) + ('-')) + ((Integer.parseInt(libera.ora)) + (1))) + ('.')
                    liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1Char = (liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1) + ('.');


                    // read (((((((((libera.giorno) + (' ')) + (libera.ora)) + ('.')) + ('0')) + ('0')) + ('-')) + ((Integer.parseInt(libera.ora)) + (1))) + ('.')) + ('0')
                    liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1CharChar0 = (liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1Char) + ('0');


                    // read ((((((((((libera.giorno) + (' ')) + (libera.ora)) + ('.')) + ('0')) + ('0')) + ('-')) + ((Integer.parseInt(libera.ora)) + (1))) + ('.')) + ('0')) + ('0')
                    liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1CharChar0Char0 = (liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1CharChar0) + ('0');
            }
        }
        // batch finished
        if ((dirtyFlags & 0x83L) != 0) {
            // api target 1

            androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable(this.cover, androidx.databinding.adapters.Converters.convertColorToDrawable(liberaId));
        }
        if ((dirtyFlags & 0xe1L) != 0) {
            // api target 1

            this.dateAndHour.setText(liberaGiornoCharLiberaOraCharChar0Char0CharIntegerParseIntLiberaOraInt1CharChar0Char0);
        }
        if ((dirtyFlags & 0x99L) != 0) {
            // api target 1

            this.docente.setText(liberaNomeCharLiberaCognome);
        }
        if ((dirtyFlags & 0x81L) != 0) {
            // api target 1

            this.prenota.setTag(libera);
        }
        if ((dirtyFlags & 0x85L) != 0) {
            // api target 1

            this.title.setText(liberaTitolo);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): libera
        flag 1 (0x2L): libera.id
        flag 2 (0x3L): libera.titolo
        flag 3 (0x4L): libera.nome
        flag 4 (0x5L): libera.cognome
        flag 5 (0x6L): libera.giorno
        flag 6 (0x7L): libera.ora
        flag 7 (0x8L): null
    flag mapping end*/
    //end
}
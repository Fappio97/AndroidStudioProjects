package com.unito.prenotazioniandroid;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.unito.prenotazioniandroid.databinding.LiberaFragmentDetailsBindingImpl;
import com.unito.prenotazioniandroid.databinding.PrenotazioneFragmentDetailsBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_LIBERAFRAGMENTDETAILS = 1;

  private static final int LAYOUT_PRENOTAZIONEFRAGMENTDETAILS = 2;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(2);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.unito.prenotazioniandroid.R.layout.libera_fragment_details, LAYOUT_LIBERAFRAGMENTDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.unito.prenotazioniandroid.R.layout.prenotazione_fragment_details, LAYOUT_PRENOTAZIONEFRAGMENTDETAILS);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_LIBERAFRAGMENTDETAILS: {
          if ("layout/libera_fragment_details_0".equals(tag)) {
            return new LiberaFragmentDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for libera_fragment_details is invalid. Received: " + tag);
        }
        case  LAYOUT_PRENOTAZIONEFRAGMENTDETAILS: {
          if ("layout/prenotazione_fragment_details_0".equals(tag)) {
            return new PrenotazioneFragmentDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for prenotazione_fragment_details is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(12);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "stato");
      sKeys.put(2, "titolo");
      sKeys.put(3, "prenotazione");
      sKeys.put(4, "cognome");
      sKeys.put(5, "giorno");
      sKeys.put(6, "nome");
      sKeys.put(7, "id");
      sKeys.put(8, "libera");
      sKeys.put(9, "ora");
      sKeys.put(10, "username");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(2);

    static {
      sKeys.put("layout/libera_fragment_details_0", com.unito.prenotazioniandroid.R.layout.libera_fragment_details);
      sKeys.put("layout/prenotazione_fragment_details_0", com.unito.prenotazioniandroid.R.layout.prenotazione_fragment_details);
    }
  }
}

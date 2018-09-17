package com.example.elab_yang.bleee;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainListWrapper {
    public TextView needleTimeView;
    public ImageView contactView;

    public MainListWrapper(View root){
        needleTimeView = (TextView) root.findViewById(R.id.main_item_needletime);
        contactView = (ImageView) root.findViewById(R.id.main_itme_contact);
    }
}

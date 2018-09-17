package com.example.elab_yang.bleee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainListAdapter extends ArrayAdapter<NeedleVO> {
    Context context;
    ArrayList<NeedleVO> datas;
    int resId;

    public MainListAdapter(Context context, int resId, ArrayList<NeedleVO> datas){
        super(context, resId);
        this.context = context;
        this.datas = datas;
        this.resId = resId;
    }

    @Override
    public int getCount(){
        return datas.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, null);

            MainListWrapper wrapper = new MainListWrapper(convertView);
            convertView.setTag(wrapper);
        }

        MainListWrapper wrapper = (MainListWrapper) convertView.getTag();
        TextView needleTimeView = wrapper.needleTimeView;
        final ImageView contactView = wrapper.contactView;

        final NeedleVO vo = datas.get(position);
        needleTimeView.setText(vo.needleTime);

        contactView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //

            }
        });
        return convertView;
    }
}

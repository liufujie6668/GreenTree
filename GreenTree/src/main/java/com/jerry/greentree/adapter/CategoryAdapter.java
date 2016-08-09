package com.jerry.greentree.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jerry.greentree.R;
import com.jerry.greentree.entity.KeyWord;

import java.util.List;

/**
 * Created by Administrator on 16-1-5.
 */
public class CategoryAdapter<T> extends BaseAdapter
{
    private Context context;
    private List<T> list = null;
    private ChangeCategoryListener listener;

    public CategoryAdapter(Context context, List<T> list, ChangeCategoryListener listener)
    {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        Object item = getItem(position);

        TextView textView = new TextView(context);
        textView.setClickable(true);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        if (item instanceof KeyWord.ResponseDataEntity)
        {
            KeyWord.ResponseDataEntity entity = (KeyWord.ResponseDataEntity) item;
            textView.setGravity(Gravity.CENTER);

            layoutParams.height = 100;
            //textView.setLayoutParams(layoutParams);

            textView.setText(entity.getGroupName());

            textView.setTag(position);

        }
        else if (item instanceof KeyWord.ResponseDataEntity.ItemsEntity)
        {
            KeyWord.ResponseDataEntity.ItemsEntity entity
                    = (KeyWord.ResponseDataEntity.ItemsEntity) item;

            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            textView.setTextColor(context.getResources().getColor(R.color.color_green));
            textView.setText(entity.getTitle());

            layoutParams.height = 130;
            layoutParams.leftMargin = 300;
            //textView.setLayoutParams(layoutParams);

            textView.setTag(position);

        }
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listener.changeCategory((Integer)view.getTag());
            }
        });

        return textView;
    }

    public interface ChangeCategoryListener
    {
        public void changeCategory(int position);
    }
}

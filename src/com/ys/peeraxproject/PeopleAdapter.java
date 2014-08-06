package com.ys.peeraxproject;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ys.peeraxproject.view.PeopleListItem;

public class PeopleAdapter extends BaseAdapter{
	private ArrayList<HashMap<String, String>> list;
	private Context context;
	
	public PeopleAdapter(Context context, ArrayList<HashMap<String, String>> pList) {
		this.context = context;
		this.list = pList;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;	
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		PeopleListItem pitem = new PeopleListItem(context);
		pitem.setAbout(list.get(arg0).get("about"));
		pitem.setName(list.get(arg0).get("name"));
		pitem.setPhonenumber(list.get(arg0).get("phonenumber"));
	
		return pitem;
	}
}
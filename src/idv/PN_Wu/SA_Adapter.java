package idv.PN_Wu;

import idv.PN_Wu.NcutStudentsActivities.R;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SA_Adapter extends BaseAdapter {

	public class SA_Data {
		String strStartDate, strEndDate, strOrganization, actid, strActionName,
				strActionFullName, strDate, strLocation, strChiefCoordinator,
				strPhoneNumber, strIntroduction;

		public SA_Data(String strStartDate, String strEndDate,
				String strOrganization, String actid, String strActionName,
				String strActionFullName, String strDate, String strLocation,
				String strChiefCoordinator, String strPhoneNumber,
				String strIntroduction) {
			this.strStartDate = strStartDate;
			this.strEndDate = strEndDate;
			this.strOrganization = strOrganization;
			this.actid = actid;
			this.strActionName = strActionName;
			this.strActionFullName = strActionFullName;
			this.strDate = strDate;
			this.strLocation = strLocation;
			this.strChiefCoordinator = strChiefCoordinator;
			this.strPhoneNumber = strPhoneNumber;
			this.strIntroduction = strIntroduction;
		}
	}

	LayoutInflater layoutInflater;
	private List<SA_Data> SA_List = new ArrayList<SA_Data>();

	public SA_Adapter(Context context) {
		String strInflater = Context.LAYOUT_INFLATER_SERVICE;
		layoutInflater = (LayoutInflater) context.getSystemService(strInflater);
	}

	public void addSA(String strStartDate, String strEndDate,
			String strOrganization, String actid, String strActionName,
			String strActionFullName, String strDate, String strLocation, String strChiefCoordinator,
			String strPhoneNumber, String strIntroduction) {
		addSA(new SA_Data(strStartDate, strEndDate, strOrganization, actid,
				strActionName, strActionFullName, strDate, strLocation, strChiefCoordinator,
				strPhoneNumber, strIntroduction));
	}

	public void addSA(SA_Data sA_Data) {
		SA_List.add(sA_Data);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return SA_List.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return SA_List.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	static class ViewHolder {
		TextView textView2, textView4, textView6, textView8, textView10,
				textView12, textView14;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final ViewHolder holder;

		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.activity_main_listview_item, null);
			holder = new ViewHolder();
			holder.textView2 = (TextView) convertView
					.findViewById(R.id.textView2);
			holder.textView4 = (TextView) convertView
					.findViewById(R.id.textView4);
			holder.textView6 = (TextView) convertView
					.findViewById(R.id.textView6);
			holder.textView8 = (TextView) convertView
					.findViewById(R.id.textView8);
			holder.textView10 = (TextView) convertView
					.findViewById(R.id.textView10);
			holder.textView12 = (TextView) convertView
					.findViewById(R.id.textView12);
			holder.textView14 = (TextView) convertView
					.findViewById(R.id.textView14);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textView2.setText(SA_List.get(position).strActionFullName);
		holder.textView4.setText(SA_List.get(position).strDate);
		holder.textView6.setText(SA_List.get(position).strLocation);
		holder.textView8.setText(SA_List.get(position).strOrganization);
		holder.textView10.setText(SA_List.get(position).strChiefCoordinator);
		holder.textView12.setText(SA_List.get(position).strPhoneNumber);
		holder.textView14.setText(SA_List.get(position).strIntroduction);
		return convertView;
	}

}

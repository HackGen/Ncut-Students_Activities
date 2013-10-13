package idv.PN_Wu.NcutStudentsActivities;

import idv.PN_Wu.SA_Adapter;

import java.io.UnsupportedEncodingException;

import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView listView1;
	SA_Adapter sA_Adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView1 = (ListView) findViewById(R.id.listView1);
		init();
		Refresh();
	}

	SharedPreferences settings = null;
	int int_top_actid, int_top_actid_count = 0;

	void init() {
		settings = getPreferences(MODE_PRIVATE);
		int_top_actid = settings.getInt("int_top_actid", -1);
	}

	String strPage;
	String[] strTemp1;
	int intPage;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	void Refresh() {
		showLoading();
		sA_Adapter = new SA_Adapter(getApplicationContext());
		listView1.setAdapter(sA_Adapter);
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				intPage = 0;
				do {
					intPage++;
					strPage = idv.PN_Wu.Http.getSA_List(intPage);
					try {
						strPage = new String(strPage.getBytes("ISO-8859-1"),
								"Big5");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					strPage = strPage.substring(strPage.indexOf("<tr>") + 4);
					// 此區段之前為垃圾資料

					strTemp1 = strPage.split("<table");
					// strTemp1[0] 為活動清單區段
					// strTemp1[1] 為跳頁清單區段

					String[] strTemp2 = strTemp1[0].split("<tr>");
					// strTemp2[0] 為欄位名稱
					// strTemp2[n] iff n>0 為每一筆社團資料

					String[] strTemp3;
					for (int j = 1; j < strTemp2.length; j++) {
						String strTemp4 = strTemp2[j];
						// 一筆社團資料
						strTemp3 = strTemp4.split("\">");
						// strTemp3[n] iff n<3 為垃圾資料
						// strTemp3[3] 為 開始日期
						// strTemp3[4] 為 結束日期
						// strTemp3[5] 為 發佈社團
						// strTemp3[6] 為 actid參數值(活動詳細資料網址)
						// strTemp3[7] 為 活動名稱
						strTemp3[3] = strTemp3[3]
								.substring(0, strTemp3[3].indexOf("</td>"))
								.trim().replaceAll("\t", "")
								.replaceAll("\n", "");
						strTemp3[4] = strTemp3[4]
								.substring(0, strTemp3[4].indexOf("</td>"))
								.trim().replaceAll("\t", "")
								.replaceAll("\n", "");
						strTemp3[5] = strTemp3[5]
								.substring(0, strTemp3[5].indexOf("</td>"))
								.trim().replaceAll("\t", "")
								.replaceAll("\n", "");
						strTemp3[6] = strTemp3[6].substring(0,
								strTemp3[6].indexOf("&"));
						strTemp3[6] = strTemp3[6].substring(strTemp3[6]
								.indexOf("d=") + 2);
						strTemp3[7] = strTemp3[7]
								.substring(0, strTemp3[7].indexOf("</a>"))
								.trim().replaceAll("\t", "")
								.replaceAll("\n", "");
						final String strStartDate = strTemp3[3], strEndDate = strTemp3[4], strOrganization = strTemp3[5], actid = strTemp3[6], strActionName = strTemp3[7];

						strPage = idv.PN_Wu.Http.getSA_Information(actid);
						try {
							strPage = new String(
									strPage.getBytes("ISO-8859-1"), "Big5");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						strPage = strPage.substring(strPage
								.indexOf(">活動名稱：</td>"));
						strPage = strPage.substring(strPage.indexOf("\">") + 2);
						final String strActionFullName = strPage.substring(0,
								strPage.indexOf("</td>"));
						strPage = strPage.substring(strPage
								.indexOf("<td>活動日期：</td>"));
						strPage = strPage.substring(strPage.indexOf("\">") + 2);
						final String strDate = strPage.substring(0,
								strPage.indexOf("</td>"));
						strPage = strPage.substring(strPage
								.indexOf("<td>活動地點：</td>"));
						strPage = strPage.substring(strPage.indexOf("\">") + 2);
						final String strLocation = strPage.substring(0,
								strPage.indexOf("</td>"));
						strPage = strPage.substring(strPage
								.indexOf("<td>活動總召：</td>"));
						strPage = strPage.substring(strPage.indexOf("\">") + 2);
						final String strChiefCoordinator = strPage.substring(0,
								strPage.indexOf("</td>"));
						strPage = strPage.substring(strPage
								.indexOf("<td>連絡電話：</td>"));
						strPage = strPage.substring(strPage.indexOf("\">") + 2);
						final String strPhoneNumber = strPage.substring(0,
								strPage.indexOf("</td>"));
						strPage = strPage.substring(strPage
								.indexOf("<td>活動簡介：</td>"));
						strPage = strPage.substring(strPage.indexOf("\">") + 2);
						final String strIntroduction = strPage
								.substring(0, strPage.indexOf("</td>")).trim()
								.replaceAll("\t", "").replaceAll("\n", "");
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								sA_Adapter.addSA(strStartDate, strEndDate,
										strOrganization, actid, strActionName,
										strActionFullName, strDate,
										strLocation, strChiefCoordinator,
										strPhoneNumber, strIntroduction);
								switch (int_top_actid_count) {
								case -1:
									/* Do Nothing! */

									break;
								case 0:
									if (Integer.parseInt(actid) != int_top_actid) {
										Toast.makeText(getApplicationContext(),
												"發現新活動! 數量計算中...",
												Toast.LENGTH_SHORT).show();
										SharedPreferences.Editor editor = settings
												.edit();
										editor.putInt("int_top_actid",
												Integer.parseInt(actid));
										editor.commit();
										int_top_actid_count++;
									} else {
										Toast.makeText(getApplicationContext(),
												"沒有發現新活動!", Toast.LENGTH_SHORT)
												.show();
										int_top_actid_count = -1;
									}
									break;
								case 101:
									Toast.makeText(getApplicationContext(),
											"發現超過100個新活動!", Toast.LENGTH_LONG)
											.show();
									int_top_actid_count = -1;
									break;
								default:
									if (Integer.parseInt(actid) != int_top_actid) {
										int_top_actid_count++;
									} else {
										Toast.makeText(
												getApplicationContext(),
												String.format("發現%d個新活動!",
														int_top_actid_count),
												Toast.LENGTH_LONG).show();

										int_top_actid_count = -1;
									}
									break;
								}
							}
						});
						progressDiglog.dismiss();
					}
				} while (strTemp1[1].indexOf(String.format("<option value=%d>",
						intPage + 1)) != -1);

			}
		}.start();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	ProgressDialog progressDiglog;

	public void showLoading() {
		if (progressDiglog == null) {
			progressDiglog = new ProgressDialog(this);
			progressDiglog.setMessage("載入中, 請稍後…");
		}
		if (progressDiglog != null && progressDiglog.isShowing()) {
			progressDiglog.dismiss();
		}
		progressDiglog.show();
	}
}

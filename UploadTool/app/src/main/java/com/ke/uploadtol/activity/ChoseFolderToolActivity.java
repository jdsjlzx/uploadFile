package com.ke.uploadtol.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ke.uploadtol.R;

public class ChoseFolderToolActivity extends Activity {
	private final static String TAG = "UPLOADTOL:ChoseAllFilesToolActivity";
	public final static int resultCode = 0x12;
	private final static String rootUrl = "/mnt/sdcard";
	
	ListView listView ;
	TextView textView ;
	Button btnParent;
	//记录当前的父类文件夹url
	String currentParent;
	//记录当前路径下所有文件的文件数组
//	File[] currentFiles;
	
	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chose_all_files);
		
		//获取列表全部文件的ListView
		listView = (ListView) findViewById(R.id.file_list);
		textView = (TextView) findViewById(R.id.file_path);
		btnParent = (Button) findViewById(R.id.btn_parent);
		//获取系统的SD卡目录
		final File root = new File(rootUrl);
		if(root.exists()){
			//如果SD卡存在
			currentParent = rootUrl;
			inflateListView(rootUrl);
		} else {
			textView.setText("SD卡不存在");
			return ;
		}
		//为listView的列表项的单击事件绑定监听器
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int postion, long id) {
				File[] currentFiles = (new File(currentParent)).listFiles();
				Log.d(TAG, "setOnItemClickListener_url : "+(new File(currentParent)).getPath() );
				//获取点击的是文件夹，获取文件夹下面的所有文件夹
				File[] tep = currentFiles[postion].listFiles();
				Log.d(TAG, "currentFiles[postion]_Url : "+currentFiles[postion].getPath() );
				if( tep == null || tep.length == 0){
					Log.e(TAG, "当前文件夹下没有文件！");
					Toast.makeText(ChoseFolderToolActivity.this, "该文件夹下没有  文件", Toast.LENGTH_SHORT).show();
				} else {
					boolean isFolder = haveFolder(tep);
					if(isFolder){
						//获取单击的列表的列表项对应的文件夹，设为当前的父类文件夹
						currentParent = currentFiles[postion].getPath();
						//TODO
						showPopup();
					} else {
						Log.e(TAG, "当前文件夹下没有文件夹！");
						Toast.makeText(ChoseFolderToolActivity.this, "该文件夹下没有  文件夹", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("current_path", "【"+currentFiles[postion].getPath()+"】");  
						intent.putExtras(bundle); 
						setResult( resultCode, intent );
						finish();
					}
				}
			}
		});
			
			//获取上一级目录的按键
			btnParent.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if ( !currentParent.equals("/mnt/sdcard") ) {
						//获取上一层目录
						currentParent = getParentUrl(currentParent);
						inflateListView(currentParent);
					}
				}
			});
	}
	
	private void inflateListView(String url){
		//创建一个List集合，List集合的元素是 Map
		File file_p = new File(url);
		if (! file_p.exists()) {
			return ;
		}
		File[] files = file_p.listFiles();
		List< Map<String,Object> > listItems = new ArrayList< Map<String,Object> >();
		for (int i=0 ; i<files.length ; i++) {
			Map<String,Object> listItem = new HashMap<String,Object>();
			if(files[i].isDirectory()){
				//是文件夹
				listItem.put("icon", R.drawable.folderke);
				listItem.put("fileName", files[i].getName());
				//添加到List 项
				listItems.add(listItem);
			}
		}
		//创建一个 SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.line, 
				new String[]{"icon", "fileName"}, 
				new int[]{R.id.icon, R.id.file_name} );
		//为listView设置 Adapter
		listView.setAdapter(simpleAdapter);
		textView.setText("当前路径为: "+ url );
	}

	//判断是否存在文件夹
	private boolean haveFolder(File[] files){
		if(files == null || files.length == 0){
			return false;
		}
		for(File file : files){
			if(file.isDirectory()){
				return true;
			}
		}
		return false;
	}
	
	//获取上一层目录url
	private String getParentUrl(String pathUrl){
		if(null == pathUrl){
			return "";
		}
		String newUrl = pathUrl.substring(0, pathUrl.lastIndexOf("/"));
		return newUrl;
	}
	
	/**
	 * 弹出选择的popup
	 * @return
	 * true  :进入下一级
	 * false :返回当前目录
	 * boolean
	 * ChoseFolderToolActivity.java
	 */
	private void showPopup(){
		final String[] arrayChose = new String[] {"进入下一级目录", "返回当前文件夹"};
		AlertDialog builder = new AlertDialog.Builder(ChoseFolderToolActivity.this)
			.setTitle("选择对话框")//设置标题
			.setIcon(R.drawable.icon)//设置简单的列表项内容
			.setItems(arrayChose, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int witch) {
					if(arrayChose[witch].equals(arrayChose[0])){
						Log.d(TAG, "进入下一级目录");
						inflateListView(currentParent);
					}
					else if(arrayChose[witch].equals(arrayChose[1])){
						Log.d(TAG, "返回当前文件夹");
						Intent intent = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("current_path", "【"+currentParent+"】");  
						intent.putExtras(bundle); 
						setResult( resultCode, intent );
						finish();
					}
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int witch) {
					// TODO Auto-generated method stub
					currentParent = getParentUrl(currentParent);
					inflateListView(currentParent);
				}
			} )
			.setCancelable(false)
			.create();
		builder.show();
	}
	
}

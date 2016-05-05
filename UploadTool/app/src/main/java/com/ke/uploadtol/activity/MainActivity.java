package com.ke.uploadtol.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ke.uploadtol.R;
import com.ke.uploadtol.tool.FileAccessI;
import com.ke.uploadtol.tool.HttpUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

public class MainActivity extends Activity {
	private final static String TAG = "UPLOADTOL:MainActivity";
	
	private Button btnChoseFile;
	private Button btnChoseAllFiles;
	private Button btnStartUpload;
	private TextView txtResult;
	private TextView textView;
	private View vie;
	private PopupWindow  popupWindow;
	
	private final static int FILE_PATH = 0x01;
	private final static int FILES_ALL_PATH = 0x02;
	private final static int ADD_LOG = 0x03;
	private final static int DELETE_LOG = 0x04;
	private final static int SHOW_LOG = 0x05;
	private final static int NOT_SUPPORT_FOLDER = 0x06;
	
	private String uploadUrl = "http://guonan99138.xicp.net:10386/AppServer/UploadFileServlet"; 
	
	private final static int backCode = 0x11;
	
	
	Handler handler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
			int what = msg.what;
			switch(what){
			case FILE_PATH:
				Log.d(TAG, "@handleMessage_FILE_PATH");
				String path = (String) msg.obj;
				txtResult.setText(path);
				break;
			case FILES_ALL_PATH:
				Log.v(TAG, "@handleMessage_FILES_ALL_PATH");
				String path2 = (String) msg.obj;
				txtResult.setText(path2);
				break;
			case ADD_LOG:
				Log.v(TAG, "@handleMessage_ADD_LOG");
				showPopup(vie);
				break;
			case DELETE_LOG:
				Log.v(TAG, "@handleMessage_DELETE_LOG");
				dissPopup();
				break;
			case SHOW_LOG:
				Log.v(TAG, "@handleMessage_SHOW_LOG");
				txtResult.setText("上传完成！");
				break;
			case NOT_SUPPORT_FOLDER:
				Log.v(TAG, "@handleMessage_NOT_SUPPORT_FOLDER");
				Toast.makeText(MainActivity.this, "抱歉，暂不支持【文件夹】上传", Toast.LENGTH_SHORT).show();
				break;
			}
	    }
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnChoseFile = (Button) findViewById(R.id.choseFile);
		btnChoseAllFiles = (Button) findViewById(R.id.choseAllFiles);
		btnStartUpload = (Button) findViewById(R.id.startUpload);
		txtResult = (TextView) findViewById(R.id.tvData);
		textView = (TextView) findViewById(R.id.groupItem);
		
		LayoutInflater lay = LayoutInflater.from(this);
		vie = lay.inflate(R.layout.popup, null);

		
		OnClickListener btnListener = new OnClickListener() { 
			@Override  
			public void onClick(View v) {  
				switch (v.getId()) {
				case R.id.choseFile:
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, ChoseFileToolActivity.class);
					startActivityForResult(intent, backCode);
					break;  
				case R.id.choseAllFiles:  
					Intent intent_all = new Intent();
					intent_all.setClass(MainActivity.this, ChoseFolderToolActivity.class);
					startActivityForResult(intent_all, backCode);
					break;  
				case R.id.startUpload:
					String pathUrl = txtResult.getText().toString();
					/*****************/
					if(Pattern.matches("【(\\S+)】", pathUrl)){
								/*not support folder*/
						//TODO
						Message msg0 = Message.obtain();
						msg0.what = NOT_SUPPORT_FOLDER;
						msg0.obj = pathUrl;
						handler.sendMessage(msg0);
					} else {
						/*****************/
						Message msg = Message.obtain();
						msg.what = ADD_LOG;
						msg.obj = pathUrl;
						handler.sendMessage(msg);
						try {
							Log.d(TAG, "before_retStr : "+ pathUrl);
							HttpUtils.cutFileUpload("video", getFilePath(pathUrl));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
								/*end upload*/
						//TODO
						Message msg2 = Message.obtain();
						msg2.what = DELETE_LOG;
						msg2.obj = pathUrl;
						handler.sendMessage(msg2);
								/*show success or fail*/
						//TODO
						Message msg3 = Message.obtain();
						msg3.what = SHOW_LOG;
						msg3.obj = pathUrl;
						handler.sendMessage(msg3);
					}
					break;
				default:  
					break;  
				} 
			}  
		};
		
		btnChoseFile.setOnClickListener(btnListener);
		btnChoseAllFiles.setOnClickListener(btnListener);
		btnStartUpload.setOnClickListener(btnListener);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "@onActivityResult");
		if(requestCode == backCode){
			if(resultCode == ChoseFileToolActivity.resultCode){
				Bundle bundle = data.getExtras();
				String str = bundle.getString("current_path");
				String testStr = bundle.getString("testStr");
				Log.d(TAG, "获取的 文件路径： "+str);
				Log.d(TAG, "testStr : "+testStr);
				txtResult.setText(str);
				if(str != null){
					btnStartUpload.setBackgroundColor(Color.rgb(96, 96, 96));
				}
			}
			else if(resultCode == ChoseFolderToolActivity.resultCode) {
				Bundle bundle = data.getExtras();
				String str = bundle.getString("current_path");
				String testStr = bundle.getString("testStr");
				Log.d(TAG, "获取的 文件夹路径： "+str);
				Log.d(TAG, "testStr : "+testStr);
				txtResult.setText(str);
				if(str != null){
					btnStartUpload.setBackgroundColor(Color.rgb(96, 96, 96));
				}
			}
		}
	}
	
	//弹出popup
	private void showPopup(View view){
		popupWindow=new PopupWindow(view,300, 350);
		// 使其聚集  
		popupWindow.setFocusable(true);  
		// 设置不允许在外点击消失  
		popupWindow.setOutsideTouchable(false);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景  
		popupWindow.setBackgroundDrawable(new BitmapDrawable());  
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半  
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2  
				- popupWindow.getWidth() / 2;  
		Log.i("coder", "xPos:" + xPos);
		popupWindow.showAsDropDown(view, xPos, 0); 
	}	
	//去掉 popup
	private void dissPopup(){
		if(popupWindow != null){
			popupWindow.dismiss();
		}
	}
	// 休眠时间
	private void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//截取字符串
	private String getFilePath(String path){
		if(path == null){
			return new String();
		}
		String result = path.substring(4);
		Log.d(TAG, "result : "+ result);
		return result;
	}
	//获取保存文件的名字
	private String getFileName(String path){
		if(path == null){
			return new String("nothing");
		}
		String name = path.substring(path.lastIndexOf("/")+1);
		Log.d(TAG, "name : "+name);
		return name;
	}

	public void cutFileUpload(String fileType, String filePath) {

		Log.d("lzx","cutFileUpload");
		try {
			FileAccessI fileAccessI = new FileAccessI(filePath, 0);
			Long nStartPos = 0l;
			Long length = fileAccessI.getFileLength();
			int mBufferSize = 1024 * 1000; // 每次处理1024 * 100字节
			byte[] buffer = new byte[mBufferSize];
			FileAccessI.Detail detail;
			long nRead = 0l;
			String vedioFileName = generatePicName(); // 分配一个文件名
			long nStart = nStartPos;
			List<String> fileList = new ArrayList<String>();
			while (nStart < length) {
				detail = fileAccessI.getContent(nStart);
				nRead = detail.length;
				buffer = detail.b;
				JSONObject mInDataJson = new JSONObject();
				mInDataJson.put("a", "282");
				mInDataJson.put("FileName", vedioFileName);
				mInDataJson.put("start", nStart); // 服务端获取开始文章进行写文件
				mInDataJson.put("filetype", fileType);
				mInDataJson.put("VEDIO", encodeByte(buffer));
				nStart += nRead;
				nStartPos = nStart;
				final String url = "http://172.16.1.60:8080/AppServer/UploadFileServlet?json="+mInDataJson.toString();
				//UploadTool.upLoadFile(url, buffer, filePath);
				RequestParams params = new RequestParams();
				params.put("json",mInDataJson.toString());
				params.put("name", "woshishishi");//传输的字符数据


				AsyncHttpClient client = new AsyncHttpClient();
				/*client.post(url, params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {

					}

					@Override
					public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

					}
				});*/
			}



		} catch (Exception e) {
		}
	}

	public static String generatePicName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS", Locale.CHINA);
		String filename = sdf.format(new Date(System.currentTimeMillis())) + ".mp4";
		return filename;
	}
	private static String encodeByte(byte[] buffer) {
		Log.d("lzx","encodeByte");
		return Base64.encodeToString(buffer, 0, buffer.length, Base64.DEFAULT);
	}
}

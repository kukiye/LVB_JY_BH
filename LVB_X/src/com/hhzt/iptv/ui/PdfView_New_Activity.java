package com.hhzt.iptv.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.BaseActivity;
import com.hhzt.iptv.lvb_x.log.LogUtil;
import com.hhzt.iptv.lvb_x.utils.OpenFileTypeUtil;
import com.hhzt.iptv.lvb_x.utils.StringUtil;
import com.lidroid.xutils.view.annotation.ContentView;

import java.io.File;

import static com.hhzt.iptv.R.id.pdfView;


@ContentView(R.layout.fragment_pdfview_new)
public class PdfView_New_Activity extends BaseActivity {

	private PDFView mPdfView;
	private int mPageCount;
	private int mCountPage;

	private SharedPreferences sp = null;
	private String filePath = null;
	private PDFView.Configurator mConfigurator;
	private ScaleAnimation myAnimation_Scale;
	private boolean isSwitch = true;
	private ScaleAnimation myAnimation_Scale_Off;
	private float left = 0.1f;
	private float count  ;
	private String mZoom = "低";
	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("lvb_pdf", MODE_PRIVATE);
		mPdfView = (PDFView) findViewById(pdfView);
//		mPdfView.zoomWithAnimation(2.5f);
		Intent intent = getIntent();
		filePath = intent.getStringExtra("pdf_filepath");
		setPDFView();
		myAnimation_Scale = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		myAnimation_Scale.setFillAfter(true);
		myAnimation_Scale.setInterpolator(new AccelerateInterpolator());
		myAnimation_Scale.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}
			@Override
			public void onAnimationEnd(Animation animation) {
               isSwitch = false;
			}
			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		myAnimation_Scale.setDuration(400);

		myAnimation_Scale_Off = new ScaleAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		myAnimation_Scale_Off.setFillAfter(true);
		myAnimation_Scale_Off.setInterpolator(new AccelerateInterpolator());
		myAnimation_Scale_Off.setDuration(400);



	}

		public Intent getPdfFileIntent(File file) {
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri = Uri.fromFile(file);
			intent.setDataAndType(uri, "application/pdf");
			return Intent.createChooser(intent, "Open File");
		}


	@SuppressLint("WrongCall")
	private void displayFromFile(File file, int page) {
		//   pdfView.fromUri(name)
		//  mConfigurator = pdfView.fromAsset(name);
		Log.i("TAG","pagepage:"+page);
		mConfigurator = mPdfView.fromFile(file);
		mConfigurator
		//		.pages(0, 1, 2, 3, 4, 5, 6, 7) // //所有页面默认显示
				.enableSwipe(true) // 允许阻止改变使用滑动页
				.swipeHorizontal(false)
				.enableDoubletap(true)
				.defaultPage(page)
				// allows to draw something on the current page, usually visible in the middle of the screen
				.onDraw(onDrawListener)
				// allows to draw something on all pages, separately for every page. Called only for visible pages
				.onDrawAll(onDrawListener)
				.onLoad(onLoadCompleteListener) // //在文件加载后调用，并开始呈现
				.onPageChange(onPageChangeListener)
				.onPageScroll(onPageScrollListener)
				.onError(onErrorListener)
				.onRender(onRenderListener) // called after document is rendered for the first time
				.enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
				.password(null)
				.scrollHandle(null)
				.enableAntialiasing(true) // improve rendering a little bit on low-res screens
				// spacing between pages in dp. To define spacing color, set view background
				.spacing(0)
				.load();
	}

	public OnDrawListener onDrawListener = new OnDrawListener() {
		@Override
		public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
		}
	};
	public OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
		@Override
		public void loadComplete(int nbPages) {
		}
	};

	public OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageChanged(int page, int pageCount) {
			//    Toast.makeText(MainActivity.this,"pageCount::"+pageCount+"--page--"+page,Toast.LENGTH_SHORT).show();
			//   count = page;
			Log.i("TAG","page::"+page+"--mPageCount--"+mPageCount);
			mCountPage = page;
			mPageCount = pageCount;
		}
	};

	public OnPageScrollListener onPageScrollListener = new OnPageScrollListener() {
		@Override
		public void onPageScrolled(int page, float positionOffset) {
			Log.i("TAG","onPageScrolled"+page);
			mCountPage = page;
		}
	};

	public OnErrorListener onErrorListener = new OnErrorListener() {
		@Override
		public void onError(Throwable t) {
			LogUtil.e("ttttttttt:"+t);
			if(file != null && file.exists()){
				LogUtil.i("file:"+file.getAbsolutePath());
				file.delete();
			}
		}
	};
	public OnRenderListener onRenderListener = new OnRenderListener() {
		@Override
		public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
			mPdfView.fitToWidth();
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		if(!StringUtil.isEmpty(filePath)){
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt(filePath, mCountPage);
			editor.commit();
		}
	}
	public void setPage(){
		if(mPageCount<1){
		  return;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	public void setPDFView(){
		Log.i("TAG","iii:"+mPdfView);
		if(null != mPdfView){
//			if (filePath != null) {
//				if (filePath.endsWith(".pdf")) {
//					LogUtil.i("filePath::"+filePath);
//					file = new File(filePath);
//					int page = sp.getInt(filePath, 1);
//					displayFromFile(file, page);
//					//startActivity( getPdfFileIntent(file));
//				} else {
//					BaseActivity.getInstance().showToast(
//							getString(R.string.file_error), Toast.LENGTH_LONG);
//					finish();
//				}
//			} else {
//				BaseActivity.getInstance().showToast(
//						getString(R.string.file_error), Toast.LENGTH_LONG);
//				finish();
//			}
			OpenFileTypeUtil.openFile(this,new File("/storage/emulated/0/lvb/book/server.doc"));

			mPdfView.setOnKeyListener(new View.OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					int currentIndex = mPdfView.getCurrentPage();
					switch (keyCode) {
						case KeyEvent.KEYCODE_DPAD_UP:
							if (event.getAction() == KeyEvent.ACTION_DOWN) {
								LogUtil.i("currentIndex::"+currentIndex);
								LogUtil.i("-mPdfView.getWidth()::"+-mPdfView.getWidth());
								if (count >= 0) {
									count-=0.001;
								//	mPdfView.jumpTo(--currentIndex);
//								count = count - (float) 1/mPageCount;
								mPdfView.setPositionOffset(count,false);
								}
								return true;
							}
							break;
						case KeyEvent.KEYCODE_DPAD_DOWN:
							// 增加一页
							if (event.getAction() == KeyEvent.ACTION_DOWN) {
//							count = count +0.005f;
//							mPdfView.setPositionOffset(count,false);
								LogUtil.i("getPositionOffset:"+currentIndex);
								LogUtil.i("mPageCount:"+mPageCount);
//							mPdfView.getPositionOffset();
								try {
									count+=0.001;
									mPdfView.setPositionOffset(count,false);
								}catch (Exception e){
									LogUtil.e(e.getMessage());
								}
//								if (currentIndex < mPageCount) {
//								//	mPdfView.jumpTo(++currentIndex);
////								count = count + (float)1/mPageCount;
//									count+=10;
//								mPdfView.setPositionOffset(count,false);
//								}
								return true;
							}
							break;
						case KeyEvent.KEYCODE_DPAD_CENTER:
						case KeyEvent.KEYCODE_ENTER:
							if (event.getAction() == KeyEvent.ACTION_DOWN) {
								LogUtil.i("KEYCODE_DPAD_CENTER::"+currentIndex);
//							if(isSwitch){
//							//	mPdfView.startAnimation(myAnimation_Scale);
//								isSwitch = false;
//								mPdfView.zoomWithAnimation(3f);
//							}else{
//								isSwitch = true;
//							//	mPdfView.startAnimation(myAnimation_Scale_Off);
//							//	myAnimation_Scale.cancel();
//								mPdfView.zoomWithAnimation(3f);
//							}
//							//mPdfView.zoomWithAnimation(3f);
								switch (mZoom){
									case "低":
										mZoom = "中";
										mPdfView.zoomWithAnimation(3f);
										break;
									case "中":
										mZoom = "高";
										mPdfView.zoomWithAnimation(3.5f);
										break;
									case "高":
										mZoom = "低";
										mPdfView.zoomWithAnimation(2.5f);
										break;
								}
							}
							break;
						case KeyEvent.KEYCODE_DPAD_LEFT:
							if (event.getAction() == KeyEvent.ACTION_DOWN) {
//							if(left > 0){
//								left = left - 0.1f;
//								mPdfView.moveTo(left,0);
//							}
								mPdfView.moveRelativeTo(-10f,0f);
							}
							break;
						case KeyEvent.KEYCODE_DPAD_RIGHT:
							if (event.getAction() == KeyEvent.ACTION_DOWN) {
								//	left =left + 0.1f;
								//	mPdfView.moveTo(-0.3f,0);
								mPdfView.moveRelativeTo(10f,0f);
							}
							break;
						default:
							break;
					}
					return false;
				}
			});
		}
	}

}

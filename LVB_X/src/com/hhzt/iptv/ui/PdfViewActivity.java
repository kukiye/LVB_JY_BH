//package com.hhzt.iptv.ui;
//
//import java.io.File;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.graphics.Canvas;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnKeyListener;
//import android.widget.Toast;
//
//import com.hhzt.iptv.R;
//import com.hhzt.iptv.lvb_x.BaseActivity;
//import com.joanzapata.pdfview.PDFView;
//import com.joanzapata.pdfview.listener.OnDrawListener;
//import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
//import com.joanzapata.pdfview.listener.OnPageChangeListener;
//import com.lidroid.xutils.view.annotation.ContentView;
//
//@ContentView(R.layout.fragment_pdfview)
//public class PdfViewActivity extends BaseActivity implements
//		OnPageChangeListener, OnLoadCompleteListener, OnDrawListener {
//
//	private PDFView mPdfView;
//	private int mPageCount;
//
//	private SharedPreferences sp = null;
//	private String filePath = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		sp = getSharedPreferences("lvb_pdf", MODE_PRIVATE);
//		mPdfView = (PDFView) findViewById(R.id.pdfView);
//		Intent intent = getIntent();
//		filePath = intent.getStringExtra("pdf_filepath");
//		mPdfView.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				int currentIndex = mPdfView.getCurrentPage();
//				switch (keyCode) {
//				case KeyEvent.KEYCODE_DPAD_UP:
//					if (event.getAction() == KeyEvent.ACTION_DOWN) {
//						if (currentIndex > 0) {
//							mPdfView.jumpTo(currentIndex);
//						}
//						return true;
//					}
//					break;
//				case KeyEvent.KEYCODE_DPAD_DOWN:
//					// 增加一页
//					if (event.getAction() == KeyEvent.ACTION_DOWN) {
//						if (currentIndex < mPageCount) {
//							mPdfView.jumpTo(currentIndex + 2);
//						}
//						return true;
//					}
//					break;
//				default:
//					break;
//				}
//				return false;
//			}
//		});
//		// displayFromAssets("about.pdf");
//		if (filePath != null) {
//			if (filePath.endsWith(".pdf")) {
//				File file = new File(filePath);
//				int page = sp.getInt(filePath, 1);
//				displayFromFile(file, page);
//			} else {
//				BaseActivity.getInstance().showToast(
//						getString(R.string.file_error), Toast.LENGTH_LONG);
//				finish();
//			}
//		} else {
//			BaseActivity.getInstance().showToast(
//					getString(R.string.file_error), Toast.LENGTH_LONG);
//			finish();
//		}
//	}
//
//	@SuppressLint("WrongCall")
//	private void displayFromFile(File file, int page) {
//		mPdfView.fromFile(file) // 设置pdf文件地址
//				.defaultPage(page) // 设置默认显示第1页
//				.onPageChange(this) // 设置翻页监听
//				.onLoad(this) // 设置加载监听
//				.onDraw(this) // 绘图监听
//				.showMinimap(false) // pdf放大的时候，是否在屏幕的右上角生成小地图
//				.swipeVertical(true) // pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
//				.enableSwipe(true) // 是否允许翻页，默认是允许翻
//				//.pages( 2 , 3 , 4 , 5 ) //把2 , 3 , 4 , 5 过滤掉
//				.load();
//	}
//
//	/**
//	 * 翻页回调
//	 *
//	 * @param page
//	 * @param pageCount
//	 */
//	@Override
//	public void onPageChanged(int page, int pageCount) {
//		// TODO Auto-generated method stub
//		mPageCount = pageCount;
//		Editor editor = sp.edit();
//		editor.putInt(filePath, page);
//		editor.commit();
//	}
//
//	/**
//	 * 加载完成回调
//	 *
//	 * @param nbPages
//	 *            总共的页数
//	 */
//	@Override
//	public void loadComplete(int nbPages) {
//
//	}
//
//	@Override
//	public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight,
//			int displayedPage) {
//
//	}
//
//}

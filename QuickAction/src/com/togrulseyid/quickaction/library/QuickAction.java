package com.togrulseyid.quickaction.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.togrulseyid.quickaction.library.listeners.OnDismissListener;
import com.togrulseyid.quickaction.library.listeners.OnItemClickListener;


public class QuickAction implements PopupWindow.OnDismissListener {

	private static final int CONTENT_VIEW = android.R.id.content;

//	private static final String PARAM_STATUS_BAR_HEIGHT = "status_bar_height";
//	private static final String PARAM_DIMEN = "dimen";
//	private static final String PARAM_ANDROID = "android";
	private static final float PADDING_DP = 4;

	private Context context;
//	private int screenWidth;
//	private int screenHeight;
	private int popupBackgroundResource;
	private int textAppearanceStyle;
	private int padding;

	private OnDismissListener onDismissListener;
	private OnItemClickListener onItemClickListener;

	private PopupWindow popupWindow;
	private WindowManager windowManager;
	private RelativeLayout rootLayout;
	private LinearLayout contentLayout;
	private ScrollView scrollView;
	private boolean isUseDefaultView;

	public QuickAction(Context context, int animationStyle,
			int popupBackgroundResource, RelativeLayout rootLayout) {
		init(context, animationStyle, 0, popupBackgroundResource, rootLayout);
	}

	private void init(Context context, int animationStyle,
			int textAppearanceStyle, int popupBackgroundResource,
			RelativeLayout rootLayout) {
		this.context = context;
		this.popupBackgroundResource = popupBackgroundResource;
		this.textAppearanceStyle = textAppearanceStyle;

		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

//		initScreen();

		if (rootLayout == null) {
			this.padding = (int) (PADDING_DP * context.getResources()
					.getDisplayMetrics().density);
			this.rootLayout = configureDefaultPopupView();
		} else {
			this.rootLayout = rootLayout;
			rootLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
		}

		initPopupWindow(animationStyle);
	}

//	@SuppressWarnings("deprecation")
//	private void initScreen() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//			Display display = windowManager.getDefaultDisplay();
//			Point size = new Point();
//			display.getSize(size);
//			screenWidth = size.x;
//			screenHeight = size.y;
//		} else {
//			screenWidth = windowManager.getDefaultDisplay().getWidth();
//			screenHeight = windowManager.getDefaultDisplay().getHeight();
//		}
//	}

	private void initPopupWindow(int animationStyle) {
		popupWindow = new PopupWindow(context);
		popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setContentView(rootLayout);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.setAnimationStyle(animationStyle);
		popupWindow.setTouchInterceptor(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					popupWindow.dismiss();
					return true;
				}
				return false;
			}
		});
	}

	private RelativeLayout configureDefaultPopupView() {
		isUseDefaultView = true;

		RelativeLayout rootLayout = new RelativeLayout(context);
		rootLayout.setLayoutParams(new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		rootLayout.setPadding(padding / 2, padding, padding / 2, padding);

		contentLayout = new LinearLayout(context);
		contentLayout.setOrientation(LinearLayout.VERTICAL);
		contentLayout.setLayoutParams(new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		scrollView = new ScrollView(context);
		scrollView.setId(CONTENT_VIEW);
		RelativeLayout.LayoutParams scrollParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		scrollView.setLayoutParams(scrollParams);
		scrollView.setBackgroundResource(popupBackgroundResource);

		scrollView.addView(contentLayout);

		rootLayout.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		rootLayout.addView(scrollView);

		return rootLayout;
	}

	public void dismiss() {
		popupWindow.dismiss();
	}

	public void addActionItem(final QuickActionItem action) {
		if (isUseDefaultView) {
			TextView titleView = new TextView(context);
			titleView.setText(action.getTitle());
			titleView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
			titleView.setPadding(0, padding, 0, padding);
			titleView.setClickable(true);
			titleView.setTextAppearance(context, textAppearanceStyle);
			titleView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (onItemClickListener != null)
						onItemClickListener.onItemClick(action.getId());
				}
			});

			contentLayout.addView(titleView);
		} else {
			throw new RuntimeException(
					"You can't use custom view and default action item!");
		}
	}

	public void show(View anchor) {
//		View mainXY = anchor;
		int[] location = new int[2];
		anchor.getLocationOnScreen(location);
//
//		Rect anchorRect = new Rect(location[0], location[1], location[0]
//				+ anchor.getWidth(), location[1] + anchor.getHeight());
//
//		rootLayout.measure(View.MeasureSpec.UNSPECIFIED,
//				View.MeasureSpec.UNSPECIFIED);
//
//		int rootHeight = rootLayout.getMeasuredHeight();
//		int rootWidth = rootLayout.getMeasuredWidth();
//		int offsetTop = anchorRect.top;
//		int offsetBottom = screenHeight - anchorRect.bottom;
//		boolean onTop = offsetTop > offsetBottom;
//
//		int x = calculateHorizontalPosition(anchor, anchorRect, rootWidth,
//				screenWidth - 100);
//		int y = calculateVerticalPosition(anchor, anchorRect, rootHeight,
//				onTop, offsetTop, offsetBottom);

//		popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, x, y);
		
//		int[] locationX = new int[2];
//		mainXY.getLocationOnScreen(locationX);
//		popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, location[0] - (anchor.getWidth() + anchor.getWidth()/2), location[1]);
		
		popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, location[0] , location[1]);
	}
	
	public void show(View view, int x, int y) {
		popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
	}

//	private int calculateVerticalPosition(View anchor, Rect anchorRect,
//			int rootHeight, boolean onTop, int offsetTop, int offsetBottom) {
//		int y;
//
//		if (onTop)
//			if (rootHeight > offsetTop) {
//				scrollView.getLayoutParams().height = offsetTop
//						- anchor.getHeight();
//				y = getStatusBarHeight();
//			} else
//				y = anchorRect.top - rootHeight;
//		else {
//			y = anchorRect.bottom;
//			if (rootHeight > offsetBottom)
//				scrollView.getLayoutParams().height = offsetBottom;
//		}
//
//		return y;
//	}

//	private int calculateHorizontalPosition(View anchor, Rect anchorRect,
//			int rootWidth, int screenWidth) {
//		int x;
//
//		if ((anchorRect.left + rootWidth) > screenWidth) {
//			x = anchorRect.left - (rootWidth - anchor.getWidth());
//			if (x < 0)
//				x = 0;
//		} else {
//			if (anchor.getWidth() > rootWidth)
//				x = anchorRect.centerX() - (rootWidth / 2);
//			else
//				x = anchorRect.left;
//		}
//
//		return x;
//	}

	public static Bitmap rotateBitmap(int rotate, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.setRotate(rotate);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, false);
	}

//	private int getStatusBarHeight() {
//		int result = 0;
//		int resourceId = context.getResources().getIdentifier(
//				PARAM_STATUS_BAR_HEIGHT, PARAM_DIMEN, PARAM_ANDROID);
//		if (resourceId > 0)
//			result = context.getResources().getDimensionPixelSize(resourceId);
//
//		return result;
//	}

	@Override
	public void onDismiss() {
		if (onDismissListener != null)
			onDismissListener.onDismiss();
	}

	/**
	 * Listeners
	 */
	public void setOnDismissListener(OnDismissListener onDismissListener) {
		this.onDismissListener = onDismissListener;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public void setMaxHeightResource(int heightResource) {
		int maxHeight = context.getResources().getDimensionPixelSize(
				heightResource);
		popupWindow.setHeight(maxHeight);
	}

}
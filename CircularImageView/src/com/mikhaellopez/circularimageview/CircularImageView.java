package com.mikhaellopez.circularimageview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class CircularImageView extends ImageView {

	public static final boolean isViewShowsCorrectly = (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) ? true : false;
	private int borderWidth;
	private int canvasSize;
	private Bitmap image;
	private Paint paint;
	private Paint paintBorder;
//==============================================

	  public static final String TAG = "RoundedImageView";
	  public static final float DEFAULT_RADIUS = 0f;
	  public static final float DEFAULT_BORDER_WIDTH = 0f;
	  private static final ScaleType[] SCALE_TYPES = {
	      ScaleType.MATRIX,
	      ScaleType.FIT_XY,
	      ScaleType.FIT_START,
	      ScaleType.FIT_CENTER,
	      ScaleType.FIT_END,
	      ScaleType.CENTER,
	      ScaleType.CENTER_CROP,
	      ScaleType.CENTER_INSIDE
	  };

	  private float cornerRadius = DEFAULT_RADIUS;
	  private float borderWidthR = DEFAULT_BORDER_WIDTH;
	  private ColorStateList borderColor =
	      ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
	  private boolean isOval = true;
	  private boolean mutateBackground = false;

	  private int mResource;
	  private Drawable mDrawable;
	  private Drawable mBackgroundDrawable;

	  private ScaleType mScaleType;
//===================================================
	public CircularImageView(final Context context) {
		this(context, null);
		Log.d("CircularImageView","" + isViewShowsCorrectly);
	}

	public CircularImageView(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.circularImageViewStyle);
		Log.d("CircularImageView","" + isViewShowsCorrectly);
	}

	public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		Log.d("CircularImageView","" + isViewShowsCorrectly);
		
		if(isViewShowsCorrectly) {
			// init paint
			paint = new Paint();
			paint.setAntiAlias(true);

			paintBorder = new Paint();
			paintBorder.setAntiAlias(true);

			// load the styled attributes and set their properties
			TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, defStyle, 0);

			if(attributes.getBoolean(R.styleable.CircularImageView_border, true)) {
				int defaultBorderSize = (int) (4 * getContext().getResources().getDisplayMetrics().density + 0.5f);
				setBorderWidth(attributes.getDimensionPixelOffset(R.styleable.CircularImageView_border_width, defaultBorderSize));
				setBorderColor(attributes.getColor(R.styleable.CircularImageView_border_color, Color.WHITE));
			}

			if(attributes.getBoolean(R.styleable.CircularImageView_shadow, false))
				addShadow();
		} else {
//			RoundImageView
			 TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyle, 0);

			    int index = a.getInt(R.styleable.RoundedImageView_android_scaleType, -1);
			    if (index >= 0) {
			      setScaleType(SCALE_TYPES[index]);
			    } else {
			      // default scaletype to FIT_CENTER
			      setScaleType(ScaleType.FIT_CENTER);
			    }

			    cornerRadius = a.getDimensionPixelSize(R.styleable.RoundedImageView_corner_radius, -1);
			    borderWidthR = a.getDimensionPixelSize(R.styleable.RoundedImageView_border_width2, -1);

			    // don't allow negative values for radius and border
			    if (cornerRadius < 0) {
//			      cornerRadius = DEFAULT_RADIUS;
				cornerRadius = context.obtainStyledAttributes(attrs,
						R.styleable.CircularImageView, defStyle, 0)
						.getDimensionPixelOffset(
								R.styleable.CircularImageView_border_width,
								(int) (4 * getContext().getResources()
										.getDisplayMetrics().density + 0.5f));
			    }
			    if (borderWidthR < 0) {
			      borderWidthR = DEFAULT_BORDER_WIDTH;
			    }

			    borderColor = a.getColorStateList(R.styleable.RoundedImageView_border_color2);
			    if (borderColor == null) {
			      borderColor = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
			    }

			    mutateBackground = a.getBoolean(R.styleable.RoundedImageView_mutate_background, false);
			    isOval = a.getBoolean(R.styleable.RoundedImageView_oval, true);

			    updateDrawableAttrs();
			    updateBackgroundDrawableAttrs(true);

			    a.recycle();
		}
	}

	public void setBorderWidth(int borderWidth) {
		if(isViewShowsCorrectly) {
			this.borderWidth = borderWidth;
			this.requestLayout();
			this.invalidate();
		}else {
			setBorderWidth(getResources().getDimension(borderWidth));
		}
	}

	public void setBorderColor(int borderColor) {
		if(isViewShowsCorrectly) {
			if (paintBorder != null)
				paintBorder.setColor(borderColor);
			this.invalidate();
		} else {
			setBorderColor(ColorStateList.valueOf(borderColor));
		}
	}

	public void addShadow() {
		setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
		paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
	}

	@Override
	public void onDraw(Canvas canvas) {
		if(isViewShowsCorrectly) {
			// load the bitmap
			try {//I have made it to reduce crashes
				image = drawableToBitmap(getDrawable());
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}

			// init shader
			if (image != null) {

				canvasSize = canvas.getWidth();
				if(canvas.getHeight()<canvasSize)
					canvasSize = canvas.getHeight();

				BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvasSize, canvasSize, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
				paint.setShader(shader);

				// circleCenter is the x or y of the view's center
				// radius is the radius in pixels of the cirle to be drawn
				// paint contains the shader that will texture the shape
				int circleCenter = (canvasSize - (borderWidth * 2)) / 2;
				canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, ((canvasSize - (borderWidth * 2)) / 2) + borderWidth - 4.0f, paintBorder);
				canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, ((canvasSize - (borderWidth * 2)) / 2) - 4.0f, paint);
			}
		} else {
			super.onDraw(canvas);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width, height);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// The parent has determined an exact size for the child.
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			// The child can be as large as it wants up to the specified size.
			result = specSize;
		} else {
			// The parent has not imposed any constraint on the child.
			result = canvasSize;
		}

		return result;
	}

	private int measureHeight(int measureSpecHeight) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			// The child can be as large as it wants up to the specified size.
			result = specSize;
		} else {
			// Measure the text (beware: ascent is a negative number)
			result = canvasSize;
		}

		return (result + 2);
	}

	public Bitmap drawableToBitmap(Drawable drawable) throws OutOfMemoryError {
		if (drawable == null) {
			return null;
		} else if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}
	
	
	
	
	
//	RoundImageView
	  /**
	   * Return the current scale type in use by this ImageView.
	   *
	   * @attr ref android.R.styleable#ImageView_scaleType
	   * @see android.widget.ImageView.ScaleType
	   */
	  @Override
	  public ScaleType getScaleType() {
	    return mScaleType;
	  }
	  
	  
	  

	  /**
	   * Controls how the image should be resized or moved to match the size
	   * of this ImageView.
	   *
	   * @param scaleType The desired scaling mode.
	   * @attr ref android.R.styleable#ImageView_scaleType
	   */
	  @Override
	  public void setScaleType(ScaleType scaleType) {
	    assert scaleType != null;

	    if (mScaleType != scaleType) {
	      mScaleType = scaleType;

	      switch (scaleType) {
	        case CENTER:
	        case CENTER_CROP:
	        case CENTER_INSIDE:
	        case FIT_CENTER:
	        case FIT_START:
	        case FIT_END:
	        case FIT_XY:
	          super.setScaleType(ScaleType.FIT_XY);
	          break;
	        default:
	          super.setScaleType(scaleType);
	          break;
	      }

	      updateDrawableAttrs();
	      updateBackgroundDrawableAttrs(false);
	      invalidate();
	    }
	  }

	  @Override
	  public void setImageDrawable(Drawable drawable) {
	    mResource = 0;
	    mDrawable = RoundedDrawable.fromDrawable(drawable);
	    updateDrawableAttrs();
	    super.setImageDrawable(mDrawable);
	  }

	  @Override
	  public void setImageBitmap(Bitmap bm) {
	    mResource = 0;
	    mDrawable = RoundedDrawable.fromBitmap(bm);
	    updateDrawableAttrs();
	    super.setImageDrawable(mDrawable);
	  }

	  @Override
	  public void setImageResource(int resId) {
	    if (mResource != resId) {
	      mResource = resId;
	      mDrawable = resolveResource();
	      updateDrawableAttrs();
	      super.setImageDrawable(mDrawable);
	    }
	  }

	  @Override 
	  public void setImageURI(Uri uri) {
	    super.setImageURI(uri);
	    setImageDrawable(getDrawable());
	  }

	  private Drawable resolveResource() {
	    Resources rsrc = getResources();
	    if (rsrc == null) { return null; }

	    Drawable d = null;

	    if (mResource != 0) {
	      try {
	        d = rsrc.getDrawable(mResource);
	      } catch (Exception e) {
	        Log.w(TAG, "Unable to find resource: " + mResource, e);
	        // Don't try again.
	        mResource = 0;
	      }
	    }
	    return RoundedDrawable.fromDrawable(d);
	  }

	  @Override
	  public void setBackground(Drawable background) {
	    setBackgroundDrawable(background);
	  }

	  private void updateDrawableAttrs() {
	    updateAttrs(mDrawable);
	  }

	  private void updateBackgroundDrawableAttrs(boolean convert) {
	    if (mutateBackground) {
	      if (convert) {
	        mBackgroundDrawable = RoundedDrawable.fromDrawable(mBackgroundDrawable);
	      }
	      updateAttrs(mBackgroundDrawable);
	    }
	  }

	  private void updateAttrs(Drawable drawable) {
	    if (drawable == null) { return; }

	    if (drawable instanceof RoundedDrawable) {
	      ((RoundedDrawable) drawable)
	          .setScaleType(mScaleType)
	          .setCornerRadius(cornerRadius)
	          .setBorderWidth(borderWidthR)
	          .setBorderColor(borderColor)
	          .setOval(isOval);
	    } else if (drawable instanceof LayerDrawable) {
	      // loop through layers to and set drawable attrs
	      LayerDrawable ld = ((LayerDrawable) drawable);
	      for (int i = 0, layers = ld.getNumberOfLayers(); i < layers; i++) {
	        updateAttrs(ld.getDrawable(i));
	      }
	    }
	  }

	  @Override
	  @Deprecated
	  public void setBackgroundDrawable(Drawable background) {
	    mBackgroundDrawable = background;
	    updateBackgroundDrawableAttrs(true);
	    super.setBackgroundDrawable(mBackgroundDrawable);
	  }

	  public float getCornerRadius() {
	    return cornerRadius;
	  }

	  public void setCornerRadius(int resId) {
	    setCornerRadius(getResources().getDimension(resId));
	  }

	  public void setCornerRadius(float radius) {
	    if (cornerRadius == radius) { return; }

	    cornerRadius = radius;
	    updateDrawableAttrs();
	    updateBackgroundDrawableAttrs(false);
	  }

	  public float getBorderWidth() {
	    return borderWidthR;
	  }

	  public void setBorderWidth(float width) {
	    if (borderWidthR == width) { return; }

	    borderWidthR = width;
	    updateDrawableAttrs();
	    updateBackgroundDrawableAttrs(false);
	    invalidate();
	  }

	  public int getBorderColor() {
	    return borderColor.getDefaultColor();
	  }

	  public ColorStateList getBorderColors() {
	    return borderColor;
	  }

	  public void setBorderColor(ColorStateList colors) {
	    if (borderColor.equals(colors)) { return; }

	    borderColor =
	        (colors != null) ? colors : ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
	    updateDrawableAttrs();
	    updateBackgroundDrawableAttrs(false);
	    if (borderWidthR > 0) {
	      invalidate();
	    }
	  }

	  public boolean isOval() {
	    return isOval;
	  }

	  public void setOval(boolean oval) {
	    isOval = oval;
	    updateDrawableAttrs();
	    updateBackgroundDrawableAttrs(false);
	    invalidate();
	  }

	  public boolean isMutateBackground() {
	    return mutateBackground;
	  }

	  public void setMutateBackground(boolean mutate) {
	    if (mutateBackground == mutate) { return; }

	    mutateBackground = mutate;
	    updateBackgroundDrawableAttrs(true);
	    invalidate();
	  }
	  
	  @Override
	  protected void drawableStateChanged() {
	    super.drawableStateChanged();
	    invalidate();
	  }
}
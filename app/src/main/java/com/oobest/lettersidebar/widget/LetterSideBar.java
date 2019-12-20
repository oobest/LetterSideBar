package com.oobest.lettersidebar.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.oobest.lettersidebar.R;


public class LetterSideBar extends View {

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    private static String[] letterArray = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private LetterRange letterRange;

    private int choose = -1; //选中
    private Paint paint = new Paint();

    private Paint linePaint = new Paint();

    private float textSize = 20F;

    private boolean touching = false;

    private int normalColor;

    private int normalPressedColor;

    private int showRangeColor;

    private int padding = 20;

    public LetterSideBar(Context context) {
        super(context);
        init(context);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        Resources resources = context.getResources();
        textSize = resources.getDimension(R.dimen.letter_text_size);
        padding = resources.getDimensionPixelOffset(R.dimen.latter_padding);
        linePaint.setStrokeWidth(resources.getDimensionPixelSize(R.dimen.frame_line_width));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.parseColor("#eef0fb"));

        normalColor = Color.parseColor("#747995");
        normalPressedColor = Color.parseColor("#4756ac");
        showRangeColor = Color.parseColor("#3f51b5");

    }

    public void setShowLetter(String startLetter, String endLetter) {
        if (TextUtils.isEmpty(startLetter) || TextUtils.isEmpty(endLetter)) {
            letterRange = null;
        } else {
            letterRange = new LetterRange();
            for (int i = 0; i < letterArray.length; i++) {
                if (letterArray[i].equalsIgnoreCase(startLetter)) {
                    letterRange.start = i;
                }
                if (letterArray[i].equalsIgnoreCase(endLetter)) {
                    letterRange.end = i;
                }
            }
        }
        invalidate();
    }

    Rect rect = new Rect();
    RectF rectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();

        float singleHeight = (height * 1f - 2 * padding) / (letterArray.length);
        if (touching) {
            rectF.left = width / 4;
            rectF.top = 0;
            rectF.right = width - width / 4;
            rectF.bottom = height;
            canvas.drawRoundRect(rectF, width / 2, width / 2, linePaint);
        }
        if (letterRange != null) {
            rectF.left = width / 4;
            rectF.top = singleHeight * letterRange.start + padding;
            rectF.right = width - width / 4;
            rectF.bottom = singleHeight * (letterRange.end + 1) + padding;
            paint.setColor(showRangeColor);
            canvas.drawRoundRect(rectF, width / 2, width / 2, paint);
        }

        for (int i = 0; i < letterArray.length; i++) {
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(textSize);
            if (letterRange != null && i >= letterRange.start && i <= letterRange.end) {
                if (i == choose) {
                    paint.setColor(Color.BLACK);
                    paint.setFakeBoldText(true);
                } else {
                    paint.setColor(Color.WHITE);
                }
            } else {
                if (i == choose) {
                    paint.setColor(normalPressedColor);
                    paint.setFakeBoldText(true);
                } else {
                    paint.setColor(normalColor);
                }
            }
            paint.getTextBounds(letterArray[i], 0, 1, rect);
            float xPos = width / 2f - rect.width() / 2f;
            float yPos = singleHeight * i + (singleHeight + rect.height()) / 2 + padding;
            canvas.drawText(letterArray[i], xPos, yPos, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * letterArray.length);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            touching = false;
            choose = -1;
            if (listener != null) {
                listener.onTouchingLetterChanged(null);
            }
            invalidate();
        } else {
            touching = true;
            if (oldChoose != c) {
                if (c >= 0 && c < letterArray.length) {
                    if (listener != null) {
                        listener.onTouchingLetterChanged(letterArray[c]);
                    }
                    choose = c;
                    invalidate();
                }
            }
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(@Nullable String s);
    }

    static class LetterRange {
        int start;
        int end;
    }
}

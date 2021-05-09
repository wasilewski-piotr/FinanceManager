package pjatk.prm.s17918.managerfinansowy.custom.views.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pjatk.prm.s17918.managerfinansowy.models.Event;

public class LineChart extends View {

    public static List<Event> eventList = new ArrayList<>();

    public static List<Event> getEventList() {
        return eventList;
    }

    public static void setEventList(List<Event> eventList) {
        LineChart.eventList = eventList;
    }

    public LineChart(Context context) {
        super(context);
        init(null);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set){

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(5);

        Paint thinPaint = new Paint();
        thinPaint.setColor(Color.GRAY);
        thinPaint.setStrokeWidth(1);

        Paint horizontalBlackPaint = new Paint();
        horizontalBlackPaint.setColor(Color.BLACK);
        horizontalBlackPaint.setStrokeWidth(8);

        Paint blackPoint = new Paint();
        blackPoint.setColor(Color.BLACK);
        blackPoint.setStrokeWidth(18);

        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.parseColor("#048838"));
        greenPaint.setStrokeWidth(5);

        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(5);

        int middle = getMeasuredHeight()/2;
        int day = getMeasuredWidth()/32;

        int step_4 = getMeasuredHeight()/4;
        int step_8 = getMeasuredHeight()/8;
        int step_16 = getMeasuredHeight()/16;

        blackPaint.setTextSize(14);

        for(int i = 1; i < 32; i++){
            canvas.drawPoint(i*day, middle, horizontalBlackPaint);
            canvas.drawText(Integer.toString(i), i*day-5, middle + 20, blackPaint);
        }

        blackPaint.setTextSize(25);

        canvas.drawLine(0,middle, getMeasuredWidth(), middle, blackPaint);
        canvas.drawLine(5,0, 5, getMeasuredHeight(), blackPaint);

        float price_end = 0;
        float price_max = 0;
        float price_min = 0;
        int divider = 0;
        for(int i = 1; i < 32; i++) {
            for(Event event: eventList){
                if(i == Integer.parseInt(event.getDate().substring(8,10))) {
                    price_end += Float.parseFloat(event.getPrice());
                    if(price_end > price_max){
                        price_max = price_end;
                        System.out.println("Price max: " + price_max);
                    }
                    if(price_end < price_min){
                        price_min = price_end;
                        System.out.println("Price min: " + price_min);
                    }
                }
            }
        }

        if(price_max > 2000 || price_min < -2000) {
            if(price_max > 4000 || price_min < -4000){
                if(price_max > 8000 || price_min < -8000){
                    divider = 32;
                    canvas.drawLine(0,middle - step_4 - step_4 + 20, getMeasuredWidth(),middle - step_4 - step_4 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_4 - step_8 - step_16 + 20, getMeasuredWidth(),middle - step_4 - step_8 - step_16 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_4 - step_8 + 20, getMeasuredWidth(),middle - step_4 - step_8 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_4 - step_16 + 20, getMeasuredWidth(),middle - step_4 - step_16 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_4 + 20, getMeasuredWidth(),middle - step_4 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_8 - step_16 + 20, getMeasuredWidth(),middle - step_8 - step_16 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_8 + 20, getMeasuredWidth(),middle - step_8 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_16 + 20, getMeasuredWidth(),middle - step_16 + 20, thinPaint);

                    canvas.drawLine(0,middle + step_4 + step_4 - 20, getMeasuredWidth(),middle + step_4 + step_4 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_4 + step_8 + step_16 - 20, getMeasuredWidth(),middle + step_4 + step_8 + step_16 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_4 + step_8 - 20, getMeasuredWidth(),middle + step_4 + step_8 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_4 + step_16 - 20, getMeasuredWidth(),middle + step_4 + step_16 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_4 - 20, getMeasuredWidth(),middle + step_4 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_8 + step_16 - 20, getMeasuredWidth(),middle + step_8 + step_16 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_8 - 20, getMeasuredWidth(),middle + step_8 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_16 - 20, getMeasuredWidth(),middle + step_16 - 20, thinPaint);

                    canvas.drawPoint(0, middle - step_8 - step_16+ 20, blackPoint);
                    canvas.drawPoint(0, middle - step_8 + 20, blackPoint);
                    canvas.drawText("  4000 zł",20,middle - step_8 + 60, blackPaint);

                    canvas.drawPoint(0, middle - step_16 + 20, blackPoint);

                    canvas.drawPoint(0, middle - step_4 + 20, blackPoint);
                    canvas.drawText("  8000 zł",20,middle - step_4 + 60, blackPaint);

                    canvas.drawPoint(0, middle - step_4 - step_16 + 20, blackPoint);

                    canvas.drawPoint(0, middle - step_4 - step_8 + 20, blackPoint);
                    canvas.drawText("  12000 zł",20,middle - step_4 - step_8 + 60, blackPaint);

                    canvas.drawPoint(0, middle - step_4 - step_8 - step_16 + 20, blackPoint);

                    canvas.drawPoint(0, middle - step_4 - step_4 + 20, blackPoint);
                    canvas.drawText("  16000 zł",20,middle - step_4 - step_4 + 60, blackPaint);

                    canvas.drawPoint(0, middle + step_16 - 20, blackPoint);

                    canvas.drawPoint(0, middle + step_8 - 20, blackPoint);
                    canvas.drawText("- 4000 zł",20, middle + step_8 - 40, blackPaint);

                    canvas.drawPoint(0, middle + step_8 + step_16 - 20, blackPoint);

                    canvas.drawPoint(0, middle + step_4 - 20, blackPoint);
                    canvas.drawText("- 8000 zł",20, middle + step_4 - 40, blackPaint);

                    canvas.drawPoint(0, middle + step_4 + step_16 - 20, blackPoint);

                    canvas.drawPoint(0, middle + step_4 + step_8 - 20, blackPoint);
                    canvas.drawText("- 12000 zł",20, middle + step_4 + step_8 - 40, blackPaint);

                    canvas.drawPoint(0, middle + step_4 + step_8 + step_16 - 20, blackPoint);

                    canvas.drawPoint(0, middle + step_4 + step_4 - 20, blackPoint);
                    canvas.drawText("- 16000 zł",20, middle + step_4 + step_4 - 40, blackPaint);
                }else{
                    divider = 16;

                    canvas.drawLine(0,middle - step_4 - step_4 + 20, getMeasuredWidth(),middle - step_4 - step_4 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_4 - step_8 - step_16 + 20, getMeasuredWidth(),middle - step_4 - step_8 - step_16 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_4 - step_8 + 20, getMeasuredWidth(),middle - step_4 - step_8 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_4 - step_16 + 20, getMeasuredWidth(),middle - step_4 - step_16 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_4 + 20, getMeasuredWidth(),middle - step_4 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_8 - step_16 + 20, getMeasuredWidth(),middle - step_8 - step_16 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_8 + 20, getMeasuredWidth(),middle - step_8 + 20, thinPaint);
                    canvas.drawLine(0,middle - step_16 + 20, getMeasuredWidth(),middle - step_16 + 20, thinPaint);

                    canvas.drawLine(0,middle + step_4 + step_4 - 20, getMeasuredWidth(),middle + step_4 + step_4 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_4 + step_8 + step_16 - 20, getMeasuredWidth(),middle + step_4 + step_8 + step_16 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_4 + step_8 - 20, getMeasuredWidth(),middle + step_4 + step_8 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_4 + step_16 - 20, getMeasuredWidth(),middle + step_4 + step_16 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_4 - 20, getMeasuredWidth(),middle + step_4 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_8 + step_16 - 20, getMeasuredWidth(),middle + step_8 + step_16 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_8 - 20, getMeasuredWidth(),middle + step_8 - 20, thinPaint);
                    canvas.drawLine(0,middle + step_16 - 20, getMeasuredWidth(),middle + step_16 - 20, thinPaint);

                    canvas.drawPoint(0, middle - step_8 - step_16+ 20, blackPoint);
                    canvas.drawPoint(0, middle - step_8 + 20, blackPoint);
                    canvas.drawText("  2000 zł",20,middle - step_8 + 60, blackPaint);

                    canvas.drawPoint(0, middle - step_16 + 20, blackPoint);

                    canvas.drawPoint(0, middle - step_4 + 20, blackPoint);
                    canvas.drawText("  4000 zł",20,middle - step_4 + 60, blackPaint);

                    canvas.drawPoint(0, middle - step_4 - step_16 + 20, blackPoint);

                    canvas.drawPoint(0, middle - step_4 - step_8 + 20, blackPoint);
                    canvas.drawText("  6000 zł",20,middle - step_4 - step_8 + 60, blackPaint);

                    canvas.drawPoint(0, middle - step_4 - step_8 - step_16 + 20, blackPoint);

                    canvas.drawPoint(0, middle - step_4 - step_4 + 20, blackPoint);
                    canvas.drawText("  8000 zł",20,middle - step_4 - step_4 + 60, blackPaint);

                    canvas.drawPoint(0, middle + step_16 - 20, blackPoint);

                    canvas.drawPoint(0, middle + step_8 - 20, blackPoint);
                    canvas.drawText("- 2000 zł",20, middle + step_8 - 40, blackPaint);

                    canvas.drawPoint(0, middle + step_8 + step_16 - 20, blackPoint);

                    canvas.drawPoint(0, middle + step_4 - 20, blackPoint);
                    canvas.drawText("- 4000 zł",20, middle + step_4 - 40, blackPaint);

                    canvas.drawPoint(0, middle + step_4 + step_16 - 20, blackPoint);

                    canvas.drawPoint(0, middle + step_4 + step_8 - 20, blackPoint);
                    canvas.drawText("- 6000 zł",20, middle + step_4 + step_8 - 40, blackPaint);

                    canvas.drawPoint(0, middle + step_4 + step_8 + step_16 - 20, blackPoint);

                    canvas.drawPoint(0, middle + step_4 + step_4 - 20, blackPoint);
                    canvas.drawText("- 8000 zł",20, middle + step_4 + step_4 - 40, blackPaint);
                }
            }else{
                divider = 8;

                canvas.drawLine(0,middle - step_4 - step_4 + 20, getMeasuredWidth(),middle - step_4 - step_4 + 20, thinPaint);
                canvas.drawLine(0,middle - step_4 - step_8 + 20, getMeasuredWidth(),middle - step_4 - step_8 + 20, thinPaint);
                canvas.drawLine(0,middle - step_4 + 20, getMeasuredWidth(),middle - step_4 + 20, thinPaint);
                canvas.drawLine(0,middle - step_8 + 20, getMeasuredWidth(),middle - step_8 + 20, thinPaint);
                canvas.drawLine(0,middle + step_8 - 20, getMeasuredWidth(),middle + step_8 - 20, thinPaint);
                canvas.drawLine(0,middle + step_4 - 20, getMeasuredWidth(),middle + step_4 - 20, thinPaint);
                canvas.drawLine(0,middle + step_4 + step_8 - 20, getMeasuredWidth(),middle + step_4 + step_8 - 20, thinPaint);
                canvas.drawLine(0,middle + step_4 + step_4 - 20, getMeasuredWidth(),middle + step_4 + step_4 - 20, thinPaint);

                canvas.drawPoint(0, middle - step_8 + 20, blackPoint);
                canvas.drawText("  1000 zł",20,middle - step_8 + 60, blackPaint);

                canvas.drawPoint(0, middle - step_4 + 20, blackPoint);
                canvas.drawText("  2000 zł",20,middle - step_4 + 60, blackPaint);

                canvas.drawPoint(0, middle - step_4 - step_8 + 20, blackPoint);
                canvas.drawText("  3000 zł",20,middle - step_4 - step_8 + 60, blackPaint);

                canvas.drawPoint(0, middle - step_4 - step_4 + 20, blackPoint);
                canvas.drawText("  4000 zł",20,middle - step_4 - step_4 + 60, blackPaint);

                canvas.drawPoint(0, middle + step_8 - 20, blackPoint);
                canvas.drawText("- 1000 zł",20, middle + step_8 - 40, blackPaint);

                canvas.drawPoint(0, middle + step_4 - 20, blackPoint);
                canvas.drawText("- 2000 zł",20, middle + step_4 - 40, blackPaint);

                canvas.drawPoint(0, middle + step_4 + step_8 - 20, blackPoint);
                canvas.drawText("- 3000 zł",20, middle + step_4 + step_8 - 40, blackPaint);

                canvas.drawPoint(0, middle + step_4 + step_4 - 20, blackPoint);
                canvas.drawText("- 4000 zł",20, middle + step_4 + step_4 - 40, blackPaint);
            }
        }else {
            divider = 4;
            canvas.drawLine(0,middle - step_4 - step_4 + 20, getMeasuredWidth(),middle - step_4 - step_4 + 20, thinPaint);
            canvas.drawLine(0,middle - step_4 + 20, getMeasuredWidth(),middle - step_4 + 20, thinPaint);
            canvas.drawLine(0,middle + step_4 - 20, getMeasuredWidth(),middle + step_4 - 20, thinPaint);
            canvas.drawLine(0,middle + step_4 + step_4 - 20, getMeasuredWidth(),middle + step_4 + step_4 - 20, thinPaint);

            canvas.drawPoint(0, middle - step_4 + 20, blackPoint);
            canvas.drawText("  2000 zł",20,middle - step_4 - step_4 + 60, blackPaint);

            canvas.drawPoint(0, middle - step_4 - step_4 + 20, blackPoint);
            canvas.drawText("  1000 zł",20,middle - step_4 + 60, blackPaint);

            canvas.drawPoint(0, middle + step_4 - 20, blackPoint);
            canvas.drawText("- 1000 zł",20, middle + step_4 - 40, blackPaint);

            canvas.drawPoint(0, middle  +step_4 + step_4 - 20, blackPoint);
            canvas.drawText("- 2000 zł",20, middle + step_4 + step_4 -40, blackPaint);
        }

        float y_old = 0;
        for(int i = 1; i < 32; i++){
            float price = 0;
            for(Event event: eventList){
                if(i == Integer.parseInt(event.getDate().substring(8,10))){
                    price += Float.parseFloat(event.getPrice());
                }
            }

            float y_start = getMeasuredHeight()/2;
            float x_jump = getMeasuredWidth()/32;
            float y_jump = price/divider;
            float y0 = y_start - y_old;
            float y1 = y_start - y_jump;
            float y2 = y0 - y_jump;

            if(i == 1){
                if(y1 > y_start){
                    canvas.drawLine((i-1)*x_jump+5, y0, i*x_jump, y1, redPaint);
                }else{
                    if(y1 == y_start){
                        canvas.drawLine((i-1)*x_jump+5, y0, i*x_jump, y1, blackPaint);
                    }else{
                        canvas.drawLine((i-1)*x_jump+5, y0, i*x_jump, y1, greenPaint);
                    }
                }
            }else{
                if(y0 > y_start && y2 > y_start){
                    canvas.drawLine((i-1)*x_jump, y0, i*x_jump, y2, redPaint);
                }else if(y0 < y_start && y2 < y_start){
                    canvas.drawLine((i-1)*x_jump, y0, i*x_jump, y2, greenPaint);
                }else{
                    if(y0 > y2){
                        if(y0 == y_start){
                            canvas.drawLine(((i-1)*x_jump), y_start, i*x_jump, y2, greenPaint);
                        }else{
                            canvas.drawLine((i-1)*x_jump, y0, (i*x_jump)-(x_jump/2), y_start, redPaint);
                            canvas.drawLine(((i-1)*x_jump)+(x_jump/2), y_start, i*x_jump, y2, greenPaint);
                        }
                    }else if(y0 < y2){
                        if(y0 == y_start){
                            canvas.drawLine(((i-1)*x_jump), y_start, i*x_jump, y2, redPaint);
                        }else{
                            canvas.drawLine((i-1)*x_jump, y0, (i*x_jump)-(x_jump/2), y_start, greenPaint);
                            canvas.drawLine(((i-1)*x_jump)+(x_jump/2), y_start, i*x_jump, y2, redPaint);
                        }
                    }else{
                        canvas.drawLine((i-1)*x_jump, y0, (i*x_jump)-(x_jump/2), y_start, blackPaint);
                        canvas.drawLine(((i-1)*x_jump)+(x_jump/2), y_start, i*x_jump, y2, blackPaint);
                    }
                }
            }
            y_old += price/divider;
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }
}

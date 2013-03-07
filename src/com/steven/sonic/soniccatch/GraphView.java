package com.steven.sonic.soniccatch;



import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.view.SurfaceView;
import android.view.View;


public class GraphView extends View {

	public static boolean BAR = true;
	public static boolean LINE = false;

	private Paint paint;
	private Paint paintBg;
	private Paint paintPts;
	private Paint paintGbg;
	
	private float[] values;
	private float[] xValues;
	private String[] horlabels;
	private String[] verlabels;
	private String title;
	private boolean type;

	public GraphView(Context context, float[] xValues, float[] values, int[] earValues, String title, String[] horlabels, String[] verlabels, boolean type) {
		super(context);
		if (values == null)
			values = new float[0];
		else
			this.values = values;
		if (xValues == null)
			xValues = new float[0];
		else
			this.xValues = xValues;
		if (earValues == null)
			earValues = new int[0];
		else 
			this.earValues = earValues;
			
		if (title == null)
			title = "";
		else
			this.title = title;
		if (horlabels == null)
			this.horlabels = new String[0];
		else
			this.horlabels = horlabels;
		if (verlabels == null)
			this.verlabels = new String[0];
		else
			this.verlabels = verlabels;
		this.type = type;
		paintBg = new Paint();
		paint = new Paint();
		paintPts = new Paint();
		paintGbg = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		float border = 20;
		float horstart = border * 2;
		float height = getHeight();
		float width = getWidth() - 1;
		float max = getMax();
		float min = getMin();
		float diff = max - min;
		float graphheight = height - (2 * border);
		float graphwidth = width - (2 * border);
		
		float aHeight = height - border*2;
		float ySpacing = aHeight / 100;
		
		float aWidth = width - border*2;
		float xSpacing = aWidth / (horlabels.length - 1);
		
		//paintBg.setColor(0xff646669);
		paintBg.setColor(Color.BLACK);
		paintPts.setColor(0xff33b5e5);
		paintGbg.setColor(0x6411B9F7);
		
		paintEarBoth.setColor (0xff33b5e5); //holo blue
		paintEarLeft.setColor (0xff30BA02); //greenish
		paintEarRight.setColor (0xffFFFB00); //yellowish
		
		canvas.drawPaint(paintBg);

		paint.setTextAlign(Align.LEFT);
		int vers = verlabels.length - 1;
		for (int i = 0; i < verlabels.length; i++) {
			paint.setColor(Color.DKGRAY);
			float y = ((graphheight / vers) * i) + border;
			canvas.drawLine(horstart, y, width, y, paint);
			//Using Holo color theme
			paint.setColor(0xff33b5e5);
			canvas.drawText(verlabels[i], 0, y, paint);
		}
		int hors = horlabels.length - 1;
		for (int i = 0; i < horlabels.length; i++) {
			paint.setColor(Color.DKGRAY);
			float x = ((graphwidth / hors) * i) + horstart;
			canvas.drawLine(x, height - border, x, border, paint);
			paint.setTextAlign(Align.CENTER);
			if (i==horlabels.length-1)
				paint.setTextAlign(Align.RIGHT);
			if (i==0)
				paint.setTextAlign(Align.LEFT);
			//Using Holo color theme;
			paint.setColor(0xff33b5e5);
			canvas.drawText(horlabels[i], x, height - 4, paint);
		}

		paint.setTextAlign(Align.CENTER);
		canvas.drawText(title, (graphwidth / 2) + horstart, border - 4, paint);

		if (max != min) {
			paint.setColor(Color.LTGRAY);
			if (type == BAR) {
				/*float datalength = values.length;
				float colwidth = (width - (2 * border)) / datalength;
				for (int i = 0; i < values.length; i++) {
					float val = values[i] - min;
					float rat = val / diff;
					float h = graphheight * rat;
					canvas.drawRect((i * colwidth) + horstart, (border - h) + graphheight, ((i * colwidth) + horstart) + (colwidth - 1), height - (border - 1), paint);
				}*/
			} else {
				float datalength = values.length;
				float colwidth = (width - (2 * border)) / datalength;
				float halfcol = colwidth / 2;
				float lasth = 0;
				for (int i = 0; i < values.length; i++) {
					float val = values[i] - min;
					float rat = val / diff;
					float h = graphheight * rat;
					//if (i >= 0)
					//canvas.drawLine(((i - 1) * colwidth) + (horstart + 1) + halfcol, (border - lasth) + graphheight, (i * colwidth) + (horstart + 1) + halfcol, (border - h) + graphheight, paint);
					//Divide by 1000 to convert Hz to KHz
					
					//canvas.drawPoint(xSpacing*xValues[i]/1000 + border*2, height - (ySpacing*values[i] + border), paintPts);
					
					if (xValues[i] > 0) { // ONLY DRAW ON GRAPH IF X VALUE > 0. THIS IS TO AVOID NULL IN EMPTY ARRAY ELEMENTS
						if (i>0) {
							//draws the semi-transparent filling
							/*Path path = new Path();
							path.moveTo(xSpacing*xValues[i]/1000 + border*2, height - (ySpacing*values[i] + border));
	                        path.lineTo(xSpacing*xValues[i-1]/1000 + border*2, height - (ySpacing*values[i-1] + border));
	                        path.lineTo(xSpacing*xValues[i-1]/1000 + border*2, height-border);
	                        path.lineTo(xSpacing*xValues[i]/1000 + border*2, height-border);
	                        path.close();
							canvas.drawPath(path, paintGbg);*/
							
							
						}
						
						Paint temp = new Paint();
						if (earValue[i] == 0) temp = paintEarBoth;
						else if (earValue[i] == 1) temp = paintEarLeft;
						else if (earValue[i] == 2) temp = paintEarRight;
						//canvas.drawCircle( xSpacing*xValues[i]/1000 + border*2 , height - (ySpacing*values[i] + border) , 5 , paintPts);
						canvas.drawCircle( xSpacing*xValues[i]/1000 + border*2 , (ySpacing*values[i] + border) , 5 , temp);
						lasth = h;
					}
				}
			}
		}
	}

	private float getMax() {
		float largest = Integer.MIN_VALUE;
		for (int i = 0; i < values.length; i++)
			if (values[i] > largest)
				largest = values[i];
		return largest;
	}

	private float getMin() {
		float smallest = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++)
			if (values[i] < smallest)
				smallest = values[i];
		return smallest;
	}

}



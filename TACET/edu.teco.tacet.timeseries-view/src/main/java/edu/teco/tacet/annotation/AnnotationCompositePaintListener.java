package edu.teco.tacet.annotation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class AnnotationCompositePaintListener implements PaintListener {

	private String title;
	private int width;
	private int height;
	private int borderWidth = 1;
	public Color backgroundColor = new Color(Display.getCurrent(), new RGB(150, 150, 150));
	public Color borderColor = new Color(Display.getCurrent(), new RGB(0, 0, 0));
	public Color fontColor = new Color(Display.getCurrent(), new RGB(0, 0, 0));
	
	public final Color BORDER_COLOR_HIGHLIGHT = Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
	public final Color BORDER_COLOR_DEFAULT = new Color(Display.getCurrent(), new RGB(0, 0, 0));
	
	private int padding = 10;
	
	@Override
	public void paintControl(PaintEvent e) {
		e.gc.setBackground(backgroundColor);
		e.gc.setForeground(fontColor);
		
		if(title != null && height > 0 && width > 0) {
			drawBorder(e);
			drawBackground(e);
			drawTitle(e);
		}
	}
		
	private void drawBorder(PaintEvent e) {
		e.gc.setBackground(borderColor);
		e.gc.fillRoundRectangle(0, 0, width, height, padding, padding);
	}
	
	private void drawBackground(PaintEvent e) {
		e.gc.setBackground(backgroundColor);
		e.gc.fillRoundRectangle(borderWidth, borderWidth, width-2*borderWidth, height-2*borderWidth, padding-borderWidth, padding-borderWidth);

	}
	
	private void drawTitle(PaintEvent e) {
		e.gc.setForeground(fontColor);
		
		// if string is too long, shorten with ellipsis
		String text = shortenString(title, width - (2*padding), e);
    	
		// center String horizontally
		int textWidth = (int) e.gc.stringExtent(text).x;
		int textPositionX = (width - textWidth) / 2;
		
		// center String vertically
		int textHeight = (int) e.gc.stringExtent(text).y;
		int textPositionY = (height - textHeight) / 2;
	
		// draw
		e.gc.drawString(text, textPositionX, textPositionY, true);
	}
	
	private String shortenString(String input, int maxWidth, PaintEvent e) {
		// shorten if necessary, leave room for ellipsis
    	String result = cutOffString(input, maxWidth - e.gc.stringExtent("...").x, e);
    	
    	// add ellipsis
    	if (result.length() < input.length()) {
    		result = result + "...";
    	}
    	return result;
    }
	
	// TODO fix current behaviour which always replaces 3 letters with ...
	private String cutOffString(String input, int maxWidth, PaintEvent e) {
		String result = input;
		
		// TODO solution for very small annotations 
		if(e.gc.stringExtent(input).x > maxWidth) {
			if (input.length() < 3) {
				return "";
			}
			result = cutOffString(input.substring(0, input.length() - 2), maxWidth, e); // could cut off more than one letter per round
		}
		return result;
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	// currently unused method
	// tries to match borderColor and fontColor to backgroundColor
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
		
		// finding the right contrast color for font and border
		float[] hsb = color.getRGB().getHSB();
		if (hsb[2] > 0.5) {
			hsb[2] = (float) (hsb[2] - 0.4);
			borderColor = new Color(Display.getCurrent(), new RGB(hsb[0], hsb[1], hsb[2]));
			fontColor = new Color(Display.getCurrent(), new RGB(0, 0, 0));
		} else {
			hsb[2] = (float) (hsb[2] + 0.4);
			borderColor = new Color(Display.getCurrent(), new RGB(hsb[0], hsb[1], hsb[2]));
			fontColor = new Color(Display.getCurrent(), new RGB(255, 255, 255));
		}
	}
	
	public void setBorderColor(Color color) {
		borderColor = color;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}
}

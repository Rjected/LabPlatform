package lab.component;

import java.awt.Cursor;
import java.awt.Point;

import javax.swing.JPanel;


// this entire class is really messy :(
public class UserComponentResizing {

	private static UserComponentResizing currentlyResizing = null;
	private static UserComponentResizing currentlyHasHovering = null;
	private static JPanel panel;
	
	private static void setCursorToN() {
		panel.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
	}
	
	private static void setCursorToNW() {
		panel.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
	}
	
	private static void setCursorToW() {
		panel.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
	}
	
	private static void setCursorToSW() {
		panel.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
	}
	
	private static void setCursorToS() {
		panel.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
	}
	
	private static void setCursorToSE() {
		panel.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
	}
	
	private static void setCursorToE() {
		panel.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
	}
	
	private static void setCursorToNE() {
		panel.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
	}
	
	private static void resetCursor() {
		panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	

	private final LabComponent component;
	private int clickableAreaWidth = 5;
	private ClickableArea N_dragArea;
	private ClickableArea E_dragArea;
	private ClickableArea S_dragArea;
	private ClickableArea W_dragArea;
	private ClickableArea NE_dragArea;
	private ClickableArea NW_dragArea;
	private ClickableArea SW_dragArea;
	private ClickableArea SE_dragArea;
	private ClickableArea[] dragAreas = new ClickableArea[8];
	private int minWidth;
	private int minHeight;
	private boolean hasDrag = false;
	private boolean hasHover = false;

	public UserComponentResizing(LabComponent component, int minWidth, int minHeight) {
		this.component = component;
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		
		for (int i = 0; i < 8; i++) {
			dragAreas[i] = new ClickableArea(component);
		}

		N_dragArea = dragAreas[0];
		E_dragArea = dragAreas[1];
		S_dragArea = dragAreas[2];
		W_dragArea = dragAreas[3];
		NE_dragArea = dragAreas[4];
		NW_dragArea = dragAreas[5];
		SW_dragArea = dragAreas[6];
		SE_dragArea = dragAreas[7];

	}

	public int getClickableAreaWidth() {
		return clickableAreaWidth;
	}

	public void setClickableAreaWidth(int clickableAreaWidth) {
		this.clickableAreaWidth = clickableAreaWidth;
	}

	public void enableNDrag(boolean on) {
		N_dragArea.setEnabled(on);
	}

	public void enableSDrag(boolean on) {
		S_dragArea.setEnabled(on);
	}

	public void enableEDrag(boolean on) {
		E_dragArea.setEnabled(on);
	}

	public void enableWDrag(boolean on) {
		W_dragArea.setEnabled(on);
	}

	public void enableNEDrag(boolean on) {
		NE_dragArea.setEnabled(on);
	}

	public void enableSEDrag(boolean on) {
		SE_dragArea.setEnabled(on);
	}

	public void enableNWDrag(boolean on) {
		NW_dragArea.setEnabled(on);
	}

	public void enableSWDrag(boolean on) {
		SW_dragArea.setEnabled(on);
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public boolean hasDrag() {
		return hasDrag;
	}

	private int clickHeight = -1, clickWidth = -1, clickOffsetX = -1, clickOffsetY = -1;

	public void check(int x, int y, int width, int height) {
		if (currentlyResizing != null) {
			if (currentlyResizing != this) {
				if (currentlyResizing.component.getZOrder() > component.getZOrder()) {
					return;
				}
			}
		}
		
		Point p = N_dragArea.getMousePosition();
		
		if (p != null) {
			if (component.getRoot().isPointCovered(p.x, p.y, component.getZOrder())) {
				return;
			}
		}
		

		NE_dragArea.checkRaw(x + width - clickableAreaWidth, y - clickableAreaWidth, 2 * clickableAreaWidth,
				clickableAreaWidth * 2);
		N_dragArea.checkRaw(x + clickableAreaWidth, y - clickableAreaWidth, width - 2 * clickableAreaWidth,
				clickableAreaWidth * 2);
		NW_dragArea.checkRaw(x - clickableAreaWidth, y - clickableAreaWidth, 2 * clickableAreaWidth,
				clickableAreaWidth * 2);
		W_dragArea.checkRaw(x - clickableAreaWidth, y + clickableAreaWidth, clickableAreaWidth * 2,
				height - clickableAreaWidth * 2);
		SW_dragArea.checkRaw(x - clickableAreaWidth, y + height - clickableAreaWidth, clickableAreaWidth * 2,
				clickableAreaWidth * 2);
		S_dragArea.checkRaw(x + clickableAreaWidth, y + height - clickableAreaWidth, width - 2 * clickableAreaWidth,
				clickableAreaWidth * 2);
		SE_dragArea.checkRaw(x + width - clickableAreaWidth, y + height - clickableAreaWidth, clickableAreaWidth * 2,
				clickableAreaWidth * 2);
		E_dragArea.checkRaw(x + width - clickableAreaWidth, y + clickableAreaWidth, clickableAreaWidth * 2,
				height - clickableAreaWidth * 2);

		hasDrag = false;
		
		for (ClickableArea a : dragAreas) {
			if (a.hasClick()) {
				hasDrag = true;
				break;
			}
		}
		
		if (hasDrag) {
			if (currentlyResizing != null) {
				if (currentlyResizing != this) {
					if (currentlyResizing.component.getZOrder() < component.getZOrder()) {
						currentlyResizing.hasDrag = false;
						currentlyResizing = this;
					} else {
						hasDrag = false;
						return;
					}
				}
			} else {
				currentlyResizing = this;
			}
		}
		
		checkForHovering();
		
		if (NE_dragArea.hasClick()) {
			setClickState(width, height);

			component.setWidth(NE_dragArea.getDragDelta().x + clickWidth);
			component.setHeight(-NE_dragArea.getDragDelta().y + clickHeight);

			component.setOffsetY(NE_dragArea.getDragDelta().y + clickOffsetY);

			checkDimensions(false, true);
			
			setCursorToNE();

		} else if (N_dragArea.hasClick()) {
			setClickState(width, height);

			component.setHeight(-N_dragArea.getDragDelta().y + clickHeight);

			component.setOffsetY(N_dragArea.getDragDelta().y + clickOffsetY);

			checkDimensions(false, true);
			
			setCursorToN();

		} else if (NW_dragArea.hasClick()) {
			setClickState(width, height);

			component.setWidth(-NW_dragArea.getDragDelta().x + clickWidth);
			component.setHeight(-NW_dragArea.getDragDelta().y + clickHeight);

			component.setOffsetX(NW_dragArea.getDragDelta().x + clickOffsetX);
			component.setOffsetY(NW_dragArea.getDragDelta().y + clickOffsetY);

			checkDimensions(true, true);
			
			setCursorToNW();

		} else if (W_dragArea.hasClick()) {
			setClickState(width, height);

			component.setWidth(-W_dragArea.getDragDelta().x + clickWidth);

			component.setOffsetX(W_dragArea.getDragDelta().x + clickOffsetX);

			checkDimensions(true, false);
			
			setCursorToW();

		} else if (SW_dragArea.hasClick()) {
			setClickState(width, height);

			component.setWidth(-SW_dragArea.getDragDelta().x + clickWidth);
			component.setHeight(SW_dragArea.getDragDelta().y + clickHeight);

			component.setOffsetX(SW_dragArea.getDragDelta().x + clickOffsetX);

			checkDimensions(true, false);
			
			setCursorToSW();

		} else if (S_dragArea.hasClick()) {
			setClickState(width, height);

			component.setHeight(S_dragArea.getDragDelta().y + clickHeight);

			checkDimensions();
			
			setCursorToS();

		} else if (SE_dragArea.hasClick()) {
			setClickState(width, height);

			component.setWidth(SE_dragArea.getDragDelta().x + clickWidth);
			component.setHeight(SE_dragArea.getDragDelta().y + clickHeight);

			checkDimensions();
			
			setCursorToSE();

		} else if (E_dragArea.hasClick()) {
			setClickState(width, height);

			component.setWidth(E_dragArea.getDragDelta().x + clickWidth);

			checkDimensions();
			
			setCursorToE();

		} else {
			clickWidth = -1;
			clickHeight = -1;
			clickOffsetX = -1;
			clickOffsetY = -1;
			
			if (currentlyResizing != null) {
				if (currentlyResizing == this) {
					currentlyResizing = null;
				}
			}

		}

	}

	private void checkForHovering() {
		hasHover = false;
		
		for (ClickableArea a : dragAreas) {
			if (a.hasHover()) {
				hasHover = true;
				break;
			}
		}
		
		if (hasHover) {
			if (currentlyHasHovering != null) {
				if (currentlyHasHovering != this) {
					if (currentlyHasHovering.component.getZOrder() < component.getZOrder()) {
						currentlyHasHovering.hasHover = false;
						currentlyHasHovering = this;
					} else {
						hasHover = false;
						return;
					}
				}
			} else {
				currentlyHasHovering = this;
			}
		}
		
		if (NE_dragArea.hasHover()) {
			setCursorToNE();
		} else if (N_dragArea.hasHover()) {
			setCursorToN();
		} else if (NW_dragArea.hasHover()) {
			setCursorToNW();
		} else if (W_dragArea.hasHover()) {
			setCursorToW();
		} else if (SW_dragArea.hasHover()) {
			setCursorToSW();
		} else if (S_dragArea.hasHover()) {
			setCursorToS();
		} else if (SE_dragArea.hasHover()) {
			setCursorToSE();
		} else if (E_dragArea.hasHover()) {
			setCursorToE();
		} else {
			hasHover = false;
			
			if (currentlyHasHovering != null) {
				if (currentlyHasHovering == this) {
					currentlyHasHovering = null;
					resetCursor();
				}
			} else {
				resetCursor();
			}
		}
	}
	
	private void checkDimensions(boolean x, boolean y) {
		if (component.getWidth() < minWidth) {
			component.setWidth(minWidth);

			if (x) {
				component.setOffsetX(clickOffsetX + clickWidth - minWidth);
			}
		}

		if (component.getHeight() < minHeight) {
			component.setHeight(minHeight);

			if (y) {
				component.setOffsetY(clickOffsetY + clickHeight - minHeight);
			}
		}
	}

	private void checkDimensions() {
		checkDimensions(false, false);
	}

	private void setClickState(int w, int h) {
		clickWidth = clickWidth == -1 ? w : clickWidth;
		clickHeight = clickHeight == -1 ? h : clickHeight;
		clickOffsetX = clickOffsetX == -1 ? component.getOffsetX() : clickOffsetX;
		clickOffsetY = clickOffsetY == -1 ? component.getOffsetY() : clickOffsetY;
	}

	public void initializeMouseListeners(JPanel p) {
		if (panel == null) {
			panel = p;
		}
		
		for (ClickableArea a : dragAreas) {
			a.initializeMouseListeners(panel);
		}
	}

}

package lab.component;

import javax.swing.JPanel;

public class UserComponentResizing {
	
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

	public UserComponentResizing(LabComponent component) {
		this.component = component;

		toggleNDrag(true);
		toggleNEDrag(true);
		toggleNWDrag(true);
		toggleSDrag(true);
		toggleSEDrag(true);
		toggleSWDrag(true);
		toggleWDrag(true);
		toggleEDrag(true);

	}

	public int getClickableAreaWidth() {
		return clickableAreaWidth;
	}

	public void setClickableAreaWidth(int clickableAreaWidth) {
		this.clickableAreaWidth = clickableAreaWidth;
	}

	public void toggleNDrag(boolean on) {
		N_dragArea = on ? new ClickableArea(component) : null;
	}

	public void toggleSDrag(boolean on) {
		S_dragArea = on ? new ClickableArea(component) : null;
	}

	public void toggleEDrag(boolean on) {
		E_dragArea = on ? new ClickableArea(component) : null;
	}

	public void toggleWDrag(boolean on) {
		W_dragArea = on ? new ClickableArea(component) : null;
	}

	public void toggleNEDrag(boolean on) {
		NE_dragArea = on ? new ClickableArea(component) : null;
	}

	public void toggleSEDrag(boolean on) {
		SE_dragArea = on ? new ClickableArea(component) : null;
	}

	public void toggleNWDrag(boolean on) {
		NW_dragArea = on ? new ClickableArea(component) : null;
	}

	public void toggleSWDrag(boolean on) {
		SW_dragArea = on ? new ClickableArea(component) : null;
	}
	
	private int clickHeight = -1, clickWidth = -1, clickOffsetX = -1, clickOffsetY = -1;
	
	public void check(int x, int y, int width, int height) {
		NE_dragArea.checkRaw(x + width - clickableAreaWidth, y - clickableAreaWidth, 2 * clickableAreaWidth, clickableAreaWidth * 2);
		N_dragArea.checkRaw(x + clickableAreaWidth, y - clickableAreaWidth, width - 2 * clickableAreaWidth, clickableAreaWidth * 2);
		NW_dragArea.checkRaw(x - clickableAreaWidth, y - clickableAreaWidth, 2 * clickableAreaWidth, clickableAreaWidth * 2);
		W_dragArea.checkRaw(x - clickableAreaWidth, y + clickableAreaWidth, clickableAreaWidth * 2, height - clickableAreaWidth * 2);
		SW_dragArea.checkRaw(x - clickableAreaWidth, y + height - clickableAreaWidth, clickableAreaWidth * 2, clickableAreaWidth * 2);
		S_dragArea.checkRaw(x + clickableAreaWidth, y + height - clickableAreaWidth, width - 2 * clickableAreaWidth, clickableAreaWidth * 2);
		SE_dragArea.checkRaw(x + width - clickableAreaWidth, y + height - clickableAreaWidth, clickableAreaWidth * 2, clickableAreaWidth * 2);
		E_dragArea.checkRaw(x + width - clickableAreaWidth, y + clickableAreaWidth, clickableAreaWidth * 2, height - clickableAreaWidth * 2);
		
		
		if (NE_dragArea.hasClick()) {
			setClickState(width, height);
			
			component.setWidth(NE_dragArea.getDragDelta().x + clickWidth);
			component.setHeight(-NE_dragArea.getDragDelta().y + clickHeight);
			
			component.setOffsetY(NE_dragArea.getDragDelta().y + clickOffsetY);
			
			
		} else if (N_dragArea.hasClick()) {
			setClickState(width, height);
			
			component.setHeight(-N_dragArea.getDragDelta().y + clickHeight);
			
			component.setOffsetY(N_dragArea.getDragDelta().y + clickOffsetY);
			
			
		} else if (NW_dragArea.hasClick()) {
			setClickState(width, height);
			
			component.setWidth(-NW_dragArea.getDragDelta().x + clickWidth);
			component.setHeight(-NW_dragArea.getDragDelta().y + clickHeight);
			
			component.setOffsetX(NW_dragArea.getDragDelta().x + clickOffsetX);
			component.setOffsetY(NW_dragArea.getDragDelta().y + clickOffsetY);
			
			
		} else if (W_dragArea.hasClick()) {
			setClickState(width, height);
			
			component.setWidth(-W_dragArea.getDragDelta().x + clickWidth);
			
			component.setOffsetX(W_dragArea.getDragDelta().x + clickOffsetX);
			
		} else if (SW_dragArea.hasClick()) {
			setClickState(width, height);
			
			component.setWidth(-SW_dragArea.getDragDelta().x + clickWidth);
			component.setHeight(SW_dragArea.getDragDelta().y + clickHeight);
			
			component.setOffsetX(SW_dragArea.getDragDelta().x + clickOffsetX);
			
		} else if (S_dragArea.hasClick()) {
			setClickState(width, height);
			
			component.setHeight(S_dragArea.getDragDelta().y + clickHeight);
			
		} else if (SE_dragArea.hasClick()) {
			setClickState(width, height);
			
			component.setWidth(SE_dragArea.getDragDelta().x + clickWidth);
			component.setHeight(SE_dragArea.getDragDelta().y + clickHeight);
			
		} else if (E_dragArea.hasClick()) {
			setClickState(width, height);
			
			component.setWidth(E_dragArea.getDragDelta().x + clickWidth);
			
		} else {
			clickWidth = -1;
			clickHeight = -1;
			clickOffsetX = -1;
			clickOffsetY = -1;
		}
		
		
	}
	
	private void setClickState(int w, int h) {
		clickWidth = clickWidth == -1 ? w : clickWidth;
		clickHeight = clickHeight == -1 ? h : clickHeight;
		clickOffsetX = clickOffsetX == -1 ? component.getOffsetX() : clickOffsetX;
		clickOffsetY = clickOffsetY == -1 ? component.getOffsetY() : clickOffsetY;
	}
	
	public void initializeMouseListeners(JPanel panel) {
		N_dragArea.initializeMouseListeners(panel);
		E_dragArea.initializeMouseListeners(panel);
		S_dragArea.initializeMouseListeners(panel);
		W_dragArea.initializeMouseListeners(panel);
		NE_dragArea.initializeMouseListeners(panel);
		NW_dragArea.initializeMouseListeners(panel);
		SW_dragArea.initializeMouseListeners(panel);
		SE_dragArea.initializeMouseListeners(panel);
	}

}

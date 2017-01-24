package editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import lab.component.ClickableArea;
import lab.component.EmptyComponent;
import lab.component.LabComponent;
import lab.component.UserComponentResizing;
import lab.component.input.ButtonComponent;

public class EditorWindow extends LabComponent {
	
	private static final int DRAG_BAR_HEIGHT = 20;
	private static final int RESIZE_DRAG_AREA_SIZE = 5;
	
	
	private String name;
	private final LabComponent content;
	private final LabComponent dragBar;
	private final ButtonComponent closeButton;
	private ClickableArea dragBarDragArea;
	private final UserComponentResizing resizing = new UserComponentResizing(this, 20, 20);
	
	public EditorWindow(String name, int width, int height) {
		super(width, height);
		
		this.name = name;
		
		dragBarDragArea = new ClickableArea(this);
		
		
		dragBar = new EmptyComponent(width, DRAG_BAR_HEIGHT);
		dragBar.setOffsetY(-DRAG_BAR_HEIGHT);
		dragBar.setScaleChildren(false);
		
		final EditorWindow t = this;
		
		closeButton = new ButtonComponent(20, 20, "X") {
			@Override
			public void doSomething() {
				t.setVisible(false);
			}
		};
		
		closeButton.setOffsetX(width - closeButton.getWidth());
		closeButton.setOffsetY(0);
		
		dragBar.addChild(closeButton);
		
		content = new EmptyComponent(width, height);
		
		addChild(dragBar);
		addChild(content);
		
		setLayout(FREE_FORM);
		
		resizing.setClickableAreaWidth(RESIZE_DRAG_AREA_SIZE);
		resizing.enableNDrag(false);
		resizing.enableNEDrag(false);
		resizing.enableNWDrag(false);
		
		
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LabComponent getContent() {
		return content;
	}
	
	public int getMinWidth() {
		return resizing.getMinWidth();
	}
	
	public void setMinWidth(int minWidth) {
		resizing.setMinWidth(minWidth);
	}

	public int getMinHeight() {
		return resizing.getMinHeight();
	}

	public void setMinHeight(int minHeight) {
		resizing.setMinHeight(minHeight);
	}

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y - DRAG_BAR_HEIGHT, width, DRAG_BAR_HEIGHT);
		
		g.setColor(Color.white);
		drawCenteredString(g, name, x + width / 2, y - DRAG_BAR_HEIGHT / 3);
		
		g.setColor(Color.gray);
		g.drawRect(x, y, width, height);
		
		closeButton.setOffsetX(width - closeButton.getWidth());
		
		dragBarDragArea.checkRaw(x, y - DRAG_BAR_HEIGHT, width, DRAG_BAR_HEIGHT);
		
		if (dragBarDragArea.hasClick()) {
			
			Point newOffset = new Point();
			newOffset.x = dragBarDragArea.getMousePosition().x + dragBarDragArea.getClickRelativeToPosition().x;
			newOffset.y = dragBarDragArea.getMousePosition().y + dragBarDragArea.getClickRelativeToPosition().y + DRAG_BAR_HEIGHT;
			
			if (newOffset.x != getOffsetX() || newOffset.y != getOffsetY()) {
				setOffsetX(newOffset.x);
				setOffsetY(newOffset.y);
			}
			
		}
		
		resizing.check(x, y, width, height);
	}

	@Override
	public void initJPanel(JPanel panel) {
		dragBarDragArea.initializeMouseListeners(panel);
		resizing.initializeMouseListeners(panel);
	}
	
	
	
	
	
	

}

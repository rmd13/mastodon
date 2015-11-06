package net.trackmate.revised.bdv.overlay;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseOverListener extends MouseAdapter
{
	private final OverlayGraphRenderer< ?, ? > graphOverlay;

	public MouseOverListener( final OverlayGraphRenderer< ?, ? > graphOverlay )
	{
		this.graphOverlay = graphOverlay;
	}

	@Override
	public void mouseExited( final MouseEvent e )
	{
		// TODO Auto-generated method stub
		super.mouseExited( e );
	}

	@Override
	public void mouseMoved( final MouseEvent e )
	{
		final int x = e.getX();
		final int y = e.getY();
		graphOverlay.mouseOverHighlight( x, y );
	}
}
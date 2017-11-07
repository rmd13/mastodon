package org.mastodon.revised.trackscheme.display.laf;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collection;

public class TrackSchemeStyle
{
	private static final Stroke DEFAULT_FOCUS_STROKE = new BasicStroke( 2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, new float[] { 8f, 3f }, 0 );

	private static final Stroke DEFAULT_GHOST_STROKE = new BasicStroke( 1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] { 3.0f }, 0.0f );

	private String name;

	private Color edgeColor;

	private Color vertexFillColor;

	private Color vertexDrawColor;

	private Color selectedVertexFillColor;

	private Color selectedEdgeColor;

	private Color selectedVertexDrawColor;

	private Color simplifiedVertexFillColor;

	private Color selectedSimplifiedVertexFillColor;

	private Color ghostEdgeColor;

	private Color ghostVertexFillColor;

	private Color ghostVertexDrawColor;

	private Color ghostSelectedVertexFillColor;

	private Color ghostSelectedEdgeColor;

	private Color ghostSelectedVertexDrawColor;

	private Color ghostSimplifiedVertexFillColor;

	private Color ghostSelectedSimplifiedVertexFillColor;

	private Color backgroundColor;

	private Color currentTimepointColor;

	private Color decorationColor;

	private Color vertexRangeColor;

	private Color headerBackgroundColor;

	private Color headerDecorationColor;

	private Color headerCurrentTimepointColor;

	private Font font;

	private Font headerFont;

	private Stroke edgeStroke;

	private Stroke edgeGhostStroke;

	private Stroke edgeHighlightStroke;

	private Stroke vertexStroke;

	private Stroke vertexGhostStroke;

	private Stroke vertexHighlightStroke;

	private Stroke focusStroke;

	private Stroke decorationStroke;

	private boolean highlightCurrentTimepoint;

	private boolean paintRows;

	private boolean paintColumns;

	private boolean paintHeaderShadow;

	static Color mixGhostColor( final Color color, final Color backgroundColor )
	{
		return ( color == null || backgroundColor == null )
				? null
				: new Color(
						( color.getRed() + backgroundColor.getRed() ) / 2,
						( color.getGreen() + backgroundColor.getGreen() ) / 2,
						( color.getBlue() + backgroundColor.getBlue() ) / 2,
						color.getAlpha() );
	}

	private void updateGhostColors()
	{
		ghostEdgeColor = mixGhostColor( edgeColor, backgroundColor );
		ghostVertexFillColor = mixGhostColor( vertexFillColor, backgroundColor );
		ghostVertexDrawColor = mixGhostColor( vertexDrawColor, backgroundColor );
		ghostSelectedVertexFillColor = mixGhostColor( selectedVertexFillColor, backgroundColor );
		ghostSelectedEdgeColor = mixGhostColor( selectedEdgeColor, backgroundColor );
		ghostSelectedVertexDrawColor = mixGhostColor( selectedVertexDrawColor, backgroundColor );
		ghostSimplifiedVertexFillColor = mixGhostColor( simplifiedVertexFillColor, backgroundColor );
		ghostSelectedSimplifiedVertexFillColor = mixGhostColor( selectedSimplifiedVertexFillColor, backgroundColor );
	}

	/*
	 * GETTERS for non public fields.
	 */

	public String getName()
	{
		return name;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public Color getCurrentTimepointColor()
	{
		return currentTimepointColor;
	}

	public Color getDecorationColor()
	{
		return decorationColor;
	}

	public Stroke getDecorationStroke()
	{
		return decorationStroke;
	}

	public Color getEdgeColor()
	{
		return edgeColor;
	}

	public Stroke getEdgeGhostStroke()
	{
		return edgeGhostStroke;
	}

	public Stroke getEdgeHighlightStroke()
	{
		return edgeHighlightStroke;
	}

	public Stroke getEdgeStroke()
	{
		return edgeStroke;
	}

	public Stroke getFocusStroke()
	{
		return focusStroke;
	}

	public Font getFont()
	{
		return font;
	}

	public Color getGhostEdgeColor()
	{
		return ghostEdgeColor;
	}

	public Color getGhostSelectedEdgeColor()
	{
		return ghostSelectedEdgeColor;
	}

	public Color getGhostSelectedSimplifiedVertexFillColor()
	{
		return ghostSelectedSimplifiedVertexFillColor;
	}

	public Color getGhostSelectedVertexDrawColor()
	{
		return ghostSelectedVertexDrawColor;
	}

	public Color getGhostSelectedVertexFillColor()
	{
		return ghostSelectedVertexFillColor;
	}

	public Color getGhostSimplifiedVertexFillColor()
	{
		return ghostSimplifiedVertexFillColor;
	}

	public Color getGhostVertexDrawColor()
	{
		return ghostVertexDrawColor;
	}

	public Color getGhostVertexFillColor()
	{
		return ghostVertexFillColor;
	}

	public Color getHeaderBackgroundColor()
	{
		return headerBackgroundColor;
	}

	public Color getHeaderCurrentTimepointColor()
	{
		return headerCurrentTimepointColor;
	}

	public Color getHeaderDecorationColor()
	{
		return headerDecorationColor;
	}

	public Font getHeaderFont()
	{
		return headerFont;
	}

	public Color getSelectedEdgeColor()
	{
		return selectedEdgeColor;
	}

	public Color getSelectedSimplifiedVertexFillColor()
	{
		return selectedSimplifiedVertexFillColor;
	}

	public Color getSelectedVertexDrawColor()
	{
		return selectedVertexDrawColor;
	}

	public Color getSelectedVertexFillColor()
	{
		return selectedVertexFillColor;
	}

	public Color getVertexDrawColor()
	{
		return vertexDrawColor;
	}

	public Color getVertexFillColor()
	{
		return vertexFillColor;
	}

	public Color getSimplifiedVertexFillColor()
	{
		return simplifiedVertexFillColor;
	}

	public Stroke getVertexGhostStroke()
	{
		return vertexGhostStroke;
	}

	public Stroke getVertexHighlightStroke()
	{
		return vertexHighlightStroke;
	}

	public Color getVertexRangeColor()
	{
		return vertexRangeColor;
	}

	public Stroke getVertexStroke()
	{
		return vertexStroke;
	}

	public boolean isPaintColumns()
	{
		return paintColumns;
	}

	public boolean isHighlightCurrentTimepoint()
	{
		return highlightCurrentTimepoint;
	}

	public boolean isPaintHeaderShadow()
	{
		return paintHeaderShadow;
	}

	public boolean isPaintRows()
	{
		return paintRows;
	}

	/*
	 * SETTERS
	 */

	public TrackSchemeStyle name( final String name )
	{
		if ( this.name != name )
		{
			this.name = name;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle edgeColor( final Color color )
	{
		if ( !this.edgeColor.equals( color ) )
		{
			this.edgeColor = color;
			updateGhostColors();
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle vertexFillColor( final Color color )
	{
		if ( !this.vertexFillColor.equals( color ) )
		{
			this.vertexFillColor = color;
			updateGhostColors();
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle vertexDrawColor( final Color color )
	{
		if ( !this.vertexDrawColor.equals( color ) )
		{
			this.vertexDrawColor = color;
			updateGhostColors();
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle selectedVertexFillColor( final Color color )
	{
		if ( !this.selectedVertexFillColor.equals( color ) )
		{
			this.selectedVertexFillColor = color;
			updateGhostColors();
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle selectedEdgeColor( final Color color )
	{
		if ( !this.selectedEdgeColor.equals( color ) )
		{
			this.selectedEdgeColor = color;
			updateGhostColors();
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle selectedVertexDrawColor( final Color color )
	{
		if ( !this.selectedVertexDrawColor.equals( color ) )
		{
			this.selectedVertexDrawColor = color;
			updateGhostColors();
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle simplifiedVertexFillColor( final Color color )
	{
		if ( !this.simplifiedVertexFillColor.equals( color ) )
		{
			this.simplifiedVertexFillColor = color;
			updateGhostColors();
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle selectedSimplifiedVertexFillColor( final Color color )
	{
		if ( !this.selectedSimplifiedVertexFillColor.equals( color ) )
		{
			this.selectedSimplifiedVertexFillColor = color;
			updateGhostColors();
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle backgroundColor( final Color color )
	{
		if ( !this.backgroundColor.equals( color ) )
		{
			this.backgroundColor = color;
			updateGhostColors();
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle currentTimepointColor( final Color color )
	{
		if ( !this.currentTimepointColor.equals( color ) )
		{
			this.currentTimepointColor = color;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle decorationColor( final Color color )
	{
		if ( !this.decorationColor.equals( color ) )
		{
			this.decorationColor = color;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle vertexRangeColor( final Color color )
	{
		if ( !this.vertexRangeColor.equals( color ) )
		{
			this.vertexRangeColor = color;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle headerBackgroundColor( final Color color )
	{
		if ( !this.headerBackgroundColor.equals( color ) )
		{
			this.headerBackgroundColor = color;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle headerDecorationColor( final Color color )
	{
		if ( !this.headerDecorationColor.equals( color ) )
		{
			this.headerDecorationColor = color;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle headerCurrentTimepointColor( final Color color )
	{
		if ( !this.headerCurrentTimepointColor.equals( color ) )
		{
			this.headerCurrentTimepointColor = color;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle font( final Font font )
	{
		if ( !this.font.equals( font ) )
		{
			this.font = font;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle headerFont( final Font font )
	{
		if ( !this.headerFont.equals( font ) )
		{
			this.headerFont = font;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle edgeStroke( final Stroke stroke )
	{
		if ( !this.edgeStroke.equals( stroke ) )
		{
			this.edgeStroke = stroke;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle edgeGhostStroke( final Stroke stroke )
	{
		if ( !this.edgeGhostStroke.equals( stroke ) )
		{
			this.edgeGhostStroke = stroke;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle edgeHighlightStroke( final Stroke stroke )
	{
		if ( !this.edgeHighlightStroke.equals( stroke ) )
		{
			this.edgeHighlightStroke = stroke;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle vertexStroke( final Stroke stroke )
	{
		if ( !this.vertexStroke.equals( stroke ) )
		{
			this.vertexStroke = stroke;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle vertexGhostStroke( final Stroke stroke )
	{
		if ( !this.vertexGhostStroke.equals( stroke ) )
		{
			this.vertexGhostStroke = stroke;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle vertexHighlightStroke( final Stroke stroke )
	{
		if ( !this.vertexHighlightStroke.equals( stroke ) )
		{
			this.vertexHighlightStroke = stroke;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle focusStroke( final Stroke stroke )
	{
		if ( !this.focusStroke.equals( stroke ) )
		{
			this.focusStroke = stroke;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle decorationStroke( final Stroke stroke )
	{
		if ( !this.decorationStroke.equals( stroke ) )
		{
			this.decorationStroke = stroke;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle highlightCurrentTimepoint( final boolean b )
	{
		if ( this.highlightCurrentTimepoint != b )
		{
			this.highlightCurrentTimepoint = b;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle paintRows( final boolean b )
	{
		if ( this.paintRows != b )
		{
			this.paintRows = b;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle paintColumns( final boolean b )
	{
		if ( this.paintColumns != b )
		{
			this.paintColumns = b;
			notifyListeners();
		}
		return this;
	}

	public TrackSchemeStyle paintHeaderShadow( final boolean b )
	{
		if ( this.paintHeaderShadow != b )
		{
			this.paintHeaderShadow = b;
			notifyListeners();
		}
		return this;
	}

	private TrackSchemeStyle()
	{
		updateListeners = new ArrayList<>();
	}

	@Override
	public String toString()
	{
		return name;
	}

	public synchronized void set( final TrackSchemeStyle style )
	{
		this.name = style.name;
		this.edgeColor = style.edgeColor;
		this.vertexFillColor = style.vertexFillColor;
		this.vertexDrawColor = style.vertexDrawColor;
		this.selectedVertexFillColor = style.selectedVertexFillColor;
		this.selectedEdgeColor = style.selectedEdgeColor;
		this.selectedVertexDrawColor = style.selectedVertexDrawColor;
		this.simplifiedVertexFillColor = style.simplifiedVertexFillColor;
		this.selectedSimplifiedVertexFillColor = style.selectedSimplifiedVertexFillColor;
		this.ghostEdgeColor = style.ghostEdgeColor;
		this.ghostVertexFillColor = style.ghostVertexFillColor;
		this.ghostVertexDrawColor = style.ghostVertexDrawColor;
		this.ghostSelectedVertexFillColor = style.ghostSelectedVertexFillColor;
		this.ghostSelectedEdgeColor = style.ghostSelectedEdgeColor;
		this.ghostSelectedVertexDrawColor = style.ghostSelectedVertexDrawColor;
		this.ghostSimplifiedVertexFillColor = style.ghostSimplifiedVertexFillColor;
		this.ghostSelectedSimplifiedVertexFillColor = style.ghostSelectedSimplifiedVertexFillColor;
		this.backgroundColor = style.backgroundColor;
		this.currentTimepointColor = style.currentTimepointColor;
		this.decorationColor = style.decorationColor;
		this.vertexRangeColor = style.vertexRangeColor;
		this.headerBackgroundColor = style.headerBackgroundColor;
		this.headerDecorationColor = style.headerDecorationColor;
		this.headerCurrentTimepointColor = style.headerCurrentTimepointColor;
		this.font = style.font;
		this.headerFont = style.headerFont;
		this.edgeStroke = style.edgeStroke;
		this.edgeGhostStroke = style.edgeGhostStroke;
		this.edgeHighlightStroke = style.edgeHighlightStroke;
		this.vertexStroke = style.vertexStroke;
		this.vertexGhostStroke = style.vertexGhostStroke;
		this.vertexHighlightStroke = style.vertexHighlightStroke;
		this.focusStroke = style.focusStroke;
		this.decorationStroke = style.decorationStroke;
		this.highlightCurrentTimepoint = style.highlightCurrentTimepoint;
		this.paintRows = style.paintRows;
		this.paintColumns = style.paintColumns;
		this.paintHeaderShadow = style.paintHeaderShadow;
		notifyListeners();
	}

	public interface UpdateListener
	{
		public void trackSchemeStyleChanged();
	}

	private final ArrayList< UpdateListener > updateListeners;

	private void notifyListeners()
	{
		final ArrayList< UpdateListener > ul = new ArrayList<>( updateListeners ); // TODO: bake into Listeners class
		for ( final UpdateListener l : ul )
			l.trackSchemeStyleChanged();
	}

	public synchronized boolean addUpdateListener( final UpdateListener l )
	{
		if ( !updateListeners.contains( l ) )
		{
			updateListeners.add( l );
			return true;
		}
		return false;
	}

	public synchronized boolean removeUpdateListener( final UpdateListener l )
	{
		return updateListeners.remove( l );
	}

	/**
	 * Returns a new style instance, copied from this style.
	 *
	 * @param name
	 *            the name for the copied style.
	 * @return a new style instance.
	 */
	public TrackSchemeStyle copy( final String name )
	{
		final TrackSchemeStyle newStyle = new TrackSchemeStyle();
		newStyle.set( this );
		newStyle.name = name;
		return newStyle;
	}

	/**
	 * Returns the default TrackScheme style instance. Editing this instance
	 * will affect all view using this style.
	 *
	 * @return the single common instance for the default style.
	 */
	public static TrackSchemeStyle defaultStyle()
	{
		return df;
	}

	private static final TrackSchemeStyle df;
	static
	{
		final Color fill = new Color( 128, 255, 128 );
		df = new TrackSchemeStyle();
		df.name = "Default";
		df.backgroundColor = Color.LIGHT_GRAY;
		df.currentTimepointColor = new Color( 217, 217, 217 );
		df.vertexFillColor = Color.WHITE;
		df.selectedVertexFillColor = fill;
		df.simplifiedVertexFillColor = Color.BLACK;
		df.selectedSimplifiedVertexFillColor = new Color( 0, 128, 0 );
		df.vertexDrawColor = Color.BLACK;
		df.selectedVertexDrawColor = Color.BLACK;
		df.edgeColor = Color.BLACK;
		df.selectedEdgeColor = fill.darker();
		df.decorationColor = Color.YELLOW.darker().darker();
		df.vertexRangeColor = new Color( 128, 128, 128 );
		df.headerBackgroundColor = new Color( 217, 217, 217 );
		df.headerDecorationColor = Color.DARK_GRAY;
		df.headerCurrentTimepointColor = Color.WHITE;
		df.font = new Font( "SansSerif", Font.PLAIN, 9 );
		df.headerFont = new Font( "SansSerif", Font.PLAIN, 9 );
		df.edgeStroke = new BasicStroke();
		df.edgeGhostStroke = DEFAULT_GHOST_STROKE;
		df.edgeHighlightStroke = new BasicStroke( 2f );
		df.vertexStroke = new BasicStroke();
		df.vertexGhostStroke = DEFAULT_GHOST_STROKE;
		df.vertexHighlightStroke = new BasicStroke( 3f );
		df.focusStroke = DEFAULT_FOCUS_STROKE;
		df.decorationStroke = new BasicStroke();
		df.highlightCurrentTimepoint = true;
		df.paintRows = true;
		df.paintColumns = true;
		df.paintHeaderShadow = true;
	}

	/**
	 * Returns the modern TrackScheme style instance. Editing this instance will
	 * affect all view using this style.
	 *
	 * @return the single common instance for the modern style.
	 */
	public static TrackSchemeStyle modernStyle()
	{
		return modern;
	}

	private static final TrackSchemeStyle modern;
	static
	{
		final Color bg = new Color( 163, 199, 197 );
		final Color fill = new Color( 64, 106, 102 );
		final Color selfill = new Color( 255, 128, 128 );
		final Color currenttp = new Color( 38, 175, 185 );
		modern = new TrackSchemeStyle();
		modern.name = "Modern";
		modern.backgroundColor = bg;
		modern.currentTimepointColor = currenttp;
		modern.vertexFillColor = fill;
		modern.selectedVertexFillColor = selfill;
		modern.simplifiedVertexFillColor = fill;
		modern.selectedSimplifiedVertexFillColor = selfill;
		modern.vertexDrawColor = Color.WHITE;
		modern.selectedVertexDrawColor = Color.BLACK;
		modern.edgeColor = Color.WHITE;
		modern.selectedEdgeColor = selfill.darker();
		modern.decorationColor = bg.darker();
		modern.vertexRangeColor = Color.WHITE;
		modern.headerBackgroundColor = bg.brighter();
		modern.headerDecorationColor = bg;
		modern.headerCurrentTimepointColor = bg.darker();
		modern.font = new Font( "Calibri", Font.PLAIN, 12 );
		modern.headerFont = new Font( "Calibri", Font.PLAIN, 12 );
		modern.edgeStroke = new BasicStroke();
		modern.edgeGhostStroke = DEFAULT_GHOST_STROKE;
		modern.edgeHighlightStroke = new BasicStroke( 2f );
		modern.vertexStroke = new BasicStroke();
		modern.vertexGhostStroke = DEFAULT_GHOST_STROKE;
		modern.vertexHighlightStroke = new BasicStroke( 3f );
		modern.focusStroke = DEFAULT_FOCUS_STROKE;
		modern.decorationStroke = new BasicStroke();
		modern.highlightCurrentTimepoint = true;
		modern.paintRows = true;
		modern.paintColumns = true;
		modern.paintHeaderShadow = true;
	}

	/**
	 * Returns the lorry TrackScheme style instance. Editing this instance will
	 * affect all view using this style.
	 *
	 * @return the single common instance for the lorry style.
	 */
	public static TrackSchemeStyle lorryStyle()
	{
		return hmdyk;
	}

	private static final TrackSchemeStyle hmdyk;
	static
	{
		final Color bg = new Color( 163, 199, 197 );
		final Color fill = new Color( 225, 216, 183 );
		final Color selfill = new Color( 53, 107, 154 );
		final Color seldraw = new Color( 230, 245, 255 );
		final Color seledge = new Color( 91, 137, 158 );
		hmdyk = new TrackSchemeStyle();
		hmdyk.name( "Lorry" );
		hmdyk.backgroundColor = bg;
		hmdyk.currentTimepointColor = bg.brighter();
		hmdyk.vertexFillColor = fill;
		hmdyk.selectedVertexFillColor = selfill;
		hmdyk.simplifiedVertexFillColor = Color.DARK_GRAY;
		hmdyk.selectedSimplifiedVertexFillColor = selfill;
		hmdyk.vertexDrawColor = Color.DARK_GRAY;
		hmdyk.selectedVertexDrawColor = seldraw;
		hmdyk.edgeColor = Color.DARK_GRAY;
		hmdyk.selectedEdgeColor = seledge;
		hmdyk.decorationColor = bg.darker();
		hmdyk.vertexRangeColor = Color.DARK_GRAY;
		hmdyk.headerBackgroundColor = bg.brighter();
		hmdyk.headerDecorationColor = bg;
		hmdyk.headerCurrentTimepointColor = bg.darker();
		hmdyk.font = new Font( "Calibri", Font.PLAIN, 12 );
		hmdyk.headerFont = new Font( "Calibri", Font.PLAIN, 12 );
		hmdyk.edgeStroke = new BasicStroke();
		hmdyk.edgeGhostStroke = DEFAULT_GHOST_STROKE;
		hmdyk.edgeHighlightStroke = new BasicStroke( 2f );
		hmdyk.vertexStroke = new BasicStroke();
		hmdyk.vertexGhostStroke = DEFAULT_GHOST_STROKE;
		hmdyk.vertexHighlightStroke = new BasicStroke( 3f );
		hmdyk.focusStroke = DEFAULT_FOCUS_STROKE;
		hmdyk.decorationStroke = new BasicStroke();
		hmdyk.highlightCurrentTimepoint = true;
		hmdyk.paintRows = true;
		hmdyk.paintColumns = true;
		hmdyk.paintHeaderShadow = true;
	}

	public static Collection< TrackSchemeStyle > defaults;
	static
	{
		defaults = new ArrayList<>( 3 );
		defaults.add( df );
		defaults.add( hmdyk );
		defaults.add( modern );
	}

}

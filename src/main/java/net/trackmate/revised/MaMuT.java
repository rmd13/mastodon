package net.trackmate.revised;

import java.io.File;
import java.io.IOException;

import mpicbg.spim.data.SpimDataException;
import net.trackmate.graph.GraphIdBimap;
import net.trackmate.graph.listenable.ListenableGraph;
import net.trackmate.revised.bdv.overlay.MouseOverListener;
import net.trackmate.revised.bdv.overlay.OverlayGraphRenderer;
import net.trackmate.revised.bdv.overlay.wrap.OverlayGraphWrapper;
import net.trackmate.revised.bdv.overlay.wrap.OverlayHighlightWrapper;
import net.trackmate.revised.model.mamut.BoundingSphereRadiusStatistics;
import net.trackmate.revised.model.mamut.Link;
import net.trackmate.revised.model.mamut.Model;
import net.trackmate.revised.model.mamut.ModelOverlayProperties;
import net.trackmate.revised.model.mamut.Spot;
import net.trackmate.revised.trackscheme.DefaultModelGraphProperties;
import net.trackmate.revised.trackscheme.DefaultModelHighlightProperties;
import net.trackmate.revised.trackscheme.TrackSchemeGraph;
import net.trackmate.revised.trackscheme.TrackSchemeHighlight;
import net.trackmate.revised.trackscheme.display.TrackSchemeFrame;
import net.trackmate.revised.ui.selection.HighlightListener;
import net.trackmate.revised.ui.selection.HighlightModel;
import net.trackmate.revised.ui.selection.Selection;
import bdv.BigDataViewer;
import bdv.export.ProgressWriterConsole;
import bdv.spimdata.SpimDataMinimal;
import bdv.spimdata.XmlIoSpimDataMinimal;
import bdv.tools.InitializeViewerState;
import bdv.viewer.ViewerOptions;
import bdv.viewer.ViewerPanel;

public class MaMuT
{
	public static void main1( final String[] args )
	{
		final Model model = new Model();
		final double[] pos = new double[] { 0, 0, 0 };
		final double[][] cov = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };

		final ListenableGraph< Spot, Link > graph = model.getGraph();
		final GraphIdBimap< Spot, Link > idmap = model.getGraphIdBimap();
		final Selection< Spot, Link > selection = new Selection<>( graph, idmap );
		final DefaultModelGraphProperties< Spot, Link > properties = new DefaultModelGraphProperties<>( graph, idmap, selection );
		final TrackSchemeGraph< Spot, Link > trackSchemeGraph = new TrackSchemeGraph<>( graph, idmap, properties );

		final Spot s0 = model.addSpot( 0, pos, cov, model.getGraph().vertexRef() );
		final Spot s1 = model.addSpot( 1, pos, cov, model.getGraph().vertexRef() );
		final Spot s2 = model.addSpot( 1, pos, cov, model.getGraph().vertexRef() );
		final Spot s3 = model.addSpot( 2, pos, cov, model.getGraph().vertexRef() );
		final Spot s4 = model.addSpot( 2, pos, cov, model.getGraph().vertexRef() );
		final Spot s5 = model.addSpot( 3, pos, cov, model.getGraph().vertexRef() );

		final Link l0 = model.addLink( s0, s1, model.getGraph().edgeRef() );
		final Link l1 = model.addLink( s0, s2, model.getGraph().edgeRef() );
		final Link l2 = model.addLink( s2, s3, model.getGraph().edgeRef() );
		final Link l3 = model.addLink( s4, s5, model.getGraph().edgeRef() );

		System.out.println( trackSchemeGraph );
	}

	public static void main2( final String[] args )
	{
		final Model model = new Model();
		final double[] pos = new double[] { 0, 0, 0 };
		final double[][] cov = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };

		final Spot s0 = model.addSpot( 0, pos, cov, model.getGraph().vertexRef() );
		final Spot s1 = model.addSpot( 1, pos, cov, model.getGraph().vertexRef() );
		final Spot s2 = model.addSpot( 1, pos, cov, model.getGraph().vertexRef() );
		final Spot s3 = model.addSpot( 2, pos, cov, model.getGraph().vertexRef() );
		final Spot s4 = model.addSpot( 2, pos, cov, model.getGraph().vertexRef() );
		final Spot s5 = model.addSpot( 3, pos, cov, model.getGraph().vertexRef() );

		final Link l0 = model.addLink( s0, s1, model.getGraph().edgeRef() );
		final Link l1 = model.addLink( s0, s2, model.getGraph().edgeRef() );
		final Link l2 = model.addLink( s2, s3, model.getGraph().edgeRef() );
		final Link l3 = model.addLink( s4, s5, model.getGraph().edgeRef() );

		final ListenableGraph< Spot, Link > graph = model.getGraph();
		final GraphIdBimap< Spot, Link > idmap = model.getGraphIdBimap();
		final Selection< Spot, Link > selection = new Selection<>( graph, idmap );
		final DefaultModelGraphProperties< Spot, Link > properties = new DefaultModelGraphProperties<>( graph, idmap, selection );
		final TrackSchemeGraph< Spot, Link > trackSchemeGraph = new TrackSchemeGraph<>( graph, idmap, properties );

		System.out.println( trackSchemeGraph );
	}

	public static void main3( final String[] args ) throws IOException
	{
		final File modelFile = new File( "/Users/pietzsch/TGMM/data/tifs/model_revised.raw" );

		final Model model = new Model();
		model.loadRaw( modelFile );


		final ListenableGraph< Spot, Link > graph = model.getGraph();
		final GraphIdBimap< Spot, Link > idmap = model.getGraphIdBimap();
		final Selection< Spot, Link > selection = new Selection<>( graph, idmap );
		final DefaultModelGraphProperties< Spot, Link > properties = new DefaultModelGraphProperties<>( graph, idmap, selection );
		final TrackSchemeGraph< Spot, Link > trackSchemeGraph = new TrackSchemeGraph<>( graph, idmap, properties );

		System.out.println( trackSchemeGraph );
	}

	public static void main4( final String[] args ) throws IOException, SpimDataException
	{
		final String bdvFile = "/Users/pietzsch/TGMM/data/tifs/datasethdf5.xml";
		final String modelFile = "/Users/pietzsch/TGMM/data/tifs/model_revised.raw";
		final int initialTimepointIndex = 10;

		final Model model = new Model();
		final BoundingSphereRadiusStatistics radiusStats = new BoundingSphereRadiusStatistics( model );
		model.loadRaw( new File( modelFile ) );

		System.setProperty( "apple.laf.useScreenMenuBar", "true" );

		/*
		 * TrackSchemeGraph listening to model
		 */
		final ListenableGraph< Spot, Link > graph = model.getGraph();
		final GraphIdBimap< Spot, Link > idmap = model.getGraphIdBimap();
		final Selection< Spot, Link > selection = new Selection<>( graph, idmap );
		final DefaultModelGraphProperties< Spot, Link > properties = new DefaultModelGraphProperties<>( graph, idmap, selection );
		final TrackSchemeGraph< Spot, Link > trackSchemeGraph = new TrackSchemeGraph<>( graph, idmap, properties );

		/*
		 * TrackSchemeHighlight wrapped HighlightModel
		 */
		final HighlightModel< Spot, Link > highlightModel = new HighlightModel< Spot, Link  >( idmap );
		final DefaultModelHighlightProperties< Spot, Link > highlightProperties = new DefaultModelHighlightProperties< Spot, Link >( graph, idmap, highlightModel );
		final TrackSchemeHighlight< Spot, Link > trackSchemeHighlight = new TrackSchemeHighlight< Spot, Link >( highlightProperties, trackSchemeGraph );

		/*
		 * show TrackSchemeFrame
		 */
		final TrackSchemeFrame frame = new TrackSchemeFrame( trackSchemeGraph, trackSchemeHighlight );
		frame.getTrackschemePanel().graphChanged();
		frame.setVisible( true );

		/*
		 * show BDV frame(s)
		 */
		final SpimDataMinimal spimData = new XmlIoSpimDataMinimal().load( bdvFile );
		final String windowTitle = new File( bdvFile ).getName();

		for ( int i = 0; i < 2; ++i )
		{
			final BigDataViewer bdv = openBDV( model, highlightModel, radiusStats, spimData, windowTitle, initialTimepointIndex, bdvFile );
			final ViewerPanel viewer = bdv.getViewer();
			viewer.repaint();
		}
	}

	public static BigDataViewer openBDV(
			final Model model,
			final HighlightModel< Spot, Link > highlightModel,
			final BoundingSphereRadiusStatistics radiusStats,
			final SpimDataMinimal spimData,
			final String windowTitle,
			final int initialTimepointIndex,
			final String bdvFile )
	{
		final OverlayGraphWrapper< Spot, Link > overlayGraph = new OverlayGraphWrapper<>(
				model.getGraph(),
				model.getGraphIdBimap(),
				model.getSpatioTemporalIndex(),
				new ModelOverlayProperties( radiusStats ) );
		final OverlayHighlightWrapper< Spot, Link > overlayHighlight = new OverlayHighlightWrapper<>(
				model.getGraph(),
				model.getGraphIdBimap(),
				highlightModel );
		final BigDataViewer bdv = BigDataViewer.open( spimData, windowTitle, new ProgressWriterConsole(), ViewerOptions.options() );
		if ( !bdv.tryLoadSettings( bdvFile ) )
			InitializeViewerState.initBrightness( 0.001, 0.999, bdv.getViewer(), bdv.getSetupAssignments() );
		final ViewerPanel viewer = bdv.getViewer();
		viewer.setTimepoint( initialTimepointIndex );
		final OverlayGraphRenderer< ?, ? > tracksOverlay = new OverlayGraphRenderer<>( viewer, overlayGraph, overlayHighlight );
		viewer.getDisplay().addOverlayRenderer( tracksOverlay );
		viewer.addRenderTransformListener( tracksOverlay );
		overlayHighlight.addHighlightListener( new HighlightListener()
		{
			@Override
			public void highlightChanged()
			{
				viewer.getDisplay().repaint();
			}
		} );
		final MouseOverListener mouseOver = new MouseOverListener( tracksOverlay );
		viewer.getDisplay().addHandler( mouseOver );
		return bdv;
	}

	public static void main( final String[] args ) throws IOException, SpimDataException
	{
		main4( args );
	}
}
package org.mastodon.feature.ui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.mastodon.feature.FeatureProjectionSpec;
import org.mastodon.feature.FeatureSpec;
import org.mastodon.feature.Multiplicity;
import org.mastodon.revised.ui.coloring.feature.FeatureProjectionId;
import org.mastodon.revised.ui.coloring.feature.TargetType;
import org.mastodon.util.Listeners;

/**
 * A JPanel, one line, in which the user can select a pair of feature /
 * projection keys.
 *
 * @author Jean-Yves Tinevez
 */
public class FeatureSelectionPanel
{
	private final JPanel panel;

	private AvailableFeatureProjections availableFeatureProjections;

	private TargetType targetType;

	private final JComboBox< String > cbFeatures;

	private final JComboBox< String > cbProjections;

	private final JLabel lblArrow;

	private final JComboBox< Integer > cbSource1;

	private final JLabel lblAnd;

	private final JComboBox< Integer > cbSource2;

	private final JLabel lblSource1;

	private final JLabel lblSource2;

	private final Listeners.List< UpdateListener > updateListeners;

	private final Component projectionStrut;

	private final Component featureStrut;

	private final Component arrowStrut;

	private final Component source1Strut;

	private final Component andStrut;

	public FeatureSelectionPanel()
	{
		this.panel = new JPanel();

		this.updateListeners = new Listeners.SynchronizedList<>();
		// Forwarding listener.
		final ItemListener forwardListener = new ItemListener()
		{

			@Override
			public void itemStateChanged( final ItemEvent e )
			{
				if ( e.getStateChange() == ItemEvent.SELECTED )
					notifyListeners();
			}
		};

		final BoxLayout layout = new BoxLayout( panel, BoxLayout.LINE_AXIS );
		panel.setLayout( layout );

		// Feature CB.
		this.cbFeatures = new JComboBox<>();
		cbFeatures.addItemListener( new ItemListener()
		{

			@Override
			public void itemStateChanged( final ItemEvent e )
			{
				if ( e.getStateChange() == ItemEvent.SELECTED )
					refreshProjections();
			}
		} );
		panel.add( cbFeatures );
		this.featureStrut = panel.add( Box.createHorizontalStrut( 5 ) );

		// Arrow label
		this.lblArrow = new JLabel( "\u2192" );
		panel.add( lblArrow );
		this.arrowStrut = panel.add( Box.createHorizontalStrut( 5 ) );

		// Projection CB.
		this.cbProjections = new JComboBox<>();
		cbProjections.addItemListener( forwardListener );
		panel.add( cbProjections );
		this.projectionStrut = panel.add( Box.createHorizontalStrut( 5 ) );

		// Source index 1.
		this.lblSource1 = new JLabel( "ch" );
		panel.add( lblSource1 );
		this.cbSource1 = new JComboBox<>();
		cbSource1.addItemListener( forwardListener );
		panel.add( cbSource1 );
		this.source1Strut = panel.add( Box.createHorizontalStrut( 5 ) );

		// & label.
		this.lblAnd = new JLabel( "&" );
		panel.add( lblAnd );
		this.andStrut = panel.add( Box.createHorizontalStrut( 5 ) );

		// Source index 2.
		this.lblSource2 = new JLabel( "ch" );
		panel.add( lblSource2 );
		this.cbSource2 = new JComboBox<>();
		cbSource2.addItemListener( forwardListener );
		panel.add( cbSource2 );
	}

	// TODO remove, make this class extend JPanel
	public JPanel getPanel()
	{
		return panel;
	}

	private void notifyListeners()
	{
		for ( final UpdateListener l : updateListeners.list )
			l.featureKeyChanged();
	}

	/**
	 * Exposes the listeners that will be notified when a change is made in this
	 * panel.
	 *
	 * @return the listeners.
	 */
	public Listeners< UpdateListener > updateListeners()
	{
		return updateListeners;
	}

	/**
	 * Returns the key pair corresponding to the selection in this panel, as an
	 * 2-elements string array.
	 * <p>
	 * The first element is the feature key ({@link FeatureSpec#getKey()}) or the
	 * empty string if there is no selection.
	 * <p>
	 * The second element is the feature projection key, built from the selected
	 * feature projection name ({@link FeatureProjectionSpec#projectionName}), the
	 * feature multiplicity and the source indices selected in this panel. Or the
	 * empty string if there is no selection.
	 *
	 * @return a 2-element string array.
	 */
	public FeatureProjectionId getSelection()
	{
		final String featureKey = ( String ) cbFeatures.getSelectedItem();
		if ( null == featureKey )
			return null;

		try
		{
			String projectionKey = ( String ) cbProjections.getSelectedItem();
			if ( null == projectionKey )
				projectionKey = availableFeatureProjections.projectionKeys( targetType, featureKey ).iterator().next();
			if ( null == projectionKey )
				return null;

			final int i0, i1;
			switch ( availableFeatureProjections.multiplicity( targetType, featureKey ) )
			{
			default:
			case SINGLE:
				i0 = -1;
				i1 = -1;
				break;
			case ON_SOURCES:
				i0 = availableFeatureProjections.getSourceIndices().get( Math.max( 0, cbSource1.getSelectedIndex() ) );
				i1 = -1;
				break;
			case ON_SOURCE_PAIRS:
				i0 = availableFeatureProjections.getSourceIndices().get( Math.max( 0, cbSource1.getSelectedIndex() ) );
				i1 = availableFeatureProjections.getSourceIndices().get( Math.max( 0, cbSource2.getSelectedIndex() ) );
				break;
			}

			return new FeatureProjectionId( featureKey, projectionKey, i0, i1 );
		}
		catch( NoSuchElementException e )
		{
			return null;
		}
	}

	public void setSelection( final FeatureProjectionId selection )
	{
		cbFeatures.setSelectedItem( selection.getFeatureKey() );
		cbProjections.setSelectedItem( selection.getProjectionKey() );
		cbSource1.setSelectedIndex( Math.max( 0, availableFeatureProjections.getSourceIndices().indexOf( selection.getI0() ) ) );
		cbSource2.setSelectedIndex( Math.max( 0, availableFeatureProjections.getSourceIndices().indexOf( selection.getI1() ) ) );
	}

	private void refreshProjections()
	{
		final String featureKey = ( String ) cbFeatures.getSelectedItem();

		// Projections.
		final Collection< String > projectionKeys = availableFeatureProjections.projectionKeys( targetType, featureKey );
		final String[] pk = projectionKeys.toArray( new String[] {} );
		Arrays.sort( pk );
		cbProjections.setModel( new DefaultComboBoxModel<>( pk ) );

		// Visibility.
		final Multiplicity multiplicity = availableFeatureProjections.multiplicity( targetType, featureKey );
		final boolean projectionCBVisible = ( null != featureKey ) &&
				( ( projectionKeys.size() > 1 )
						|| multiplicity != Multiplicity.SINGLE );
		arrowStrut.setVisible( projectionCBVisible );
		cbProjections.setVisible( projectionCBVisible );
		lblArrow.setVisible( projectionCBVisible );
		featureStrut.setVisible( projectionCBVisible );

		projectionStrut.setVisible( projectionCBVisible && ( multiplicity != Multiplicity.SINGLE ) );
		lblSource1.setVisible( projectionCBVisible && ( multiplicity != Multiplicity.SINGLE ) );
		cbSource1.setVisible( projectionCBVisible && ( multiplicity != Multiplicity.SINGLE ) );

		source1Strut.setVisible( projectionCBVisible && ( multiplicity == Multiplicity.ON_SOURCE_PAIRS ) );
		lblAnd.setVisible( projectionCBVisible && ( multiplicity == Multiplicity.ON_SOURCE_PAIRS ) );
		andStrut.setVisible( projectionCBVisible && ( multiplicity == Multiplicity.ON_SOURCE_PAIRS ) );

		lblSource2.setVisible( projectionCBVisible && ( multiplicity == Multiplicity.ON_SOURCE_PAIRS ) );
		cbSource2.setVisible( projectionCBVisible && ( multiplicity == Multiplicity.ON_SOURCE_PAIRS ) );

		notifyListeners();
	}

	/**
	 * Sets the feature specifications to display in this panel.
	 */
	public void setAvailableFeatureProjections( final AvailableFeatureProjections afp, final TargetType targetType )
	{
		if ( afp != null && targetType != null && ! ( afp.equals( this.availableFeatureProjections ) && targetType.equals( this.targetType ) ) )
		{
			this.availableFeatureProjections = afp;
			this.targetType = targetType;

			final Integer[] indices = Arrays.stream( afp.getSourceIndices().toArray() )
					.map( i -> i + 1 )
					.boxed()
					.toArray( Integer[]::new );
			cbSource1.setModel( new DefaultComboBoxModel<>( indices ) );
			cbSource2.setModel( new DefaultComboBoxModel<>( indices ) );

			final ArrayList< String > featureKeys = new ArrayList<>( afp.featureKeys( targetType ) );
			featureKeys.sort( Comparator.naturalOrder() );

			final String previousSelection = ( String ) cbFeatures.getSelectedItem();
			final int selectIndex = Math.max( 0, featureKeys.indexOf( previousSelection ) );

			cbFeatures.setModel( new DefaultComboBoxModel<>( featureKeys.toArray( new String[] {} ) ) );
			cbFeatures.setSelectedIndex( selectIndex );
			// If selectIndex != 0, this will trigger refreshProjections().
			// Otherwise, we do it explicitly
			if ( selectIndex == 0 )
				refreshProjections();
		}
	}

	public interface UpdateListener
	{
		public void featureKeyChanged();
	}

//	public static void main( final String[] args ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
//	{
//		UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
//		JComponent.setDefaultLocale( Locale.ROOT );
//
//		final FeatureSelectionPanel selectionPanel = new FeatureSelectionPanel();
//		selectionPanel.updateListeners().add( () -> System.out.println( selectionPanel.getSelection() ) );
//
//		final JFrame frame = new JFrame( "Feature selection panel" );
//		frame.getContentPane().add( selectionPanel.panel );
//		frame.setVisible( true );
//
//		final AvailableFeatureProjections afp = Playground.dummyAvailableFeatureProjections();
//		selectionPanel.setAvailableFeatureProjections( afp, TargetType.VERTEX );
//		frame.pack();
//	}
}
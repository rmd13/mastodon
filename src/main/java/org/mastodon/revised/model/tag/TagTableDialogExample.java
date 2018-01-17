package org.mastodon.revised.model.tag;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.mastodon.revised.model.tag.TagTable.Element;

public class TagTableDialogExample
{
	static class MyElement implements TagTable.Element
	{
		private String name;

		public MyElement( final String name )
		{
			this.name = name;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public void setName( final String name )
		{
			this.name = name;
		}
	}

	static class MyElements extends ArrayList< MyElement > implements TagTable.Elements< MyElement >
	{
		private static final long serialVersionUID = 1L;

		private final MyElement dummy = new MyElement( "" );

		@Override
		public Element getDummyElement()
		{
			return dummy;
		}

		@Override
		public Element addElement()
		{
			final MyElement element = new MyElement( "addElement" );
			add( element );
			return element;
		}
	}

	static class TagSetDialog extends JDialog
	{
		private static final long serialVersionUID = 1L;

		public TagSetDialog( final Frame owner, final MyElements elements )
		{
			super( owner, "tag sets configuration", false );

			final JPanel tagSetPanel = new JPanel( new BorderLayout( 0, 0 ) );
			final JScrollPane scrollPaneTagSet = new JScrollPane();
			scrollPaneTagSet.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
			tagSetPanel.add( scrollPaneTagSet, BorderLayout.CENTER );
			final TagTable tagTable = new TagTable<>( elements );
			scrollPaneTagSet.setViewportView( tagTable.getTable() );

			getContentPane().add( tagSetPanel );
			setSize( 400, 500 );
			pack();
		}
	}

	public static void main( final String[] args ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException
	{
		final MyElements elements = new MyElements();
		elements.add( new MyElement( "element 1" ) );
		elements.add( new MyElement( "element 2" ) );
		elements.add( new MyElement( "element 3" ) );

		UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );

		final TagSetDialog frame = new TagSetDialog( null, elements );
		frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		frame.setLocationByPlatform( true );
		frame.setVisible( true );
	}
}
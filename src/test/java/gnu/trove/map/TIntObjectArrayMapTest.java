package gnu.trove.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import gnu.trove.function.TObjectFunction;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.procedure.TIntObjectProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TObjectProcedure;

public class TIntObjectArrayMapTest
{

	private HashMap< Integer, String > truthMap;

	private TIntObjectArrayMap< String > map;

	private int[] storedIds;

	@Before
	public void setUp() throws Exception
	{
		truthMap = new HashMap< >( 10 );
		for ( int i = 0; i < 10; i++ )
		{
			final String str = Character.toString( ( char ) ( 'a' + i ) );
			truthMap.put( Integer.valueOf( 20 + i ), str );
		}

		map = new TIntObjectArrayMap< >();
		storedIds = new int[] { 22, 23, 26, 28 };
		for ( final int id : storedIds )
		{
			final String val = truthMap.get( id );
			map.put( id, val );
		}
	}

	@Test
	public void testClear()
	{
		map.clear();
		assertTrue( "Map should be empty after clear().", map.isEmpty() );
		assertEquals( "Map should be of 0-size after clear().", 0, map.size() );
		for ( int i = 0; i < 10; i++ )
		{
			final String val = map.get( i );
			assertNull( "There should not be a mapping for key " + i + " after clear().", val );
		}
	}

	@Test
	public void testGetInt()
	{
		for ( final int id : storedIds )
		{
			final String vactual = map.get( id );
			final String vexpected = truthMap.get( id );
			assertEquals( "Unexpected mapping for key " + id, vexpected, vactual );
		}
	}

	@Test
	public void testIsEmpty()
	{
		assertTrue( "Full map should not be empty.", !map.isEmpty() );
		for ( final int id : storedIds )
		{
			map.remove( id );
		}
		assertTrue( "Emptied map should be empty.", map.isEmpty() );
		assertTrue( "New map should be empty.", new TIntObjectArrayMap< >().isEmpty() );
	}

	@Test
	public void testPutIntV()
	{
		final int key = 25;
		assertTrue( "Map should not yet contain a mapping for key " + key, !map.containsKey( key ) );

		final String val = truthMap.get( key );
		final String put = map.put( key, val );
		assertNull( "There should not be a previous mapping for key " + key, put );
		assertTrue( "Map should now contain a mapping for key " + key, map.containsKey( key ) );
	}

	@Test
	public void testRemoveInt()
	{
		final int key = 26;
		final int size = map.size();
		assertTrue( "Map should contain a mapping for key " + key, map.containsKey( key ) );
		final String removed = map.remove( key );
		assertNotNull( "Object removed by existing mapping should not be null.", removed );
		assertTrue( "Map should not contain a mapping for removed key " + key, !map.containsKey( key ) );
		assertEquals( "Map size should have shrunk by 1 after removal.", size - 1, map.size() );

		final String removed2 = map.remove( key );
		assertNull( "Object removed by non-existing mapping should be null.", removed2 );
		assertEquals( "Map size should not have shrunk by 1 after removal of non-existing mapping.", size - 1, map.size() );
	}

	@Test
	public void testSize()
	{
		assertEquals( "Unexpected map size.", storedIds.length, map.size() );
		final int[] toAdd = new int[] { 24, 25 };
		for ( final int add : toAdd )
		{
			final String val = truthMap.get( add );
			map.put( add, val );
		}
		assertEquals( "Unexpected map size after addition.", storedIds.length + toAdd.length, map.size() );
		assertEquals( "Unexpected new map size.", 0, new TIntObjectArrayMap< >().size() );
	}

	@Test
	public void testGetNoEntryKey()
	{
		final int noEntryKey = map.getNoEntryKey();
		assertTrue( "The no entry key should be negative.", noEntryKey < 0 );
	}

	@Test
	public void testContainsKey()
	{
		for ( final int key : storedIds )
		{
			assertTrue( "The map should contain a mapping for key " + key, map.containsKey( key ) );
		}
		for ( final Integer key : truthMap.keySet() )
		{
			if ( Arrays.binarySearch( storedIds, key ) < 0 )
			{
				assertFalse( "The map should not contain a mapping for key " + key, map.containsKey( key ) );
			}

		}
	}

	@Test
	public void testContainsValue()
	{
		for ( final int id : storedIds )
		{
			final String val = truthMap.get( id );
			assertTrue( "Map should contain the value " + val, map.containsValue( val ) );
		}
		Arrays.sort( storedIds );
		for ( final Integer id : truthMap.keySet() )
		{
			if ( Arrays.binarySearch( storedIds, id ) < 0 )
			{
				final String val = truthMap.get( id );
				assertFalse( "Map should not contain the value " + val, map.containsValue( val ) );
			}
		}
	}

	@Test
	public void testPutIfAbsent()
	{
		final int index = 100;
		final String neu = "" + index;
		final String absent = map.putIfAbsent( index, neu );
		assertNull( "There was not a mapping for index " + index + " before; returned object should be null.", absent );
		assertEquals( "Unexpected mapping for new key " + index, neu, map.get( index ) );

		final int existingMapping = storedIds[ 0 ];
		final String absent2 = map.putIfAbsent( existingMapping, neu );
		assertNotNull( "There was a mapping for index " + existingMapping + " before; returned object should not be null.", absent2 );

		final String expected = truthMap.get( existingMapping );
		assertEquals( "Returned object by putIfAbsent is unexpected.", expected, absent2 );
	}

	@Test
	public void testPutAllMapOfQextendsIntegerQextendsV()
	{
		final Map< Integer, String > m = new HashMap< >();
		final int[] newIds = new int[] { 101, 102 };
		for ( final int id : newIds )
		{
			m.put( id, "" + id );
		}

		final int size = map.size();
		map.putAll( m );
		assertEquals( "Map does not have the expected size after putAll.", size + m.size(), map.size() );
		for ( final int key : m.keySet() )
		{
			final String v = m.get( key );
			assertTrue( "Map should now contain a mapping for key " + key, map.containsKey( key ) );
			assertTrue( "Map should now contain a mapping for value " + v, map.containsValue( v ) );
			assertEquals( "New mapping is different than in the source map.", m.get( key ), v );
		}
	}

	@Test
	public void testPutAllTIntObjectMapOfQextendsV()
	{
		final TIntObjectHashMap< String > m = new TIntObjectHashMap< >();
		final int[] newIds = new int[] { 101, 102 };
		for ( final int id : newIds )
		{
			m.put( id, "" + id );
		}

		final int size = map.size();
		map.putAll( m );
		assertEquals( "Map does not have the expected size after putAll.", size + m.size(), map.size() );
		for ( final int key : m.keys() )
		{
			final String v = m.get( key );
			assertTrue( "Map should now contain a mapping for key " + key, map.containsKey( key ) );
			assertTrue( "Map should now contain a mapping for value " + v, map.containsValue( v ) );
			assertEquals( "New mapping is different than in the source map.", m.get( key ), v );
		}
	}

	@Test
	public void testKeys()
	{
		final int[] keys = map.keys();
		assertEquals( "Key array does not have the expected length.", map.size(), keys.length );
		// We know they are in the right order.
		for ( int i = 0; i < keys.length; i++ )
		{
			assertEquals( "Unexpected key returned by keys().", storedIds[ i ], keys[ i ] );
		}
	}

	@Test
	public void testKeysIntArray()
	{
		final int[] arr = new int[ 100 ];
		final int[] keys = map.keys( arr );
		assertEquals( "Returned array and passed array are not the same instance.", arr, keys );
		// They should since arr is larger than the map size.

		// We know they are in the right order.
		for ( int i = 0; i < storedIds.length; i++ )
		{
			assertEquals( "Unexpected key returned by keys().", storedIds[ i ], keys[ i ] );
		}
		for ( int i = storedIds.length; i < keys.length; i++ )
		{
			assertEquals( "Unexpected key returned by keys().", map.getNoEntryKey(), keys[ i ] );
		}
	}

	@Test
	public void testValues()
	{
		final Object[] values = map.values();
		assertEquals( "values() array is not of the expected length.", map.size(), values.length );
		for ( final Object obj : values )
		{
			assertTrue( "Object returned by values() is not of the expected class.", obj instanceof String );
			assertTrue( "Object returned by values() should be in the map.", map.containsValue( obj ) );
		}
	}

	@Test
	public void testValuesVArray()
	{
		final String[] arr = new String[ 100 ];
		final String[] values = map.values( arr );
		assertEquals( "Returned array and passed array are not the same instance.", arr.hashCode(), values.hashCode() );
		for ( int i = 0; i < map.size(); i++ )
		{
			final String v = values[ i ];
			assertTrue( "Object returned by values() should be in the map.", map.containsValue( v ) );
		}
		for ( int i = map.size(); i < values.length; i++ )
		{
			assertNull( "Remaining elements should be null.", values[ i ] );
		}
	}

	@Test
	public void testIterator()
	{
		// Test iterate in the right order.
		final TIntObjectIterator< String > it = map.iterator();
		int index = 0;
		while ( it.hasNext() )
		{
			final int key = storedIds[ index++ ];
			final String val = truthMap.get( key );

			it.advance();
			assertEquals( "Iterator returns unexpected key.", key, it.key() );
			assertEquals( "Iterator returns unexpected value.", val, it.value() );
		}

		// Test iterator removal.
		// Remove the 6.
		final int size = map.size();
		final TIntObjectIterator< String > it2 = map.iterator();
		it2.advance(); // 2
		it2.advance(); // 3
		it2.advance(); // 6
		final String val = it2.value();
		it2.remove();
		assertEquals( "Map does not have the expected size after removal by keyset iterator.", size - 1, map.size() );
		assertFalse( "Map should not contain a mapping for key " + val + " after removal by keyset iterator.", map.containsValue( val ) );

		// Remove all.
		final TIntObjectIterator< String > it3 = map.iterator();
		while ( it3.hasNext() )
		{
			it3.advance();
			it3.remove();
		}
		assertTrue( "Map should be empty after removing all content with keyset iterator.", map.isEmpty() );
	}

	@Test
	public void testForEachKey()
	{
		final AtomicInteger ai = new AtomicInteger( 0 );
		final TIntProcedure proc = new TIntProcedure()
		{
			@Override
			public boolean execute( final int value )
			{
				ai.incrementAndGet();
				assertTrue( "Iterated key is not contained in the map.", map.containsKey( value ) );
				return true;
			}
		};
		final boolean ok = map.forEachKey( proc );
		assertTrue( "ForEach procedure should have terminated ok.", ok );
		assertEquals( "All the values have not been iterated through.", map.size(), ai.get() );
	}

	@Test
	public void testForEachValue()
	{
		final AtomicInteger ai = new AtomicInteger( 0 );
		final TObjectProcedure< String > proc = new TObjectProcedure< String >()
		{
			@Override
			public boolean execute( final String value )
			{
				ai.incrementAndGet();
				assertTrue( "Iterated value is not contained in the map.", map.containsValue( value ) );
				return true;
			}
		};
		final boolean ok = map.forEachValue( proc );
		assertTrue( "ForEach procedure should have terminated ok.", ok );
		assertEquals( "All the values have not been iterated through.", map.size(), ai.get() );
	}

	@Test
	public void testForEachEntry()
	{
		final AtomicInteger ai = new AtomicInteger( 0 );
		final TIntObjectProcedure< String > proc = new TIntObjectProcedure< String >()
		{
			@Override
			public boolean execute( final int key, final String value )
			{
				ai.incrementAndGet();
				assertTrue( "Iterated key is not contained in the map.", map.containsKey( key ) );
				assertTrue( "Iterated value is not contained in the map.", map.containsValue( value ) );
				return true;
			}
		};
		final boolean ok = map.forEachEntry( proc );
		assertTrue( "ForEach procedure should have terminated ok.", ok );
		assertEquals( "All the values have not been iterated through.", map.size(), ai.get() );
	}

	@Test
	public void testTransformValues()
	{
		final String v = "" + 100;
		final TObjectFunction< String, String > function = new TObjectFunction< String, String >()
		{
			@Override
			public String execute( final String value )
			{
				return v;
			}
		};
		map.transformValues( function );

		for ( final String value : map.valueCollection() )
		{
			assertEquals( "Unexpected value after change.", v, value );
		}
	}

	@Test
	public void testRetainEntries()
	{
		final TIntObjectProcedure< String > proc = new TIntObjectProcedure< String >()
		{

			@Override
			public boolean execute( final int a, final String b )
			{
				return b.equals( "i" );
			}
		};
		final boolean changed = map.retainEntries( proc );
		assertTrue( "RetainEntries should have changed the map.", changed );
		assertEquals( "There should be only 1 mapping left.", 1, map.size() );
		final TIntObjectIterator< String > it = map.iterator();
		it.advance();
		final String value = it.value();
		assertEquals( "Remaining value is not the right one.", "i", value );
	}
}
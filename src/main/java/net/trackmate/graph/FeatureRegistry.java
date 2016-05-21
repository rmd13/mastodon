package net.trackmate.graph;

import java.util.HashMap;
import java.util.Map;

import gnu.trove.map.TIntObjectArrayMap;
import gnu.trove.map.TIntObjectMap;

/**
 * Assign unique IDs to features.
 *
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public final class FeatureRegistry
{
	private static int vertexIdGenerator = 0;

	private static int edgeIdGenerator = 0;

	private static Map< String, Integer > vertexFeatureKeyIds = new HashMap<>();

	private static Map< String, Integer > edgeFeatureKeyIds = new HashMap< >();

	private static Map< String, VertexFeature< ?, ?, ? > > vertexFeatures = new HashMap<>();

	private static Map< String, EdgeFeature< ?, ?, ? > > edgeFeatures = new HashMap< >();

	private static TIntObjectMap< VertexFeature< ?, ?, ? > > vertexFeaturesById = new TIntObjectArrayMap< VertexFeature< ?, ?, ? > >();

	private static TIntObjectMap< EdgeFeature< ?, ?, ? > > edgeFeaturesById = new TIntObjectArrayMap< EdgeFeature< ?, ?, ? > >();

	public static final class DuplicateKeyException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public DuplicateKeyException()
		{
			super();
		}

		public DuplicateKeyException( final String message )
		{
			super( message );
		}
	}

	static synchronized void registerVertexFeature( final VertexFeature< ?, ?, ? > feature ) throws DuplicateKeyException
	{
		final String key = feature.getKey();
		if ( vertexFeatures.containsKey( key ) )
			throw new DuplicateKeyException( String.format( "vertex feature key \"%s\" already exists", key ) );
		vertexFeatures.put( key, feature );
		vertexFeaturesById.put( feature.getUniqueFeatureId(), feature );
	}

	public static void registerEdgeFeature( final EdgeFeature< ?, ?, ? > feature )
	{
		final String key = feature.getKey();
		if ( edgeFeatures.containsKey( key ) )
			throw new DuplicateKeyException( String.format( "edge feature key \"%s\" already exists", key ) );
		edgeFeatures.put( key, feature );
		edgeFeaturesById.put( feature.getUniqueFeatureId(), feature );
	}

	/**
	 * @param key
	 * @return unique ID assigned to feature.
	 */
	public static synchronized int getUniqueVertexFeatureId( final String key )
	{
		final Integer id = vertexFeatureKeyIds.get( key );
		if ( id != null )
			return id;

		final int newId = vertexIdGenerator++;
		vertexFeatureKeyIds.put( key, newId );
		return newId;
	}

	/**
	 * @param key
	 * @return unique ID assigned to feature.
	 */
	public static synchronized int getUniqueEdgeFeatureId( final String key )
	{
		final Integer id = edgeFeatureKeyIds.get( key );
		if ( id != null )
			return id;

		final int newId = edgeIdGenerator++;
		edgeFeatureKeyIds.put( key, newId );
		return newId;
	}

	public static synchronized VertexFeature< ?, ?, ? > getVertexFeature( final String key )
	{
		return vertexFeatures.get( key );
	}

	public static synchronized VertexFeature< ?, ?, ? > getVertexFeature( final int uniqueId )
	{
		return vertexFeaturesById.get( uniqueId );
	}

	private FeatureRegistry()
	{}

}
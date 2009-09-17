package se.combitech.strokesformartians.dancing;

import java.util.HashMap;
import se.combitech.strokesformartians.Leroy2;
import se.combitech.strokesformartians.Leroy2.Bone;
import android.opengl.*;

public class MartianAnimator 
{
	private class VertexWeight
	{
		public Leroy2.Bone [] bone;
		public float [] weight;
	}
	
	public Leroy2 leroy;
	
	// stupid bone stuff
	public float[] boneVertexBuffer;
	public byte[] boneIndexBuffer;
	
	// cool outline stuff
	public float [] texCoordBuffer;
	public float [] vertexBuffer;
	public byte [] indexBuffer;
	VertexWeight [] vertexWeights;	
	public int numVertices;
	public int numIndices;
	public int numTriangles;
	
	public static float fatness = 1;
	public static float leglength = 1;
	public static float armlength = 1;
	public static float headlength = 0.5f;
	
	/** @TODO change these values */
	public static float textureBottom = 0;
	public static float textureLeft = 0;
	public static float textureTop = 1;
	public static float textureRight = 1;
	

	public MartianAnimator()
	{
		leroy = new Leroy2();

		boneVertexBuffer = new float[ 16 * 3 ];
		boneIndexBuffer = new byte[ 30 ];
		
		generateSkeleton();

		numVertices = 29;
		numTriangles = 27;
		numIndices = numTriangles * 3;  
		
		texCoordBuffer= new float[numVertices * 2];
		vertexBuffer = new float[numVertices * 3];
		
		generateOutline();
		generateTextureCoordinates();
		generateIndices();
		generateVertexWeight();
	}
	
	/**
	 * Generate the vertex weights for the outline.
	 */
	private void generateVertexWeight()
	{
		vertexWeights = new VertexWeight[ numVertices ];

		for(int loop0 = 0; loop0 < numVertices; ++loop0)
		{
			vertexWeights[loop0] = new VertexWeight();
		}
		
		// ROOT
		vertexWeights[ 0 ].bone = new Leroy2.Bone[ 1 ];
		vertexWeights[ 0 ].bone[ 0 ] = leroy.bones.get( "root" );
		
		vertexWeights[ 0 ].weight = new float[ 1 ];
		vertexWeights[ 0 ].weight[ 0 ] = 1.0f;
		
		// LOWER LEG RIGHT INNER
		vertexWeights[ 1 ].bone = new Leroy2.Bone[ 2 ];
		vertexWeights[ 1 ].bone[ 0 ] = leroy.bones.get( "upper_leg_right" );
		vertexWeights[ 1 ].bone[ 1 ] = leroy.bones.get( "lower_leg_right" );
		
		vertexWeights[ 1 ].weight = new float[ 2 ];
		vertexWeights[ 1 ].weight[ 0 ] = 0.9f;		
		vertexWeights[ 1 ].weight[ 1 ] = 0.1f;

		// FOOT RIGHT INNER
		vertexWeights[ 2 ].bone = new Leroy2.Bone[ 1 ];
		vertexWeights[ 2 ].bone[ 0 ] = leroy.bones.get( "lower_leg_right" );
		
		vertexWeights[ 2 ].weight = new float[ 1 ];
		vertexWeights[ 2 ].weight[ 0 ] = 1.0f;		
		
		// FOOT RIGHT OUTERS
		vertexWeights[ 3 ].bone = new Leroy2.Bone[ 1 ];
		vertexWeights[ 3 ].bone[ 0 ] = leroy.bones.get( "lower_leg_right" );
		
		vertexWeights[ 3 ].weight = new float[ 1 ];
		vertexWeights[ 3 ].weight[ 0 ] = 1.0f;		

		// LOWER LEG RIGHT OUTER
		vertexWeights[ 4 ].bone = new Leroy2.Bone[ 2 ];
		vertexWeights[ 4 ].bone[ 0 ] = leroy.bones.get( "upper_leg_right" );
		vertexWeights[ 4 ].bone[ 1 ] = leroy.bones.get( "lower_leg_right" );
		
		vertexWeights[ 4 ].weight = new float[ 2 ];
		vertexWeights[ 4 ].weight[ 0 ] = 0.9f;
		vertexWeights[ 4 ].weight[ 1 ] = 0.1f;
		
	}

	/**
	 * Generate the vertices for the outline.
	 */
	private void generateOutline()
	{
		int index = 0;
	
		// 0
		vertexBuffer[index++] = leroy.bones.get("root").restPose[0];
		vertexBuffer[index++] = leroy.bones.get("root").restPose[1] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("root").restPose[2];
		
		// 1
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[0] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[2];
	
		// 2
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[0] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[1] - leglength;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[2];

		// 3
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[0] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[1] - leglength;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[2];
		
		// 4
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[0] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("lower_leg_right").restPose[2];
		
		// 5
		vertexBuffer[index++] = leroy.bones.get("upper_leg_right").restPose[0] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("upper_leg_right").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("upper_leg_right").restPose[2];
		
		// 6
		vertexBuffer[index++] = leroy.bones.get("spine").restPose[0] - 0.4f * fatness;
		vertexBuffer[index++] = leroy.bones.get("spine").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("spine").restPose[2];		

		// 7
		vertexBuffer[index++] = leroy.bones.get("upper_arm_right").restPose[0] + 0.05f;
		vertexBuffer[index++] = leroy.bones.get("upper_arm_right").restPose[1] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("upper_arm_right").restPose[2];				

		// 8
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[0] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[2];						

		// 9
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[0] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[1] - armlength;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[2];	
		
		// 10
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[0] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[1] - armlength;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[2];		
		
		// 11
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[0] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("lower_arm_right").restPose[2];				

		// 12
		vertexBuffer[index++] = leroy.bones.get("upper_arm_right").restPose[0] - 0.05f;
		vertexBuffer[index++] = leroy.bones.get("upper_arm_right").restPose[1] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("upper_arm_right").restPose[2];		

		// 13
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[0] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[1] + 0.05f;
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[2];				

		// 14
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[0] - 0.1f * fatness + 0.05f;
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[1] + headlength;
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[2];	

		// 15
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[0] + 0.1f * fatness - 0.05f;
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[1] + headlength;
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[2];		
		
		// 16
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[0] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[1] + 0.05f;
		vertexBuffer[index++] = leroy.bones.get("neck").restPose[2];
		
		// 17
		vertexBuffer[index++] = leroy.bones.get("upper_arm_left").restPose[0] + 0.05f;
		vertexBuffer[index++] = leroy.bones.get("upper_arm_left").restPose[1] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("upper_arm_left").restPose[2];		
		
		// 18
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[0] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[2];			
		
		// 19
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[0] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[1] - armlength;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[2];			
		
		// 20
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[0] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[1] - armlength;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[2];	
		
		// 21
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[0] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[2];
		
		// 22
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[0] - 0.05f;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[1] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_arm_left").restPose[2];
		
		// 23
		vertexBuffer[index++] = leroy.bones.get("spine").restPose[0] + 0.4f * fatness;
		vertexBuffer[index++] = leroy.bones.get("spine").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("spine").restPose[2];		
		
		// 24
		vertexBuffer[index++] = leroy.bones.get("upper_leg_left").restPose[0] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("upper_leg_left").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("upper_leg_left").restPose[2];		
		
		// 25
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[0] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[2];		
		
		// 26
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[0] + 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[1] - leglength;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[2];		
		
		// 27
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[0] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[1] - leglength;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[2];		
		
		// 28
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[0] - 0.1f * fatness;
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[1];
		vertexBuffer[index++] = leroy.bones.get("lower_leg_left").restPose[2];		
	}
	
	/**
	 * Generate texture coordinates using values from the outline.
	 */
	private void generateTextureCoordinates()
	{
		float width = textureRight - textureLeft;
		float height = textureTop - textureBottom;
		
		assert width > 0;
		assert height > 0;
		
		for(int loop0 = 0; loop0 < numVertices; ++loop0)
		{
			// do some normalization on the x and y coordinates from the outline
			texCoordBuffer[loop0 * 2] = (vertexBuffer[loop0 * 3] - textureLeft) / width;
			texCoordBuffer[loop0 * 2 + 1] = (vertexBuffer[loop0 * 3 + 1] - textureBottom) / height;
		}
	}
	
	/**
	 * Generate indices for the outline
	 */
	private void generateIndices()
	{
		int index = 0;
		
		indexBuffer = new byte[] {}; /** @TODO add lots of values here. */
	
		assert indexBuffer.length == numIndices;
	}
	
	private void generateSkeleton()
	{
		int index = 0;
		boneVertexBuffer[index++] = leroy.bones.get( "root" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "root" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "root" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "spine" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "spine" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "spine" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "neck" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "neck" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "neck" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "collar_left" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "collar_left" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "collar_left" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "upper_arm_left" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "upper_arm_left" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "upper_arm_left" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "lower_arm_left" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "lower_arm_left" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "lower_arm_left" ).restPose[2];
		
		boneVertexBuffer[index++] = leroy.bones.get( "collar_right" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "collar_right" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "collar_right" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "upper_arm_right" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "upper_arm_right" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "upper_arm_right" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "lower_arm_right" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "lower_arm_right" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "lower_arm_right" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "pelvic_left" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "pelvic_left" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "pelvic_left" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "upper_leg_left" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "upper_leg_left" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "upper_leg_left" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "lower_leg_left" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "lower_leg_left" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "lower_leg_left" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "pelvic_right" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "pelvic_right" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "pelvic_right" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "upper_leg_right" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "upper_leg_right" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "upper_leg_right" ).restPose[2];

		boneVertexBuffer[index++] = leroy.bones.get( "lower_leg_right" ).restPose[0];
		boneVertexBuffer[index++] = leroy.bones.get( "lower_leg_right" ).restPose[1];
		boneVertexBuffer[index++] = leroy.bones.get( "lower_leg_right" ).restPose[2];
		
		
		index = 0;
		boneIndexBuffer[ index++ ] = 0;
		boneIndexBuffer[ index++ ] = 1;
		
		boneIndexBuffer[ index++ ] = 1;
		boneIndexBuffer[ index++ ] = 2;
		
		boneIndexBuffer[ index++ ] = 2;
		boneIndexBuffer[ index++ ] = 3;
		
		boneIndexBuffer[ index++ ] = 2;
		boneIndexBuffer[ index++ ] = 4;
		
		boneIndexBuffer[ index++ ] = 4;
		boneIndexBuffer[ index++ ] = 5;
		
		boneIndexBuffer[ index++ ] = 5;
		boneIndexBuffer[ index++ ] = 6;
		
		boneIndexBuffer[ index++ ] = 2;
		boneIndexBuffer[ index++ ] = 7;
		
		boneIndexBuffer[ index++ ] = 7;
		boneIndexBuffer[ index++ ] = 8;

		boneIndexBuffer[ index++ ] = 8;
		boneIndexBuffer[ index++ ] = 9;
		
		boneIndexBuffer[ index++ ] = 0;
		boneIndexBuffer[ index++ ] = 10;
		
		boneIndexBuffer[ index++ ] = 10;
		boneIndexBuffer[ index++ ] = 11;
		
		boneIndexBuffer[ index++ ] = 11;
		boneIndexBuffer[ index++ ] = 12;
		
		boneIndexBuffer[ index++ ] = 0;
		boneIndexBuffer[ index++ ] = 13;
		
		boneIndexBuffer[ index++ ] = 13;
		boneIndexBuffer[ index++ ] = 14;
		
		boneIndexBuffer[ index++ ] = 14;
		boneIndexBuffer[ index++ ] = 15;

	}
	
	public void something()
	{
		
	}
	
	public void getSkeletonFrame( 	float frame, 
									float[] vertexBuffer,
									byte[] indexBuffer )
	{
		vertexBuffer = boneVertexBuffer;
		indexBuffer = boneIndexBuffer;
	}
	
	/**
	 * Get a mesh from the animation
	 * @param frame Which frame to get
	 * @param vertices[out] Where the vertices will be stored
	 * @param texCoords[out] Where the texture coordinates will be stored
	 * @param indices[out] Where the indices will be stored
	 */
	public void getFrame( 	float frame, 
							float [] vertices, 
							float [] texCoords, 
							byte [] indices)
	{
		assert frame>=0;
		assert frame<=leroy.numFrames;
		
		/** @todo could quite easily support a variable number of bones here */
		float [] tmpVertex0 = new float[3];
		float [] tmpVertex1 = new float[3];
		
		int intframe = (int)frame;
		
		for(int vertex = 0; vertex < numVertices; ++vertex)
		{		
			// @todo weight the second bone as well
			getTransformedVertex(tmpVertex0, 0, vertex, vertexWeights[vertex].bone[0], intframe);
			getTransformedVertex(tmpVertex1, 0, vertex, vertexWeights[vertex].bone[0], intframe);
			
			for(int loop0 = 0; loop0 < 3; ++loop0)
			{
				int vertexStart = 3 * vertex;
				vertices[vertexStart + loop0] = tmpVertex0[loop0] * vertexWeights[vertex].weight[0];
				vertices[vertexStart + loop0] += tmpVertex1[loop0] * vertexWeights[vertex].weight[1];
			}
		}	
		
		// always use indices from indexBuffer
		System.arraycopy(boneIndexBuffer,0,indices,0,boneIndexBuffer.length);
		
		// always use texture coordinates from texCoordBuffer
		System.arraycopy(texCoordBuffer,0,texCoords,0,texCoordBuffer.length);
	}
	
	/**
	 * Poo!
	 * 
	 * @param[out] output where to place the transformed vertices
	 * @param outputOffset where in output to start storing the output, not currently used 
	 * @param vertexNum which vertex to transform
	 * @param bone the bone to use for the transform
	 * @param frame which frame to use
	 */
	private void getTransformedVertex(float [] output, int outputOffset, int vertexNum, Leroy2.Bone bone, int frame)
	{
		// get the vertex position relative to the bone, this can be optimized by calculating it only once
		for(int loop0 = 0; loop0 < 3; ++loop0)
		{
			output[loop0] = boneVertexBuffer[vertexNum * 3 + loop0] - bone.restPose[loop0];
		}
		
		// transform using bone transformation
		Matrix.multiplyMV(output, 0, bone.frames, (frame << 4), output, 0);
		
		// add the bone rest pose
		for(int loop0 = 0; loop0 < 3; ++loop0)
		{
			output[loop0] += bone.restPose[loop0];
		}		
	}
}
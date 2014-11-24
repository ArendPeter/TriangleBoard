public class TriangleBoard{
	
	private final float width;
	private final float height;
	private final float triSideLength;
	private final float triHeight;
	private final float triOffsetY;
	private final float triOffsetX;
	private float gapHeight;
	private float gapWidth;
	private float speed;
	private int curTri;
	
	private Vertex[] triangles;
	private Vertex[] triCenter;
	
	private float[] triRot; 
	
	Matrix4f projection;
	
	public TriangleBoard(float width, float height, float triSideLength, float gapHeight,
		float speed){
		this.width = width;
		this.height = height;
		this.triSideLength = triSideLength;
		this.speed = speed;
		triHeight = (float)(Math.sqrt(3)*triSideLength)/2;
		this.gapHeight = gapHeight;
		gapWidth = (float)((2*gapHeight)/Math.sqrt(3));
		triOffsetY = (float)(Math.sqrt(3)*gapWidth/4f);
		triOffsetX = (float)((gapWidth/2f)+(gapHeight/(2*Math.sqrt(3))));
		generateTriangles();
	}
	
	private void generateTriangles(){
		int numRows = (int)Math.ceil(height/(triHeight+gapHeight));
		int numCols = (int)(Math.ceil(width/((triSideLength/2f)+triOffsetX)));
		triangles = new Vertex[(numRows * numCols * 3)];
		triCenter = new Vertex[numRows*numCols];
		triRot = new float[numRows * numCols];
		curTri = (int)(Math.random() * numRows * numCols);
		int index = 0;
		float xx = -width/2;
		float yy = height/2;
		boolean up = true;
		boolean startup = up;
		
		for(int i=0;i<numRows;i++){
			for(int j=0;j<numCols;j++){
				addTriangle(index, xx, yy + (gapHeight/2) - (up?triOffsetY:0), up);
				xx+=triOffsetX + triSideLength/2f;
				up = !up;
				index+=3;
			}
			up=!startup;
			startup = up;
			yy-=(triHeight + gapHeight);
			xx=-width/2;
		}
		
		//for(int i=0;i<triRot.length;i++){
			/*random
			triRot[i] = (float)(Math.random()*2*Math.PI);*/
			/*offset
			triRot[i]=triCenter[i].GetX()/4f + triCenter[i].GetY()/5f;*/
			/*curve*/
		//}
	}
	
	private void addTriangle(int index, float x, float y, boolean up){
		float z = (float)(Math.pow(x,2)/(-2f));
		triCenter[index/3] = new Vertex(x,y,z);
		triRot[index/3] = (float)Math.atan(-Math.pow(x,2));
		if(x<0)	triRot[index/3]=-triRot[index/3];
		
		if(up){
			triangles[index    ] = new Vertex( x, y, z);
			triangles[index + 1] = new Vertex( x - triSideLength/2f, y - triHeight, z);
			triangles[index + 2] = new Vertex( x + triSideLength/2f, y - triHeight, z);
		}else{
			triangles[index    ] = new Vertex( x - (triSideLength/2f), y, z);
			triangles[index + 1] = new Vertex( x + (triSideLength/2f), y, z);
			triangles[index + 2] = new Vertex( x, y - triHeight, z);
		}
	}
	
	public void UpdateAndRender(RenderContext target, float delta){
		if(projection==null)
		projection = new Matrix4f().InitPerspective((float)Math.toRadians(70.0f),
			(float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 1000.0f);
	
		Matrix4f translation = new Matrix4f().InitTranslation(0.0f,0.0f,5.0f);
		//Matrix4f transform = projection.Mul(translation);
		/*Matrix4f screenSpaceTransform = new Matrix4f().InitIdentity().Mul(transform).
			InitScreenSpaceTransform(target.GetWidth()/2, target.GetHeight()/2);*/
			
		target.Clear((byte)0x00);
	
		for(int i = 0; i < triangles.length; i+=3){
			Matrix4f thisRotation = new Matrix4f().InitRotation(0.0f, triRot[i/3], 0.0f);
			triRot[i/3]+=speed;
			
			Matrix4f thisTranslate1 = new Matrix4f().InitTranslation(triCenter[i/3].GetX(),
				triCenter[i/3].GetY(),triCenter[i/3].GetZ());
			
			Matrix4f thisTranslate2 = new Matrix4f().InitTranslation(-triCenter[i/3].GetX(),
				-triCenter[i/3].GetY(),-triCenter[i/3].GetZ());
			
			Matrix4f transform = projection.Mul(translation.Mul(
				thisTranslate1.Mul(thisRotation.Mul(thisTranslate2))));
			
			target.FillTriangle(
				triangles[i    ].Transform(transform),
				triangles[i + 1].Transform(transform),
				triangles[i + 2].Transform(transform)
			);
		}
	}

}
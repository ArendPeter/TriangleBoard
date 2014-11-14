public class TriangleBoard{
	
	private final float width;
	private final float height;
	private final float triSideLength;
	private final float triHeight;
	private final float triOffset;
	private float gapHeight;
	private float gapWidth;
	
	private Vertex[] triangles;
	
	Matrix4f projection;
	
	public TriangleBoard(float width, float height, float triSideLength, float gapHeight){
		this.width = width;
		this.height = height;
		this.triSideLength = triSideLength;
		triHeight = (float)(Math.sqrt(3)*triSideLength)/2;
		this.gapHeight = gapHeight;
		gapWidth = (float)((2*gapHeight)/Math.sqrt(3));
		triOffset = (float)(Math.sqrt(3)*gapWidth/4f);
		generateTriangles();
	}
	
	private void generateTriangles(){
		int numRows = (int)Math.ceil(height/(triHeight+gapHeight))-1;
		int numCols = (int)(Math.ceil(width/((triSideLength/2f)+gapWidth)));
		triangles = new Vertex[(numRows * numCols * 3)];
		
		int index = 0;
		float xx = -width/2;
		float yy = height/2;
		boolean up = true;
		boolean startup = up;
		for(int i=0;i<numRows;i++){
			for(int j=0;j<numCols;j++){
				addTriangle(index, xx, yy + (gapHeight/2) - (up?triOffset:0), up);
				if(up)
					xx+=gapWidth + triSideLength/2f;
				else
					xx+=gapWidth + triSideLength/2f;
				
				up = !up;
				index+=3;
			}
			up=!startup;
			startup = up;
			yy-=(triHeight + gapHeight + triOffset);
			xx=-width/2;
		}
	}
	
	private void addTriangle(int index, float x, float y, boolean up){
		if(up){
			triangles[index    ] = new Vertex( x, y, 0);
			triangles[index + 1] = new Vertex( x - triSideLength/2f, y - triHeight, 0);
			triangles[index + 2] = new Vertex( x + triSideLength/2f, y - triHeight, 0);
		}else{
			triangles[index    ] = new Vertex( x - (triSideLength/2f), y, 0);
			triangles[index + 1] = new Vertex( x + (triSideLength/2f), y, 0);
			triangles[index + 2] = new Vertex( x, y - triHeight, 0);
		}
	}
	
	public void UpdateAndRender(RenderContext target, float delta){
		if(projection==null)
		projection = new Matrix4f().InitPerspective((float)Math.toRadians(70.0f),
			(float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 1000.0f);
	
		Matrix4f translation = new Matrix4f().InitTranslation(0.0f,0.0f,3.0f);
		Matrix4f transform = projection.Mul(translation);
		Matrix4f screenSpaceTransform = new Matrix4f().InitIdentity().Mul(transform).
			InitScreenSpaceTransform(target.GetWidth()/2, target.GetHeight()/2);
			
		target.Clear((byte)0x00);
	
		for(int i = 0; i < triangles.length; i+=3){
			/*System.out.println("[ "+
				triangles[i    ].Transform(screen).GetY()+" "+
				triangles[i + 1].Transform(transform).GetY()+" "+
				triangles[i + 2].Transform(transform).GetY()+" ]"
			);*/
			target.FillTriangle(
				triangles[i    ].Transform(transform),
				triangles[i + 1].Transform(transform),
				triangles[i + 2].Transform(transform)
			);
		}
	}
}
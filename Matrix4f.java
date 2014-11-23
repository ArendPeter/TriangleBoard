public class Matrix4f{
	private float[][] m;
	
	public Matrix4f(){
		m=new float[4][4];
	}
	
	public Matrix4f InitIdentity(){
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				m[i][j]=(i==j)? 1 : 0;
		return this;
	}
	
	public Matrix4f InitScreenSpaceTransform(float halfWidth, float halfHeight){
		InitIdentity();
		m[0][0] = halfWidth;
		m[1][1] = -halfHeight;
		m[0][3] = halfWidth;
		m[1][3] = halfHeight;
		return this;
	}
	
	public Matrix4f InitTranslation(float x, float y, float z){
		for(int i = 0;i < 4; i++)
			for(int j = 0; j < 4; j++)
				if(j==3){
					switch(i){
						case 0: m[i][j] = x; break;
						case 1: m[i][j] = y; break;
						case 2: m[i][j] = z; break;
						case 3: m[i][j] = 1; break;
					}
				}else{
					m[i][j] = (i == j) ? 1 : 0;
				}
		return this;
	}
	
	public Matrix4f InitRotation(float x, float y, float z){
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
		
		rz.InitIdentity();
		rz.Set(0,0, (float)Math.cos(z));
		rz.Set(0,1,-(float)Math.sin(z));
		rz.Set(1,0, (float)Math.sin(z));
		rz.Set(1,1, (float)Math.cos(z));
		
		rx.InitIdentity();
		rx.Set(1,1, (float)Math.cos(x));
		rx.Set(1,2,-(float)Math.sin(x));
		rx.Set(2,1, (float)Math.sin(x));
		rx.Set(2,2, (float)Math.cos(x));
		
		ry.InitIdentity();
		ry.Set(0,0, (float)Math.cos(y));
		ry.Set(0,2,-(float)Math.sin(y));
		ry.Set(2,0, (float)Math.sin(y));
		ry.Set(2,2, (float)Math.cos(y));
		
		m = rz.Mul(ry.Mul(rx)).GetM();
		
		return this;
	}
	
	public Matrix4f InitRotation(float x, float y, float z, float angle)
	{
		float sin = (float)Math.sin(angle);
		float cos = (float)Math.cos(angle);

		m[0][0] = cos+x*x*(1-cos); m[0][1] = x*y*(1-cos)-z*sin; m[0][2] = x*z*(1-cos)+y*sin; m[0][3] = 0;
		m[1][0] = y*x*(1-cos)+z*sin; m[1][1] = cos+y*y*(1-cos);	m[1][2] = y*z*(1-cos)-x*sin; m[1][3] = 0;
		m[2][0] = z*x*(1-cos)-y*sin; m[2][1] = z*y*(1-cos)+x*sin; m[2][2] = cos+z*z*(1-cos); m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

		return this;
	}
	
	public Matrix4f InitPerspective(float fov, float aspectRatio, float zNear, float zFar)
	{
		float tanHalfFOV = (float)Math.tan(fov / 2);
		float zRange = zNear - zFar;

		m[0][0] = 1.0f / (tanHalfFOV * aspectRatio);	m[0][1] = 0;					m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;						m[1][1] = 1.0f / tanHalfFOV;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;						m[2][1] = 0;					m[2][2] = (-zNear -zFar)/zRange;	m[2][3] = 2 * zFar * zNear / zRange;
		m[3][0] = 0;						m[3][1] = 0;					m[3][2] = 1;	m[3][3] = 0;


		return this;
	}
	
	
	public Vector4f Transform(Vector4f r){
		return new Vector4f(
			m[0][0] * r.GetX() + m[0][1] * r.GetY() + m[0][2] * r.GetZ() + m[0][3] * r.GetW(),
			m[1][0] * r.GetX() + m[1][1] * r.GetY() + m[1][2] * r.GetZ() + m[1][3] * r.GetW(),
			m[2][0] * r.GetX() + m[2][1] * r.GetY() + m[2][2] * r.GetZ() + m[2][3] * r.GetW(),
			m[3][0] * r.GetX() + m[3][1] * r.GetY() + m[3][2] * r.GetZ() + m[3][3] * r.GetW()
		);
	}
	
	public Matrix4f Mul(Matrix4f r){
		Matrix4f res = new Matrix4f();
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++){
				float sum=0;
				for(int k=0;k<4;k++)
					sum+=m[i][k]*r.Get(k,j);
				res.Set(i,j,sum);
			}
		return res;
	}
	
	public float Get(int x, int y){
		return m[x][y];
	}
	
	public void Set(int x, int y, float value){
		m[x][y]=value;
	}
	
	public void SetM(float[][] m){
		this.m = m;
	}
	
	public float[][] GetM(){
		return m;
	}
	
	
}
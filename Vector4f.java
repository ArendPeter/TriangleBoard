public class Vector4f{
	private float x;
	private float y;
	private float z;
	private float w;
	
	public Vector4f(float x, float y, float z, float w){
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
	}
	
	public float length(){
		return (float)Math.sqrt(x*x+y*y+z*z+w*w);
	}
	
	public float dot(Vector4f r){
		return x * r.GetX() + y * r.GetY() + z * r.GetZ() + w * r.GetW();
	}
		
	public Vector4f normalize(){
		float l=length();
		x/=l;
		y/=l;
		z/=l;
		w/=l;
		return this;
	}
	
	public Vector4f rotate(float angle){
		return null;
	}
	
	public Vector4f add(Vector4f r){
		return new Vector4f(
			x + r.GetX(),
			y + r.GetY(),
			z + r.GetZ(),
			w + r.GetW()
		);
	}
	
	public Vector4f add(float r){
		return new Vector4f(
			x + r,
			y + r,
			z + r,
			w + r
		);
	}
	
	public Vector4f sub(float r){
		return new Vector4f(
			x - r,
			y - r,
			z - r,
			w - r
		);
	}
	
	public Vector4f mult(float r){
		return new Vector4f(
			x * r,
			y * r,
			z * r,
			w * r
		);
	}
	
	public Vector4f div(float r){
		return new Vector4f(
			x / r,
			y / r,
			z / r,
			w / r
		);
	}
	
	public String toString(){
		return "(" + x + " " + y + " " + z + " " + w + ")"; 
	}
	
	public float GetX()			{ return x; }
	public void SetX(float x)	{ this.x = x; }

	public float GetY()			{ return y; }
	public void SetY(float y)	{ this.y = y; }
	
	public float GetZ()			{ return z; }
	public void SetZ(float z)	{ this.z = z; }
	
	public float GetW()			{ return w; }
	public void SetW(float w)	{ this.w = w; }
}
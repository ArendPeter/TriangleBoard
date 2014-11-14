public class Main{
	public static void main(String args[]){
		int width = 800;
		int height = 600;
	
		Display display =new Display(width,height,"Software Rendering");
		RenderContext target = display.GetFrameBuffer();
		
		TriangleBoard tb = new TriangleBoard(5f, 4f, .5f, .05f);
		
		long previousTime = System.nanoTime();
		while(true){
			long currentTime = System.nanoTime();
			float delta=(float)((currentTime-previousTime)/1000000000.0);
			previousTime=currentTime;
			
			tb.UpdateAndRender(target,delta);
			
			display.SwapBuffers();
		}
	}
}
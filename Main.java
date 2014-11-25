public class Main{
	public static void main(String args[]){
		int width = 1920;
		int height = 1080;
	
		Display display =new Display(width,height,"Software Rendering");
		RenderContext target = display.GetFrameBuffer();
		
		TriangleBoard tb = new TriangleBoard(4.5f, 3f, .2f, .02f,0/*(float)(Math.PI/256f)*/);
		
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
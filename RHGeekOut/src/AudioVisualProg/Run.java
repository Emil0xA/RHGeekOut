package AudioVisualProg;

public class Run {
	public static void main(String[] args) {
		ReadImage imageRGB = new ReadImage("sample.jpg");
		imageRGB.imageArrayToRowVector(imageRGB.getImageArray());
	}
}

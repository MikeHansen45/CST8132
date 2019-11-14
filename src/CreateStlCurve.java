import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import curves.Curve;
import curves.ConeCurve;

public class CreateStlCurve {
	private List<List<Point3D>> list;

	public void createMatrix(Curve curve1)
	{
		//Curve coneCurve = new ConeCurve();
		double ss=curve1.getStepSize();
		double minX = curve1.getMinX();
		double minY = curve1.getMinY();
		double maxX = curve1.getMaxX();
		double maxY = curve1.getMaxY();

		int xSize = (int)((maxX-minX)/ss)+1;
		int ySize = (int)((maxY-minY)/ss)+1;
		list = new ArrayList<List<Point3D>>(ySize);

		double xValue, yValue=0;
		for(int y=0; y<ySize; y++)
		{
			List<Point3D> row = new ArrayList<Point3D>();
			list.add(row);
			xValue=0;
			for(int x=0; x<xSize; x++)
			{
				row.add(new Point3D(xValue,yValue,curve1.getZ(xValue+minX, yValue+minY)));
				xValue+=ss;
			}
			yValue+=ss;
		}
	}
	
	public Solid createSolid(String name)
	{
		Solid solid = new Solid(name);
		List<Point3D> bottomPlane = new ArrayList<Point3D>();// added to create a list of Point3D's for facet creation to make side and bottom planes
		List<Point3D> topPlane = new ArrayList<Point3D>();
		List<Point3D> leftPlane = new ArrayList<Point3D>();
		List<Point3D> rightPlane = new ArrayList<Point3D>();
		List<Point3D> basePlane = new ArrayList<Point3D>();
		int width=list.size()-1;
		int height=list.get(0).size()-1;
		//System.out.println(height);

		// Create curve
		for(int h=0; h<height; h++)
		{
			for(int w=0; w<width; w++)
			{
				// as viewed from above
				solid.addFacet(
						list.get(w).get(h),    // bottom left
						list.get(w+1).get(h),  // bottom right
						list.get(w+1).get(h+1),// top right
						list.get(w).get(h+1)); // top left
			}
		}
		
		for(int x=0; x<height; x++)
		{
			for(int y=0; y<width; y++)
			{

						bottomPlane.add(new Point3D(list.get(x).get(y).getX(),0,list.get(x).get(y).getZ()));//bottomEdge
						solid.addFacet(new Point3D(list.get(x).get(y).getX(),0,list.get(x).get(y).getZ()));
						solid.addFacet(new Point3D(list.get(x).get(y).getX(),0,list.get(x).get(y).getZ()));
						topPlane.add(new Point3D(list.get(x).get(y).getX(),10,list.get(x).get(y).getZ()));//leftPlane
						leftPlane.add(new Point3D(0,list.get(x).get(y).getX(),list.get(x).get(y).getZ()));
						rightPlane.add(new Point3D(10,list.get(x).get(y).getX(),list.get(x).get(y).getZ()));
						basePlane.add(new Point3D(list.get(x).get(y).getX(),list.get(x).get(y).getX(),0));

			}	
			
			
		}

		

		
		return solid;
	}

	public void printMatrix()
	{
		for(List<Point3D> row : list)
		{
			for(Point3D value : row)
				System.out.print("("+value+") ");
			System.out.println();
		}
	}
	
	public static void main(String[] arg)
	{
		Curve curveyBum = new ConeCurve();
		CreateStlCurve curve = new CreateStlCurve();
		curve.createMatrix(curveyBum);
		Solid solid = curve.createSolid("ConeCurve");
		
		String name = "c:\\temp\\ConeCurve.stl";
		Path path = Paths.get(name);
		
		try {
			solid.toTextFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}

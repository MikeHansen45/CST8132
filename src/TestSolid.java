import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class to test labs 1, 2, and 4.
 * @author      Howard Rosenblum rosenbh@algonquincollege.com
 * @version     1.0              
 */
public class TestSolid {

	/**
	 * @param args Commandline argument (not used)
	 */
	public static void main(String[] args) {
		// Define corner points
		Point3D p1 = new Point3D(0,0,0);  // bottom lower left
		Point3D p2 = new Point3D(100,0,0);  // bottom lower right
		Point3D p3 = new Point3D(50,86.6,0);  // bottom behind
		Point3D p4 = new Point3D(50,43.3,81.65);  // top height
		

		// Create facets
		Facet f1=new Facet(p1,p3,p2);  // bottom
		Facet f2=new Facet(p1,p2,p4);  // front
		Facet f3=new Facet(p2,p3,p4);  // right
		Facet f4=new Facet(p3,p1,p4);  // left

		// Create solid
		Solid solid = new Solid("Tetrahedron");
		solid.addFacet(f1);
		solid.addFacet(f2);
		solid.addFacet(f3);
		solid.addFacet(f4);
		
		String name = "c:\\temp\\TetraText.stl";
		Path path = Paths.get(name);
		
		try {
			solid.toTextFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}

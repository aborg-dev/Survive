package survive.common.utils;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class Direction {
	public Direction(int x, int y) {
		float length = (float) Math.sqrt(x * x + y * y);
		angleCos = x / length;
		angleSin = y / length;
	}

	public float angleCos, angleSin;
}

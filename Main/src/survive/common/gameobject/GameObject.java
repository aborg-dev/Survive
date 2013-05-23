package survive.common.gameobject;

/**
 * Created with IntelliJ IDEA.
 * User: iiotep9huy
 * Date: 5/24/13
 * Time: 2:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameObject {
    public GameObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
}

package googleApi;

/**
 * Created by bogdansandu on 2/25/18.
 */
public class Resource {
    //You can call method from the class name with className.Method if that method is defined with static
    public static String createPlaceData()
    {
        String resource = "/maps/api/place/add/json";
        return resource;
    }
}

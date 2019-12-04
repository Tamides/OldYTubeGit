package pl.tamides.ytube.helpers;

import pl.tamides.ytube.App;

public class ResourcesHelper {

    private static volatile ResourcesHelper instance = null;

    public static ResourcesHelper getInstance() {
        if (instance == null) {
            synchronized (ResourcesHelper.class) {
                if (instance == null) {
                    instance = new ResourcesHelper();
                }
            }
        }

        return instance;
    }

    public String getString(int stringRes) {
        return App.getAppContext().getString(stringRes);
    }

    public int getDimension(int dimensionRes) {
        return (int) App.getAppContext().getResources().getDimension(dimensionRes);
    }
}

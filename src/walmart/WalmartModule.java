package walmart;

import com.google.inject.Binder;
import com.google.inject.Module;

public class WalmartModule implements Module {

	@Override
	public void configure(Binder binder) {
    WalmartComponents components = new WalmartComponents();
    
    binder.bind(WalmartComponents.class).toInstance(components);
	}

}

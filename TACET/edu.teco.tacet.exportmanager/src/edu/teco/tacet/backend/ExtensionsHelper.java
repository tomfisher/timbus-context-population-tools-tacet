package edu.teco.tacet.backend;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import static edu.teco.tacet.util.collection.IterableAdditions.*;
import edu.teco.tacet.util.collection.CollectionAdditions;
import edu.teco.tacet.util.function.F1;

public class ExtensionsHelper {
    public static Iterable<String> getExtensionsGroupedByAttribute(final String attribute,
        String... extensionPoints) {

        Iterable<? extends Set<String>> attributeValues = map(new F1<String, Set<String>>() {
            @Override
            public Set<String> apply(String extPoint) {
                Set<String> values = new TreeSet<>();
                for (IConfigurationElement elem : getConfigElements(extPoint)) {
                    values.add(elem.getAttribute(attribute));
                }
                return values;
            }
        }, toIterable(extensionPoints));

        Collection<String> extensions = reduce(CollectionAdditions.<String> retainAll(), attributeValues);
        return extensions == null ? Collections.<String>emptySet() : extensions;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getClassFor(String attribute, String attributeValue, String extensionPoint) {
        for (IConfigurationElement elem : getConfigElements(extensionPoint)) {
            if (elem.getAttribute(attribute).equals(attributeValue)) {
                try {
                    return (T) elem.createExecutableExtension("class");
                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private static IConfigurationElement[] getConfigElements(String extensionPoint) {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        return registry.getConfigurationElementsFor(extensionPoint);
    }
}

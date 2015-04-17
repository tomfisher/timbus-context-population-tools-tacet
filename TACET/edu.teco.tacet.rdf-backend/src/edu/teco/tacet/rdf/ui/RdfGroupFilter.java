package edu.teco.tacet.rdf.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import edu.teco.tacet.meta.Datasource;
import edu.teco.tacet.meta.Group;
import edu.teco.tacet.meta.RdfDatasource;
import edu.teco.tacet.meta.Timeseries;
import edu.teco.tacet.meta.TimeseriesType;

/**
 * This filter passes groups that meet the following criteria:
 * - contains only tracks from exactly one RdfDatasource
 * - annotation tracks from in memory datasource are also allowed
 * - everything else does not get through this filter 
 */
public class RdfGroupFilter extends ViewerFilter {
    
    
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        // only touch groups
        if (!(element instanceof Group)) {
            return true;
        }
        
        Group group = (Group) element;
        // Check if all Timeseries are RdfTimeseries coming from the same RdfDatasource.
        Datasource seenRdfDatasource = null;
        for(Timeseries member : group.getTimeseries()) {
            Datasource source = member.getDatasource();
            if (source instanceof RdfDatasource) {
                if (seenRdfDatasource != null && seenRdfDatasource.getId() != source.getId()) {
                    // more than one RdfDatasource
                    return false;
                }
                seenRdfDatasource = source;
            } else {
                // Group contains at least one non-RDF timeseries.
                // This is OK if it's either in memory or an annotation timeseries.
                if (!(source.isIsInMemory() && member.getType() == TimeseriesType.ANNOTATION)) {
                    return false;
                }
            }
        }
        return seenRdfDatasource != null;
    }
    
}
